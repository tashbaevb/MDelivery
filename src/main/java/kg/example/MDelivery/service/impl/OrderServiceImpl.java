package kg.example.MDelivery.service.impl;

import kg.example.MDelivery.dto.OrderDTO;
import kg.example.MDelivery.entity.Order;
import kg.example.MDelivery.entity.OrderProduct;
import kg.example.MDelivery.entity.Product;
import kg.example.MDelivery.entity.TransportType;
import kg.example.MDelivery.entity.users.Delivery;
import kg.example.MDelivery.entity.users.User;
import kg.example.MDelivery.enums.OrderStatus;
import kg.example.MDelivery.mapper.GenericMapper;
import kg.example.MDelivery.repository.DeliveryRepository;
import kg.example.MDelivery.repository.OrderRepository;
import kg.example.MDelivery.repository.ProductRepository;
import kg.example.MDelivery.repository.TransportTypeRepository;
import kg.example.MDelivery.repository.UserRepository;
import kg.example.MDelivery.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final GenericMapper genericMapper;
    private final TransportTypeRepository transportTypeRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Product> products = orderDTO.getProductIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found")))
                .collect(Collectors.toList());

        BigDecimal totalPrice = products.stream()
                .map(product -> BigDecimal.valueOf(product.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        TransportType transportType = transportTypeRepository.findById(orderDTO.getTransportTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Transport type not found"));

        BigDecimal deliveryPrice = transportType.getBasePrice()
                .add(transportType.getPricePerKm().multiply(BigDecimal.valueOf(orderDTO.getDistanceInKm())));

        BigDecimal totalCost = totalPrice.add(deliveryPrice);

        if (user.getBalance() < totalCost.intValue()) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - totalCost.intValue());

        Order order = new Order();
        order.setToX(orderDTO.getToX());
        order.setToY(orderDTO.getToY());
        order.setDistanceInKm(orderDTO.getDistanceInKm());
        order.setTransportType(transportType);
        order.setOrderProducts(products.stream().map(product -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setOrder(order);
            return orderProduct;
        }).collect(Collectors.toList()));
        order.setTotalPrice(totalPrice);
        order.setDeliveryPrice(deliveryPrice);
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);

        OrderDTO responseDto = genericMapper.toDTO(savedOrder, OrderDTO.class);
        responseDto.setProductIds(products.stream()
                .map(Product::getId)
                .collect(Collectors.toList()));
        responseDto.setDeliveryId(null);

        return responseDto;
    }


    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    OrderDTO orderDTO = genericMapper.toDTO(order, OrderDTO.class);
                    orderDTO.setProductIds(order.getOrderProducts().stream()
                            .map(orderProduct -> orderProduct.getProduct().getId())
                            .collect(Collectors.toList()));
                    orderDTO.setDeliveryId(order.getDelivery() != null ? order.getDelivery().getId() : null);
                    return orderDTO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderDTO updateOrderStatus(Integer orderId, String newStatus, String userEmail) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        OrderStatus status = OrderStatus.valueOf(newStatus);

        if (status == OrderStatus.ACCEPTED) {
            Delivery delivery = deliveryRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
            order.setDelivery(delivery);
        }

        if (status == OrderStatus.CANCELLED) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.setBalance(user.getBalance() - 200);
            userRepository.save(user);
        }

        order.setOrderStatus(status);
        orderRepository.save(order);

        OrderDTO responseDto = genericMapper.toDTO(order, OrderDTO.class);
        responseDto.setProductIds(order.getOrderProducts().stream()
                .map(orderProduct -> orderProduct.getProduct().getId())
                .collect(Collectors.toList()));
        responseDto.setDeliveryId(order.getDelivery() != null ? order.getDelivery().getId() : null);
        return responseDto;
    }
}


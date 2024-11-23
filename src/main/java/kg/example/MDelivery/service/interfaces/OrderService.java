package kg.example.MDelivery.service.interfaces;

import kg.example.MDelivery.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO, String email);

    OrderDTO updateOrderStatus(Integer orderId, String newStatus, String email);

    List<OrderDTO> getAllOrders();
}

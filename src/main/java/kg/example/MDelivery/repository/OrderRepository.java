package kg.example.MDelivery.repository;

import kg.example.MDelivery.entity.Order;
import kg.example.MDelivery.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}

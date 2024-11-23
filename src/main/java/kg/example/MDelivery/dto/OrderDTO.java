package kg.example.MDelivery.dto;

import kg.example.MDelivery.entity.OrderProduct;
import kg.example.MDelivery.entity.TransportType;
import kg.example.MDelivery.entity.users.Delivery;
import kg.example.MDelivery.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {

    Integer id;

    Integer deliveryId;

    List<Integer> productIds;

    LocalDateTime orderTime;

    BigDecimal totalPrice, deliveryPrice;

    Double toX, toY;

    Double distanceInKm;

    Integer transportTypeId;

    OrderStatus orderStatus;

}

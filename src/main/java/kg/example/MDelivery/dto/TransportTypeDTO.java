package kg.example.MDelivery.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportTypeDTO {

    Integer id;

    String name;

    BigDecimal basePrice;

    BigDecimal pricePerKm;
}

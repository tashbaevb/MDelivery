package kg.example.MDelivery.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import kg.example.MDelivery.entity.OrderProduct;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {

    Integer id;

    String title;

    Double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderProduct> orderProducts;
}

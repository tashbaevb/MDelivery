package kg.example.MDelivery.service.interfaces;

import kg.example.MDelivery.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Integer id, ProductDTO productDTO);

    ProductDTO getProductById(Integer id);

    List<ProductDTO> getAllProducts();

    void deleteProduct(Integer id);
}

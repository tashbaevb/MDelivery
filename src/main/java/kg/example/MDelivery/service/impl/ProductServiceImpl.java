package kg.example.MDelivery.service.impl;

import kg.example.MDelivery.dto.ProductDTO;
import kg.example.MDelivery.entity.Product;
import kg.example.MDelivery.mapper.GenericMapper;
import kg.example.MDelivery.repository.ProductRepository;
import kg.example.MDelivery.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final GenericMapper genericMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = genericMapper.toEntity(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return genericMapper.toDTO(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
        existingProduct.setTitle(productDTO.getTitle());
        existingProduct.setPrice(productDTO.getPrice());
        Product updatedProduct = productRepository.save(existingProduct);
        return genericMapper.toDTO(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
        return genericMapper.toDTO(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> genericMapper.toDTO(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}

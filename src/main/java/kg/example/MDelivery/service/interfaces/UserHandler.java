package kg.example.MDelivery.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserHandler<T, E> {

    ResponseEntity<Map<String, String>> register(T dto);

    ResponseEntity<Map<String, String>> login(T dto);

    E convertToEntity(T dto);

    T convertToDTO(E entity);
}

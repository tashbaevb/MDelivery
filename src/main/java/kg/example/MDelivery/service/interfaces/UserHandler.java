package kg.example.MDelivery.service.interfaces;

import kg.example.MDelivery.dto.users.BaseResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface UserHandler<T, E> {

    ResponseEntity<Map<String, String>> register(T dto);

    ResponseEntity<Map<String, String>> login(T dto);

    ResponseEntity<List<? extends BaseResponseDTO>> getAll();

    E convertToEntity(T dto);

    ResponseEntity<? extends BaseResponseDTO> myProfile(Authentication authentication);

    T convertToDTO(E entity);
}

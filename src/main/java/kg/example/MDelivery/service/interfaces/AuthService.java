package kg.example.MDelivery.service.interfaces;

import kg.example.MDelivery.dto.DeliveryDTO;
import kg.example.MDelivery.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface AuthService {

    ResponseEntity<Map<String, String>> register(UserDTO userDTO);

    ResponseEntity<Map<String, String>> registerDelivery(DeliveryDTO deliveryDTO);

    ResponseEntity<Map<String, String>> login(UserDTO userDTO);

    ResponseEntity<Map<String, String>> login(DeliveryDTO deliveryDTO);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    String resetPassword(String email);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    String saveNewPassword(String resetToken, String newPassword);
}

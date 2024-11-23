package kg.example.MDelivery.service.impl;

import kg.example.MDelivery.dto.users.DeliveryDTO;
import kg.example.MDelivery.dto.users.UserDTO;
import kg.example.MDelivery.entity.users.Delivery;
import kg.example.MDelivery.entity.users.User;
import kg.example.MDelivery.enums.UserRole;
import kg.example.MDelivery.service.interfaces.AuthService;
import kg.example.MDelivery.service.HandlerFactory;
import kg.example.MDelivery.service.PasswordResetService;
import kg.example.MDelivery.service.interfaces.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final HandlerFactory handlerFactory;
    private final PasswordResetService passwordResetService;

    @Override
    public ResponseEntity<Map<String, String>> register(UserDTO userDTO) {
        UserHandler<UserDTO, User> handler = handlerFactory.getHandler(UserRole.USER_ROLE);
        return handler.register(userDTO);
    }

    @Override
    public ResponseEntity<Map<String, String>> registerDelivery(DeliveryDTO deliveryDTO) {
        UserHandler<DeliveryDTO, Delivery> handler = handlerFactory.getHandler(UserRole.DELIVERY_ROLE);
        return handler.register(deliveryDTO);
    }

    @Override
    public ResponseEntity<Map<String, String>> login(UserDTO userDTO) {
        UserHandler<UserDTO, User> handler = handlerFactory.getHandler(UserRole.USER_ROLE);
        return handler.login(userDTO);
    }

    @Override
    public ResponseEntity<Map<String, String>> login(DeliveryDTO deliveryDTO) {
        UserHandler<DeliveryDTO, Delivery> handler = handlerFactory.getHandler(UserRole.DELIVERY_ROLE);
        return handler.login(deliveryDTO);
    }

    @Override
    public String resetPassword(String email) {
        return passwordResetService.resetPassword(email);
    }

    @Override
    public String saveNewPassword(String resetToken, String newPassword) {
        return passwordResetService.saveNewPassword(resetToken, newPassword);
    }
}

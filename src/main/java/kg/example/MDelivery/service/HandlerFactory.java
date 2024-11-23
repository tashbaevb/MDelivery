package kg.example.MDelivery.service;

import kg.example.MDelivery.enums.UserRole;
import kg.example.MDelivery.service.impl.users.DeliveryHandlerImpl;
import kg.example.MDelivery.service.impl.users.UserHandlerImpl;
import kg.example.MDelivery.service.interfaces.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HandlerFactory {

    private final UserHandlerImpl userHandler;
    private final DeliveryHandlerImpl deliveryHandler;

    @SuppressWarnings("unchecked")
    public <T, E> UserHandler<T, E> getHandler(UserRole role) {
        return switch (role) {
            case USER_ROLE -> (UserHandler<T, E>) userHandler;
            case DELIVERY_ROLE -> (UserHandler<T, E>) deliveryHandler;
            default -> throw new IllegalArgumentException("Unsupported role: " + role);
        };
    }
}



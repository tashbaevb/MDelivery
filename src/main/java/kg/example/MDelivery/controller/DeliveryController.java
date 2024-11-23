package kg.example.MDelivery.controller;

import kg.example.MDelivery.dto.users.BaseResponseDTO;
import kg.example.MDelivery.service.impl.users.DeliveryHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryHandlerImpl deliveryHandler;

    @GetMapping("/profile")
    public ResponseEntity<? extends BaseResponseDTO> myProfile(Authentication authentication) {
        return deliveryHandler.myProfile(authentication);
    }
}

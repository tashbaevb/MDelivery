package kg.example.MDelivery.controller;

import kg.example.MDelivery.dto.DeliveryDTO;
import kg.example.MDelivery.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;

    @PostMapping("/delivery/register")
    public ResponseEntity<Map<String, String>> registerDelivery(DeliveryDTO deliveryDTO) {
        return authService.registerDelivery(deliveryDTO);
    }
}

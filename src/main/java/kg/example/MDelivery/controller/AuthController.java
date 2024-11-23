package kg.example.MDelivery.controller;

import kg.example.MDelivery.dto.users.DeliveryDTO;
import kg.example.MDelivery.dto.users.UserDTO;
import kg.example.MDelivery.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> auth(@RequestBody UserDTO userDto) {
        return authService.login(userDto);
    }

    @PostMapping("/auth/delivery")
    public ResponseEntity<Map<String, String>> auth(@RequestBody DeliveryDTO deliveryDTO) {
        return authService.login(deliveryDTO);
    }

    @GetMapping("/reset")
    public String resetPassword(@RequestParam String email) {
        return authService.resetPassword(email);
    }

    @PostMapping("/reset/{resetToken}")
    public String updatePassword(@PathVariable String resetToken, @RequestParam String password) {
        return authService.saveNewPassword(resetToken, password);
    }
}

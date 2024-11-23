package kg.example.MDelivery.controller;

import kg.example.MDelivery.dto.users.BaseResponseDTO;
import kg.example.MDelivery.service.impl.users.UserHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserHandlerImpl userHandler;

    @GetMapping("/role")
    public String getRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<? extends BaseResponseDTO> myProfile(Authentication authentication) {
        return userHandler.myProfile(authentication);
    }
}

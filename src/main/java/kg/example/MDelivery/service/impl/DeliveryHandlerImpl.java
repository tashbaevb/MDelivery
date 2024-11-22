package kg.example.MDelivery.service.impl;

import kg.example.MDelivery.dto.DeliveryDTO;
import kg.example.MDelivery.entity.users.Delivery;
import kg.example.MDelivery.enums.UserRole;
import kg.example.MDelivery.mapper.GenericMapper;
import kg.example.MDelivery.repository.DeliveryRepository;
import kg.example.MDelivery.security.JwtUtil;
import kg.example.MDelivery.service.interfaces.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryHandlerImpl implements UserHandler<DeliveryDTO, Delivery> {

    private final DeliveryRepository deliveryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final GenericMapper genericMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Map<String, String>> register(DeliveryDTO deliveryDTO) {
        if (deliveryRepository.findByEmail(deliveryDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Delivery already exists"));
        }

        Delivery delivery = convertToEntity(deliveryDTO);
        delivery.setPassword(passwordEncoder.encode(delivery.getPassword()));
        delivery.setUserRole(UserRole.DELIVERY_ROLE);
        deliveryRepository.save(delivery);

        return generateTokens(delivery.getEmail());
    }

    @Override
    public ResponseEntity<Map<String, String>> login(DeliveryDTO deliveryDTO) {
        authenticate(deliveryDTO.getEmail(), deliveryDTO.getPassword());
        return generateTokens(deliveryDTO.getEmail());
    }

    @Override
    public Delivery convertToEntity(DeliveryDTO dto) {
        return genericMapper.toEntity(dto, Delivery.class);
    }

    @Override
    public DeliveryDTO convertToDTO(Delivery entity) {
        return genericMapper.toDTO(entity, DeliveryDTO.class);
    }

    private void authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(token);
    }

    private ResponseEntity<Map<String, String>> generateTokens(String email) {
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("access_token", token));
    }
}

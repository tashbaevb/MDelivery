package kg.example.MDelivery.service.impl.users;

import kg.example.MDelivery.dto.users.BaseResponseDTO;
import kg.example.MDelivery.dto.users.UserDTO;
import kg.example.MDelivery.dto.users.UserResponseDTO;
import kg.example.MDelivery.entity.users.User;
import kg.example.MDelivery.enums.UserRole;
import kg.example.MDelivery.mapper.GenericMapper;
import kg.example.MDelivery.repository.users.UserRepository;
import kg.example.MDelivery.security.JwtUtil;
import kg.example.MDelivery.service.interfaces.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler<UserDTO, User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final GenericMapper genericMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Map<String, String>> register(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
        }

        User user = convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.USER_ROLE);
        userRepository.save(user);

        return generateTokens(user.getEmail());
    }

    @Override
    public ResponseEntity<Map<String, String>> login(UserDTO userDTO) {
        authenticate(userDTO.getEmail(), userDTO.getPassword());
        return generateTokens(userDTO.getEmail());
    }

    @Override
    public ResponseEntity<List<? extends BaseResponseDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(user -> genericMapper.toDTO(user, UserResponseDTO.class))
                .toList();

        return ResponseEntity.ok(userResponseDTOs);
    }

    @Override
    public ResponseEntity<UserResponseDTO> myProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserResponseDTO userResponseDTO = genericMapper.toDTO(user, UserResponseDTO.class);
        return ResponseEntity.ok(userResponseDTO);
    }

    @Override
    public User convertToEntity(UserDTO dto) {
        return genericMapper.toEntity(dto, User.class);
    }

    @Override
    public UserDTO convertToDTO(User entity) {
        return genericMapper.toDTO(entity, UserDTO.class);
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


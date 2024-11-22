package kg.example.MDelivery.service.impl;

import kg.example.MDelivery.dto.UserDTO;
import kg.example.MDelivery.entity.User;
import kg.example.MDelivery.enums.UserRole;
import kg.example.MDelivery.mapper.MapperFactory;
import kg.example.MDelivery.mapper.UserMapper;
import kg.example.MDelivery.repository.UserRepository;
import kg.example.MDelivery.security.JwtUtil;
import kg.example.MDelivery.service.AuthService;
import kg.example.MDelivery.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final MapperFactory mapperFactory;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Value("${resetUrl}")
    private String resetUrl;

    @Override
    public ResponseEntity<Map<String, String>> register(UserDTO userDTO) {
        UserMapper userMapper = mapperFactory.createUserMapper(modelMapper);
        User user = userMapper.convertToEntity(userDTO);

        if (isPresentEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.
                    singletonMap("error", "User with email " + userDTO.getEmail() + "already exist."));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.USER_ROLE);
        userRepository.save(user);

        return generateAndGetTokens(userDTO);
    }

    @Override
    public ResponseEntity<Map<String, String>> login(UserDTO userDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                return generateAndGetTokens(userDTO);
            } else {
                throw new UsernameNotFoundException("Incorrect email or password.");
            }
        } catch (AuthenticationException ae) {
            throw new UsernameNotFoundException("Incorrect email or password");
        }
    }

    @Override
    public boolean isPresentEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String resetPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return "Email not found";
        }

        String resetToken = UUID.randomUUID().toString();
        user.get().setResetToken(resetToken);
        user.get().setResetTokenExpireTime(LocalDateTime.now().plusMinutes(60));
        userRepository.save(user.get());

        String resetUrlFin = resetUrl + resetToken;
        String emailText = "Hello! " + "\n\nPlease follow this link to reset your password: " + resetUrlFin;

        emailService.sendMessage(email, "Passwort forgot", emailText);
        return "A password reset link has been sent to your e-mail address " + email;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String saveNewPassword(String resetToken, String newPassword) {
        User user = userRepository.findByResetToken(resetToken);
        if (user == null || user.getResetTokenExpireTime().isBefore(LocalDateTime.now()))
            return "The link to reset the password is outdated";

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpireTime(null);
        userRepository.save(user);
        return "The password has been successfully changed. ";
    }

    private ResponseEntity<Map<String, String>> generateAndGetTokens(UserDTO userDTO) {
        String accessToken = jwtUtil.generateToken(userDTO.getEmail());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        return ResponseEntity.ok(tokens);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("User with email: " + email + " not found"));
    }
}

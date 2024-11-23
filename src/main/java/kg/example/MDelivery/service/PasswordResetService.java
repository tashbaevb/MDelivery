package kg.example.MDelivery.service;

import kg.example.MDelivery.entity.users.User;
import kg.example.MDelivery.repository.users.UserRepository;
import kg.example.MDelivery.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Value("${resetUrl}")
    private String resetUrl;

    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email not found"));
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpireTime(LocalDateTime.now().plusMinutes(60));
        userRepository.save(user);

        String resetUrlFin = resetUrl + resetToken;
        String emailText = "Hello! " + "\n\nPlease follow this link to reset your password: " + resetUrlFin;

        emailService.sendMessage(email, resetUrlFin, emailText);
        return "A password reset link has been sent to your e-mail address " + email;
    }

    public String saveNewPassword(String resetToken, String newPassword) {
        User user = userRepository.findByResetToken(resetToken);
        if (user == null || user.getResetTokenExpireTime().isBefore(LocalDateTime.now())) {
            return "Invalid or expired reset token";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpireTime(null);
        userRepository.save(user);
        return "Password updated successfully";
    }
}

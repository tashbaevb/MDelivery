package kg.example.MDelivery.dto.users;

import kg.example.MDelivery.enums.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseUserDTO {

    Integer id;

    String email, password;

    String name, surname;

    String phoneNumber;

    UserRole userRole;

    String resetToken;

    LocalDateTime resetTokenExpireTime;
}

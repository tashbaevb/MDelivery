package kg.example.MDelivery.security;

import kg.example.MDelivery.entity.users.BaseUser;
import kg.example.MDelivery.entity.users.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
public class DetailsUser implements UserDetails {

    private final BaseUser baseUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = baseUser.getUserRole() != null ? baseUser.getUserRole().name() : "USER_ROLE";
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return baseUser.getPassword();
    }

    @Override
    public String getUsername() {
        return baseUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

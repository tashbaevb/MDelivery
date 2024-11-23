package kg.example.MDelivery.security;

import kg.example.MDelivery.entity.users.Delivery;
import kg.example.MDelivery.entity.users.User;
import kg.example.MDelivery.repository.DeliveryRepository;
import kg.example.MDelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DetailsUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return new DetailsUser(user);
        }

        Delivery delivery = deliveryRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User or Delivery not found with email: " + email));

        return new DetailsUser(delivery);
    }
}

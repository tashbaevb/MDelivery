package kg.example.MDelivery.repository;

import kg.example.MDelivery.entity.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportTypeRepository extends JpaRepository<TransportType, Integer> {

    Optional<TransportType> findByName(String name);
}

package kg.example.MDelivery.mapper;

import kg.example.MDelivery.dto.DeliveryDTO;
import kg.example.MDelivery.entity.users.Delivery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryMapper {

    private final ModelMapper modelMapper;

    public Delivery convertToEntity(DeliveryDTO deliveryDTO) {
        return modelMapper.map(deliveryDTO, Delivery.class);
    }

    public DeliveryDTO convertToDTO(Delivery delivery) {
        return modelMapper.map(delivery, DeliveryDTO.class);
    }
}

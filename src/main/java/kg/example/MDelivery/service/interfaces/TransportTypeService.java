package kg.example.MDelivery.service.interfaces;

import kg.example.MDelivery.dto.TransportTypeDTO;

import java.util.List;

public interface TransportTypeService {

    TransportTypeDTO createTransportType(TransportTypeDTO transportTypeDTO);

    List<TransportTypeDTO> getAllTransportTypes();
}

package kg.example.MDelivery.service.impl;

import kg.example.MDelivery.dto.TransportTypeDTO;
import kg.example.MDelivery.entity.TransportType;
import kg.example.MDelivery.mapper.GenericMapper;
import kg.example.MDelivery.repository.TransportTypeRepository;
import kg.example.MDelivery.service.interfaces.TransportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportTypeServiceImpl implements TransportTypeService {

    private final TransportTypeRepository transportTypeRepository;
    private final GenericMapper genericMapper;

    @Override
    public TransportTypeDTO createTransportType(TransportTypeDTO transportTypeDTO) {
        TransportType transportType = genericMapper.toEntity(transportTypeDTO, TransportType.class);
        TransportType savedTransportType = transportTypeRepository.save(transportType);
        return genericMapper.toDTO(savedTransportType, TransportTypeDTO.class);
    }

    @Override
    public List<TransportTypeDTO> getAllTransportTypes() {
        List<TransportType> transportTypes = transportTypeRepository.findAll();
        return transportTypes.stream()
                .map(transportType -> genericMapper.toDTO(transportType, TransportTypeDTO.class))
                .collect(Collectors.toList());
    }
}

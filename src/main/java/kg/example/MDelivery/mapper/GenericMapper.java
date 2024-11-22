package kg.example.MDelivery.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenericMapper {

    private final ModelMapper modelMapper;

    public <D, E> E toEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <E, D> D toDTO(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}

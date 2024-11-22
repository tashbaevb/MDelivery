package kg.example.MDelivery.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MapperFactoryImpl implements MapperFactory {

    @Override
    public UserMapper createUserMapper(ModelMapper modelMapper) {
        return new UserMapper(modelMapper);
    }
}

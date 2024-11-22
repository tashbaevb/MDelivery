package kg.example.MDelivery.mapper;

import org.modelmapper.ModelMapper;

public interface MapperFactory {

    UserMapper createUserMapper(ModelMapper modelMapper);
}

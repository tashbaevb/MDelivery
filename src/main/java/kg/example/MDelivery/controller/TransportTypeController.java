package kg.example.MDelivery.controller;

import kg.example.MDelivery.dto.TransportTypeDTO;
import kg.example.MDelivery.service.interfaces.TransportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transportType")
public class TransportTypeController {

    private final TransportTypeService transportTypeService;

    @GetMapping("/getAll")
    public List<TransportTypeDTO> getAllTransportTypes() {
        return transportTypeService.getAllTransportTypes();
    }
}

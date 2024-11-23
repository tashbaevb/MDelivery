package kg.example.MDelivery.controller;

import kg.example.MDelivery.dto.DeliveryDTO;
import kg.example.MDelivery.dto.TransportTypeDTO;
import kg.example.MDelivery.service.interfaces.AuthService;
import kg.example.MDelivery.service.interfaces.TransportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final TransportTypeService transportTypeService;

    @PostMapping("/delivery/register")
    public ResponseEntity<Map<String, String>> registerDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        return authService.registerDelivery(deliveryDTO);
    }

    @PostMapping("/transportType/create")
    public TransportTypeDTO createTransportType(@RequestBody TransportTypeDTO transportTypeDTO) {
        return transportTypeService.createTransportType(transportTypeDTO);
    }

    @GetMapping("/transportType/getAll")
    public List<TransportTypeDTO> getAllTransportTypes() {
        return transportTypeService.getAllTransportTypes();
    }
}

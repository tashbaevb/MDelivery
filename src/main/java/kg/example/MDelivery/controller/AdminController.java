package kg.example.MDelivery.controller;

import kg.example.MDelivery.dto.users.BaseResponseDTO;
import kg.example.MDelivery.dto.users.DeliveryDTO;
import kg.example.MDelivery.dto.TransportTypeDTO;
import kg.example.MDelivery.service.impl.users.DeliveryHandlerImpl;
import kg.example.MDelivery.service.impl.users.UserHandlerImpl;
import kg.example.MDelivery.service.interfaces.AnalyticsService;
import kg.example.MDelivery.service.interfaces.AuthService;
import kg.example.MDelivery.service.interfaces.TransportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final TransportTypeService transportTypeService;
    private final DeliveryHandlerImpl deliveryHandler;
    private final UserHandlerImpl userHandler;
    private final AnalyticsService analyticsService;

    @PostMapping("/delivery/register")
    public ResponseEntity<Map<String, String>> registerDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        return authService.registerDelivery(deliveryDTO);
    }

    @PostMapping("/transportType/create")
    public TransportTypeDTO createTransportType(@RequestBody TransportTypeDTO transportTypeDTO) {
        return transportTypeService.createTransportType(transportTypeDTO);
    }

    @GetMapping("/getAll/users")
    public ResponseEntity<List<? extends BaseResponseDTO>> getAllUsers() {
        return userHandler.getAll();
    }

    @GetMapping("/getAll/delivers")
    public ResponseEntity<List<? extends BaseResponseDTO>> getAllDelivers() {
        return deliveryHandler.getAll();
    }

    @GetMapping("/analytics/orders")
    public ResponseEntity<byte[]> getOrdersChart(@RequestParam String sort) throws IOException {
        byte[] chartImage = analyticsService.generateOrdersChart(sort);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(chartImage, headers, HttpStatus.OK);
    }
}

package kg.example.MDelivery.service.interfaces;

import java.io.IOException;

public interface AnalyticsService {

    byte[] generateOrdersChart(String sort) throws IOException;
}

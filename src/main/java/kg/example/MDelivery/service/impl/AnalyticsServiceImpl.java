package kg.example.MDelivery.service.impl;

import kg.example.MDelivery.entity.Order;
import kg.example.MDelivery.repository.OrderRepository;
import kg.example.MDelivery.service.interfaces.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository orderRepository;

    @Override
    public byte[] generateOrdersChart(String sort) throws IOException {
        List<Order> orders;
        LocalDateTime now = LocalDateTime.now();

        if ("month".equalsIgnoreCase(sort)) {
            orders = orderRepository.findAllByOrderTimeBetween(now.minusMonths(1), now);
        } else if ("week".equalsIgnoreCase(sort)) {
            orders = orderRepository.findAllByOrderTimeBetween(now.minusWeeks(1), now);
        } else if ("day".equalsIgnoreCase(sort)) {
            orders = orderRepository.findAllByOrderTimeBetween(now.minusDays(1), now);
        } else {
            orders = orderRepository.findAll();
        }

        // Группируем заказы по дате
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Order order : orders) {
            LocalDateTime orderTime = order.getOrderTime();
            BigDecimal totalPrice = order.getTotalPrice();
            dataset.addValue(totalPrice, "Orders", orderTime.toLocalDate());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Orders Analytics",
                "Date",
                "Total Amount",
                dataset
        );

        BufferedImage chartImage = chart.createBufferedImage(800, 600);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(baos, chartImage);

        return baos.toByteArray();
    }
}

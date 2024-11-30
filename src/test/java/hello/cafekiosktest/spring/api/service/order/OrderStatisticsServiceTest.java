package hello.cafekiosktest.spring.api.service.order;

import hello.cafekiosktest.spring.client.mail.MailSendClient;
import hello.cafekiosktest.spring.domain.history.mail.MailSendHistory;
import hello.cafekiosktest.spring.domain.history.mail.MailSendHistoryRepository;
import hello.cafekiosktest.spring.domain.order.Order;
import hello.cafekiosktest.spring.domain.order.OrderRepository;
import hello.cafekiosktest.spring.domain.order.OrderStatus;
import hello.cafekiosktest.spring.domain.product.Product;
import hello.cafekiosktest.spring.domain.product.ProductRepository;
import hello.cafekiosktest.spring.domain.product.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static hello.cafekiosktest.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockitoBean
    private MailSendClient mailSendClient;

    @Test
    void sendOrderStatisticsMail() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2024, 11, 30, 01, 13);
        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", HANDMADE, 2000);
        Product product3 = createProduct("002", HANDMADE, 3000);
        List<Product> products = productRepository.saveAll(List.of(product1, product2, product3));
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 11, 29, 23, 59));
        Order order2 = createPaymentCompletedOrder(products, now);
        Order order3 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 11, 30, 23, 59));
        Order order4 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 12, 1, 0, 0));

        // stubbing
        when(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        //when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 11, 30), "test@test.com");

        //then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 18000원 입니다.");
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        Order order1 = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();
        return orderRepository.save(order1);
    }

    private Product createProduct(String productNumber, ProductType type, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .price(price)
                .build();
    }
}
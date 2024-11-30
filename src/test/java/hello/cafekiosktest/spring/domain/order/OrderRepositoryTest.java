package hello.cafekiosktest.spring.domain.order;

import hello.cafekiosktest.spring.domain.product.Product;
import hello.cafekiosktest.spring.domain.product.ProductRepository;
import hello.cafekiosktest.spring.domain.product.ProductSellingStatus;
import hello.cafekiosktest.spring.domain.product.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hello.cafekiosktest.spring.domain.product.ProductSellingStatus.HOLD;
import static hello.cafekiosktest.spring.domain.product.ProductSellingStatus.SELLING;
import static hello.cafekiosktest.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findOrdersBy() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = now.minusDays(1);
        LocalDateTime endDateTime = now.plusDays(1);

        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 5000);

        List<Product> products = productRepository.saveAll(List.of(product1, product2));

        //when
        Order order1 = Order.create(products, now.minusHours(2));
        Order order2 = Order.create(products, now.plusHours(2));
        order1.changeOrderStatus(OrderStatus.PAYMENT_COMPLETED);
        order2.changeOrderStatus(OrderStatus.PAYMENT_COMPLETED);

        orderRepository.saveAll(List.of(order1, order2));

        List<Order> completedOrders = orderRepository.findOrdersBy(startDateTime, endDateTime, OrderStatus.PAYMENT_COMPLETED);

        //then
        assertThat(completedOrders).isNotEmpty();
        assertThat(completedOrders).hasSize(2);
        assertThat(completedOrders.get(0).getOrderStatus()).isEqualTo(OrderStatus.PAYMENT_COMPLETED);
        assertThat(completedOrders.get(0).getTotalPrice()).isEqualTo(9000);
        assertThat(completedOrders.get(0).getRegisteredDateTime()).isEqualTo(order1.getRegisteredDateTime());
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus status, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(status)
                .name(name)
                .price(price)
                .build();
    }
}
package hello.cafekiosktest.spring.domain.order;

import hello.cafekiosktest.spring.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static hello.cafekiosktest.spring.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderTest {

    @Test
    void calculateTotalPrice() throws Exception {
        //given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000));

        //when
        Order order = Order.create(products, LocalDateTime.now());

        //then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }
    @Test
    void init() throws Exception {
        //given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000));

        //when
        Order order = Order.create(products, LocalDateTime.now());

        //then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);

    }

    @Test
    void dateTime() throws Exception {
        //given
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000));

        //when
        Order order = Order.create(products, dateTime);

        //then
        assertThat(order.getRegisteredDateTime()).isEqualTo(dateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();

    }
}
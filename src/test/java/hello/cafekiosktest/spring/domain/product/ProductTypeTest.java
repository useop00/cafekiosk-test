package hello.cafekiosktest.spring.domain.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @Test
    void containsStockType() throws Exception {
        //given
        ProductType givenType = ProductType.HANDMADE;


        //when
        boolean result = ProductType.containsStockType(givenType);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void containsStockType2() throws Exception {
        //given
        ProductType givenType = ProductType.BAKERY;


        //when
        boolean result = ProductType.containsStockType(givenType);

        //then
        assertThat(result).isTrue();
    }

}
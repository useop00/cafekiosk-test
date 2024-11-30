 package hello.cafekiosktest.spring.api.service.product.request;

 import hello.cafekiosktest.spring.domain.product.Product;
 import hello.cafekiosktest.spring.domain.product.ProductSellingStatus;
 import hello.cafekiosktest.spring.domain.product.ProductType;
 import lombok.Builder;
 import lombok.Getter;
 import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}

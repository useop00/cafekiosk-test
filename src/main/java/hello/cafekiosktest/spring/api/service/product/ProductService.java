package hello.cafekiosktest.spring.api.service.product;

import hello.cafekiosktest.spring.api.controller.product.dto.response.ProductResponse;
import hello.cafekiosktest.spring.api.service.product.request.ProductCreateServiceRequest;
import hello.cafekiosktest.spring.domain.product.Product;
import hello.cafekiosktest.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hello.cafekiosktest.spring.domain.product.ProductSellingStatus.forDisplay;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {

        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);

    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProduct();
        if (latestProductNumber == null) {
            return "001";
        }
        Integer latestProductNumberInt = Integer.valueOf(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }

}

package hello.cafekiosktest.spring.api.controller.order;

import hello.cafekiosktest.spring.api.ApiResponse;
import hello.cafekiosktest.spring.api.controller.order.request.OrderCreateRequest;
import hello.cafekiosktest.spring.api.service.order.response.OrderResponse;
import hello.cafekiosktest.spring.api.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Validated @RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }
}

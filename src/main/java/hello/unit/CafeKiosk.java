package hello.unit;

import hello.unit.beverage.Beverage;
import hello.unit.order.Order;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_ITEM = LocalTime.of(10, 0);
    public static final LocalTime SHOP_CLOSE_ITEM = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문 가능");
        }

        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }


    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {
        return beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    public Order createOrder() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();
        if (currentTime.isBefore(SHOP_OPEN_ITEM) || currentTime.isAfter(SHOP_CLOSE_ITEM)) {
            throw new IllegalArgumentException("카페 영업시간이 아닙니다.");
        }

        return new Order(currentDateTime, beverages);
    }

    public Order createOrder(LocalDateTime currentDateTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();
        if (currentTime.isBefore(SHOP_OPEN_ITEM) || currentTime.isAfter(SHOP_CLOSE_ITEM)) {
            throw new IllegalArgumentException("카페 영업시간이 아닙니다.");
        }

        return new Order(currentDateTime, beverages);
    }
}

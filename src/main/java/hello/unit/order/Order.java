package hello.unit.order;

import hello.unit.beverage.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Order {

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;
}



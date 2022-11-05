package pl.edu.pg.eti.kask.restaurants.dish.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateDishRequest {
    private String name;

    private float price;

    private int weight;

    public static Function<CreateDishRequest, Dish> dtoToEntityMapper() {
        return request -> Dish.builder()
                .name(request.getName())
                .price(request.getPrice())
                .weight(request.getWeight())
                .build();
    }

}

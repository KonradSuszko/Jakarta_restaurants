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


import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateDishRequest {
    private String name;

    private float price;

    private int weight;

    public static BiFunction<Dish, UpdateDishRequest, Dish> dtoToEntityUpdater() {
        return (dish, request) -> {
            dish.setName(request.getName());
            dish.setPrice(request.getPrice());
            dish.setWeight(request.getWeight());
            return dish;
        };
    }
}

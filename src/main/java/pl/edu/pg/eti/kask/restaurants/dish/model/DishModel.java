package pl.edu.pg.eti.kask.restaurants.dish.model;


import lombok.*;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class DishModel {
    private Long id;

    private String name;

    private float price;

    private int weight;

    private String restaurantName;

    public static Function<Dish, DishModel> entityToModelMapper() {
        return dish -> DishModel.builder()
                .name(dish.getName())
                .id(dish.getId())
                .price(dish.getPrice())
                .weight(dish.getWeight())
                .restaurantName(dish.getRestaurant().getName())
                .build();
    }
}

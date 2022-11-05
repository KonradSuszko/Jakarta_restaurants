package pl.edu.pg.eti.kask.restaurants.dish.model;

import lombok.*;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;

import java.util.function.BiFunction;
import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DishEditModel {
    private String name;

    private float price;

    private int weight;

    @Getter
    private RestaurantModel restaurantModel;

    public static Function<Dish, DishEditModel> entityToModelMapper() {
        return dish -> DishEditModel.builder()
                .name(dish.getName())
                .price(dish.getPrice())
                .weight(dish.getWeight())
                .restaurantModel(RestaurantModel.entityToModelMapper().apply(dish.getRestaurant()))
                .build();
    }

    public static BiFunction<Dish, DishEditModel, Dish> modelToEntityUpdater(Function<RestaurantModel, Restaurant> restaurantMapper) {
        return (dish, request) -> {
            dish.setName(request.getName());
            dish.setPrice(request.getPrice());
            dish.setWeight(request.getWeight());
            dish.setRestaurant(restaurantMapper.apply(request.getRestaurantModel()));
            return dish;
        };
    }
}

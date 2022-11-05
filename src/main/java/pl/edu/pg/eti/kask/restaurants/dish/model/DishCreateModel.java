package pl.edu.pg.eti.kask.restaurants.dish.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;

import java.util.function.Function;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishCreateModel {
    private Long id;

    private String name;

    private float price;

    private int weight;

    private RestaurantModel restaurantModel;

    public static Function<Dish, DishCreateModel> entityToModelMapper() {
        return dish -> DishCreateModel.builder()
                .id(dish.getId())
                .weight(dish.getWeight())
                .name(dish.getName())
                .price(dish.getPrice())
                .restaurantModel(RestaurantModel.entityToModelMapper().apply(dish.getRestaurant()))
                .build();
    }

    public static Function<DishCreateModel, Dish> modelToEntityMapper(
            Function<RestaurantModel, Restaurant> restaurantMapper
    ) {
        return dish -> Dish.builder()
                .id(dish.getId())
                .name(dish.getName())
                .price(dish.getPrice())
                .weight(dish.getWeight())
                .restaurant(restaurantMapper.apply(dish.getRestaurantModel()))
                .build();
    }
}

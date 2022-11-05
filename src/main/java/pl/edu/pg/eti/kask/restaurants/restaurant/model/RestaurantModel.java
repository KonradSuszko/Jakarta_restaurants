package pl.edu.pg.eti.kask.restaurants.restaurant.model;

import lombok.*;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishModel;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishesModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class RestaurantModel implements Serializable {
    private String name;

    private int establishmentYear;

    private double rating;

    public static Function<Restaurant, RestaurantModel> entityToModelMapper() {
        return restaurant -> RestaurantModel.builder()
                .name(restaurant.getName())
                .establishmentYear(restaurant.getEstablishmentYear())
                //.menu(DishesModel.entityToModelMapper().apply(restaurant.getMenu()))
                .rating(restaurant.getRating())
                        .build();
    }
}

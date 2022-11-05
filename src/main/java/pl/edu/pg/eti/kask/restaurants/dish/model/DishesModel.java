package pl.edu.pg.eti.kask.restaurants.dish.model;

import lombok.*;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantsModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class DishesModel implements Serializable {

    @Singular
    private List<Dish> dishes;

    public static Function<Collection<Dish>, DishesModel> entityToModelMapper() {
        return dishes -> {
            DishesModel.DishesModelBuilder model = DishesModel.builder();
            dishes.stream()
                    .map(dish -> Dish.builder()
                            .id(dish.getId())
                            .name(dish.getName())
                            .price(dish.getPrice())
                            .restaurant(dish.getRestaurant())
                            .build())
                    .forEach(model::dish);
            return model.build();
        };
    }
}

package pl.edu.pg.eti.kask.restaurants.restaurant.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RestaurantModel {
    private String name;

    private List<DishModel> menu;

    private int establishmentYear;

    public static Function<Restaurant, RestaurantModel> entityToModelMapper() {
        return restaurant -> RestaurantModel.builder()
                .name(restaurant.getName())
                .establishmentYear(restaurant.getEstablishmentYear())
                .menu(restaurant.getMenu().stream().map(obj -> DishModel.entityToModelMapper().apply(obj)).collect(Collectors.toList()))
                        .build();
    }
}

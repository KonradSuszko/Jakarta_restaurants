package pl.edu.pg.eti.kask.restaurants.restaurant.model;

import lombok.*;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import java.io.Serializable;
import java.util.Collection;
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
public class RestaurantsModel implements Serializable {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Restaurant {
        private String name;

        private int establishmentYear;

        private double rating;
    }
    @Singular
    private List<Restaurant> restaurants;

    public static Function<Collection<pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant>, RestaurantsModel> entityToModelMapper() {
        return restaurants -> {
            RestaurantsModel.RestaurantsModelBuilder model = RestaurantsModel.builder();
            restaurants.stream()
                    .map(restaurant -> RestaurantsModel.Restaurant.builder()
                            .name(restaurant.getName())
                            .establishmentYear(restaurant.getEstablishmentYear())
                            .rating(restaurant.getRating())
                            .build())
                    .forEach(model::restaurant);
            return model.build();
        };
    }
}

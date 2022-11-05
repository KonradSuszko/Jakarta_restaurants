package pl.edu.pg.eti.kask.restaurants.restaurant.dto;

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
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetRestaurantResponse {
    private String name;

    private int establishmentYear;

    private double rating;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Restaurant, GetRestaurantResponse> entityToDtoMapper() {
        return restaurant -> GetRestaurantResponse.builder()
                .name(restaurant.getName())
                .establishmentYear(restaurant.getEstablishmentYear())
                .rating(restaurant.getRating())
                .build();
    }


}

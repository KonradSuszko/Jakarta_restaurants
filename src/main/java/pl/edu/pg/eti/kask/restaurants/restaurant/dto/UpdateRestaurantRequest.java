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
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateRestaurantRequest {
    private String name;

    private double rating;

    private int establishmentYear;

    public static BiFunction<Restaurant, UpdateRestaurantRequest, Restaurant> dtoToEntityUpdater() {
        return (restaurant, request) -> {
            restaurant.setName(request.getName());
            restaurant.setRating(request.getRating());
            restaurant.setEstablishmentYear(request.getEstablishmentYear());
            return restaurant;
        };
    }
}

package pl.edu.pg.eti.kask.restaurants.restaurant.view;

import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.inject.Inject;

public class RestaurantView {
    private String name;

    private int establishmentYear;

    private final RestaurantService restaurantService;

    @Inject
    public RestaurantView(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
}

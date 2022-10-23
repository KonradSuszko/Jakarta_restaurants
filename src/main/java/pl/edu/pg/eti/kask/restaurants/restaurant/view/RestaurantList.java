package pl.edu.pg.eti.kask.restaurants.restaurant.view;

import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantsModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class RestaurantList implements Serializable {
    private final RestaurantService restaurantService;

    private RestaurantsModel restaurants;

    @Inject
    private RestaurantList(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public RestaurantsModel getRestaurants() {
        if (restaurants == null) {
            restaurants = RestaurantsModel.entityToModelMapper().apply(restaurantService.findAll());
        }
        return restaurants;
    }


}

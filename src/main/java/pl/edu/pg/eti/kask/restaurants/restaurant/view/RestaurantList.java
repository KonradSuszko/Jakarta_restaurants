package pl.edu.pg.eti.kask.restaurants.restaurant.view;

import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.service.DishService;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantsModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class RestaurantList implements Serializable {
    private RestaurantService restaurantService;

    private DishService dishService;

    private RestaurantsModel restaurants;

//    public RestaurantList(){
//
//    }
//
//    @EJB
//    public void setRestaurantService(RestaurantService restaurantService){
//        this.restaurantService = restaurantService;
//    }
//
//    @EJB
//    public void setDishService(DishService dishService){
//        this.dishService = dishService;
//    }

    @Inject
    private RestaurantList(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    public RestaurantsModel getRestaurants() {
        if (restaurants == null) {
            restaurants = RestaurantsModel.entityToModelMapper().apply(restaurantService.findAll());
        }
        return restaurants;
    }

    public String deleteAction(RestaurantsModel.Restaurant restaurant){
        for (Dish dish : dishService.findByRestaurant(restaurant.getName())){
            dishService.delete(dish);
        }
        restaurantService.delete(restaurant.getName());
        return "restaurant_list?faces-redirect=true";
    }

}

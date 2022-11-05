package pl.edu.pg.eti.kask.restaurants.restaurant.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishModel;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishesModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantsModel;
import pl.edu.pg.eti.kask.restaurants.dish.service.DishService;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
public class RestaurantView implements Serializable {
    @Getter
    @Setter
    private String name;

    @Getter
    private int establishmentYear;

    @Getter
    private RestaurantModel restaurant;

    private DishesModel dishesModel;

    private RestaurantService restaurantService;

    private DishService dishService;

    public RestaurantView() {

    }

    @EJB
    public void setRestaurantService(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @EJB
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    public void init() throws IOException {
        Optional<Restaurant> restaurant = restaurantService.find(name);
        if (restaurant.isPresent()){
            this.restaurant = RestaurantModel.entityToModelMapper().apply(restaurant.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant not found");
        }
    }

    public DishesModel getDishesModel() {
        if (dishesModel == null) {
            dishesModel = DishesModel.entityToModelMapper().apply(dishService.findByRestaurant(name));
        }
        return dishesModel;
    }

    public String deleteAction(Dish dish){
        dishService.delete(dish);
        return "/restaurant/restaurant_view.xhtml?name=" + name + "&faces-redirect=true&includeViewParams=true";
    }
}

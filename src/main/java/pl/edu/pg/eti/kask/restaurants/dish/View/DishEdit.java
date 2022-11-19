package pl.edu.pg.eti.kask.restaurants.dish.View;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishEditModel;
import pl.edu.pg.eti.kask.restaurants.dish.service.DishService;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class DishEdit implements Serializable {
    private DishService dishService;

    private RestaurantService restaurantService;

    @Getter
    private List<RestaurantModel> restaurantsList;

    @Inject
    public DishEdit(DishService dishService, RestaurantService restaurantService){
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

//    @EJB
//    public void setDishService(DishService dishService) {
//        this.dishService = dishService;
//    }
//
//    @EJB
//    public void setRestaurantService(RestaurantService restaurantService){
//        this.restaurantService = restaurantService;
//    }

    @Setter
    @Getter
    private Long id;

    @Getter
    private DishEditModel dish;


    public void init() throws IOException {
        Optional<Dish> dish = dishService.find(id);
        if (dish.isPresent()) {
            this.dish = DishEditModel.entityToModelMapper().apply(dish.get());
            this.restaurantsList = restaurantService.
                    findAll()
                    .stream().map(restaurant -> RestaurantModel.entityToModelMapper().apply(restaurant))
                    .collect(Collectors.toList());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Dish not found");
        }

    }

    public String saveAction() {

        Dish dish = dishService.find(id).orElseThrow();
        dish = DishEditModel.modelToEntityUpdater(rest -> restaurantService.find(rest.getName()).orElseThrow()).apply(dish, this.dish);
        dishService.update(dish);
        return "/restaurant/restaurant_view.xhtml?name=" + dish.getRestaurant().getName() + "&faces-redirect=true&includeViewParams=true";
    }
}

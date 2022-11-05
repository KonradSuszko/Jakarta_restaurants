package pl.edu.pg.eti.kask.restaurants.dish.View;


import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishCreateModel;
import pl.edu.pg.eti.kask.restaurants.dish.service.DishService;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class DishCreate implements Serializable {
    private final RestaurantService restaurantService;
    private final DishService dishService;

    @Getter
    @Setter
    private String restaurant;

    @Getter
    private DishCreateModel dishCreateModel;

    @Getter
    private List<RestaurantModel> restaurants;

    @Inject
    public DishCreate(DishService dishService, RestaurantService restaurantService){
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

    public void init() {
        dishCreateModel = new DishCreateModel();
        dishCreateModel.setId(dishService.findAll().stream().count());
        restaurants = restaurantService.findAll().stream().map(rest -> RestaurantModel.entityToModelMapper().apply(rest))
                .collect(Collectors.toList());
        Optional<Restaurant> restaurant = restaurantService.find(this.restaurant);
        restaurant.ifPresent(val -> dishCreateModel.setRestaurantModel(RestaurantModel.entityToModelMapper().apply(val)));
    }

    public String saveAction() throws IOException {
        var dish = dishService.find(dishCreateModel.getId()+1);
        if (dish.isPresent()) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_CONFLICT, "invalid id");
            return "";
        }
        var createdDish = DishCreateModel.modelToEntityMapper(model -> restaurantService.find(model.getName()).orElseThrow())
                .apply(dishCreateModel);
        dishService.create(createdDish);
        return "/restaurant/restaurant_view.xhtml?name=" + createdDish.getRestaurant().getName() + "&faces-redirect=true&includeViewParams=true";
    }
}

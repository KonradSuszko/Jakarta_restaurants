package pl.edu.pg.eti.kask.restaurants.restaurant.converter;

import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.model.RestaurantModel;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

@FacesConverter(forClass = RestaurantModel.class, managed = true)
public class RestaurantConverter implements Converter<RestaurantModel> {
    private final RestaurantService restaurantService;

    @Inject
    public RestaurantConverter(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @Override
    public RestaurantModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String string){
        if (string.isBlank() || string == null){
            return null;
        }
        Optional<Restaurant> restaurant = restaurantService.find(string);
        if (restaurant.isEmpty()){
            return null;
        } else {
            return RestaurantModel.entityToModelMapper().apply(restaurant.get());
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiCOmponent, RestaurantModel restaurantModel){
        return restaurantModel == null ? "" : restaurantModel.getName();
    }
}

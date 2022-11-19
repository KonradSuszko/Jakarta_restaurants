package pl.edu.pg.eti.kask.restaurants.dish.View;


import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishModel;
import pl.edu.pg.eti.kask.restaurants.dish.model.DishesModel;
import pl.edu.pg.eti.kask.restaurants.dish.service.DishService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@RequestScoped
@Named
public class DishView implements Serializable {
    @Getter
    @Setter
    private Long id;

    @Getter
    private String name;

    @Getter
    private float price;

    @Getter
    private int weight;

    private DishService dishService;

    @Getter
    private DishModel dish;

    @Inject
    public DishView(DishService dishService){
        this.dishService = dishService;
    }

//    public DishView(){
//    }
//
//    @EJB
//    public void setDishService(DishService dishService){
//        this.dishService = dishService;
//    }

    public void init() throws IOException {
        Optional<Dish> dish = dishService.find(id);
        if (dish.isPresent()) {
            this.dish = DishModel.entityToModelMapper().apply(dish.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Dish not found");
        }
    }

}

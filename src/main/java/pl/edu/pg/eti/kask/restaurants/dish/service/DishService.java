package pl.edu.pg.eti.kask.restaurants.restaurant.service;


import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.repository.DishRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class DishService {
    private DishRepository repository;

    @Inject
    public DishService(DishRepository repository){
        this.repository = repository;
    }

    public Optional<Dish> find(Long id){
        return repository.find(id);
    }

    public void create(Dish dish){
        repository.create(dish);
    }
}

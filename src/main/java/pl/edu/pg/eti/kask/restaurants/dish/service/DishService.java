package pl.edu.pg.eti.kask.restaurants.dish.service;


import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.repository.DishRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
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

    public List<Dish> findAll() {
        return repository.findAll();
    }

    public void create(Dish dish){
        repository.create(dish);
    }

    public void delete(Dish dish) {
        repository.delete(dish);
    }

    public void update(Dish dish) {
        repository.update(dish);
    }

    public List<Dish> findByRestaurant(String name){
        return repository.findByRestaurant(name);
    }
}

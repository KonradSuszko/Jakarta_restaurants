package pl.edu.pg.eti.kask.restaurants.dish.service;


import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.repository.DishRepository;
import pl.edu.pg.eti.kask.restaurants.restaurant.repository.RestaurantRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class DishService {
    private DishRepository repository;

    private RestaurantRepository restaurantRepository;

    @Inject
    public DishService(DishRepository repository, RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
        this.repository = repository;
    }

    public Optional<Dish> find(Long id){
        return repository.find(id);
    }

    public List<Dish> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(Dish dish){
        repository.create(dish);
        restaurantRepository.find(dish.getRestaurant().getName()).ifPresent(rest -> rest.getDishes().add(dish));
    }

    @Transactional
    public void delete(Dish dish) {
        Dish og = repository.find(dish.getId()).orElseThrow();
        og.getRestaurant().getDishes().remove(og);
        repository.delete(dish);
    }

    @Transactional
    public void update(Dish dish) {
        repository.update(dish);
    }

    public List<Dish> findByRestaurant(String name){
        return repository.findByRestaurant(name);
    }
}

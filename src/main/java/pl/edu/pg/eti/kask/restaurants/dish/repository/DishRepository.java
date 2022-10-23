package pl.edu.pg.eti.kask.restaurants.dish.repository;

import pl.edu.pg.eti.kask.restaurants.datastore.DataStore;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.repository.Repository;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.user.repository.UserRepository;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class DishRepository implements Repository<Dish, Long> {

    private DataStore store;

    @Inject
    public DishRepository(DataStore store){
        this.store = store;
    }

    @Override
    public Optional<Dish> find(Long id){
        return store.findDish(id);
    }

    @Override
    public List<Dish> findAll() {
        return store.findAllDishes();
    }

    @Override
    public void create(Dish entity){
        store.createDish(entity);
    }

    @Override
    public void delete(Dish entity){
        store.deleteDish(entity.getId());
    }

    @Override
    public void update(Dish entity){
        store.updateDish(entity);
    }
}

package pl.edu.pg.eti.kask.restaurants.restaurant.repository;

import pl.edu.pg.eti.kask.restaurants.datastore.DataStore;
import pl.edu.pg.eti.kask.restaurants.repository.Repository;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class RestaurantRepository implements Repository<Restaurant, String> {

    private DataStore store;

    @Inject
    public RestaurantRepository(DataStore store){
        this.store = store;
    }

    @Override
    public Optional<Restaurant> find(String id){
        return store.findRestaurant(id);
    }

    @Override
    public List<Restaurant> findAll() {
        return store.findAllRestaurants();
    }

    @Override
    public void create(Restaurant entity){
        store.createRestaurant(entity);
    }

    @Override
    public void delete(Restaurant entity){
        store.deleteRestaurant(entity);
    }

    @Override
    public void update(Restaurant entity){
        throw new UnsupportedOperationException("Not implemented");
    }
}

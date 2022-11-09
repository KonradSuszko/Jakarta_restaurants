package pl.edu.pg.eti.kask.restaurants.restaurant.repository;

import pl.edu.pg.eti.kask.restaurants.datastore.DataStore;
import pl.edu.pg.eti.kask.restaurants.repository.Repository;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class RestaurantRepository implements Repository<Restaurant, String> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Restaurant> find(String id){
        return Optional.ofNullable(em.find(Restaurant.class, id));
    }

    @Override
    public List<Restaurant> findAll()
    {
        return em.createQuery("select r from Restaurant r", Restaurant.class).getResultList();
    }

    @Override
    public void create(Restaurant entity)
    {
        em.persist(entity);
    }

    @Override
    public void delete(Restaurant entity){
        em.remove(em.find(Restaurant.class, entity.getName()));
    }

    @Override
    public void update(Restaurant entity)
    {
        em.merge(entity);
    }

    @Override
    public void detach(Restaurant entity) {
        em.detach(entity);
    }
}

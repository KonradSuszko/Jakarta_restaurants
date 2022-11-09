package pl.edu.pg.eti.kask.restaurants.dish.repository;

import pl.edu.pg.eti.kask.restaurants.datastore.DataStore;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.repository.Repository;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.user.repository.UserRepository;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class DishRepository implements Repository<Dish, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Dish> find(Long id){
        return Optional.ofNullable(em.find(Dish.class, id));
    }

    @Override
    public List<Dish> findAll()
    {
        return em.createQuery("select d from Dish d", Dish.class).getResultList();
    }

    @Override
    public void create(Dish entity)
    {
        em.persist(entity);
    }

    @Override
    public void delete(Dish entity){
        em.remove(em.find(Dish.class, entity.getId()));
    }

    @Override
    public void update(Dish entity){
        em.merge(entity);
    }

    @Override
    public void detach(Dish entity) {
        em.detach(entity);
    }

    public List<Dish> findByRestaurant(String name) {
        return em.createQuery("select d from Dish d where d.restaurant.name = :restaurant", Dish.class)
                .setParameter("restaurant", name)
                .getResultList();
    }
}

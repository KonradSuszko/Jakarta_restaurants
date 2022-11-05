package pl.edu.pg.eti.kask.restaurants.restaurant.service;


import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.repository.RestaurantRepository;
import pl.edu.pg.eti.kask.restaurants.user.context.UserContext;
import pl.edu.pg.eti.kask.restaurants.user.repository.UserRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@Stateless
@LocalBean
@NoArgsConstructor
public class RestaurantService {

    private RestaurantRepository repository;

    private UserRepository userRepository;

    @Inject
    public RestaurantService(RestaurantRepository repository, UserRepository userRepository, UserContext userContext){
        this.userRepository = userRepository;
        this.repository = repository;
    }

//    public Optional<Restaurant> findForCallerPrincipal(Long id){
//        return repository.findByIdAndUser(id, userRepository.find(userContext.getPrincipal()).orElseThrow());
//    }

    public Optional<Restaurant> find(String name){
        return repository.find(name);
    }

    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    public void create(Restaurant restaurant){
        repository.create(restaurant);
    }

    public void delete(String name){
        repository.delete(find(name).get());
    }

    public void update(Restaurant restaurant) {
        repository.update(restaurant);
    }
}

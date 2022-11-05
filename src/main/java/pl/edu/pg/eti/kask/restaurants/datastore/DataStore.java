package pl.edu.pg.eti.kask.restaurants.datastore;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.serialization.CloningUtility;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;


import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class DataStore {
    private Set<Restaurant> restaurants = new HashSet<>();

    private Set<Dish> dishes = new HashSet<>();

    private Set<User> users = new HashSet<>();


    public synchronized List<Restaurant> findAllRestaurants() {
        return restaurants.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Restaurant> findRestaurant(String name) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getName().equals(name))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createRestaurant(Restaurant restaurant) throws IllegalArgumentException {
        if(findRestaurant(restaurant.getName()).isPresent()){
                throw new IllegalArgumentException(
                        String.format("The restaurant name \"%s\" is not unique", restaurant.getName()));
        } else {
            restaurants.add(CloningUtility.clone(restaurant));
        }
    }

    public synchronized void updateRestaurant(Restaurant restaurant) throws IllegalArgumentException {
        if (findRestaurant(restaurant.getName()).isPresent()){
            restaurants.remove(findRestaurant(restaurant.getName()).get());
            restaurants.add(CloningUtility.clone(restaurant));
        } else {
            throw new IllegalArgumentException(
                    String.format("The restaurant with id \"%s\" does not exist", restaurant.getName()));
        }
    }

    public synchronized void deleteRestaurant(Restaurant restaurant) {
        if (findRestaurant(restaurant.getName()).isPresent()){
            for (Dish dish : findDishesByRestaurant(restaurant.getName())) {
                deleteDish(dish.getId());
            }
            restaurants.remove(findRestaurant(restaurant.getName()).get());
        } else {
            throw new IllegalArgumentException(
                    String.format("The restaurant with name \"%s\" does not exist", restaurant.getName()));
        }
    }

    /**
     * Seeks for all characters.
     *
     * @return list (can be empty) of all characters
     */
    public synchronized List<Dish> findAllDishes() {
        return dishes.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Seeks for single character.
     *
     * @param id character's id
     * @return container (can be empty) with character
     */
    public synchronized Optional<Dish> findDish(Long id) {
        return dishes.stream()
                .filter(dish -> dish.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Dish> findDishesByRestaurant(String name){
        return dishes.stream().filter(dish -> dish.getRestaurant().getName().equals(name))
                .collect(Collectors.toList());
    }


    public synchronized void createDish(Dish dish) throws IllegalArgumentException {
        dish.setId(findAllDishes().stream().count() + 1);
        dishes.add(CloningUtility.clone(dish));
    }


    public synchronized void updateDish(Dish dish) throws IllegalArgumentException {
        if (findDish(dish.getId()).isPresent()){
            dishes.remove(findDish(dish.getId()).get());
            dishes.add(CloningUtility.clone(dish));
        } else {
            throw new IllegalArgumentException(
                    String.format("The dish with id \"%d\" does not exist", dish.getId()));
        }
    }

    /**
     * Deletes existing character.
     *
     * @param id character's id
     * @throws IllegalArgumentException if character with provided id does not exist
     */
    public synchronized void deleteDish(Long id) throws IllegalArgumentException {
        if (findDish(id).isPresent()){
            dishes.remove(findDish(id).get());
        } else {
                throw new IllegalArgumentException(
                        String.format("The character with id \"%d\" does not exist", id));
        }
    }

    /**
     * Seeks for single user.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    public synchronized Optional<User> findUser(String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Seeks for all users.
     *
     * @return collection of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param user new user to be stored
     * @throws IllegalArgumentException if user with provided login already exists
     */
    public synchronized void createUser(User user) throws IllegalArgumentException {
        if (findUser(user.getLogin()).isPresent()){
            throw new IllegalArgumentException(
                    String.format("The user login \"%s\" is not unique", user.getLogin()));
        } else {
            users.add(CloningUtility.clone(user));
        }
    }

    public synchronized void deleteUser(String login) throws IllegalArgumentException {
        findUser(login).ifPresentOrElse(
                og -> users.remove(og),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("User with login \"%d\" does not exist", login));
                }
        );
    }

    public synchronized void updateUser(User user) throws IllegalArgumentException {
        findUser(user.getLogin()).ifPresentOrElse(
                og -> {
                    users.remove(og);
                    users.add(CloningUtility.clone(user));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("User with login \"%d\" does not exists", user.getLogin()));
                });
    }

}

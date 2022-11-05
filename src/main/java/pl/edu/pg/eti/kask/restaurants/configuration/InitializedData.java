package pl.edu.pg.eti.kask.restaurants.configuration;

import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.service.DishService;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.user.entity.EnumRole;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;
import pl.edu.pg.eti.kask.restaurants.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Arrays;

@ApplicationScoped
public class InitializedData {
    private final RestaurantService restaurantService;

    private final UserService userService;

    private final DishService dishService;

    @Inject
    public InitializedData(RestaurantService restaurantService, UserService userService, DishService dishService){
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.dishService = dishService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init){
        init();
    }

    private synchronized void init() {
        User admin = User.builder()
                .login("admin")
                .name("Admin")
                .surname("Adminowski")
                .password("admin")
                .role(EnumRole.OWNER)
                .build();
        User user = User.builder()
                .login("user")
                .name("User")
                .surname("Userowski")
                .password("user")
                .role(EnumRole.EMPLOYEE)
                .build();
        User boss = User.builder()
                .login("boss")
                .name("Boss")
                .surname("Bossowski")
                .password("boss")
                .role(EnumRole.MANAGER)
                .build();
        User adam = User.builder()
                .login("adam")
                .name("Adam")
                .surname("Kowalski")
                .password("adam123")
                .role(EnumRole.EMPLOYEE)
                .build();
        userService.create(admin);
        userService.create(user);
        userService.create(boss);
        userService.create(adam);
        Restaurant sushiNova = Restaurant.builder()
                .name("Sushi Nova")
                .establishmentYear(1997)
                .rating(4.6)
                .build();
        Restaurant gyozilla = Restaurant.builder()
                .name("Gyozilla")
                .establishmentYear(2012)
                .rating(4.8)
                .build();
        restaurantService.create(sushiNova);
        restaurantService.create(gyozilla);
        Dish sushi = Dish.builder()
                .name("Futomaki")
                .id(new Long(0))
                .price((float)19.9)
                .weight(250)
                .restaurant(sushiNova)
                .build();
        Dish ramen = Dish.builder()
                .name("Ramen tsumeken")
                .id(new Long(1))
                .price((float)49.9)
                .weight(450)
                .restaurant(gyozilla)
                .build();
        dishService.create(sushi);
        dishService.create(ramen);
    }
}

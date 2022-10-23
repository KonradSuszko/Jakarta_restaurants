package pl.edu.pg.eti.kask.restaurants.restaurant.servlet;

import pl.edu.pg.eti.kask.restaurants.servlet.HttpHeaders;
import pl.edu.pg.eti.kask.restaurants.servlet.MimeTypes;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.DishService;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.CreateRestaurantRequest;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.GetRestaurantResponse;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.GetRestaurantsResponse;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.UpdateRestaurantRequest;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.servlet.ServletUtility;
import pl.edu.pg.eti.kask.restaurants.servlet.UrlFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;


@WebServlet(urlPatterns = RestaurantServlet.Paths.RESTAURANTS + "/*")

public class RestaurantServlet extends HttpServlet {
    private RestaurantService restaurantService;

    private DishService dishService;

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public RestaurantServlet(RestaurantService restaurantService, DishService dishService){
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    public static class Paths {
        public static final String RESTAURANTS = "/api/restaurants";
    }

    public static class Patterns {
        public static final String RESTAURANTS = "^/?$";
        public static final String RESTAURANT = "^/[A-Za-z0-9]+(_?[A-Za-z0-9]+)$";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("1");
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.RESTAURANTS.equals(servletPath)) {
            System.out.println("2");
            if (path.matches(Patterns.RESTAURANT)) {
                System.out.println("3");
                getRestaurant(request, response);
                return;
            } else if (path.matches(Patterns.RESTAURANTS)){
                getRestaurants(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.RESTAURANTS.equals(request.getServletPath())) {
            if (path.matches(Patterns.RESTAURANTS)) {
                CreateRestaurantRequest restaurantRequest = jsonb.fromJson(request.getInputStream(), CreateRestaurantRequest.class);
                Restaurant restaurant = CreateRestaurantRequest
                        .dtoToEntityMapper()
                        .apply(restaurantRequest);
                try {
                    restaurantService.create(restaurant);
                    response.addHeader(HttpHeaders.LOCATION,
                            UrlFactory.createUrl(
                                    request,
                                    Paths.RESTAURANTS,
                                    restaurant.getName().replace(' ', '_')
                            ));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (IllegalArgumentException ex){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.RESTAURANTS.equals(request.getServletPath())) {
            if (path.matches(Patterns.RESTAURANT)) {
                String name = ServletUtility.parseRequestPath(request).replaceAll("/", "");
                Optional<Restaurant> restaurant = restaurantService.find(name);
                if (restaurant.isPresent()) {
                    UpdateRestaurantRequest requestBody = jsonb.fromJson(request.getInputStream(),
                            UpdateRestaurantRequest.class);
                    UpdateRestaurantRequest.dtoToEntityUpdater().apply(restaurant.get(), requestBody);

                    restaurantService.update(restaurant.get());
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.RESTAURANTS.equals(request.getServletPath())) {
            if (path.matches(Patterns.RESTAURANT)) {
                String name = ServletUtility.parseRequestPath(request).replaceAll("/", "");
                Optional<Restaurant> restaurant = restaurantService.find(name);

                if (restaurant.isPresent()){
                    restaurantService.delete(restaurant.get());
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        name = name.replaceAll("_", " ");
        System.out.println(name);
        Optional<Restaurant> restaurant = restaurantService.find(name);
        if (restaurant.isPresent()){
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter().write(jsonb.toJson(GetRestaurantResponse.entityToDtoMapper().apply(restaurant.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getRestaurants(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter().write(jsonb.toJson(GetRestaurantsResponse.entityToDtoMapper().apply(restaurantService.findAll())));
    }
}

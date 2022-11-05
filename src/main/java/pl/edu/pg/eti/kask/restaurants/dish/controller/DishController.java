package pl.edu.pg.eti.kask.restaurants.dish.controller;

import pl.edu.pg.eti.kask.restaurants.dish.dto.CreateDishRequest;
import pl.edu.pg.eti.kask.restaurants.dish.dto.GetDishResponse;
import pl.edu.pg.eti.kask.restaurants.dish.dto.GetDishesResponse;
import pl.edu.pg.eti.kask.restaurants.dish.dto.UpdateDishRequest;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.dish.service.DishService;
import pl.edu.pg.eti.kask.restaurants.restaurant.controller.RestaurantController;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.CreateRestaurantRequest;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Path("/restaurants/{name}/dishes")
public class DishController {
    private RestaurantService restaurantService;

    private DishService dishService;

    @Inject
    public void setRestaurantService(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @Inject
    public void setDishService(DishService dishService){
        this.dishService = dishService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishes(@PathParam("name") String restaurantName) {
        List<Dish> dishes = dishService.findByRestaurant(restaurantName);
        if (dishes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response
                .ok(GetDishesResponse.entityToDtoMapper().apply(dishes))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDish(@PathParam("name") String restaurantName, @PathParam("id") Long id) {
        var dish = dishService.find(id);
        if (dish.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        if (dish.get().getRestaurant().getName().equals(restaurantName)) {
            return Response
                    .ok(GetDishResponse.entityToDtoMapper().apply(dish.get()))
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDish(@PathParam("name") String name, CreateDishRequest request) {
        Dish dish = CreateDishRequest
                .dtoToEntityMapper()
                .apply(request);
        var restaurant = restaurantService.find(name);
        if (restaurant.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        dish.setRestaurant(restaurant.get());
        dishService.create(dish);
        return Response
                .created(UriBuilder
                        //.fromMethod(DishController.class, "getDish")
                        .fromPath("/restaurants/{name}/dishes/{id}")
                        .build(name, dish.getId()))
                .build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDish(@PathParam("name") String name, @PathParam("id") Long id) {
        var dish = dishService.find(id);
        if(dish.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } else if (!restaurantService.find(dish.get().getRestaurant().getName()).get().getName().equals(name)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        dishService.delete(dish.get());
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDish(@PathParam("name") String name, @PathParam("id") Long id, UpdateDishRequest request) {
        var dish = dishService.find(id);
        if(dish.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } else if (!restaurantService.find(dish.get().getRestaurant().getName()).get().getName().equals(name)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        UpdateDishRequest.dtoToEntityUpdater().apply(dish.get(), request);
        dishService.update(dish.get());
        return Response
                .created(UriBuilder
                        .fromPath("/restaurants/{name}/dishes/{id}")
                        .build(name, dish.get().getId()))
                .build();

    }
}

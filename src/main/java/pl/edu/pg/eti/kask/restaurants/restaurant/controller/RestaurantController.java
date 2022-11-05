package pl.edu.pg.eti.kask.restaurants.restaurant.controller;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.CreateRestaurantRequest;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.GetRestaurantResponse;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.GetRestaurantsResponse;
import pl.edu.pg.eti.kask.restaurants.restaurant.dto.UpdateRestaurantRequest;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@Path("")
public class RestaurantController {
    private RestaurantService restaurantService;

    public RestaurantController() {

    }

    @Inject
    public void setRestaurantService(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @GET
    @Path("/restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurants() {
        return Response.ok(GetRestaurantsResponse.entityToDtoMapper().apply(restaurantService.findAll()))
                .build();
    }

    @GET
    @Path("/restaurants/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(@PathParam("name") String name){
        Optional<Restaurant> restaurant = restaurantService.find(name);
        if (restaurant.isPresent()) {
            return Response.ok(GetRestaurantResponse.entityToDtoMapper().apply(restaurant.get()))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    @Path("/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRestaurant(CreateRestaurantRequest request) {
        Restaurant restaurant = CreateRestaurantRequest
                .dtoToEntityMapper()
                .apply(request);
        restaurantService.create(restaurant);
        return Response
                .created(UriBuilder
                        .fromMethod(RestaurantController.class, "getRestaurant")
                        .build(restaurant.getName()))
                .build();
    }

    @PUT
    @Path("/restaurants/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putRestaurant(@PathParam("name") String name, UpdateRestaurantRequest request) {
        var restaurant = restaurantService.find(name);
        if (restaurant.isPresent()) {
            UpdateRestaurantRequest.dtoToEntityUpdater().apply(restaurant.get(), request);
            restaurantService.update(restaurant.get());
            return Response
                    .created(UriBuilder
                            .fromMethod(RestaurantController.class, "getRestaurant")
                            .build(restaurant.get().getName()))
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }

    @DELETE
    @Path("/restaurants/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRestaurant(@PathParam("name") String name) {
        var restaurant = restaurantService.find(name);
        if (restaurant.isPresent()) {
            restaurantService.delete(name);
            return Response.noContent().build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}

package pl.edu.pg.eti.kask.restaurants.dish.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import java.util.List;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetDishResponse {
    private Long id;

    private String name;

    private float price;

    private int weight;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Dish, GetDishResponse> entityToDtoMapper() {
        return dish -> GetDishResponse.builder()
                .id(dish.getId())
                .name(dish.getName())
                .price(dish.getPrice())
                .weight(dish.getWeight())
                .build();
    }


}

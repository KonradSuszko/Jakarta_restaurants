package pl.edu.pg.eti.kask.restaurants.dish.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetDishesResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Dish {
        private Long id;

        private String name;

        private float price;
    }

    @Singular
    private List<pl.edu.pg.eti.kask.restaurants.dish.dto.GetDishesResponse.Dish> dishes;

    public static Function<Collection<pl.edu.pg.eti.kask.restaurants.dish.entity.Dish>, pl.edu.pg.eti.kask.restaurants.dish.dto.GetDishesResponse> entityToDtoMapper() {
        return dishes -> {
            pl.edu.pg.eti.kask.restaurants.dish.dto.GetDishesResponse.GetDishesResponseBuilder response = pl.edu.pg.eti.kask.restaurants.dish.dto.GetDishesResponse.builder();
            dishes.stream()
                    .map(dish -> pl.edu.pg.eti.kask.restaurants.dish.dto.GetDishesResponse.Dish.builder()
                            .id(dish.getId())
                            .name(dish.getName())
                            .price(dish.getPrice())
                            .build())
                    .forEach(response::dish);
            return response.build();
        };
    }
}

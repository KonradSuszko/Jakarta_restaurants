package pl.edu.pg.eti.kask.restaurants.restaurant.dto;

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

public class GetRestaurantsResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Restaurant {
        private String name;

        private int establishmentYear;
    }

    @Singular
    private List<Restaurant> restaurants;

    public static Function<Collection<pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant>, GetRestaurantsResponse> entityToDtoMapper() {
        return restaurants -> {
            GetRestaurantsResponse.GetRestaurantsResponseBuilder response = GetRestaurantsResponse.builder();
            restaurants.stream()
                    .map(restaurant -> Restaurant.builder()
                            .name(restaurant.getName())
                            .establishmentYear(restaurant.getEstablishmentYear())
                            .build())
                    .forEach(response::restaurant);
            return response.build();
        };
    }
}

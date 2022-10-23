package pl.edu.pg.eti.kask.restaurants.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;
import pl.edu.pg.eti.kask.restaurants.user.entity.EnumRole;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {
    private String login;

    private String name;

    private String surname;

    @ToString.Exclude
    private String password;

    private String role;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User
                .builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .role(EnumRole.valueOf(request.getRole()))
                .name(request.getName())
                .surname(request.getSurname())
                .build();
    }
}

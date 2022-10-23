package pl.edu.pg.eti.kask.restaurants.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.restaurants.user.entity.EnumRole;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUserRequest {
    private String login;

    private String name;

    private String surname;

    private String password;

    private String role;

    public static BiFunction<User, UpdateUserRequest, User> dtoToEntityUpdater() {
        return (user, request) -> {
            user.setLogin(request.getLogin());
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setPassword(request.getPassword());
            user.setRole(EnumRole.valueOf(request.getRole()));
            return user;
        };
    }
}

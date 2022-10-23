package pl.edu.pg.eti.kask.restaurants.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;

import java.util.function.Function;

@Data
@Builder
@AllArgsConstructor
public class GetUserResponse {
    private String login;
    private String role;
    private String name;
    private String surname;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse
                .builder()
                .role(user.getRole().toString())
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }
}

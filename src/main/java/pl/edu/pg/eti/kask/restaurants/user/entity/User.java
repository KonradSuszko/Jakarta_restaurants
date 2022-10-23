package pl.edu.pg.eti.kask.restaurants.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable{
    private String login;

    private String name;

    private String surname;

    @ToString.Exclude
    private String password;

    private EnumRole role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Restaurant> restaurants;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;
}

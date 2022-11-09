package pl.edu.pg.eti.kask.restaurants.restaurant.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.restaurants.dish.entity.Dish;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "restaurants")
public class Restaurant implements Serializable{

    @Id
    private String name;

    private int establishmentYear;

    private double rating;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<Dish> dishes;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}

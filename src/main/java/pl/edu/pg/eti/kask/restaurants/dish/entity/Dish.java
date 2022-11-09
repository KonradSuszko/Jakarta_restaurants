package pl.edu.pg.eti.kask.restaurants.dish.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.restaurants.restaurant.entity.Restaurant;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "dishes")
public class Dish implements Serializable {
    @Id
    private Long id;

    private String name;

    private float price;

    private int weight;

    @ManyToOne
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;
}

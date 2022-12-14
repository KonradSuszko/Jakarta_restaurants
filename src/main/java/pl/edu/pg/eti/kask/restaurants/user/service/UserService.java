package pl.edu.pg.eti.kask.restaurants.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;
import pl.edu.pg.eti.kask.restaurants.user.repository.UserRepository;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding user entity.
 */
@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class UserService {

    /**
     * Repository for user entity.
     */
    private UserRepository repository;

    /**
     * @param repository repository for character entity
     */
    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * @param login existing username
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login) {
        return repository.find(login);
    }

    public List<User> findAll(){
        return repository.findAll();
    }
    /**
     * Seeks for single user using login and password. Can be use in authentication module.
     *
     * @param login    user's login
     * @param password user's password (hash)
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }

    /**
     * Saves new user.
     *
     * @param user new user to be saved
     */
    public void create(User user) {
        repository.create(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void update(User user){
        repository.update(user);
    }

    public void updateAvatar(String login, InputStream is){
        repository.find(login).ifPresent(user -> {
            try {
                user.setAvatar(is.readAllBytes());
                repository.update(user);
            } catch (IOException ex){
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(String login){
        repository.find(login).ifPresent(user -> {
            user.setAvatar(null);
            repository.update(user);
        });
    }
}

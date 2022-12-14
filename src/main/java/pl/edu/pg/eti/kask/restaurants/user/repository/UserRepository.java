package pl.edu.pg.eti.kask.restaurants.user.repository;
import pl.edu.pg.eti.kask.restaurants.datastore.DataStore;
import pl.edu.pg.eti.kask.restaurants.repository.Repository;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Repository for User entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class UserRepository implements Repository<User, String> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public UserRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(String id) {
        return store.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        store.deleteUser(entity.getLogin());
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }

    @Override
    public void detach(User entity){

    }

    /**
     * Seeks for single user using login and password. Can be use in authentication module.
     *
     * @param login    user's login
     * @param password user's password (hash)
     * @return container (can be empty) with user
     */
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findAllUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

}

package pl.edu.pg.eti.kask.restaurants.user.servlet;

import pl.edu.pg.eti.kask.restaurants.servlet.HttpHeaders;
import pl.edu.pg.eti.kask.restaurants.servlet.MimeTypes;
import pl.edu.pg.eti.kask.restaurants.restaurant.service.RestaurantService;
import pl.edu.pg.eti.kask.restaurants.servlet.ServletUtility;
import pl.edu.pg.eti.kask.restaurants.servlet.UrlFactory;
import pl.edu.pg.eti.kask.restaurants.user.context.UserContext;
import pl.edu.pg.eti.kask.restaurants.user.dto.CreateUserRequest;
import pl.edu.pg.eti.kask.restaurants.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.restaurants.user.dto.UpdateUserRequest;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;
import pl.edu.pg.eti.kask.restaurants.user.service.UserService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = UserServlet.Paths.USERS + "/*")
public class UserServlet extends HttpServlet {

    private UserService userService;
    private UserContext context;
    private RestaurantService restaurantService;

    @Inject
    public UserServlet(UserService service, UserContext context, RestaurantService restaurantService) {
        this.userService = service;
        this.context = context;
        this.restaurantService = restaurantService;
    }

    public static class Paths {
        public static final String USERS = "/api/users";

    }

    public static class Patterns {
        public static final String USERS = "^/?$";
        public static final String USER = "^/[A-Za-z0-9]+(_?[A-Za-z0-9]+)$";
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USERS)) {
                createUser(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MimeTypes.APPLICATION_JSON);
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        System.out.println("test");
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USERS)) {
                List<GetUserResponse> list = userService.findAll()
                        .stream()
                        .map(user -> GetUserResponse.entityToDtoMapper().apply(user))
                        .collect(Collectors.toList());
                resp.getWriter().write(jsonb.toJson(list));
                return;
            } else if (path.matches(Patterns.USER)) {
                String login = ServletUtility.parseRequestPath(req).replaceAll("/", "");
                login.replaceAll("_", " ");
                Optional<User> result = userService.find(login);
                if (result.isPresent()) {
                    resp.getWriter().write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(result.get())));
                    return;
                }
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if(Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                String login = ServletUtility.parseRequestPath(req).replaceAll("/", "");
                Optional<User> user = userService.find(login);
                if(user.isPresent()) {
                    userService.delete(user.get());
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.USERS.equals(servletPath)){
            if (path.matches(Patterns.USER)) {
                // update user
                String login = ServletUtility.parseRequestPath(req).replaceAll("/", "");
                Optional<User> user = userService.find(login);
                if (user.isPresent()) {
                    UpdateUserRequest requestBody = jsonb.fromJson(req.getInputStream(), UpdateUserRequest.class);
                    UpdateUserRequest.dtoToEntityUpdater().apply(user.get(), requestBody);

                    userService.update(user.get());
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserRequest requestBody = jsonb.fromJson(
                req.getInputStream(),
                CreateUserRequest.class
        );
        User user = CreateUserRequest.dtoToEntityMapper().apply(requestBody);
        try {
            userService.create(user);
            resp.addHeader(HttpHeaders.LOCATION,
                    UrlFactory.createUrl(
                            req,
                            Paths.USERS,
                            user.getLogin()
                    ));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException ex){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

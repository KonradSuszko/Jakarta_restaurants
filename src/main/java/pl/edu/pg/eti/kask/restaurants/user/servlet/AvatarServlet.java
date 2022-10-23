package pl.edu.pg.eti.kask.restaurants.user.servlet;

import pl.edu.pg.eti.kask.restaurants.servlet.HttpHeaders;
import pl.edu.pg.eti.kask.restaurants.servlet.MimeTypes;
import pl.edu.pg.eti.kask.restaurants.servlet.ServletUtility;
import pl.edu.pg.eti.kask.restaurants.servlet.UrlFactory;
import pl.edu.pg.eti.kask.restaurants.user.dto.CreateUserRequest;
import pl.edu.pg.eti.kask.restaurants.user.entity.User;
import pl.edu.pg.eti.kask.restaurants.user.service.UserService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = AvatarServlet.Paths.AVATARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {
    private UserService userService;

    @Inject
    public AvatarServlet(UserService userService){
        this.userService = userService;
    }

    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }

    public static class Patterns {

        /**
         * Specified portrait (for download).
         */
        public static final String AVATAR = "^/[A-Za-z0-9]+(_?[A-Za-z0-9]+)$";
    }

    public static class Parameters {
        /**
         * Portrait image part.
         */
        public static final String AVATAR = "avatar";

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                updateAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                deleteAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void deleteAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = ServletUtility.parseRequestPath(req).replaceAll("/", "");
        Optional<User> user = userService.find(login);
        if (user.isPresent()){
            userService.deleteAvatar(login);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = userService.find(login);

        if (user.isPresent()) {
            if (user.get().getAvatar() != null) {
                //Type should be stored in the database but in this project we assume everything to be png.
                response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
                response.setContentLength(user.get().getAvatar().length);
                response.getOutputStream().write(user.get().getAvatar());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void updateAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = ServletUtility.parseRequestPath(req).replaceAll("/", "");
        Optional<User> user = userService.find(login);
        if (user.isPresent()){
            Part avatar = req.getPart(Parameters.AVATAR);
            if (avatar != null) {
                userService.updateAvatar(login, avatar.getInputStream());
            }
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

package fit.iuh.bai5;

import java.util.List;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/users")
public class UserController {
    private UserService userService = new UserService();
    
    @GET
    @Path("/mon")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        return "hellloooo thien";
    }
    // Lấy danh sách tất cả người dùng
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Lấy thông tin của một người dùng
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@jakarta.ws.rs.PathParam("id") int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Thêm mới một người dùng
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        userService.createUser(user);
        User userDTO = new User(user.getFirstName(), user.getLastName(), user.getDob());
        return Response.status(Response.Status.CREATED).entity(userDTO).build();
    }

    // Cập nhật thông tin người dùng
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@jakarta.ws.rs.PathParam("id") int id, User user) {
        user.setId(id);
        if (userService.updateUser(user)) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Xóa một người dùng
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@jakarta.ws.rs.PathParam("id") int id) {
        if (userService.deleteUser(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

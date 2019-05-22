/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.nimbusds.jose.JOSEException;
import domain.User;


import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.AuthService;

@Path("auth")
//@Tag(name = "Auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(JsonObject credential) {
        try {
            String username = credential.getString("username");
            String password = credential.getString("password");
            String token = authService.login(username, password);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch ( JOSEException ex) {
            Logger.getLogger(AuthResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
        }
    }

    /*@POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a user")
    public Response add(User user) {
        try {
            authService.(user);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
        URI id = URI.create(user.getUsername());
        return Response.created(id).build();
    }

    @PUT
    @Path("/register/verify/{key}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a user")
    public Response add(@PathParam("key") String key) {
        authService.confirmEmail(key);
        return Response.ok().build();
    }*/

}

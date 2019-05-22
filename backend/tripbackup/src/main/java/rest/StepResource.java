package rest;

import auth.JWTStore;
import domain.Journey;
import domain.Step;
import domain.User;
import service.JourneyService;
import service.MessageService;
import service.StepService;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.List;

@Path("Step")
@Stateless
public class StepResource {

    @Context
    SecurityContext securityContext;

    @Inject
    private UserService uS;
    @Inject
    private StepService sS;

    @Inject
    MessageService mS;

    @Inject
    JWTStore jwtStore;

    @Inject
    private JourneyService jS;

    @GET
    @Path("{stepname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Step getStep(@PathParam("stepname") String name) {
        return sS.findByName(name);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createStep(Step step) {
        sS.addStep(step);
        return Response.ok().build();
    }
    @GET
    @Path("getStep/{journeyname}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Step> getStepByJourney(@PathParam("journeyname") String journeyname) {
        Journey journey = jS.findByName(journeyname);
        return sS.findByJourney(journey);
    }
    @DELETE
    @Path("{name}")
    public void deleteJourney(@PathParam("name")String name) {
        sS.removeStep(name);
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{id}")
    public Response likeStep(@PathParam("id") int id, @HeaderParam("Authorization") String authorization) {
        try {
             sS.likeStep(id, this.jwtStore.getCredential(authorization.substring(7)).getCaller());
            return Response.status(Response.Status.OK).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/unlike/{id}")
    public Response unlikeStep(@PathParam("id") int id, @HeaderParam("Authorization") String authorization) {
        try {

            sS.unlikeStep(id, this.jwtStore.getCredential(authorization.substring(7)).getCaller());

            return Response.status(Response.Status.OK).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }


}

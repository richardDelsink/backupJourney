package rest;

import domain.Journey;
import domain.User;
import service.JourneyService;
import service.UserService;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("Journey")
@Stateless
public class JourneyResource {
    @Inject
    private JourneyService jS;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("get/{journeyname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJourney(@PathParam("journeyname") String name) {
        Journey j = jS.findByName(name);
      if(j.getLinks().isEmpty()) {
            j.addLink(j.getJourneyName(), getUriSelf(uriInfo, j),"GET");
            j.addLink("step",getUriStep(uriInfo, j),"GET");
        }
        return Response.ok(j).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createJourney(Journey j) {
        final URI selfUri = uriInfo.getBaseUriBuilder().path(UserResource.class).path(UserResource.class, "createUser").build();
        jS.addJourney(j);
        return Response.created(selfUri).build();
    }
    @GET
    @Path("getByUser/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJourneyByUser(@PathParam("name") String name) {
        try {
            List<Journey> journeys = jS.findByUsername(name);
           for(Journey j :journeys){
                if(j.getLinks().isEmpty()) {
                    j.addLink(j.getJourneyName(), getUriSelf(uriInfo, j),"GET");
                  // j.addLink("step", getUriStep(uriInfo, j), "GET");
                }
            }
            return Response.ok(journeys).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }
    @DELETE
    @Path("/delete/{name}")
    public void deleteJourney(@PathParam("name")String name) {
        jS.removeJourney(name);
    }

    private String getUriStep(UriInfo uriInfo, Journey journey){
        String uri = uriInfo.getBaseUriBuilder()
                .path(StepResource.class)
                .path(StepResource.class, "getStepByJourney")
                .resolveTemplate("id", journey.getJourneyId())
                .build()
                .toString();
        return uri;
    }

    private String getUriSelf(UriInfo uriInfo, Journey journey)
    {
        String uri = uriInfo.getBaseUriBuilder()
                .path(JourneyResource.class)
                .path(JourneyResource.class,"getJourney")
                .resolveTemplate("journeyname", journey.getJourneyName())
                .build()
                .toString();
        return uri;
    }
}

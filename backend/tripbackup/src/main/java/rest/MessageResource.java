package rest;

import auth.JWTStore;
import domain.Message;
import domain.Step;
import service.MessageService;
import service.StepService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("Message")
@Stateless
public class MessageResource {

    @Inject
    private MessageService mS;

    @Inject
    private StepService sS;

    @GET
    @Path("{messagecontext}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message getComment(@PathParam("messagecontext") String messagecontext) {
        return mS.findByName(messagecontext);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComment(Message comment) {
        mS.addComment(comment);
        URI id = URI.create(comment.getUserName());
        return Response.created(id).build();
    }
    @GET
    @Path("search/{comments}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getCommentByStep(@PathParam("comments") String name) {
        Step step = sS.findByName(name);
        return mS.findByStep(step);
    }
    @DELETE
    @Path("{name}")
    public void deleteComment(@PathParam("name")String name) {
        mS.removeComment(name);
    }
}

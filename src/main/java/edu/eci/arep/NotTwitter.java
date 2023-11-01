package edu.eci.arep;

import edu.eci.arep.model.Post;
import edu.eci.arep.service.StreamService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jboss.logging.annotations.Pos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/notwitter")
public class NotTwitter {
    @Inject
    private StreamService streamService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stream(){
        try {
            return Response.ok("{\"data\": " + new ObjectMapper().writeValueAsString(streamService.getStreams()) + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessage(Post newPost) {
        try {
            return Response.ok("{\"data\": " + new ObjectMapper().writeValueAsString(StreamService.add(newPost)) + "}").build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}

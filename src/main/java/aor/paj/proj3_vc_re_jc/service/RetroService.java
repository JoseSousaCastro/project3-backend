package aor.paj.proj3_vc_re_jc.service;

import aor.paj.proj3_vc_re_jc.bean.RetroBean;
import aor.paj.proj3_vc_re_jc.bean.UserBean;
import aor.paj.proj3_vc_re_jc.dto.AddCommentDto;
import aor.paj.proj3_vc_re_jc.dto.CreateRetroEventDto;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/retrospective")
@Stateless
public class RetroService {

    @EJB
    RetroBean retroBean;
    @EJB
    UserBean userBean;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRetrospectives(@HeaderParam("token") String token) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            List<CreateRetroEventDto> retroEvents = retroBean.getRetrospectives();
            response = Response.status(200).entity(retroEvents).build();
        }
        return response;
    }

    @GET
    @Path("/{id}/allComments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@HeaderParam("token") String token, @PathParam("id") int id) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            List<AddCommentDto> retroComments = retroBean.getComments(id);
            if (retroComments == null) {
                response = Response.status(404).entity("Retrospective with this id not found").build();
            } else {
                response = Response.status(200).entity(retroComments).build();
            }
        }
        return response;
    }

    @GET
    @Path("/{id}/allMembers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembers(@HeaderParam("token") String token, @PathParam("id") int id) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            List<String> retroMembers = retroBean.getMembers(id);
            if (retroMembers == null) {
                response = Response.status(404).entity("Retrospective with this id not found").build();
            } else {
                response = Response.status(200).entity(retroMembers).build();
            }
        }
        return response;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRetrospective(@HeaderParam("token") String token, @PathParam("id") int id) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            CreateRetroEventDto retroEvent = retroBean.getRetrospective(id);
            if (retroEvent == null) {
                response = Response.status(404).entity("Retrospective with this id not found").build();
            } else {
                response = Response.status(200).entity(retroEvent).build();
            }
        }
        return response;
    }

    @GET
    @Path("/{id}/comment/{id2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(@HeaderParam("token") String token, @PathParam("id") int id, @PathParam("id2") int id2) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            if (id == 0) {
                response = Response.status(400).entity("Invalid retrospective id").build();
            } else {
                AddCommentDto retroComment = retroBean.getComment(id, id2);
                if (retroComment == null) {
                    response = Response.status(404).entity("Comment with this id not found").build();
                } else {
                    response = Response.status(200).entity(retroComment).build();
                }
            }
        }
        return response;
    }

    @GET
    @Path("/{id}/member/{id2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMember(@HeaderParam("token") String token, @PathParam("id") int id, @PathParam("id2") int id2) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            if (id == 0) {
                response = Response.status(400).entity("Invalid retrospective id").build();
            } else {
                String retroMember = retroBean.getMember(id, id2);
                if (retroMember == null) {
                    response = Response.status(404).entity("Member with this id not found").build();
                } else {
                    response = Response.status(200).entity(retroMember).build();
                }
            }
        }
        return response;
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRetrospective(@HeaderParam("token") String token, CreateRetroEventDto createRetroEventDTO) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean added = retroBean.addRetrospective(token, createRetroEventDTO);
            if (!added) {
                response = Response.status(400).entity("Retrospective not created. Verify all fields").build();
            } else {
                response = Response.status(200).entity("Retrospective created successfully").build();
            }
        }
        return response;
    }

    @POST
    @Path("/{id}/addComment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newComment(@HeaderParam("token") String token, @PathParam("id") int id, AddCommentDto temporaryRetroComment) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean added = retroBean.addCommentToRetrospective(token, id, temporaryRetroComment);
            if (!added) {
                response = Response.status(400).entity("Comment not created. Verify all fields").build();
            } else {
                response = Response.status(200).entity("Comment created successfuly").build();
            }
        }
        return response;
    }

    @POST
    @Path("/{id}/addMember/{id2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newMember(@HeaderParam("token") String token, @PathParam("id") int id, @PathParam("id2") int id2) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean added = retroBean.addMemberToRetrospective(id, id2);
            if (!added) {
                response = Response.status(400).entity("Member not added. Verify all fields").build();
            } else {
                response = Response.status(200).entity("Member added successfuly").build();
            }
        }
        return response;
    }

    @PUT
    @Path("/{id}/editComment/{id2}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editComment(@HeaderParam("token") String token, @PathParam("id") int id, @PathParam("id2") int id2, AddCommentDto temporaryRetroComment) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean edited = retroBean.editComment(id, id2, temporaryRetroComment);
            if (!edited) {
                response = Response.status(400).entity("Comment not edited. Verify all fields").build();
            } else {
                response = Response.status(200).entity("Comment edited successfuly").build();
            }
        }
        return response;
    }

    @DELETE
    @Path("/{id}/deleteComment/{id2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@HeaderParam("token") String token, @PathParam("id") int id, @PathParam("id2") int id2) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean deleted = retroBean.deleteComment(id, id2);
            if (!deleted) {
                response = Response.status(400).entity("Comment not deleted. Verify all fields").build();
            } else {
                response = Response.status(200).entity("Comment deleted successfuly").build();
            }
        }
        return response;
    }

    @DELETE
    @Path("/{id}/deleteAllComments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllComments(@HeaderParam("token") String token, @PathParam("id") int id) {
        Response response;
        if (!userBean.tokenExist(token)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean deleted = retroBean.deleteAllComments(id);
            if (!deleted) {
                response = Response.status(400).entity("Comments not deleted. Verify all fields").build();
            } else {
                response = Response.status(200).entity("Comments deleted successfuly").build();
            }
        }
        return response;
    }
}
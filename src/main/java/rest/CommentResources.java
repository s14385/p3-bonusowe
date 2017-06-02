package rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Movie;

@Path("/movies")
public class CommentResources{
	
	@PersistenceContext
	EntityManager em;
	
	@GET
	@Path("{idMovie}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List <Comment> showAllComment(@PathParam("idMovie") int idMovie){
		
		Movie result = em.createNamedQuery("movie.id", Movie.class)
				.setParameter("movieId", idMovie)
				.getSingleResult();
		
		if(result == null){
			
			return null;
		}
		
		return result.getComments();
	}
	
	@GET
	@Path("{idMovie}/comments/{idComment}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showCommentById(@PathParam("idMovie") int idMovie, @PathParam("idComment") int idComment){
		
		Movie result = em.createNamedQuery("movie.id", Movie.class)
				.setParameter("movieId", idMovie)
				.getSingleResult();
		
		if(result == null){
			
			return Response.status(404).build();
		}
		
		for(Comment comment : result.getComments()){
			
			if(comment.getId() == idComment){
				
				return Response.ok(comment).build();
			}
		}
		
		return Response.status(404).build();
	}
	
	@POST
	@Path("{idMovie}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("idMovie") int idMovie, Comment comment){
		
		Movie result = em.createNamedQuery("movie.id", Movie.class)
				.setParameter("movieId", idMovie)
				.getSingleResult();
		
		if(result == null){
			
			return Response.status(404).build();
		}
		
		result.getComments().add(comment);
		comment.setMovie(result);
		em.persist(comment);
		
		return Response.ok().build();
	}
	
	/*
	@DELETE
	@Path("/{idMovie}/comments/{idComment}")
	public Response deleteComment(@PathParam("idMovie") int idMovie, @PathParam("idComment") int idComment){
		
		Comment result = db.getCommentById(idMovie, idComment);
		
		if(result == null){
			
			return Response.status(404).build();
		}
		
		db.getMovieById(idMovie).getComments().remove(result);
		return Response.ok().build();
	}
	*/
}
package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Movie;

@Path("/movies")
@Stateless
public class MovieResources{
	
	@PersistenceContext
	EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List <Movie> showAllMovies(){
		
		return em.createNamedQuery("movie.all", Movie.class).getResultList();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showMovieById(@PathParam("id") int id){
		
		Movie result = em.createNamedQuery("movie.id", Movie.class)
				.setParameter("movieId", id)
				.getSingleResult();
		
		if(result == null){
			
			return Response.status(404).build();
		}
		
		return Response.ok(result).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddMovie(Movie movie){
		
		em.persist(movie);
		return Response.ok(movie.getId()).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMovie(@PathParam("id") int id, Movie movie){
		
		Movie result = em.createNamedQuery("movie.id", Movie.class)
				.setParameter("movieId", id)
				.getSingleResult();
		
		if(result == null){
			
			return Response.status(404).build();
		}
		else{
			
			result.setTitle(movie.getTitle());
			result.setProduction(movie.getProduction());
			result.setReleaseYear(movie.getReleaseYear());
			result.setBoxOffice(movie.getBoxOffice());
			result.setComments(movie.getComments());
			
			em.persist(result);
		}
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteMovieById(@PathParam("id") int id){
		
		Movie result = em.createNamedQuery("movie.id", Movie.class)
				.setParameter("movieId", id)
				.getSingleResult();
		
		if(result == null){
			
			return Response.status(404).build();
		}
		
		em.remove(result);
		return Response.ok().build();
	}
}
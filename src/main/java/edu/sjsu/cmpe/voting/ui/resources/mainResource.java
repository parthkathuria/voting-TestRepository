package edu.sjsu.cmpe.voting.ui.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.voting.domain.Users;
import edu.sjsu.cmpe.voting.repository.UserRepositoryInterface;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;
import edu.sjsu.cmpe.voting.ui.views.UserDetails;
import edu.sjsu.cmpe.voting.ui.views.HomePage;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class mainResource {
	
	private final UserRepositoryInterface userRepository;
	
	public mainResource(UserRepositoryInterface userRepository) {
		this.userRepository = userRepository;
	}
	
	@GET
	public HomePage getHome() {
		return new HomePage("index.mustache");
	}
	
	@POST
	@Timed(name = "create-user")
	public Response createUser(Users newUser) {
		Users user = userRepository.getUser(newUser.getId());
		if(user == null){
			userRepository.saveUser(newUser);
			return Response.status(200).entity(newUser.getId()).build();
		}else{
			return Response.status(201).entity(newUser.getId()).build();
		}
		
	}

}

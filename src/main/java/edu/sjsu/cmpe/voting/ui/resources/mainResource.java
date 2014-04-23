package edu.sjsu.cmpe.voting.ui.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cmpe.voting.domain.Users;
import edu.sjsu.cmpe.voting.repository.UserRepositoryInterface;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;
import edu.sjsu.cmpe.voting.ui.views.createPoll;
import edu.sjsu.cmpe.voting.ui.views.homePage;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class mainResource {
	private final UserRepositoryInterface userRepository;
	
	public mainResource(UserRepositoryInterface userRepository) {
		this.userRepository = userRepository;
	}
	
	@GET
	public homePage getHome() {
		return new homePage("index.mustache");
	}
	
	@POST
	@Path("/poll/register")
	public createPoll registerUser(Users user) {
		
		Users userData = userRepository.getUser(user.getUsername(), user.getPassword());
		if(userData == null){
			Users newUser = userRepository.saveUser(user);
			return new createPoll(newUser);
		}else{
			return null;
		}
	}
	
	@POST
	@Path("/poll/login")
	public createPoll loginUser(Users user) {
		
		Users userData = userRepository.getUser(user.getUsername(), user.getPassword());
		if(userData == null){
			return null;
			
		}else{
			return new createPoll(userData);
		}
	}
}

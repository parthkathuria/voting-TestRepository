/**
 * 
 */
package edu.sjsu.cmpe.voting.ui.resources;

import java.io.Console;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Authentication.User;

import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.research.ws.wadl.Request;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.voting.domain.Poll;
import edu.sjsu.cmpe.voting.domain.PollDetails;
import edu.sjsu.cmpe.voting.domain.Users;
import edu.sjsu.cmpe.voting.dto.LinkDto;
import edu.sjsu.cmpe.voting.dto.LinksDto;
import edu.sjsu.cmpe.voting.dto.PollDto;
import edu.sjsu.cmpe.voting.repository.UserRepositoryInterface;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;
import edu.sjsu.cmpe.voting.ui.views.ViewPoll_View;
import edu.sjsu.cmpe.voting.ui.views.UserDetails;
import edu.sjsu.cmpe.voting.ui.views.homePage;

@Path("/{userId}")
@Produces(MediaType.TEXT_HTML)
public class ViewPollResource {
	private final VotingRepositoryInterface voteRepository;
	private final UserRepositoryInterface userRepository;
	// private final String template;
	public ViewPollResource(VotingRepositoryInterface voteRepository, UserRepositoryInterface userRepository) {
		this.voteRepository = voteRepository;
		this.userRepository = userRepository;
	}

	/*@GET
	public homePage getHome() {

		return new homePage("index.mustache");
	}
	*/
	/*@POST
	@Timed(name = "create-user")
	public Response createUser(@PathParam("userId") LongParam userId) {
		Users user = userRepository.getUser(userId.get());
		if(user == null){
			Users newUser = new Users();
			newUser.setId(userId.get());
			userRepository.saveUser(newUser);
			return Response.status(200).entity(userId).build();
		}else{
			return Response.status(201).entity(userId).build();
		}
		
	}
*/
	@GET
	public UserDetails getCreatePoll(@PathParam("userId") String userId) {
		Users user = userRepository.getUser(userId);
		return new UserDetails(userRepository.getUser(userId));
	}

	@GET
	@Path("/{key}")
	@Timed(name = "view-poll")
	public ViewPoll_View getBookByIsbn(@PathParam("userId") String userId, @PathParam("key") String key) {
		Poll poll = voteRepository.getPollbyKey(key);
		Users user = userRepository.getUser(userId);
		if (poll != null || user != null) {
			PollDetails p = new PollDetails();
			p.setId(poll.getId());
			p.setQuestion(poll.getQuestion());
			p.setOptions(poll.getOptions());
			p.setStartDate(poll.getStartDate());
			p.setEndDate(poll.getEndDate());
			p.setUserId(user.getId());
			p.setFirst_name(user.getFirst_name());
			p.setLast_name(user.getLast_name());
			p.setEmail(user.getEmail());
			return new ViewPoll_View(p, "viewPoll.mustache");
		} else {
			return null;
		}
	}

	@PUT
	@Path("/{key}")
	@Timed(name = "answer-poll")
	public ViewPoll_View answerPoll(@PathParam("userId") String userId, @PathParam("key") String key,
			@QueryParam("answer") String answer) {
		Poll p = voteRepository.getPollbyKey(key);
		Users user = userRepository.getUser(userId);
		PollDetails poll = new PollDetails();
		poll.setId(p.getId());
		poll.setQuestion(p.getQuestion());
		poll.setOptions(p.getOptions());
		poll.setStartDate(p.getStartDate());
		poll.setEndDate(p.getEndDate());
		poll.setUserId(user.getId());
		poll.setEmail(user.getEmail());
		poll.setFirst_name(user.getFirst_name());
		poll.setLast_name(user.getLast_name());
		boolean ok = false;
		for (int i = 0; i < poll.getOptions().size(); i++) {
			if (answer.equalsIgnoreCase(poll.getOptions().get(i).getOption())) {
				ok = true;
			}
		}

		if (ok) {
			voteRepository.updatePoll(key, answer);
			return new ViewPoll_View(poll,"viewPoll.mustache");
		}
		return null;

	}

	@POST
	@Timed(name = "create-poll")
	public Response createPoll(@PathParam("userId") String userId, Poll newPoll) {
		
		newPoll.setUserId(userId);
		Poll savedPoll = voteRepository.savePoll(newPoll);
		userRepository.updatePollCreation(userId, savedPoll.getId().toString());
		return Response.status(200).entity(savedPoll.getId()).build();
	}

}

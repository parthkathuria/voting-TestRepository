/**
 * 
 */
package edu.sjsu.cmpe.voting.ui.resources;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.DELETE;
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

import com.google.common.base.Optional;
import com.google.gson.Gson;
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
import edu.sjsu.cmpe.voting.repository.AmazonSES;
import edu.sjsu.cmpe.voting.repository.UserRepositoryInterface;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;
import edu.sjsu.cmpe.voting.ui.views.ViewPoll_View;
import edu.sjsu.cmpe.voting.ui.views.UserDetails;
import edu.sjsu.cmpe.voting.ui.views.HomePage;

@Path("/{userId}")
@Produces(MediaType.TEXT_HTML)
public class ViewPollResource {
	private final VotingRepositoryInterface voteRepository;
	private final UserRepositoryInterface userRepository;

	// private final String template;
	public ViewPollResource(VotingRepositoryInterface voteRepository,
			UserRepositoryInterface userRepository) {
		this.voteRepository = voteRepository;
		this.userRepository = userRepository;
	}

	/*
	 * @GET public HomePage getHome() {
	 * 
	 * return new HomePage("index.mustache"); }
	 */
	/*
	 * @POST
	 * 
	 * @Timed(name = "create-user") public Response
	 * createUser(@PathParam("userId") LongParam userId) { Users user =
	 * userRepository.getUser(userId.get()); if(user == null){ Users newUser =
	 * new Users(); newUser.setId(userId.get());
	 * userRepository.saveUser(newUser); return
	 * Response.status(200).entity(userId).build(); }else{ return
	 * Response.status(201).entity(userId).build(); }
	 * 
	 * }
	 */
	@GET
	public UserDetails getCreatePoll(@PathParam("userId") String userId,
			@QueryParam("poll_code") String code) throws ParseException {

		if (code == null) {
			PollDetails poll = new PollDetails();
			Users user = userRepository.getUser(userId);
			if (user == null) {
				return new UserDetails(user, "Error.mustache", poll);
			} else {
				return new UserDetails(user, "createPoll.mustache", poll);
			}
		} else {
			Poll poll = voteRepository.getPollbyKey(code);
			Users user = userRepository.getUser(userId);
			PollDetails p = new PollDetails();
			if (poll != null && user != null) {

				p.setId(poll.getId());
				p.setQuestion(poll.getQuestion());
				p.setOptions(poll.getOptions());
				p.setStartDate(poll.getStartDate());
				p.setEndDate(poll.getEndDate());
				p.setUserId(user.getId());
				p.setFirst_name(user.getFirst_name());
				p.setLast_name(user.getLast_name());
				p.setEmail(user.getEmail());
				Date date = new Date();
				DateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
				Date startDate = dt.parse(p.getStartDate());
				Date endDate = dt.parse(p.getEndDate());

				if (user.getPollsCreated().contains(p.getId())) {
					System.out.println("pollview");
					return new UserDetails(user, "pollViewUser.mustache", p);
				} else if (date.before(startDate)) {
					System.out.println("start");
					return new UserDetails(user, "startPoll.mustache", p);
				} else if (date.after(endDate)) {
					System.out.println("end");
					return new UserDetails(user, "endPoll.mustache", p);
				} else if (user.getPollsSubmitted().contains(p.getId())) {
					System.out.println("submit");
					return new UserDetails(user, "submittedPoll.mustache", p);
				} else {
					return new UserDetails(user, "viewPoll.mustache", p);
				}
			} else {
				p.setId(code);
				p.setUserId(user.getId());
				p.setFirst_name(user.getFirst_name());
				p.setLast_name(user.getLast_name());
				p.setEmail(user.getEmail());
				return new UserDetails(user, "pollNotFound.mustache", p);
			}
		}

	}

	@GET
	@Path("/{key}")
	@Timed(name = "view-poll")
	public ViewPoll_View getPollById(@PathParam("userId") String userId,
			@PathParam("key") String key) throws ParseException {
		Poll poll = voteRepository.getPollbyKey(key);
		Users user = userRepository.getUser(userId);
		PollDetails p = new PollDetails();

		if (poll != null && user != null) {

			p.setId(poll.getId());
			p.setQuestion(poll.getQuestion());
			p.setOptions(poll.getOptions());
			p.setStartDate(poll.getStartDate());
			p.setEndDate(poll.getEndDate());
			p.setUserId(user.getId());
			p.setFirst_name(user.getFirst_name());
			p.setLast_name(user.getLast_name());
			p.setEmail(user.getEmail());
			Date date = new Date();
			DateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate = dt.parse(p.getStartDate());
			Date endDate = dt.parse(p.getEndDate());

			if (user.getPollsCreated().contains(p.getId())) {
				System.out.println("pollview");
				return new ViewPoll_View(p, "pollViewUser.mustache");
			} else if (date.before(startDate)) {
				System.out.println("start");
				return new ViewPoll_View(p, "startPoll.mustache");
			} else if (date.after(endDate)) {
				System.out.println("end");
				return new ViewPoll_View(p, "endPoll.mustache");
			} else if (user.getPollsSubmitted().contains(p.getId())) {
				System.out.println("submit");
				return new ViewPoll_View(p, "submittedPoll.mustache");
			} else {
				return new ViewPoll_View(p, "viewPoll.mustache");
			}
		} else {
			p.setId(key);
			p.setUserId(user.getId());
			p.setFirst_name(user.getFirst_name());
			p.setLast_name(user.getLast_name());
			p.setEmail(user.getEmail());
			return new ViewPoll_View(p, "pollNotFound.mustache");
		}
	}

	@GET
	@Path("/{key}/update")
	@Timed(name = "update")
	public Response update(@PathParam("userId") String userId,
			@PathParam("key") String key) {
		Poll poll = voteRepository.getPollbyKey(key);
		Users user = userRepository.getUser(userId);
		PollDetails p = new PollDetails();

		if (poll != null && user != null) {
			p.setId(poll.getId());
			p.setQuestion(poll.getQuestion());
			p.setOptions(poll.getOptions());
			p.setStartDate(poll.getStartDate());
			p.setEndDate(poll.getEndDate());
			p.setUserId(user.getId());
			p.setFirst_name(user.getFirst_name());
			p.setLast_name(user.getLast_name());
			p.setEmail(user.getEmail());
			Gson g = new Gson();
			String json = g.toJson(p);
			return Response.status(200).entity(json).build();
		} else {
			return null;
		}
	}

	@PUT
	@Path("/{key}")
	@Timed(name = "answer-poll")
	public ViewPoll_View answerPoll(@PathParam("userId") String userId,
			@PathParam("key") String key, @QueryParam("answer") String answer) {
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
			userRepository.updatePollSubmition(userId, poll.getId());
			return new ViewPoll_View(poll, "submittedPoll.mustache");

		}
		return null;

	}

	@POST
	@Timed(name = "create-poll")
	public Response createPoll(@PathParam("userId") String userId, Poll newPoll)
			throws IOException {

		newPoll.setUserId(userId);
		Users user = userRepository.getUser(userId);
		String email = user.getEmail();
		Poll savedPoll = voteRepository.savePoll(newPoll);
		userRepository.updatePollCreation(userId, savedPoll.getId().toString());
		AmazonSES aws = new AmazonSES();
		aws.sendEmailOnCreate(savedPoll, email);
		return Response.status(200).entity(savedPoll.getId() + "$" + email)
				.build();
	}

	@DELETE
	@Timed(name = "delete-poll")
	public Response deletePoll(@PathParam("userId") String userId, String pollId)
			throws IOException {

		Users user = userRepository.getUser(userId);
		Poll poll = voteRepository.getPollbyKey(pollId);
		String email = user.getEmail();
		if (poll == null) {
			return Response.status(500).entity(pollId + "$" + email).build();
		} else {
			AmazonSES aws = new AmazonSES();
			aws.sendEmailOnDelete(poll, email);
			userRepository.deletePoll(pollId);
			voteRepository.deletePoll(pollId);
			return Response.status(200).entity(pollId + "$" + email).build();
		}
	}

	@POST
	@Path("/{key}")
	@Timed(name = "get-details")
	public Response getDetails(@PathParam("userId") String userId,
			@PathParam("key") String key) throws IOException {
		Users user = userRepository.getUser(userId);
		Poll poll = voteRepository.getPollbyKey(key);
		String email = user.getEmail();

		if (poll == null) {
			return Response.status(500).entity(key + "$" + email).build();
		} else {
			AmazonSES aws = new AmazonSES();
			boolean ok = aws.sendEmailPollDetails(poll, email);
			//return Response.status(200).entity(key + "$" + email).build();
			if (ok) {
				return Response.status(200).entity(key + "$" + email).build();
			}else{
				return Response.status(500).entity(key + "$" + email).build();
			}
		}
	}
}

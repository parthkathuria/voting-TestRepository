package edu.sjsu.cmpe.voting.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.voting.dto.LinkDto;
import edu.sjsu.cmpe.voting.dto.LinksDto;
import edu.sjsu.cmpe.voting.dto.PollDto;
import edu.sjsu.cmpe.voting.domain.Poll;
import edu.sjsu.cmpe.voting.domain.PollDetails;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;

@Path("/v1/poll")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VoteResource {
	private final VotingRepositoryInterface voteRepository;
	/**
     * VoteResource constructor
     * 
     * @param voteRepository
     *            a VoteRepository instance
     */

	public VoteResource(VotingRepositoryInterface voteRepository) {
		this.voteRepository = voteRepository;
	}

	@GET
	@Path("/{key}")
	@Timed(name = "view-poll")
	public PollDto getBookByIsbn(@PathParam("key") String key) {
		Poll poll = voteRepository.getPollbyKey(key);
		//PollDetails pollDetails = new PollDetails(poll);
		PollDto pollResponse = new PollDto(poll);
		pollResponse.addLink(new LinkDto("view-poll", "/poll/"
				+ poll.getId(), "GET"));
		pollResponse.addLink(new LinkDto("answer-poll", "/poll/"
				+ poll.getId(), "PUT"));
		pollResponse.addLink(new LinkDto("delete-poll", "/poll/"
				+ poll.getId(), "DELETE"));

		return pollResponse;
	}

	@POST
	@Timed(name = "create-poll")
	public Response createPoll(Poll request) {

		Poll savedPoll = voteRepository.savePoll(request);

		String location = "/poll/" + savedPoll.getId();
		LinksDto voteResponse = new LinksDto();
		voteResponse.addLink(new LinkDto("view-poll", location, "GET"));
		voteResponse.addLink(new LinkDto("answer-poll", location, "PUT"));
		voteResponse.addLink(new LinkDto("create-poll", location, "POST"));

		return Response.status(201).entity(voteResponse).build();
	}

	@PUT
	@Path("/{key}")
	@Timed(name = "answer-poll")
	public Response answerPoll(@PathParam("key") String key,
			@QueryParam("answer") String answer) {
		Poll poll = voteRepository.getPollbyKey(key);

		boolean ok = false;
		for(int i=0;i<poll.getOptions().size(); i++){
			if(answer.equalsIgnoreCase(poll.getOptions().get(i).getOption())){
				ok = true;
			}
		}
		
		if (ok) {
			voteRepository.updatePoll(key, answer);
			
			LinksDto pollResponse = new LinksDto();
			pollResponse.addLink(new LinkDto("view-poll", "/poll/"
					+ poll.getId(), "GET"));
			pollResponse.addLink(new LinkDto("answer-poll", "/poll/"
					+ poll.getId(), "PUT"));
			pollResponse.addLink(new LinkDto("delete-poll", "/poll/"
					+ poll.getId(), "DELETE"));

			return Response.status(200).entity(pollResponse).build();
		}else{
			return Response.status(400).entity("Error! Answer Not Valid.").build();
		}

	}

}

package edu.sjsu.cmpe.voting.ui.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.voting.domain.Poll;
import edu.sjsu.cmpe.voting.dto.LinkDto;
import edu.sjsu.cmpe.voting.dto.LinksDto;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;
import edu.sjsu.cmpe.voting.ui.views.UserDetails;;

@Path("/poll")
@Produces(MediaType.TEXT_HTML)
public class createPollResource {
	private final VotingRepositoryInterface voteRepository;
	public createPollResource(VotingRepositoryInterface voteRepository) {
		this.voteRepository = voteRepository;
	    }
	
	@POST
	@Timed(name = "create-poll")
	public UserDetails createPoll(Poll request) {

		Poll savedPoll = voteRepository.savePoll(request);

		String location = "/poll/" + savedPoll.getId();
		LinksDto voteResponse = new LinksDto();
		voteResponse.addLink(new LinkDto("view-poll", location, "GET"));
		voteResponse.addLink(new LinkDto("answer-poll", location, "PUT"));
		voteResponse.addLink(new LinkDto("create-poll", location, "POST"));
		return null;
		
	}

}

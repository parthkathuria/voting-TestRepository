/**
 * 
 */
package edu.sjsu.cmpe.voting.ui.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.research.ws.wadl.Request;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.voting.domain.Poll;
import edu.sjsu.cmpe.voting.dto.LinkDto;
import edu.sjsu.cmpe.voting.dto.LinksDto;
import edu.sjsu.cmpe.voting.dto.PollDto;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;
import edu.sjsu.cmpe.voting.ui.views.ViewPoll_View;
import edu.sjsu.cmpe.voting.ui.views.createPoll;
import edu.sjsu.cmpe.voting.ui.views.homePage;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ViewPollResource {
	private final VotingRepositoryInterface voteRepository;

	// private final String template;
	public ViewPollResource(VotingRepositoryInterface voteRepository) {
		this.voteRepository = voteRepository;
	}

	@GET
	public homePage getHome() {

		return new homePage("index.mustache");
	}

	@GET
	@Path("/poll")
	public homePage getCreatePoll() {

		return new homePage("createPoll.mustache");
	}

	@GET
	@Path("/{key}")
	@Timed(name = "view-poll")
	public ViewPoll_View getBookByIsbn(@PathParam("key") String key) {
		Poll poll = voteRepository.getPollbyKey(key);
		if (poll != null) {
			return new ViewPoll_View(voteRepository.getPollbyKey(key));
		} else {
			return null;
		}
	}

	@PUT
	@Path("/{key}")
	@Timed(name = "answer-poll")
	public ViewPoll_View answerPoll(@PathParam("key") String key,
			@QueryParam("answer") String answer) {
		Poll poll = voteRepository.getPollbyKey(key);
		boolean ok = false;
		for (int i = 0; i < poll.getOptions().size(); i++) {
			if (answer.equalsIgnoreCase(poll.getOptions().get(i).getOption())) {
				ok = true;
			}
		}

		if (ok) {
			voteRepository.updatePoll(key, answer);
			return new ViewPoll_View(voteRepository.getPollbyKey(key));
		}
		return null;

	}

	@POST
	@Path("/poll")
	@Timed(name = "create-poll")
	public Response createPoll(Poll request) {
		Poll savedPoll = voteRepository.savePoll(request);
		String pollId = savedPoll.getId();
		return Response.status(200).entity(pollId).build();
	}

}

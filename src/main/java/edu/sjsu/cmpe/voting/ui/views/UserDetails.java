package edu.sjsu.cmpe.voting.ui.views;

import com.yammer.dropwizard.views.View;

import edu.sjsu.cmpe.voting.domain.Poll;
import edu.sjsu.cmpe.voting.domain.PollDetails;
import edu.sjsu.cmpe.voting.domain.Users;

public class UserDetails extends View {

	private final Users user;
	private final PollDetails poll;
	public UserDetails(Users user,String template , PollDetails poll) {
		super(template);
		this.user = user;
		this.poll = poll;
		// TODO Auto-generated constructor stub
	}
	
	public Users getUser() {
        return user;
	}

	public PollDetails getPoll() {
		return poll;
	}
	

}

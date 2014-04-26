package edu.sjsu.cmpe.voting.ui.views;

import com.yammer.dropwizard.views.View;

import edu.sjsu.cmpe.voting.domain.Poll;
import edu.sjsu.cmpe.voting.domain.Users;

public class UserDetails extends View {

	private final Users user;
	public UserDetails(Users user) {
		super("createPoll.mustache");
		this.user = user;
		// TODO Auto-generated constructor stub
	}
	
	public Users getUser() {
        return user;
	}
	

}

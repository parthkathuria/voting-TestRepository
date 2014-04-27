package edu.sjsu.cmpe.voting.ui.views;

import com.yammer.dropwizard.views.View;

import edu.sjsu.cmpe.voting.domain.PollDetails;

public class ViewSubmittedPoll extends View {

	private final PollDetails poll;
    public ViewSubmittedPoll(PollDetails poll) {
              super("submittedPoll.mustache");
              this.poll = poll;
    }
    public PollDetails getPoll() {
              return poll;
    }
}

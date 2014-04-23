/**
 * 
 */
package edu.sjsu.cmpe.voting.ui.views;

import java.util.List;
import com.yammer.dropwizard.views.View;

import edu.sjsu.cmpe.voting.domain.Poll;


/**
 * @author parthkathuria
 *
 */
public class ViewPoll_View extends View {
	private final Poll poll;
    public ViewPoll_View(Poll poll) {
              super("viewPoll.mustache");
              this.poll = poll;
    }
    public Poll getPoll() {
              return poll;
    }

}

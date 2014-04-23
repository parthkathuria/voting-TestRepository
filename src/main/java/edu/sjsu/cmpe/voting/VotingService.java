package edu.sjsu.cmpe.voting;

import java.util.concurrent.ConcurrentHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

import edu.sjsu.cmpe.voting.domain.*;
import edu.sjsu.cmpe.voting.api.resources.RootResource;
import edu.sjsu.cmpe.voting.api.resources.VoteResource;
import edu.sjsu.cmpe.voting.config.VotingServiceConfiguration;
import edu.sjsu.cmpe.voting.repository.UserRepository;
import edu.sjsu.cmpe.voting.repository.UserRepositoryInterface;
import edu.sjsu.cmpe.voting.repository.VotingRepository;
import edu.sjsu.cmpe.voting.repository.VotingRepositoryInterface;
import edu.sjsu.cmpe.voting.ui.resources.ViewPollResource;
import edu.sjsu.cmpe.voting.ui.resources.createPollResource;
import edu.sjsu.cmpe.voting.ui.resources.mainResource;

public class VotingService extends Service<VotingServiceConfiguration> {

	public static void main(String[] args) throws Exception {
		new VotingService().run(args);
	}

	@Override
	public void initialize(Bootstrap<VotingServiceConfiguration> bootstrap) {
		bootstrap.setName("Voting-service");
		bootstrap.addBundle(new ViewBundle());
		bootstrap.addBundle(new AssetsBundle());
	}

	@Override
	public void run(VotingServiceConfiguration configuration,
			Environment environment) throws Exception {
		/**Root API */
		environment.addResource(RootResource.class);
		/** Poll APIs */
		VotingRepositoryInterface voteRepository = new VotingRepository();
		//UserRepositoryInterface userRepository = new UserRepository();
			environment.addResource(new VoteResource(voteRepository));
			//environment.addResource(new ViewPollResource(voteRepository));
			environment.addResource(new ViewPollResource(voteRepository));
			//environment.addResource(new mainResource(userRepository));
	}
}

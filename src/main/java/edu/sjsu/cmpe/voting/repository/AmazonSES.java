package edu.sjsu.cmpe.voting.repository;

import java.io.IOException;

import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.*;
import com.sun.research.ws.wadl.Option;

import edu.sjsu.cmpe.voting.domain.Poll;

public class AmazonSES {
	static final String FROM = "cmpe273infinityballotbox@gmail.com"; // Replace
																		// with
																		// your
																		// "From"
																		// address.
																		// This
																		// address
																		// must
																		// be
																		// verified.
	static String TO;// Replace with a "To" address. If you have not yet
						// requested
						// production access, this address must be verified.
	static String BODY;
	static String SUBJECT;

	public void sendEmailOnCreate(Poll poll, String email) throws IOException {
		// Your AWS credentials are stored in the AwsCredentials.properties file
		// within the project.
		// You entered these AWS credentials when you created a new AWS Java
		// project in Eclipse.
		TO = email;

		PropertiesCredentials credentials = new PropertiesCredentials(
				AmazonSES.class
						.getResourceAsStream("AwsCredentials.properties"));

		// Retrieve the AWS Access Key ID and Secret Key from
		// AwsCredentials.properties.
		credentials.getAWSAccessKeyId();
		credentials.getAWSSecretKey();
		// Construct an object to contain the recipient address.
		Destination destination = new Destination()
				.withToAddresses(new String[] { TO });

		// Create the subject and body of the message.
		SUBJECT = "Your poll has been successfully created. Poll ID: "
				+ poll.getId();

		StringBuilder option = new StringBuilder();
		for(int i=0; i<poll.getOptions().size();i++){
			option.append("Option " + i + " : " + poll.getOptions().get(i).getOption() + "\n");
		}
		BODY = "Poll ID:" + poll.getId()+"\n" + "Question: " + poll.getQuestion() + "\n" + option.toString() + "Start Date :" + poll.getStartDate() +"\n" +"End Date :" + poll.getEndDate();
		

		Content subject = new Content().withData(SUBJECT);
		Content textBody = new Content().withData(BODY.toString());
		Body body = new Body().withText(textBody);

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource(FROM)
				.withDestination(destination).withMessage(message);

		try {
			System.out
					.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

			// Instantiate an Amazon SES client, which will make the service
			// call with the supplied AWS credentials.
			AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(
					credentials);

			// Choose the AWS region of the Amazon SES endpoint you want to
			// connect to. Note that your production
			// access status, sending limits, and Amazon SES identity-related
			// settings are specific to a given
			// AWS region, so be sure to select an AWS region in which you set
			// up Amazon SES. Here, we are using
			// the US East (N. Virginia) region. Examples of other regions that
			// Amazon SES supports are US_WEST_2
			// and EU_WEST_1. For a complete list, see
			// http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
			Region REGION = Region.getRegion(Regions.US_WEST_2);
			client.setRegion(REGION);

			// Send the email.
			client.sendEmail(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		}
	}

	public void sendEmailOnExpire(String email) throws IOException {
		// Your AWS credentials are stored in the AwsCredentials.properties file
		// within the project.
		// You entered these AWS credentials when you created a new AWS Java
		// project in Eclipse.
		TO = email;

		PropertiesCredentials credentials = new PropertiesCredentials(
				AmazonSES.class
						.getResourceAsStream("AwsCredentials.properties"));

		// Retrieve the AWS Access Key ID and Secret Key from
		// AwsCredentials.properties.
		credentials.getAWSAccessKeyId();
		credentials.getAWSSecretKey();

		// Construct an object to contain the recipient address.
		Destination destination = new Destination()
				.withToAddresses(new String[] { TO });

		// Create the subject and body of the message.
		Content subject = new Content().withData(SUBJECT);
		Content textBody = new Content().withData(BODY.toString());
		Body body = new Body().withText(textBody);

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource(FROM)
				.withDestination(destination).withMessage(message);

		try {
			System.out
					.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

			// Instantiate an Amazon SES client, which will make the service
			// call with the supplied AWS credentials.
			AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(
					credentials);

			// Choose the AWS region of the Amazon SES endpoint you want to
			// connect to. Note that your production
			// access status, sending limits, and Amazon SES identity-related
			// settings are specific to a given
			// AWS region, so be sure to select an AWS region in which you set
			// up Amazon SES. Here, we are using
			// the US East (N. Virginia) region. Examples of other regions that
			// Amazon SES supports are US_WEST_2
			// and EU_WEST_1. For a complete list, see
			// http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
			Region REGION = Region.getRegion(Regions.US_WEST_2);
			client.setRegion(REGION);

			// Send the email.
			client.sendEmail(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		}
	}
	
	public void sendEmailOnDelete(Poll poll, String email) throws IOException {
		// Your AWS credentials are stored in the AwsCredentials.properties file
		// within the project.
		// You entered these AWS credentials when you created a new AWS Java
		// project in Eclipse.
		TO = email;

		PropertiesCredentials credentials = new PropertiesCredentials(
				AmazonSES.class
						.getResourceAsStream("AwsCredentials.properties"));

		// Retrieve the AWS Access Key ID and Secret Key from
		// AwsCredentials.properties.
		credentials.getAWSAccessKeyId();
		credentials.getAWSSecretKey();
		
		// Construct an object to contain the recipient address.
		Destination destination = new Destination()
				.withToAddresses(new String[] { TO });

		// Create the subject and body of the message.
		
		SUBJECT = "Your poll has been successfully Deleted. Poll ID: " + poll.getId();
		
		StringBuilder option = new StringBuilder();
		for(int i=0; i<poll.getOptions().size();i++){
			option.append("Option " + i + " : " + poll.getOptions().get(i).getOption() + "\n");
		}
		BODY = "Poll ID:" + poll.getId()+"\n" + "Question: " + poll.getQuestion() + "\n" + option.toString() + "Start Date :" + poll.getStartDate() +"\n" +"End Date :" + poll.getEndDate();
		
		
		Content subject = new Content().withData(SUBJECT);
		Content textBody = new Content().withData(BODY.toString());
		Body body = new Body().withText(textBody);

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource(FROM)
				.withDestination(destination).withMessage(message);

		try {
			System.out
					.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

			// Instantiate an Amazon SES client, which will make the service
			// call with the supplied AWS credentials.
			AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(
					credentials);

			// Choose the AWS region of the Amazon SES endpoint you want to
			// connect to. Note that your production
			// access status, sending limits, and Amazon SES identity-related
			// settings are specific to a given
			// AWS region, so be sure to select an AWS region in which you set
			// up Amazon SES. Here, we are using
			// the US East (N. Virginia) region. Examples of other regions that
			// Amazon SES supports are US_WEST_2
			// and EU_WEST_1. For a complete list, see
			// http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
			Region REGION = Region.getRegion(Regions.US_WEST_2);
			client.setRegion(REGION);

			// Send the email.
			client.sendEmail(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		}
	}
}

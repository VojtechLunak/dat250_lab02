package no.brg.hvl.dat250.lab02;

import no.brg.hvl.dat250.lab02.model.Poll;
import no.brg.hvl.dat250.lab02.model.User;
import no.brg.hvl.dat250.lab02.model.Vote;
import no.brg.hvl.dat250.lab02.model.VoteOption;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Lab02ApplicationTests {

	@Autowired
	private PollManager pollManager;

	private User user;
	private Poll poll;
	private VoteOption voteOption1;
	private VoteOption voteOption2;

	@BeforeEach
	public void setUp() {
		pollManager = new PollManager();
		pollManager.deleteUser("admin");
		// Create and add sample entities
		user = new User();
		user.setUsername("test1");
		pollManager.addUser(user);

		poll = new Poll();
		poll.setQuestion("Test question");
		poll.setCreatorUsername(user.getUsername());
		pollManager.addPoll(poll);

		voteOption1 = new VoteOption();
		voteOption1.setCaption("Test option 1");
		voteOption1.setPresentationOrder(1);
		poll.addVoteOption(voteOption1);

		voteOption2 = new VoteOption();
		voteOption2.setCaption("Test option 2");
		voteOption2.setPresentationOrder(2);
		poll.addVoteOption(voteOption2);
	}

	@Test
	public void testCreateUser() {
		User newUser = new User();
		String newUsername = "test2";
		newUser.setUsername(newUsername);
		pollManager.addUser(newUser);
		assertNotNull(newUser);
		assertEquals(newUser, pollManager.getUser(newUsername));
	}

	@Test
	public void testGetUsers() {
		User newUser = new User();
		String newUsername = "test2";
		newUser.setUsername(newUsername);
		pollManager.addUser(newUser);
		assertNotNull(newUser);
		assertEquals(pollManager.getUsers().size(), 2);
	}


	@Test
	public void testCreatePoll() {
		Poll newPoll = new Poll();
		String newQuestion = "Question 2";
		newPoll.setQuestion(newQuestion);
		newPoll.setCreatorUsername(user.getUsername());
		Integer newPollId = pollManager.addPoll(newPoll);
		assertNotNull(pollManager.getPoll(newPollId));
	}

	@Test
	public void testCreateVoteOption() {
		VoteOption newVoteOption = new VoteOption();
		String newCaption = "Option 3";
		newVoteOption.setCaption(newCaption);
		newVoteOption.setPresentationOrder(2);
		poll.addVoteOption(newVoteOption);
		VoteOption createdOption = poll.getVoteOptions().stream().filter(option -> option.getCaption().equals(newCaption)).findFirst().orElse(null);
		assertNotNull(createdOption);
		assertEquals(newCaption, createdOption.getCaption());
	}

	@Test
	public void testCastVote() {
		Vote newVote = new Vote();
		newVote.setUsername(user.getUsername());
		newVote.setPollId(poll.getId());
		newVote = pollManager.castVote(newVote, user.getUsername(), poll.getId(), voteOption1.getCaption());

		assertEquals(user.getVotes().size(), 1);
		assertEquals(voteOption1, newVote.getVoteOption());
		//assertEquals(user, pollManager.getUser(user.getUsername()).getV);
		assertTrue(user.getVotes().contains(newVote));
	}

	@Test
	public void testGetUserByUsername() {
		User retrievedUser = pollManager.getUser(user.getUsername());
		assertNotNull(retrievedUser);
		assertEquals(user.getUsername(), retrievedUser.getUsername());
	}

	@Test
	public void testGetPollById() {
		Poll retrievedPoll = pollManager.getPoll(1);
		assertNotNull(retrievedPoll);
		assertEquals(poll.getId(), retrievedPoll.getId());
	}

	@Test
	public void testDeletePoll() {
		Integer newPollId = pollManager.addPoll(new Poll());

		pollManager.deletePoll(newPollId);
		assertNull(pollManager.getPoll(2));
	}

	@Test
	public void testCastAndChangeVote() {
		String username2 = "test2";
		pollManager.addUser(new User(username2, "test2"));

		Vote createdVote = pollManager.castVote(new Vote(), username2, poll.getId(), voteOption2.getCaption());

		List<Vote> user2votes = pollManager.getUser(username2).getVotes();

		assertEquals(1, user2votes.size());
		assertEquals(voteOption2, user2votes.get(0).getVoteOption());
		assertNotEquals(user2votes.get(0).getVoteOption(), voteOption1);

		pollManager.getUser(username2).changeVoteOption(createdVote.getId(), voteOption1);
		assertEquals(voteOption1, user2votes.get(0).getVoteOption());
	}
}

package no.brg.hvl.dat250.lab02.service;

import lombok.NoArgsConstructor;
import no.brg.hvl.dat250.lab02.model.Poll;
import no.brg.hvl.dat250.lab02.model.User;
import no.brg.hvl.dat250.lab02.model.Vote;
import no.brg.hvl.dat250.lab02.model.VoteOption;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class PollManager {
    private final Set<User> users = new HashSet<>();
    private Integer pollId = 1;

    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public Vote castVote(String username, Integer pollId, String voteCaption) {
        Poll poll = getPolls().stream().filter(p -> p.getId().equals(pollId)).findFirst().orElse(null);
        VoteOption option = poll.getVoteOptions().stream().filter(v -> v.getCaption().equals(voteCaption)).findFirst().orElse(null);

        users.stream().filter(user -> user.getUsername().equals(username)).findFirst().ifPresent(user -> {
            user.castVote(option);
        });

        return getUser(username).getVotes().stream().filter(v -> v.getVoteOption().equals(option)).findFirst().orElse(null);
    }

    public Integer addPoll(Poll poll, String username) {
        poll.setId(pollId);
        pollId++;
        users.stream().filter(user -> user.getUsername().equals(username)).findFirst().ifPresent(user -> {
           user.addPoll(poll);
        });
        return poll.getId();
    }

    public void deletePoll(Integer pollId) {
        for (User user : users) {
            user.getPolls().removeIf(p -> p.getId().equals(pollId));
        }
    }

    public Poll getPoll(Integer pollId) {
        return getPolls().stream().filter(p -> p.getId().equals(pollId)).findFirst().orElse(null);
    }

    public void addVoteOption(VoteOption voteOption, String pollQuestion) {
        users.stream().map(User::getPolls)
                .flatMap(List::stream)
                .filter(poll -> poll.getQuestion().equals(pollQuestion))
                .findFirst()
                .ifPresent(poll -> poll.addVoteOption(voteOption));
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Poll> getPolls() {
        return users.stream()
                .map(User::getPolls)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    public Set<Vote> getVotes() {
        return users.stream()
                .map(User::getVotes)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    public Set<VoteOption> getVoteOptions() {
        return users.stream()
                .map(User::getVotes)
                .flatMap(List::stream)
                .map(Vote::getVoteOption)
                .collect(Collectors.toSet());
    }
}

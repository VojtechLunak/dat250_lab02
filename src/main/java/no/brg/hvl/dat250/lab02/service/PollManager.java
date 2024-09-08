package no.brg.hvl.dat250.lab02.service;

import lombok.NoArgsConstructor;
import no.brg.hvl.dat250.lab02.model.Poll;
import no.brg.hvl.dat250.lab02.model.User;
import no.brg.hvl.dat250.lab02.model.Vote;
import no.brg.hvl.dat250.lab02.model.VoteOption;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

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

    public Vote castVote(Vote vote, String username, Integer pollId, String voteOptionCaption) {
        Poll poll = getPolls().stream().filter(p -> p.getId().equals(pollId)).findFirst().orElse(null);
        VoteOption option = poll.getVoteOptions().stream().filter(v -> v.getCaption().equals(voteOptionCaption)).findFirst().orElse(null);

        vote.setUsername(username);
        vote.setPollId(pollId);
        vote.setVoteOption(option);

        users.stream().filter(user -> user.getUsername().equals(vote.getUsername())).findFirst().ifPresent(user -> {
            user.addVote(vote);
        });

        return getUser(vote.getUsername()).getVotes().stream().filter(v -> v.getPollId().equals(pollId)).findFirst().orElse(null);
    }

    public Integer addPoll(Poll poll) {
        poll.setId(pollId);
        pollId++;
        users.stream().filter(user -> user.getUsername().equals(poll.getCreatorUsername())).findFirst().ifPresent(user -> {
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

    public void addVoteOption(VoteOption voteOption) {
        users.stream().map(User::getPolls)
                .flatMap(List::stream)
                .filter(poll -> poll.getId().equals(voteOption.getPollId()))
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
        return getPolls().stream()
                .map(Poll::getVoteOptions)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<VoteOption> getVoteOptions(Integer pollId) {
        return getPoll(pollId).getVoteOptions();
    }


    public void changeVote(String username, Integer voteId, Integer pollId, String caption) {
        getUser(username).changeVoteOption(voteId, getVoteOptions(pollId).stream().filter(v -> v.getCaption().equals(caption)).findFirst().orElse(null));
    }

    public void deleteUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
    }

    public void updatePoll(Poll poll) {
        getPolls().stream().filter(p -> p.getId().equals(poll.getId())).findFirst().ifPresent(p -> {
            p = poll;
        });
    }
}

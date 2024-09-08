package no.brg.hvl.dat250.lab02.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Integer voteId = 1;
    private String username;
    private String email;
    private final List<Poll> polls = new ArrayList<>();
    private final List<Vote> votes = new ArrayList<>();

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void addPoll(Poll poll) {
        polls.add(poll);
    }

    public void castVote(VoteOption voteOption) {
        Vote vote = new Vote();
        vote.setId(voteId);
        vote.setVoteOption(voteOption);
        votes.add(vote);
    }

    public void changeVoteOption(Integer voteId, VoteOption voteOption) {
        votes.stream().filter(v -> v.getId().equals(voteId)).findFirst().ifPresent(v -> {
            v.setVoteOption(voteOption);
        });
    }
}

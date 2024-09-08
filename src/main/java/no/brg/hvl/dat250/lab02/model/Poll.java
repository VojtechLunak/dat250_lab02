package no.brg.hvl.dat250.lab02.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Poll {
    private Integer id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private final Set<VoteOption> voteOptions = new HashSet<>();

    public Set<VoteOption> getVoteOptions() {
        if (voteOptions.size() < 2) {
            throw new IllegalArgumentException("A poll must have at least two options. This is not a valid poll.");
        }
        return voteOptions;
    }

    public void addVoteOption(VoteOption voteOption) {
        voteOptions.add(voteOption);
    }
}

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
    private String creatorUsername;
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
        for (VoteOption option : voteOptions) {
            if (option.getCaption().equals(voteOption.getCaption())) {
                throw new IllegalArgumentException("A poll cannot have two options with the same caption. This is not a valid poll.");
            }
        }
        voteOption.setId(id);
        voteOptions.add(voteOption);
    }
}

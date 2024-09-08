package no.brg.hvl.dat250.lab02.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class Vote {
    private Integer id;
    private Integer pollId;
    private String username;
    private Instant publishedAt;
    private VoteOption voteOption;

    public Vote(Instant publishedAt, VoteOption voteOption) {
        this.publishedAt = publishedAt;
        this.voteOption = voteOption;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoteOptionCaption() {
       return voteOption.getCaption();
    }
}

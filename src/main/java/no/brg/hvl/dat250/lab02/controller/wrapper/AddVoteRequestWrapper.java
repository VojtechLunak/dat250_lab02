package no.brg.hvl.dat250.lab02.controller.wrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.brg.hvl.dat250.lab02.model.Vote;

@Getter
@Setter
@NoArgsConstructor
public class AddVoteRequestWrapper {
    private Vote vote;
    private Integer voteOptionId;
}

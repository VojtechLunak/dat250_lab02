package no.brg.hvl.dat250.lab02.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteOption {
    private String caption;
    private int presentationOrder;

    public VoteOption(String caption, int presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }
}

package no.brg.hvl.dat250.lab02.controller;

import no.brg.hvl.dat250.lab02.model.VoteOption;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/voteoptions")
public class VoteOptionController {

    @Autowired
    private PollManager domainManager;

    @GetMapping
    public Set<VoteOption> getAllVoteOptions() {
        return domainManager.getVoteOptions();
    }

    @PostMapping
    public void addVoteOption(@RequestBody VoteOption voteOption, @PathVariable("pollQuestion") String pollQuestion) {
        domainManager.addVoteOption(voteOption, pollQuestion);
    }
}
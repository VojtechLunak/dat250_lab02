package no.brg.hvl.dat250.lab02.controller;

import no.brg.hvl.dat250.lab02.controller.wrapper.AddVoteRequestWrapper;
import no.brg.hvl.dat250.lab02.model.Vote;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private PollManager domainManager;

    @GetMapping
    public Set<Vote> getAllVotes() {
        return domainManager.getVotes();
    }
}
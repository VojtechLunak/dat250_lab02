package no.brg.hvl.dat250.lab02.controller;

import no.brg.hvl.dat250.lab02.model.Vote;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/votes")
@CrossOrigin
public class VoteController {

    @Autowired
    private PollManager domainManager;

    @GetMapping("/{username}/{pollId}")
    public Set<Vote> getVotes(@PathVariable("username") String username, @PathVariable("pollId") Integer pollId) {
        return domainManager.getVotesForUserInPoll(username, pollId);
    }

    @GetMapping("/{username}")
    public Set<Vote> getVotes(@PathVariable("username") String username) {
        return domainManager.getVotesForUser(username);
    }

    @GetMapping
    public Set<Vote> getAllVotes() {
        return domainManager.getVotes();
    }
}
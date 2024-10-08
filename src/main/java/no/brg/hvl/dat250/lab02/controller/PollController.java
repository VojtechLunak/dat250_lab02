package no.brg.hvl.dat250.lab02.controller;

import no.brg.hvl.dat250.lab02.model.Poll;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/polls")
@CrossOrigin
public class PollController {

    @Autowired
    private PollManager domainManager;

    @GetMapping
    public Set<Poll> getAllPolls() {
        return domainManager.getPolls();
    }

    @PostMapping
    public Integer addPoll(@RequestBody Poll poll) {
        return domainManager.addPoll(poll);
    }

    @PutMapping("/{pollId}")
    public void updatePoll(@PathVariable("pollId") Integer pollId, @RequestBody Poll poll) {
        domainManager.updatePoll(poll);
    }

    @DeleteMapping("/{pollId}")
    public void deletePoll(@PathVariable("pollId") Integer pollId) {
        domainManager.deletePoll(pollId);
    }
}
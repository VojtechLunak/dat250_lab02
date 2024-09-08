package no.brg.hvl.dat250.lab02.controller;

import no.brg.hvl.dat250.lab02.model.Poll;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollManager domainManager;

    @GetMapping
    public Set<Poll> getAllPolls() {
        return domainManager.getPolls();
    }

    @PostMapping("/{username}")
    public void addPoll(@RequestBody Poll poll, @PathVariable("username") String username) {
        domainManager.addPoll(poll, username);
    }
}
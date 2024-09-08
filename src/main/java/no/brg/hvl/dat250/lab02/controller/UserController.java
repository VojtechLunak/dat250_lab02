package no.brg.hvl.dat250.lab02.controller;

import no.brg.hvl.dat250.lab02.model.User;
import no.brg.hvl.dat250.lab02.model.VoteOption;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PollManager domainManager;

    @GetMapping
    public Set<User> getAllUsers() {
        return domainManager.getUsers();
    }

    @PostMapping("/{username}/polls/{pollId}/voteoptions/{caption}")
    public void castVote(@PathVariable("username") String username, @PathVariable("pollId") Integer pollId, @PathVariable("caption") String caption) {
        domainManager.castVote(username, pollId, caption);
    }

    @PutMapping("/{username}/polls/{pollId}/votes/{voteId}/voteoptions/{caption}")
    public void changeVote(@PathVariable("username") String username, @PathVariable("pollId") Integer pollId, @PathVariable("voteId") Integer voteId, @PathVariable("caption") String caption) {
        VoteOption option = domainManager.getVoteOptions().stream().filter(v -> v.getCaption().equals(caption)).findFirst().orElse(null);
        domainManager.getUser(username).changeVoteOption(voteId, option);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        User u = domainManager.getUser(username);
        return u;
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        domainManager.addUser(user);
    }
}
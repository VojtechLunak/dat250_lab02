package no.brg.hvl.dat250.lab02.controller;

import no.brg.hvl.dat250.lab02.model.User;
import no.brg.hvl.dat250.lab02.model.Vote;
import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private PollManager domainManager;

    @GetMapping
    public Set<User> getAllUsers() {
        return domainManager.getUsers();
    }


    @PutMapping("/{username}/polls/{pollId}/votes/{voteId}/voteoptions/{caption}")
    public void changeVote(@PathVariable("username") String username, @PathVariable("pollId") Integer pollId, @PathVariable("voteId") Integer voteId, @PathVariable("caption") String caption) {
        domainManager.changeVote(username, voteId, pollId, caption);
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

    @PostMapping("/{username}/polls/{pollId}/voteoptions/{voteOptionId}")
    public void castVote(@RequestBody Vote vote, @PathVariable String username, @PathVariable Integer pollId, @PathVariable String voteOptionId) {
        domainManager.castVote(vote, username, pollId, voteOptionId);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        domainManager.deleteUser(username);
    }
}
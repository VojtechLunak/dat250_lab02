package no.brg.hvl.dat250.lab02;

import no.brg.hvl.dat250.lab02.service.PollManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Lab02Application {
	private PollManager domainManager;

	public static void main(String[] args) {
		SpringApplication.run(Lab02Application.class, args);
	}


	@GetMapping("/")
	public String hello() {
		return "Hello world!";
	}
}

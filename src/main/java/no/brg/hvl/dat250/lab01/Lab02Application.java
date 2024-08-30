package no.brg.hvl.dat250.lab01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Lab02Application {

	public static void main(String[] args) {
		SpringApplication.run(no.brg.hvl.dat250.lab01.Lab02Application.class, args);
	}


	@GetMapping("/")
	public String hello() {
		return "Hello world!";
	}
}

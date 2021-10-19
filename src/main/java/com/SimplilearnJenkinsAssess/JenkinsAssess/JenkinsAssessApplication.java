package com.SimplilearnJenkinsAssess.JenkinsAssess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JenkinsAssessApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenkinsAssessApplication.class, args);
	}
	@GetMapping("/")
	public String hello(@RequestParam(value = "name", defaultValue = "Aetna healthcare ") String name) {
		return String.format("<h1>Welcome to  %s!</h1> <h2>An US based company</h2>", name);
	}
}

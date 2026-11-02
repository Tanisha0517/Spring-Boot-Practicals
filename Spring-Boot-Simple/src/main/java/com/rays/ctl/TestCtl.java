package com.rays.ctl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "test")
public class TestCtl {

	@GetMapping
	public String get() {
		return "This is deafult get method";
	}

	@PostMapping
	public String submit() {
		return "This is deafult post method";
	}

	@GetMapping("hi")
	public String get1() {
		return "This is endpoint get method";
	}

	@PostMapping("hello") // In double quotes we can give any name to the endpoint
	public String submit1() {
		return "This is endpoint post method";
	}
}

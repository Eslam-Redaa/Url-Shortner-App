package com.demo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Controller {
	
	@Autowired
	Services serv;
	
	@Autowired
	AppRepo repo;

	@PostMapping("/generate")
	public ResponseEntity<UrlResponse> GenerateSmallUrl(@RequestBody UrlRequest request ) {
		
		String code = serv.CreateUrl(request);
		return ResponseEntity.ok( new UrlResponse(code));
	}
	
	@GetMapping("/Go/{code}")
	public ResponseEntity<Void> RedirectTo(@PathVariable String code){
		
		String originalUrl = serv.GetOriginalUrl(code);
		repo.IncrementClickCount(code);
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create(originalUrl))
				.build();
	}
	
}

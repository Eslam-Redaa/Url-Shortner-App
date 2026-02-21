package com.demo;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class Services {
	
	@Autowired
	AppRepo repo;
	
	private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;
    
	public String CreateUrl(UrlRequest request) { 
		
		UrlTable table = new UrlTable();
		table.setOriginalUrl(request.getOriginalUrl());
		table.setExpiredAt(request.getExpirationDate());
		table = repo.save(table);
		
		table.setShortCode(encode(table.getId()));
		repo.save(table);
		return "http://localhost:8080/Go/"+table.getShortCode();
	}
	
	//-------------------------------------------------------------------
	
	@Cacheable(value = "urls" , key = "#code")
	public String GetOriginalUrl(String code) {
		
		System.out.println("Fetching from DB...");
		
		UrlTable urlt = repo.findByShortCode(code)
				.orElseThrow( () -> new RuntimeException("this code not found"));
		
		if(urlt.getExpiredAt() != null && urlt.getExpiredAt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("This code Expired.....");
		}
		
		//repo.IncrementClickCount(code);
		return urlt.getOriginalUrl();
	}
	
	//-------------------------------------------------------------------
	
	public static String encode(long input) {
        StringBuilder sb = new StringBuilder();
        while (input > 0) {
            sb.append(CHARACTERS.charAt((int) (input % BASE)));
            input /= BASE;
        }
        return sb.reverse().toString();
    }
	
}

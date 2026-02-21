package com.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface AppRepo extends JpaRepository<UrlTable , Long> {

	public Optional<UrlTable> findByShortCode(String code);
	
	
	@Modifying
	@Transactional
	@Query("update UrlTable u set u.clickCount = u.clickCount + 1 where u.shortCode = :shortCode")
	public void IncrementClickCount(String shortCode);
}

package com.example.url_shortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.url_shortner.domain.ShortenedUrl;

public interface ShortUrlRepository extends JpaRepository<ShortenedUrl, String> {
}

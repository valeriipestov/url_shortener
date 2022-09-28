package com.example.url_shortner.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.example.url_shortner.domain.ShortenedUrl;
import com.example.url_shortner.domain.ShortenRequest;
import com.example.url_shortner.repository.ShortUrlRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShortenService {

    private ShortUrlRepository repository;

    @Retryable
    @Transactional
    public String shortenUrl(ShortenRequest request) {
        var shortUrl = RandomStringUtils.random(8);
        var entity = new ShortenedUrl(shortUrl, request.getUrl(), request.getTitle());
        var newEntity = repository.save(entity);
        return newEntity.getShortUrl();
    }

    @Cacheable("url")
    public ShortenedUrl getOriginalUrl(String shortUrl) throws ChangeSetPersister.NotFoundException {
        return repository.findById(shortUrl).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }
}

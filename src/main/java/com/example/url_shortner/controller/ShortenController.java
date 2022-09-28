package com.example.url_shortner.controller;


import java.net.URI;
import java.util.Objects;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.url_shortner.domain.ShortenRequest;
import com.example.url_shortner.service.ShortenService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/short")
@AllArgsConstructor
public class ShortenController {

    private ShortenService service;


    @PostMapping
    public ResponseEntity<?> shorter(@RequestBody ShortenRequest request) {
        var url = service.shortenUrl(request);
        var uri = buildUri(url);
        return ResponseEntity.status(HttpStatus.CREATED)
          .location(uri)
          .build();
    }

    @GetMapping("/{shortenUrlId}")
    public ResponseEntity<?> redirectUrl(@PathVariable String shortenUrlId) {
        Objects.requireNonNull(shortenUrlId);
        try {
            var urlObject = service.getOriginalUrl(shortenUrlId);
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
              .location(URI.create(urlObject.getOriginalUrl()))
              .build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private URI buildUri(String shortenUrl) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(shortenUrl)
          .toUri();
    }


}

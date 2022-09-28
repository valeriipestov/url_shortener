package com.example.url_shortner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "shortened_urls")
public class ShortenedUrl {

    @Id
    @Column(name = "id")
    private String shortUrl;

    private String originalUrl;

    private String title;

    @Column(name = "created_at", insertable = false)
    private LocalDateTime createdAt;

    public ShortenedUrl(String shortUrl, String originalUrl, String title) {
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
        this.title = title;
    }
}

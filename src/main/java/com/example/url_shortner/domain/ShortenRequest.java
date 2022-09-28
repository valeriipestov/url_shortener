package com.example.url_shortner.domain;

import lombok.Data;

@Data
public class ShortenRequest {

    private String url;

    private String title;
}

package com.prashant.musiclify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class GetArtistService {

    private final RestTemplate restTemplate;
    private static final String URL = "https://api.spotify.com/v1/artists/";

    public Object getArtist(String token, String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("paramters", headers);

        ResponseEntity<Object> response = restTemplate.exchange(URL + id, HttpMethod.GET, entity,
                Object.class);
        LinkedHashMap result = (LinkedHashMap) response.getBody();

        return result;
    }
}

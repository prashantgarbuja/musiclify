package com.prashant.musiclify.controller;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.constant.Template;
import com.prashant.musiclify.service.SpotifyUrlService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class IndexController {

    private final SpotifyUrlService spotifyUrlService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String showIndex(final HttpSession session, final Model model) {
        String token = (String) session.getAttribute("accessToken");
        Long expiration_time = (Long) session.getAttribute("expiration_time");
//        System.out.println("Expiration time:" + expiration_time);
        if (token != null && isTokenValid(expiration_time)) {
            return "redirect:" + ApiPath.REDIRECT;
        }
        model.addAttribute("url", spotifyUrlService.getAuthorizationURL());
        return Template.INDEX;
    }

    boolean isTokenValid(Long expiration_time) {
        return System.currentTimeMillis() < expiration_time;
    }
}

package com.prashant.musiclify.controller;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.constant.Template;
import com.prashant.musiclify.service.AccessTokenService;
import com.prashant.musiclify.service.SpotifyUrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class CallbackController {

    private final AccessTokenService accessToken;
    private final SpotifyUrlService spotifyUrlService;

    @GetMapping(value = ApiPath.CALLBACK, produces = MediaType.TEXT_HTML_VALUE)
    public String handleCallback(@RequestParam(value = "code", required = false) final String code,
                                 @RequestParam(value = "error", required = false) final String error, final Model model,
                                 HttpServletRequest request) {

        HttpSession session = request.getSession(); // Get HttpSession from the HttpServletRequest
        //System.out.println("Code is "+code+" Error is "+error+" Model is "+model+" Session is "+session);

        if (error != null) {
            model.addAttribute("url", spotifyUrlService.getAuthorizationURL());
            return Template.CALLBACK_FAILURE;
        }

//		session.setAttribute("code", code);
        String token = accessToken.getToken(code);
        String expires_in = accessToken.getExpiresIn();
        Long expiration_time = System.currentTimeMillis() + Integer.parseInt(expires_in) * 1000L;
        session.setAttribute("accessToken", token);
        session.setAttribute("expiration_time", expiration_time);

        return "redirect:" + ApiPath.REDIRECT;
    }
}

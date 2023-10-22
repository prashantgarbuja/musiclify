package com.prashant.musiclify.controller;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.constant.Template;
import com.prashant.musiclify.service.SpotifyUrlService;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class CallbackFailureController implements ErrorController {

    private final SpotifyUrlService spotifyUrlService;

    @RequestMapping(value = ApiPath.CALLBACK_FAILURE, produces = MediaType.TEXT_HTML_VALUE)
    public String handleError(final Model model) {
        model.addAttribute("url", spotifyUrlService.getAuthorizationURL());
        return Template.CALLBACK_FAILURE;
    }
}

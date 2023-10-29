package com.prashant.musiclify.controller;

import com.prashant.musiclify.service.SpotifyUrlService;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.constant.Template;
import com.prashant.musiclify.exception.NoTrackPlayingException;
import com.prashant.musiclify.service.CurrentPlayingService;
import com.prashant.musiclify.service.ProfileDetailService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class RedirectController {

	private final SpotifyUrlService spotifyUrlService;
	private final ProfileDetailService userDetails;
	private final CurrentPlayingService currentPlaying;

	@GetMapping(value = ApiPath.REDIRECT, produces = MediaType.TEXT_HTML_VALUE)
	public String redirectToCallbackSuccess(final HttpSession session, final Model model) {

		String token = (String) session.getAttribute("accessToken");
		Long expiration_time = (Long) session.getAttribute("expiration_time");
		if (token == null || !isTokenValid(expiration_time)) {
			model.addAttribute("url", spotifyUrlService.getAuthorizationURL());
			return Template.INDEX;
		}
		model.addAttribute("accessToken", token);
		model.addAttribute("userName", userDetails.getUsername(token));

		try {
			model.addAttribute("currentPlaying", currentPlaying.getCurrentPlaying(token));
			model.addAttribute("display", 1);
		} catch (NoTrackPlayingException exception) {
			model.addAttribute("display", 0);
		}
		return Template.CALLBACK_SUCCESS;
	}
	boolean isTokenValid(Long expiration_time) {
		return System.currentTimeMillis() < expiration_time;
	}

}

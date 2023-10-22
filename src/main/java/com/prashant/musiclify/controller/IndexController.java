package com.prashant.musiclify.controller;

import com.prashant.musiclify.constant.ApiPath;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prashant.musiclify.constant.Template;
import com.prashant.musiclify.service.SpotifyUrlService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class IndexController {

	private final SpotifyUrlService spotifyUrlService;

	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String showIndex(final HttpSession session, final Model model) {
		String token = (String) session.getAttribute("accessToken");
		if (token != null)
			return "redirect:"+ ApiPath.REDIRECT;
		model.addAttribute("url", spotifyUrlService.getAuthorizationURL());
		return Template.INDEX;
	}

}

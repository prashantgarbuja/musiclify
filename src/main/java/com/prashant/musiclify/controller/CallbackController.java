package com.prashant.musiclify.controller;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.service.AccessTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
public class CallbackController {

	private final AccessTokenService accessToken;
//	private final ProfileDetailService userDetails;
//	private final CurrentPlayingService currentPlaying;

	@GetMapping(value = ApiPath.CALLBACK)
	public RedirectView handleCallback(@RequestParam(value = "code", required = false) final String code,
									   @RequestParam(value = "error", required = false) final String error,
									   HttpServletRequest request) {

		HttpSession session = request.getSession(); // Get HttpSession from the HttpServletRequest
		//System.out.println("Code is "+code+" Error is "+error+" Model is "+model+" Session is "+session);

		if (error != null)
			return new RedirectView(ApiPath.CALLBACK_FAILURE);

		session.setAttribute("code", code);
		String token = accessToken.getToken(code);

		session.setAttribute("accessToken", token);

		return new RedirectView(ApiPath.REDIRECT);
	}
}

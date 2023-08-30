package com.prashant.musiclify.controller;

//import javax.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.constant.Template;
import com.prashant.musiclify.exception.InvalidSearchException;
import com.prashant.musiclify.service.SearchResultService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ItemSearchController {

	private final SearchResultService searchResults;

	@PostMapping(value = ApiPath.SEARCH, produces = MediaType.TEXT_HTML_VALUE)
	public String showSearchResults(@RequestParam("searchQuery") final String searchQuery, final HttpSession session,
			final Model model) {
		String token = (String) session.getAttribute("accessToken");
		try {
			model.addAttribute("results", searchResults.search(token, searchQuery));
		} catch (InvalidSearchException exception) {
			return Template.SEARCH_ERROR;
		}
		return Template.SEARCH_RESULTS;
	}

}

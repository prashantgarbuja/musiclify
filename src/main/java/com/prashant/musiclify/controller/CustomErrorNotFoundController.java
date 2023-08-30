package com.prashant.musiclify.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.constant.Template;

@Controller
public class CustomErrorNotFoundController implements ErrorController {

	@RequestMapping(value = ApiPath.ERROR, produces = MediaType.TEXT_HTML_VALUE)
	public String handleError() {
		return Template.ERROR;
	}

}
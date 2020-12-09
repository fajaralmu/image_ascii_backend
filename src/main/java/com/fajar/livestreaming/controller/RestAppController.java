package com.fajar.livestreaming.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fajar.livestreaming.dto.WebRequest;
import com.fajar.livestreaming.dto.WebResponse;

@CrossOrigin
@RestController(value = "api/app")
public class RestAppController extends BaseController{
	Logger log = LoggerFactory.getLogger(RestAppController.class);  
	 
	
	public RestAppController() {
		log.info("------------------RestAppController #1-----------------");
	}
	
	@PostConstruct
	public void init() {
//		LogProxyFactory.setLoggers(this);
	}
	
	@PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse generate(@RequestBody WebRequest request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) { 
//		realtimeUserService.disconnectLiveStream(request);
		return new WebResponse();
	}	
}

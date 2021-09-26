package com.app.controller;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.model.RestUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/consume")
public class ConsumerContoller{

	// Public API: https://gorest.co.in/
	private static final String URL = "https://gorest.co.in/public/v1/users/";
	private static final String ACCESS_TOKEN = "90ca8531644fa5bfa2324fa42ce9e5c25e528f097c97c32f8c4e5c29c23e32ad";

	@GetMapping("/users")
	public ResponseEntity<String> getUsers() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
		return response;
	}

	@GetMapping("/users/list")
	public ResponseEntity<String> getUserList() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);

		return responseEntity;

//		JSONObject jsonObject = new JSONObject(responseEntity.getBody());
//		JSONArray array = (JSONArray) jsonObject.get("data");
//		return ResponseEntity.status(HttpStatus.OK).body(array.toString());
	}

	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestUser getUser(@PathVariable("id") int useId) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL + useId, String.class);

		JSONObject jsonObject = new JSONObject(responseEntity.getBody());
		String str = jsonObject.get("data").toString();

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(str, RestUser.class);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addUser(@RequestBody RestUser user) {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
//		headers.set("Authorization", ACCESS_TOKEN);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "bearer " + ACCESS_TOKEN);

		HttpEntity<RestUser> entityReq = new HttpEntity<RestUser>(user, headers);
		
		ResponseEntity<String> entity = restTemplate.postForObject(URL, entityReq, ResponseEntity.class);
		System.out.println(entity);
		return ResponseEntity.status(HttpStatus.OK).body("User added successfully");
	}
}

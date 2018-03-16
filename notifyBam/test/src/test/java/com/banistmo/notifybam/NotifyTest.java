package com.banistmo.notifybam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;

@Test
public class NotifyTest extends TestNGCitrusTestDesigner {
	 
	@Autowired
	private HttpClient notify;

	 @CitrusTest
	 public void testPostOnline() {
		 	ClassPathResource request = new ClassPathResource("request.json");
	        http().client(notify).send().post().contentType(MediaType.APPLICATION_JSON_VALUE).payload(request);
			http().client(notify).receive().response(HttpStatus.OK).messageType(MessageType.JSON);
	 }
	 

	 @CitrusTest
	 public void testPost() {
		 	ClassPathResource request = new ClassPathResource("request.json");
	        http().client(notify).send().post().contentType(MediaType.APPLICATION_JSON_VALUE).payload(request);
			http().client(notify).receive().response(HttpStatus.OK).messageType(MessageType.JSON).jsonPath("$.status.statusCode", "200");
	 }
	 

	 @CitrusTest
	 public void testPostBadRequest() {
		 ClassPathResource request = new ClassPathResource("badrequest.json");
	     http().client(notify).send().post().contentType(MediaType.APPLICATION_JSON_VALUE).payload(request);
		 http().client(notify).receive().response(HttpStatus.OK).messageType(MessageType.JSON).jsonPath("$.message","should have required property 'acctId'");
	 }
}

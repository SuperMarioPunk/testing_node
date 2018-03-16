package com.banistmo.notifybam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;

@Configuration
public class EndpointConfig {
	
    private Logger log = LoggerFactory.getLogger(EndpointConfig.class);

    private String endpoint;
    
    @Value("${CITRUS_ENDPOINTURL}")
	private String citrusEndpoint;
    
    @Bean
    public HttpClient client() {
    	log.info("Endpoint: " + endpoint);
    	log.info("Endpoint Property: " + System.getProperty("endpointurl"));
    	if (endpoint == null || "".equals(endpoint)) endpoint =System.getProperty("endpointurl");
    	log.info("Endpoint Env: " + System.getenv("endpointurl"));
    	if (endpoint == null || "".equals(endpoint)) endpoint =System.getenv("endpointurl");
    	log.info("Endpoint citrus:"+ citrusEndpoint);
    	if (endpoint == null || "".equals(endpoint)) endpoint = citrusEndpoint;
    	//String endpoint = System.getProperty("endpointurl");
		//System.setProperty("endpoint","https://fh418vg5x1.execute-api.us-east-1.amazonaws.com/dev/api/notifyBam");
        return CitrusEndpoints.http().client().requestUrl(endpoint).build();
    }
}

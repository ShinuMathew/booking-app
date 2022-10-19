package com.haldoc.assessment.framework;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestBuilder {
		
	RequestSpecification request;
	Method method;	
	
	public RequestBuilder() {
		this.request = RestAssured.given();
	}
	
	public RequestBuilder setBaseUri(String baseUri) {
		this.request = this.request.baseUri(baseUri);
		return this;
	}
	
	public RequestBuilder setEndpoint(String endpoint) {
		this.request = this.request.basePath(endpoint);
		return this;
	}
	
	public RequestBuilder setHeaders(Map<String, Object> headers) {
		this.request = this.request.headers(headers);
		return this;
	}
	
	public RequestBuilder addParams(String name, String value) {
		this.request = this.request.params(name, value);
		return this;
	}
	
	public RequestBuilder setBody(String requestBody) {
		this.request = this.request.body(requestBody);
		return this;
	}
	
	public RequestBuilder setCookies(Map<String, Object> cookies) {
		this.request = this.request.cookies(cookies);
		return this;
	}
	
	
	public RequestBuilder setMethod(Method method) {
		this.method = method;
		return this;
	}
	

	public RequestSpecification buildRequest() {
		return this.request;
	}
	
	public Response sendRequest(RequestSpecification request) {		
		switch (this.method) {
		case GET:
			return this.request.get();			
		case POST:
			return this.request.post();			
		case PUT:
			return this.request.put();			
		case DELETE:
			return this.request.delete();	
		default :
			throw new UnsupportedOperationException("Not a valid method");
		}
	}

}

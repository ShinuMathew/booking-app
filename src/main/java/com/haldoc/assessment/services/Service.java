package com.haldoc.assessment.services;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haldoc.assessment.framework.RequestBuilder;
import com.haldoc.assessment.objects.Booking;
import com.haldoc.assessment.objects.Bookingdates;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Service {

	public Response createBooking(Map<String, Object> user) throws Exception {
		RequestBuilder reqBuilder = new RequestBuilder();
		ObjectMapper mapper = new ObjectMapper();
		Booking booking = Booking.builder()
				.firstname(user.get("firstname").toString())
				.lastname(user.get("lastname").toString())
				.totalprice(Integer.valueOf(user.get("lastname").toString()))
				.depositpaid(Boolean.valueOf(user.get("depositpaid").toString()))
				.bookingdates(Bookingdates.builder()
						.checkin(user.get("checkin").toString())
						.checkout(user.get("checkout").toString()).build())
				.additionalneeds(user.get("additionalneeds").toString()).build();
		String reqBody = mapper.writeValueAsString(booking);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Content-Type", "application/json");
		RequestSpecification request = reqBuilder.setBaseUri("https://restful-booker.herokuapp.com")
				.setEndpoint("/booking").setHeaders(headers).setBody(reqBody).setMethod(Method.POST).buildRequest();
		return reqBuilder.sendRequest(request);
	}

}

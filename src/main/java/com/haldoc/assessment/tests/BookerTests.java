package com.haldoc.assessment.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haldoc.assessment.framework.RequestBuilder;
import com.haldoc.assessment.objects.AuthRequest;
import com.haldoc.assessment.objects.AuthResponse;
import com.haldoc.assessment.objects.Booking;
import com.haldoc.assessment.objects.BookingResponse;
import com.haldoc.assessment.objects.Bookingdates;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookerTests extends BaseTest {

	private int bookingId;
	private String token;

	@Test(priority = 1, enabled = true)
	public void createBooking() throws Exception {
		Map<String, Object> user = this.dataProvider.getUserData("user1");
		Response response = this.service.createBooking(user);
		Assert.assertEquals(response.statusCode(), 200);
		BookingResponse bookingResponse = this.mapper.readValue(response.body().asString(), BookingResponse.class);
		Assert.assertNotEquals(bookingResponse.getBookingid(), null);
		this.bookingId = bookingResponse.getBookingid();
	}

	@Test(priority = 2, enabled = true)
	public void getBooking() throws Exception {
		RequestBuilder reqBuilder = new RequestBuilder();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Content-Type", "application/json");
		RequestSpecification request = reqBuilder.setBaseUri("https://restful-booker.herokuapp.com")
				.setEndpoint("/booking").setHeaders(headers).addParams("id", String.valueOf(this.bookingId))
				.setMethod(Method.GET).buildRequest();
		Response response = reqBuilder.sendRequest(request);
		Assert.assertEquals(response.statusCode(), 200);

		Booking bookingResponse = mapper.readValue(response.body().asString(), Booking.class);

		Assert.assertEquals(bookingResponse.getFirstname(), "Jim");
		Assert.assertEquals(bookingResponse.getLastname(), "Brown");
		Assert.assertEquals(bookingResponse.getTotalprice(), 111);
		Assert.assertEquals(bookingResponse.isDepositpaid(), true);
		Assert.assertEquals(bookingResponse.getAdditionalneeds(), "Breakfast");
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckin(), "2018-01-01");
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckout(), "2019-01-01");
	}

	@Test(priority = 3, enabled = true)
	public void auth() throws Exception {
		RequestBuilder reqBuilder = new RequestBuilder();
		ObjectMapper mapper = new ObjectMapper();
		AuthRequest authRequest = AuthRequest.builder().username("admin").password("password123").build();
		String body = mapper.writeValueAsString(authRequest);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Content-Type", "application/json");
		RequestSpecification request = reqBuilder.setBaseUri("https://restful-booker.herokuapp.com")
				.setEndpoint("/auth").setHeaders(headers).setBody(body).setMethod(Method.POST).buildRequest();
		Response response = reqBuilder.sendRequest(request);
		Assert.assertEquals(response.statusCode(), 200);

		AuthResponse authResponse = mapper.readValue(response.body().asString(), AuthResponse.class);
		Assert.assertNotEquals(authResponse.getToken(), null);
		this.token = authResponse.getToken();
	}

	@Test(priority = 4, enabled = true)
	public void updateBooking() throws Exception {
		RequestBuilder reqBuilder = new RequestBuilder();
		ObjectMapper mapper = new ObjectMapper();
		Booking booking = Booking.builder().firstname("Jim1").lastname("Brown1").totalprice(121).depositpaid(true)
				.bookingdates(Bookingdates.builder().checkin("2018-01-01").checkout("2019-01-01").build())
				.additionalneeds("Breakfast").build();
		String reqBody = mapper.writeValueAsString(booking);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Content-Type", "application/json");
		headers.put("Cookie", "token=" + this.token);
		RequestSpecification request = reqBuilder.setBaseUri("https://restful-booker.herokuapp.com")
				.setEndpoint("/booking" + this.bookingId).setHeaders(headers).setBody(reqBody).setMethod(Method.PUT)
				.buildRequest();
		Response response = reqBuilder.sendRequest(request);
		Assert.assertEquals(response.statusCode(), 200);

		Booking bookingResponse = mapper.readValue(response.body().asString(), Booking.class);

		Assert.assertEquals(bookingResponse.getFirstname(), "Jim1");
		Assert.assertEquals(bookingResponse.getLastname(), "Brown1");
		Assert.assertEquals(bookingResponse.getTotalprice(), 121);
		Assert.assertEquals(bookingResponse.isDepositpaid(), true);
		Assert.assertEquals(bookingResponse.getAdditionalneeds(), "Breakfast");
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckin(), "2018-01-01");
		Assert.assertEquals(bookingResponse.getBookingdates().getCheckout(), "2019-01-01");
	}

}

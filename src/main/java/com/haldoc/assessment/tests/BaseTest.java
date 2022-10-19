package com.haldoc.assessment.tests;

import org.testng.annotations.BeforeTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haldoc.assessment.dataprovider.DataProvider;
import com.haldoc.assessment.services.Service;

public class BaseTest {

	Service service;
	ObjectMapper mapper;
	DataProvider dataProvider;

	@BeforeTest
	public void setup() {
		this.service = new Service();
		this.mapper = new ObjectMapper();
		this.dataProvider = new DataProvider();
	}

}

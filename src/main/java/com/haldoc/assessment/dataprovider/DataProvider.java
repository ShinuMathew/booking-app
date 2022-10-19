package com.haldoc.assessment.dataprovider;

import java.util.HashMap;
import java.util.Map;

public class DataProvider {

	public Map<String, Object> getUserData(String user) {
		Map<String, Object> userData = new HashMap<String, Object>();
		switch (user) {
		case "user1":
			userData.put("firstname", "Jim");
			userData.put("lastname", "Brown");
			userData.put("totalprice", 111);
			userData.put("depositpaid", true);
			userData.put("checkin", "2018-01-01");
			userData.put("checkout", "2019-01-01");
			userData.put("additionalneeds", "Breakfast");
			return userData;
		case "user2":
			userData.put("firstname", "Mark");
			userData.put("lastname", "White");
			userData.put("totalprice", 923);
			userData.put("depositpaid", false);
			userData.put("checkin", "2018-01-01");
			userData.put("checkout", "2019-01-01");
			userData.put("additionalneeds", "Dinning");
			return userData;

		default:
			throw new UnsupportedOperationException("Invalid User provided");
		}
	}

}

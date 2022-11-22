package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadAllProduct {
	String baseURI = "https://techfios.com/api-prod/api/product";
	@Test
	public void readAllProduct() {
		
		/*
		 * given: all input
		 * details(baseURI,Headers,Authorization,Payload/Body,QueryParameters) when:
		 * submit api requests(Http method,Endpoint/Resource) then: validate
		 * response(status code, Headers, responseTime, Payload/Body) // 01.
		 * ReadAllProducts // HTTP method: GET //
		 * baseURI=https://techfios.com/api-prod/api/product // endPoint/resource=
		 * /read.php // Header/s: // Content-Type=application/json; charset=UTF-8 //
		 * Authorization: // basic auth = username,password // StatusCode=200
		 */
		Response response = // 'response'variable coming from import io.restassured.response.Response;
				given() // import static io.restassured.RestAssured.*;
						.baseUri(baseURI)
						.header("Content-Type", "application/json; charset=UTF-8")
						.auth().preemptive().basic("demo@techfios.com", "abc123").
				when()
				     .get("/read.php")
			   .then()
			         .extract().response();
		int responsCode = response.getStatusCode();                      // response statuscode validation.
		System.out.println("staus code : " + responsCode);
		Assert.assertEquals(responsCode, 200);

		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS); // respone time validation.
		System.out.println("ResponseTime : " + responseTime);
		if (responseTime <= 2000) {
			System.out.println("Response Time within rang.");
		}else {
			System.out.println("Response Time within not range");
		}

		String responseHeader = response.getHeader("Content-Type");  //response Header validation
		System.out.println("ResponsHeader : " + responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");
		
	    String responseBOdy = response.getBody().asString();         //responseBody validation
	    System.out.println("ResponseBody : " + responseBOdy);
	    
	    JsonPath jp = new JsonPath(responseBOdy);
	    String firstProductID = jp.getString("records[0].id"); 
		System.out.println("ResponseFirstProductID :"+ firstProductID);
		if (firstProductID !=null) {
			System.out.println("ResponseBody contain firstProductID");
		}else {
			System.out.println("ResponseBody not contain any ProductID");
		}
		
		
		
		
		
		
		
		

	}

}

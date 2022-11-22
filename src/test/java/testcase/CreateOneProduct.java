package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateOneProduct {
	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\CreatePayload.json";
	HashMap<String, String> createPayload;
	String firstProductID;
	String readOneProductId;

	public Map<String, String> createPayloadMap() {
		createPayload = new HashMap<String, String>();
		createPayload.put("name", "doll pillow 3.0");
		createPayload.put("price", "80");
		createPayload.put("description", "The best pillow for amazing programmers.");
		createPayload.put("category_id", "2");
		createPayload.put("created_name", "Electronics");

		return createPayload;

	}

	@Test(priority = 1)
	public void createOneProduct() {

		/*
		 * given: all input details(baseURI,Headers,Authorization,Payload/Body,QueryParameters) 
		 * when:submit api requests(Http method,Endpoint/Resource)
		 * then: validateresponse(status code, Headers, responseTime, Payload/Body) 
		 * o3 CreateOneProduct
		 *  HTTP method: POST BaseURL =https://techfios.com/api-prod/api/produc 
		 *  EndPoint/resource = /create.php
		 * Header/s: Content-Type=application/json; charset=UTF-8 Authorization:
		 *  basic auth = username,password , Status Code: 201
		 * 
		 * 
		 * Payload/Body:
		 * { "name" : "Arya doll pillow 3.0",
		 *  "price" : "800",
		 * "description" : "The best pillow for amazing programmers.", 
		 * "category_id" :2
		 * "created_name" : "Electronics" }
		 * 
		 */
		Response response = // 'response'variable coming from import io.restassured.response.Response;
				given() // import static io.restassured.RestAssured.*;
						.baseUri(baseURI).header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
						.basic("demo@techfios.com", "abc123")
//						.body(new File(filePath)).
						.body(createPayloadMap()).

						when().post("/create.php").
						then().extract().response();
		int responsCode = response.getStatusCode(); // response statuscode validation.
		System.out.println("staus code : " + responsCode);
		Assert.assertEquals(responsCode, 201);

		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS); // respone time validation.
		System.out.println("ResponseTime : " + responseTime);
		if (responseTime <= 2000) {
			System.out.println("Response Time within rang.");
		} else {
			System.out.println("Response Time within not range");
		}

		String responseHeader = response.getHeader("Content-Type"); // response Header validation
		System.out.println("ResponsHeader : " + responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");

		String responseBOdy = response.getBody().asString(); // responseBody validation
		System.out.println("ResponseBody : " + responseBOdy);

		JsonPath jp = new JsonPath(responseBOdy);
		String ProductMessage = jp.getString("message");
		System.out.println("CreateProductMessage:" + ProductMessage);
		Assert.assertEquals(ProductMessage, "Product was created.");

	}

	@Test(priority = 2)
	public void readAllProduct() {
		Response response = // 'response'variable coming from import io.restassured.response.Response;
				given() // import static io.restassured.RestAssured.*;
						.baseUri(baseURI).header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
						.basic("demo@techfios.com", "abc123").when().get("/read.php").
						 then().extract().response();
		int responsCode = response.getStatusCode(); // response statuscode validation.
		System.out.println("staus code : " + responsCode);
		Assert.assertEquals(responsCode, 200);

		String responseBOdy = response.getBody().asString(); // responseBody validation
		JsonPath jp = new JsonPath(responseBOdy);
		firstProductID = jp.getString("records[0].id");
		System.out.println("ResponseFirstProductID :" + firstProductID);
		if (firstProductID != null) {
			System.out.println("ResponseBody contain firstProductID");
		} else {
			System.out.println("ResponseBody not contain any ProductID");
		}
}
	@Test(priority = 3)
	public void readOneProduct() {
	readOneProductId= firstProductID;
	Response response = // 'response'variable coming from import io.restassured.response.Response;
			given() // import static io.restassured.RestAssured.*;
					.baseUri(baseURI)
					.header("Content-Type", "application/json")
					.auth().preemptive().basic("demo@techfios.com", "abc123")
					.queryParam("id", readOneProductId).                      //ReadOneProduct that why queryparameter
			when()
			     .get("/read_one.php")
		   .then()
		         .extract().response();
	int responsCode = response.getStatusCode();                      // response statuscode validation.
	System.out.println("staus code : " + responsCode);
	Assert.assertEquals(responsCode, 200);

	
    String responseBOdy = response.getBody().asString();         //jsonBody convarted to string by asString().
    System.out.println("Read One ProductID: " + responseBOdy);
    
    JsonPath jp = new JsonPath(responseBOdy);                    //Stringbody convated to JsonBody by jp object
    String actualProductName = jp.getString("name"); 
    System.out.println("Actual product name :"+ actualProductName);
 
    
    String actualProductPrice= jp.getString("price"); 
    System.out.println("Actual product price :"+ actualProductPrice); 
    
    String actualProductDescription = jp.getString("description"); 
    System.out.println("Actual product description :"+ actualProductDescription);         //responseBody validation
	
	
    JsonPath jp2 = new JsonPath(new File(filePath));    //by file data
    String expectedName = jp2.get("name");
    System.out.println("Expected name by FileData : "+expectedName);
    
    
	String expectedProductName =createPayloadMap().get("name") ;
	System.out.println("Expected product name :"+ expectedProductName);
	String expectedProductPrice =createPayloadMap().get("price") ;
	String expectedProductDescription =createPayloadMap().get("description") ;
	
	Assert.assertEquals(actualProductName, expectedProductName);
	

	
	
	}
}

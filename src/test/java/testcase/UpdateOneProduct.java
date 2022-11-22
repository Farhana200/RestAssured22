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

public class UpdateOneProduct {
	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\CreatePayload.json";
	HashMap<String, String> updatePayload;
	String updateProductID;
	String readOneProductId;

	public Map<String, String> updatePayloadMap() {
		updatePayload = new HashMap<String, String>();
		updatePayload.put("id", "5908");
		updatePayload.put("name", "Amazing Pillow 8.0 By MD");
		updatePayload.put("price", "300");
		updatePayload.put("description", "The best super  pillow for amazing programmers.");
		updatePayload.put("category_id", "2");
		updatePayload.put("created_name", "Electronics");

		return updatePayload;

	}

	@Test(priority = 1)
	public void updateOneProduct() {

		/*
		 * o4. UpdatedOneProduct
			HTTP method: put
			BaseURL =https://techfios.com/api-prod/api/produc 
			EndPoint/resource =/update.php
			Header/s:
			Content-Type=application/json; charset=UTF-8
			Authorization:
			basic auth = username,password
			Status Code: 200

		 * 
		 * Payload/Body:
		 * {
		    "id": "5908",
		    "name": "Amazing Pillow 8.0 By MD",
		    "description": "The best updated pillow for amazing programmers.",
		    "price": "300",
		    "category_id": "2",
		    "category_name": "Electronics"
}
		 * 
		 */
		Response response = // 'response'variable coming from import io.restassured.response.Response;
				given() // import static io.restassured.RestAssured.*;
						.baseUri(baseURI).header("Content-Type", "application/json").auth().preemptive()
						.basic("demo@techfios.com", "abc123")
//						.body(new File(filePath)).
						.body(updatePayloadMap()).

						when().put("/update.php").
						then().extract().response();
		int responsCode = response.getStatusCode(); // response statuscode validation.
		System.out.println("staus code : " + responsCode);
		Assert.assertEquals(responsCode, 200);

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
		
        JsonPath jp = new JsonPath(responseBOdy);
		String updateMessage = jp.getString("message");
		System.out.println("UpdatedProductMessage:" + updateMessage);
		Assert.assertEquals(updateMessage, "Product was updated.");

	}

	
	@Test(priority = 2)
	public void readOneProduct() {
	updateProductID = updatePayloadMap().get("id");
	readOneProductId = updateProductID;
	
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
    
    
	String expectedProductName =updatePayloadMap().get("name") ;
	System.out.println("Expected product name :"+ expectedProductName);
	String expectedProductPrice =updatePayloadMap().get("price") ;
	String expectedProductDescription =updatePayloadMap().get("description") ;
	
	Assert.assertEquals(actualProductName, expectedProductName);
	

	
	
	}
}

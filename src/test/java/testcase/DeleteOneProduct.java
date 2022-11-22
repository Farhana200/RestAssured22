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

public class DeleteOneProduct {
	String baseURI = "https://techfios.com/api-prod/api/product";
	String filePath = "src\\main\\java\\data\\CreatePayload.json";
	HashMap<String, String> deleteProductID;
	String deleteProductId ;
	String readOneProductId;

	public Map<String, String> deletePayloadMap() {
		deleteProductID = new HashMap<String, String>();
		deleteProductID.put("id", "5940");
		
		return deleteProductID;

	}

	@Test(priority = 1)
	public void deleteOneProduct() {

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
						.body(deletePayloadMap()).

						when().delete("/delete.php").
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
		String deleteMessage = jp.getString("message");
		System.out.println("Deleted Product Message:" + deleteMessage);
		Assert.assertEquals(deleteMessage, "Product was deleted.");

	}

	
	@Test(priority = 2)
	public void readOneProduct() {
		deleteProductId = deletePayloadMap().get("id");
	    readOneProductId = deleteProductId;
	
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
	Assert.assertEquals(responsCode, 404);

	
    String responseBOdy = response.getBody().asString();         //jsonBody convarted to string by asString().
    System.out.println("Read One Product body: " + responseBOdy);
    
    
    JsonPath jp = new JsonPath(responseBOdy);                    //Stringbody convated to JsonBody by jp object
    String actualProductMassege = jp.getString("message"); 
    System.out.println("Actual product message :"+ actualProductMassege);
    Assert.assertEquals(actualProductMassege, "Product does not exist.");
    
    
 
    
	

	
	
	}
}

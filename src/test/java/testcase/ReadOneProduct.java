package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadOneProduct {
	String baseURI = "https://techfios.com/api-prod/api/product";

	@Test
	public void readOneProduct() {
	
		/* given: all input details(baseURI,Headers,Authorization,Payload/Body,QueryParameters)
		   when:  submit api requests(Http method,Endpoint/Resource)
		   then:  validate response(status code, Headers, responseTime, Payload/Body) 
		        o2. ReadindOneProduct
				HTTP method: GET
				BaseURL =https://techfios.com/api-prod/api/produc 
				EndPoint/resource = /read_one.php
				Header/s:
				Content-Type=application/json
				QueryParam:
				id=5573 
				Authorization:
				basic auth = username,password
				Status Code: 200

		 */
		Response response = // 'response'variable coming from import io.restassured.response.Response;
				given() // import static io.restassured.RestAssured.*;
						.baseUri(baseURI)
						.header("Content-Type", "application/json")
						.auth().preemptive().basic("demo@techfios.com", "abc123")
						.queryParam("id", "5925").                      //ReadOneProduct that why queryparameter
				when()
				     .get("/read_one.php")
			   .then()
			         .extract().response();
		int responsCode = response.getStatusCode();                      // response statuscode validation.
		System.out.println("staus code : " + responsCode);
		Assert.assertEquals(responsCode, 200);

		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);     // respone time validation.
		System.out.println("ResponseTime : " + responseTime);
		if (responseTime <= 2000) {
			System.out.println("Response Time within rang.");
		}else {
			System.out.println("Response Time within not range");
		}

		String responseHeader = response.getHeader("Content-Type");  //response Header validation
		System.out.println("ResponsHeader : " + responseHeader);
		Assert.assertEquals(responseHeader, "application/json");
		
	    String responseBOdy = response.getBody().asString();         //jsonBody convarted to string by asString().
	    System.out.println("ResponseBody : " + responseBOdy);
	    
	    JsonPath jp = new JsonPath(responseBOdy);                    //Stringbody convated to JsonBody by jp object
	    String ProductID = jp.getString("id");    
		System.out.println("ResponseProductID :"+ ProductID);         //responseBody validation
		Assert.assertEquals(ProductID, "5925");
			 
		
		String ProductName = jp.getString("name");    
		System.out.println("ResponseProductName :"+ ProductName);  
		Assert.assertEquals(ProductName, "Audry doll pillow 3.0");
		
		String ProductDescription = jp.getString("description");    
		System.out.println("ProductDescription :"+ ProductDescription); 
		Assert.assertEquals(ProductDescription, "The Updated pillow for amazing programmers.");
//		if (firstProductID !=null) { 
//			System.out.println("ResponseBody contain firstProductID");
//		}else {
//			System.out.println("ResponseBody not contain any ProductID");
//		}
		
		
		
		
		
		
		
		

	}

}

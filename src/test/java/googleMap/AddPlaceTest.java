package googleMap;


import static io.restassured.RestAssured.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.APIDetails;

import static org.hamcrest.Matchers.*;

public class AddPlaceTest {
	
	@Test(priority = 1)
	public static void addPlaceGoogleMap(){
		// validate if Add Place API is workimg as expected 
		//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
		
		//given - all input details 
		//when - Submit the API -resource,http method
		//Then - validate the response
		 
		//------------------ADD place-----------------------------------------------------------//
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String addPlaceRespnse=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(APIDetails.getPlaceAPI()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(addPlaceRespnse);
		JsonPath addJS =  new JsonPath(addPlaceRespnse);
		String placeID = addJS.getString("place_id");
		Assert.assertEquals(addJS.getString("status"), "OK");
		//----------------------------------------------------------------------------------------//
		
		//---------------------------------Get Place----------------------------------------------//
		
		given().log().all().queryParam("place_id", placeID).queryParam("key", "qaclick123")
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200);
		//----------------------------------------------------------------------------------------//
		
	}
	
	@Test(priority = 2)
	public static void DeletePlaceGoogleMap(){
		//------------------ADD place-----------------------------------------------------------//
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String addPlaceRespnse=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(APIDetails.getPlaceAPI()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(addPlaceRespnse);
		JsonPath addJS =  new JsonPath(addPlaceRespnse);
		String placeID = addJS.getString("place_id");
		Assert.assertEquals(addJS.getString("status"), "OK");
		//----------------------------------------------------------------------------------------//
				
		//---------------------------------Delete Place----------------------------------------------//
		String deleteResponse = given().log().all().queryParam("key", "qaclick123")
		.body("{\r\n" + 
				"    \"place_id\":\""+placeID+"\"\r\n" + 
				"}")
		.when().delete("maps/api/place/delete/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath deleteJS = new JsonPath(deleteResponse);
		String deleteMsg = deleteJS.getString("status");
		Assert.assertEquals(deleteMsg, "OK");
		//----------------------------------------------------------------------------------------//
	}
	
	@Test(priority = 3)
	public static void updatePlaceGoogleMap(){
		//------------------ADD place-----------------------------------------------------------//
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String addPlaceRespnse=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(APIDetails.getPlaceAPI()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(addPlaceRespnse);
		JsonPath addJS =  new JsonPath(addPlaceRespnse);
		String placeID = addJS.getString("place_id");
		Assert.assertEquals(addJS.getString("status"), "OK");
		//----------------------------------------------------------------------------------------//
		
		//---------------------------------Update Place----------------------------------------------//
		String updateRespone = given().log().all().queryParam("key", "qaclick123")
		.body("{\r\n" + 
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\"Kadikoy, Istanbul\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath updateJS = new JsonPath(updateRespone);
		String suuceesMsg = updateJS.getString("msg");
		Assert.assertEquals(suuceesMsg, "Address successfully updated");
		//----------------------------------------------------------------------------------------//
}

}

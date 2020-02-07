import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.*;
import org.junit.experimental.theories.DataPoint;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.config.XmlConfig.xmlConfig;
import static org.hamcrest.Matchers.*;

public class ApiTestGet {


    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://httpbin.org").
                build();
    }


    @Test()
    public void testGetAis12() {
        given()
                .spec(requestSpec)
                .when()
                .get("/get?a=12")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.a", equalTo("12"))
                .body("args.a", not(equalTo("3")));
    }

    @Test
    public void testGetAis1QueryParam() {
        given()
                .spec(requestSpec)
                .queryParam("a", "1") //get?a=1
                .when()
                .get("/get")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.a", equalTo("1"));

    }

    @Test
    public void testGetBase64PathParam() {
        given()
                .spec(requestSpec)
                .pathParam("base", "base64")
                .pathParam("value", "SFRUUEJJTiBpcyBhd2Vzb21l")
                .when()
                .get("/{base}/{value}")// /base64/SFRUUEJJTiBpcyBhd2Vzb21l
                .then()
                .log().all()
                .statusCode(200)
                .body("html.body", equalTo("HTTPBIN is awesome"));
    }

    @Test()
    public void testGetAis1andBis2() {
        given()
                .spec(requestSpec)
                .when()
                .get("/get?a=1&b=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.a", equalTo("1"));
    }

    @Test
    public void testPostAis1(){
        given()
                .spec(requestSpec)
                .when()
                .post("post?a=1")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.a", equalTo("1"));
    }

    @Test
    public void testPost(){
        given()
                .spec(requestSpec)
                .formParam("a", Arrays.asList(1,2,4,5))
                .queryParam("b", 2)
                .when()
                .post("post?c=3")
                .then()
                .log().all()
                .statusCode(200);
    }


    @Test
    public void testPostWithJSON(){

        //String a = "{\"phoneNumber\":\"353837986524\", \"messageContent\":\"test\"}"; //json стрингой

        JSONObject jsonObj = new JSONObject()
                .put("phoneNumber","353837986524")
                .put("messageContent","test");

        given()
                .spec(requestSpec)
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("post")
                .then()
                .log().all();
    }

    @Test
    public void testPut(){
        JSONObject jsonObj = new JSONObject()
                .put("phoneNumber","353837986524")
                .put("messageContent","test");
        given()
                .spec(requestSpec)
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .put("put")
                .then()
                .log().all();
    }

    @Test
    public void testDelete(){
        given()
                .spec(requestSpec)
                .when()
                .delete("delete")
                .then()
                .log().all();
    }

    @Test
    public void testGetXML(){
        given()
                .spec(requestSpec)
                .when()
                .get("xml")
                .then()
                .log().all()
                .statusCode(200)
                .body("slideshow.slide[0].title", equalTo("Wake up to WonderWidgets!"))
                .contentType(ContentType.XML);
    }
    

}

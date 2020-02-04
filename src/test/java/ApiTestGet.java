import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
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
    public void testGetAis1() {
        given()
                .spec(requestSpec)
                .when()
                .get("/get?a=1")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.a", equalTo("1"));
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
}

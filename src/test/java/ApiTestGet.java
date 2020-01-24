import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ApiTestGet {
    @Test()
    public void testGet() {
        get("http://httpbin.org/get?a=1").then().body("args.a", equalTo("1"));
    }
}

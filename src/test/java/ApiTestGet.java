import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.*;
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
    public void testGet() {
        given().spec(requestSpec)
                .when().get("/get?a=1")
                .then().body("args.a", equalTo("1")).statusCode(200);
    }
}

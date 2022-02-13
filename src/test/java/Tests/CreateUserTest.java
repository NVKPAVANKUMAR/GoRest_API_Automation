package Tests;

import Utilities.APIServiceUtils;
import Utilities.ReadProps;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static Utilities.ExtentTestManager.startTest;

public class CreateUserTest {

    @BeforeClass
    public void startUp() throws Exception {
        ReadProps.UpdatePropertyFile();
        APIServiceUtils.setBaseURL();
    }

    @Test(priority = 1, description = "create user using_post_api_method_and body_from_json_file", enabled = false)
    public void test_user_created_successfully_using_post_api_body_from_json_file(Method method) throws Exception {
        startTest(method.getName(), method.getName().replace("_", " "));
        Response response = APIServiceUtils.createUserAPIFromJson("CreateUserJsonBody.json");
        Assert.assertEquals(response.getStatusCode(), 201, "status code mismatch");

        String id = response.getBody().jsonPath().getJsonObject("id").toString();
        String name = response.getBody().jsonPath().getJsonObject("name").toString();
        String email = response.getBody().jsonPath().getJsonObject("email").toString();
        String gender = response.getBody().jsonPath().getJsonObject("gender").toString();
        String status = response.getBody().jsonPath().getJsonObject("status").toString();

        String body = Files.readString(Path.of(System.getProperty("user.dir") + "\\src\\test\\java\\Data\\CreateUserJsonBody.json"), StandardCharsets.US_ASCII);
        JsonMapper mapper = new JsonMapper();
        JsonNode node = mapper.readValue(body, JsonNode.class);

        Assert.assertEquals(name, node.get("name").asText(), "name mismatch");
        Assert.assertEquals(email, node.get("email").asText(), "email mismatch");
        Assert.assertEquals(gender, node.get("gender").asText(), "gender mismatch");
        Assert.assertEquals(status, node.get("status").asText(), "status mismatch");
    }

    @Test(priority = 2, description = "create user using_post_api_method_and body_from_prop_file")
    public void test_user_created_successfully_using_post_api_body_from_properties_file(Method method) throws Exception {
        startTest(method.getName(), method.getName().replace("_", " "));
        Response response = APIServiceUtils.createUserAPIFromProp();
        Assert.assertEquals(response.getStatusCode(), 201, "status code mismatch");

        String id = response.getBody().jsonPath().getJsonObject("id").toString();
        String name = response.getBody().jsonPath().getJsonObject("name").toString();
        String email = response.getBody().jsonPath().getJsonObject("email").toString();
        String gender = response.getBody().jsonPath().getJsonObject("gender").toString();
        String status = response.getBody().jsonPath().getJsonObject("status").toString();

        Assert.assertEquals(name, ReadProps.getValue("name"), "name mismatch");
        Assert.assertEquals(email, ReadProps.getValue("email"), "email mismatch");
        Assert.assertEquals(gender, ReadProps.getValue("gender"), "gender mismatch");
        Assert.assertEquals(status, ReadProps.getValue("status"), "status mismatch");
    }

    @Test(priority = 3, description = "create user using_post_api_method_and body_from_faker api")
    public void test_user_created_successfully_using_post_api_using_dynamic_data(Method method) throws Exception {
        startTest(method.getName(), method.getName().replace("_", " "));
        Response response = APIServiceUtils.createRandomUserAPI();
        Assert.assertEquals(response.getStatusCode(), 201, "status code mismatch");

        String id = response.getBody().jsonPath().getJsonObject("id").toString();
        String name = response.getBody().jsonPath().getJsonObject("name").toString();
        String email = response.getBody().jsonPath().getJsonObject("email").toString();
        String gender = response.getBody().jsonPath().getJsonObject("gender").toString();
        String status = response.getBody().jsonPath().getJsonObject("status").toString();

        Assert.assertNotNull(name, "name not found");
        Assert.assertNotNull(email, "email not found");
        Assert.assertNotNull(gender, "gender not found");
        Assert.assertNotNull(status, "status not found");
    }
}
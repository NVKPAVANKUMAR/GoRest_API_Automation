package Tests;

import Utilities.APIServiceUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static Utilities.ExtentTestManager.startTest;

public class UpdateUserTest {
    @BeforeClass
    public void login() throws Exception {
        APIServiceUtils.setBaseURL();
    }

    @Test(priority = 1, description = "update an existing user api test")
    public void test_user_details_update_using_put_api(Method method) throws Exception {
        startTest(method.getName(), method.getName().replace("_", " "));
        Response response_Post = APIServiceUtils.createRandomUserAPI();
        String id = response_Post.getBody().jsonPath().getJsonObject("id").toString();
        Response response_Put = APIServiceUtils.updateUserAPI(id);
        Assert.assertEquals(response_Put.getStatusCode(), 200, "status code mismatch");

        String name_Posted = response_Post.getBody().jsonPath().getJsonObject("name").toString();
        String name_Updated = response_Put.getBody().jsonPath().getJsonObject("name").toString();
        String email = response_Put.getBody().jsonPath().getJsonObject("email").toString();
        String gender = response_Put.getBody().jsonPath().getJsonObject("gender").toString();
        String status = response_Put.getBody().jsonPath().getJsonObject("status").toString();

        Assert.assertNotEquals(name_Posted, name_Updated, "name not found");
        Assert.assertNotNull(email, "email not found");
        Assert.assertNotNull(gender, "gender not found");
        Assert.assertNotNull(status, "status not found");
    }
}

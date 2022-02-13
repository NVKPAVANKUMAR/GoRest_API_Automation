package Tests;

import Utilities.APIServiceUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static Utilities.ExtentTestManager.startTest;

public class DeleteUserTest {

    @BeforeClass
    public void login() throws Exception {
        APIServiceUtils.setBaseURL();
    }

    @Test(priority = 1, description = "delete user api test")
    public void test_single_user_deletion_using_delete_api(Method method) throws Exception {
        startTest(method.getName(), method.getName().replace("_", " "));
        Response response_Post = APIServiceUtils.createRandomUserAPI();
        String id = response_Post.getBody().jsonPath().getJsonObject("id").toString();
        Response response = APIServiceUtils.DeleteUserAPI(id);
        Assert.assertEquals(response.getStatusCode(), 204, "status code mismatch");
    }
}
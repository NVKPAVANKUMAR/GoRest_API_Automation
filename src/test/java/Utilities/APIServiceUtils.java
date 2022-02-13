package Utilities;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class APIServiceUtils {
    public static String timestamp = new SimpleDateFormat("ddmmhhmmss").format(new Date());
    static String[] genderSet = {"male", "female"};
    static String[] statusSet = {"inactive", "active"};
    public static RequestSpecification request;
    public static Response response;
    public static Faker faker = new Faker();

    public static void setBaseURL() throws IOException {
        RestAssured.baseURI = ReadProps.getValue("base_url");
    }

    public static RequestSpecification initRequest() {
        response = null;
        request = null;
        request = RestAssured.given();
        return request;
    }

    public static String getRequestDetails(RequestSpecification request) {
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        return "url: " + queryable.getURI() + "<br />" +
                "method: " + queryable.getMethod() + "<br />" +
                "body: " + queryable.getBody() + "<br />" +
                "headers: " + queryable.getHeaders() + "<br />";
    }

    public static Response createUserAPIFromJson(String jsonFileName) throws IOException, JSONException {
        request = initRequest();
        // set header - content type and auth
        request.header("Authorization", "Bearer " + ReadProps.getValue("access_token"));
        request.header("Content-Type", "application/json");
        // build body - method 2
        String body = Files.readString(Path.of(System.getProperty("user.dir") + "\\src\\test\\java\\Data\\" + jsonFileName), StandardCharsets.US_ASCII);
        request.body(body);
        // make post request
        response = request.post("users");
        return response;
    }

    public static Response createUserAPIFromProp() throws IOException, JSONException {
        request = initRequest();
        // set header - content type and auth
        request.header("Authorization", "Bearer " + ReadProps.getValue("access_token"));
        request.header("Content-Type", "application/json");
        // build body - method 1
        JSONObject requestparams = new JSONObject();
        requestparams.put("name", ReadProps.getValue("name"));
        requestparams.put("email", ReadProps.getValue("email"));
        requestparams.put("gender", ReadProps.getValue("gender"));
        requestparams.put("status", ReadProps.getValue("status"));
        request.body(requestparams.toString());
        // make post request
        response = request.post("users");
        return response;
    }

    public static Response createRandomUserAPI() throws IOException, JSONException {
        request = initRequest();
        // set header - content type and auth
        request.header("Authorization", "Bearer " + ReadProps.getValue("access_token"));
        request.header("Content-Type", "application/json");
        // build body
        JSONObject requestparams = new JSONObject();
        requestparams.put("name", faker.name().fullName());
        requestparams.put("email", faker.internet().emailAddress());
        requestparams.put("gender", faker.demographic().sex());
        requestparams.put("status", statusSet[new Random().nextInt(statusSet.length)]);
        request.body(requestparams.toString());
        // make post request
        response = request.post("users");
        return response;
    }

    public static Response updateUserAPI(String id) throws IOException, JSONException {
        request = initRequest();
        // set header - content type and auth
        request.header("Authorization", "Bearer " + ReadProps.getValue("access_token"));
        request.header("Content-Type", "application/json");
        // build body
        JSONObject requestparams = new JSONObject();
        requestparams.put("name", faker.name().fullName());
        requestparams.put("email", faker.internet().emailAddress());
        requestparams.put("gender", faker.demographic().sex());
        requestparams.put("status", statusSet[new Random().nextInt(statusSet.length)]);
        request.body(requestparams.toString());
        // make put request
        response = request.put("users/ " + id);
        return response;
    }

    public static Response getAllUserAPI() throws IOException {
        request = initRequest();
        // set header - content type and auth
        request.header("Authorization", "Bearer " + ReadProps.getValue("access_token"));
        request.header("Content-Type", "application/json");
        // make get request
        response = request.get("users");
        return response;
    }

    public static Response getUserAPI(String userId) throws IOException {
        request = initRequest();
        // set header - content type and auth
        request.header("Authorization", "Bearer " + ReadProps.getValue("access_token"));
        request.header("Content-Type", "application/json");
        // make get request
        response = request.get("users/ " + userId);
        return response;
    }

    public static Response DeleteUserAPI(String id) throws IOException {
        request = initRequest();
        // set header - content type and auth
        request.header("Authorization", "Bearer " + ReadProps.getValue("access_token"));
        request.header("Content-Type", "application/json");
        // make delete request
        response = request.delete("users/ " + id);
        return response;
    }
}

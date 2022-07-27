package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class GitHub_Project {

    RequestSpecification requestSpec;

    int id;

    @BeforeClass
    public void setUp(){
        String token = "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";
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com/user/keys")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token "+token)
                .build();

    }

    @Test(priority=1)
    public void  postRequest() {
        String reqBody = "{\"title\": \"TestAPIKey\",\"key\",\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCIrnSsZqa/c91U5f2baiXcsuYq7uT/rCGMi67/APbkQQenbyYQj62yGDMyOcydFFQB0BJu7S7CP1sCk0UQhIyMzv490r40kq0oZlw9y8Ow/nyvfmisMhsIdMoKmK2vmkjETA45qLvXEtO8iPAN1NoaUe/exwsrKr6lEGLL3LdPbhGXsjEY49NY9J++HhaglQ8S7ts3NlbJfrpLrCIMD+TfX5ciS7IVys8R9oYnmP9ic/0EFC9f7ZbwrTXksvO5pX+nA115GtpBvGk/YRcyM+7Luwz9KUdAKntj9UUKCshL3Nh9u6/6rfo1poZCUNoRaw+xc9tGRIr4xZmtmFRK/TtF\"}";
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();

        System.out.println(response.getBody().asString());
        id = response.then().extract().path("id");
        System.out.println("Id is " + id);
        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void GetKey(){

        //Generate Response
        Response response = given().spec(requestSpec)
                .pathParam("KeyId",id)
                .when().get("/{KeyId}");
        System.out.println(response.getBody().asString());
        response.then().statusCode(200);

    }

    @Test(priority = 3)
    public void DeleteKey(){
        //Generate Response
        Response response = given().spec(requestSpec)
                .pathParam("KeyId",id)
                .when().get("/{KeyId}");
        System.out.println(response.getBody().asString());


        //Assertions
        response.then().statusCode(204);

    }
}

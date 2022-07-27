package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //headers
    Map<String,String> reqHeaders = new HashMap<>();
    //Resource Path
    String resourcePath = "/api/users";

    //create the contract
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        //set the headers
        reqHeaders.put("Content-Type" , "application/json");

        //create the json body (no example data so random data on console)
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        return builder.given("Request to create a user")
            .uponReceiving("Request to create a user")
                .method("POST")
                .headers(reqHeaders)
                .path(resourcePath)
                .body(reqResBody)
            .willRespondWith()
                .status(201)
                .body(reqResBody)
            .toPact();
    }
    @Test
    @PactTestFor(providerName = "UserProvider" , port = "8282")
    public void consumerSideTest(){
       //Set BaseURI
        String baseURI = "http://localhost:8282";

        //Request body
                Map<String, Object> reqBody = new HashedMap<>();
                reqBody.put("id",123);
                reqBody.put("firstName","Saahil");
                reqBody.put("lastName", "Sharma");
                reqBody.put("email", "saahil@test.com");

                //Generate response
                given().headers(reqHeaders).body(reqBody). //Request Specification
                when().post(baseURI + resourcePath). //POST Request
                then().log().all().statusCode(201); //Assertions


    }

}

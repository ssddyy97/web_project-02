package kr.ac.kopo.ctc.kopo28.board;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleRestAssuredControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";

    }


    @Test
    void testGetUserById(){
        given()
            .queryParam("id", 1)
        .when()
            .get("/api/sample/selectOne")
                .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("t1"));

    }

}
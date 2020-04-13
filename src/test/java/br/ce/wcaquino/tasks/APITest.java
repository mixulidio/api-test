package br.ce.wcaquino.tasks;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import io.restassured.*;
import io.restassured.http.*;
import org.hamcrest.*;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
        .when()
            .get("/todo")
        .then()
            .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        RestAssured.given()
            .body("{\"task\" : \"Teste via API\",\"dueDate\":\"2020-12-30\"}")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured.given()
            .body("{\"task\" : \"Teste via API\",\"dueDate\":\"2010-12-30\"}")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(400)
            .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }


    public void testeLog() {
        RestAssured
        .given()
            .log().all()
        .when()
            .get("http://localhost:8001/tasks-backend/todo")
        .then()
            .log().all()
        ;
    }
}

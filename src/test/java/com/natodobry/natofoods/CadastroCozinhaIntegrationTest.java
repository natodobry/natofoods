package com.natodobry.natofoods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIntegrationTest {

   @LocalServerPort
   private int port;

   @Autowired
   private Flyway flyway;


   @BeforeEach()
   public void setup(){
       RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
       RestAssured.port = port;
       RestAssured.basePath ="/cozinhas";
       flyway.migrate();


   }

   @Test
   public void deveRetornarStatus200_QuandoConsultarCozinhas(){
      RestAssured.given()
                 .accept(ContentType.JSON)
              .when()
                .get()
              .then()
                .statusCode(HttpStatus.OK.value());
   }

   @Test
   public void deveConter4Cozinhas_QuandoConsultarCozinhas(){

      RestAssured.given()
                .accept(ContentType.JSON)
              .when()
                .get()
              .then()
                .body("", Matchers.hasSize(4))
                 .body("nome", Matchers.hasItems("Indiana"));
   }

   @Test
   public void testRetornarStatus201_QuandoCadastrarCozinha(){
       RestAssured.given()
                .body("{\"nome\" : \"Chinesa\"}")
                 .contentType(ContentType.JSON)
                 .accept(ContentType.JSON)
               .when()
                 .post()
               .then()
                .statusCode(HttpStatus.CREATED.value());
   }

   @Test
   public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente(){
       RestAssured.given()
               .pathParam("/{cozinhaId}", 2)
                 .contentType(ContentType.JSON)
               .when()
                 .get("/{cozinhaId}")
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("nome", Matchers.equalTo("indiana"));
   }
}

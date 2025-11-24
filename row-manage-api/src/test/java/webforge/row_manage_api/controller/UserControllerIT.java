package webforge.row_manage_api.controller;

import org.junit.Test;
import webforge.row_manage_api.config.IntegrationTest;

import static org.hamcrest.Matchers.is;
import static webforge.row_manage_api.fixture.UserFixture.*;
import static webforge.row_manage_api.random.Random.*;
import static webforge.row_manage_api.utils.BodyBuilder.with;

public class UserControllerIT extends IntegrationTest {
    @Test
    public void mustGetAllUsers(){
        getAllUsers()
                .then()
                .statusCode(200);
    }
    @Test
    public void mustCreateUser(){
        var nome = firstName();
        var email = generateEmail();
        var password = "Teste124@@@";
        var school = "Escola";
        var cpf = generateNumber();

        var userBody = with("name", nome)
                .and("email", email)
                .and("password", password)
                .and("school", school)
                .and("cpf", cpf);
        createUser(userBody)
                .then()
                .statusCode(201)
                .body("name", is(nome))
                .body("email", is(email))
                .body("school", is(school));

    }

    @Test
    public void mustUpdateUser(){
        var nome = firstName();
        var email = generateEmail();
        var password = "Teste124@@@";
        var school = "Escola";
        var cpf = generateNumber();

        var userBody = with("name", nome)
                .and("email", email)
                .and("password", password)
                .and("school", school)
                .and("cpf", cpf);

        var userId = createUser(userBody)
                .then().statusCode(201)
                .extract()
                .path("id");

        var userBodyUpdate = with("name", "Jason")
                .and("email", "Jason@gmail.com")
                .and("password", "Jason123@@")
                .and("school", "JasonSchool")
                .and("cpf", "111");

        updateUser(userId, userBodyUpdate)
                .then()
                .statusCode(200)
                .body("email", is("Jason@gmail.com"))
                .body("name", is("Jason"))
                .body("school", is("JasonSchool"))
                .body("cpf", is("111"));
    }

}

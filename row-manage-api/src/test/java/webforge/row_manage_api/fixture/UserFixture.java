package webforge.row_manage_api.fixture;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import webforge.row_manage_api.utils.BodyBuilder;

import static io.restassured.RestAssured.given;


public class UserFixture {
    public static Response getAllUsers(){
        return given()
                .basePath("/usuario/mostrar")
                .when()
                .get();
    }
    public static Response createUser(BodyBuilder builder){
        return given()
                .basePath("/usuario")
                .contentType(ContentType.JSON)
                .body(builder.build())
                .when()
                .post();
    }
    public static Response updateUser(Object userId, BodyBuilder builder){
        return given()
                .basePath("/usuario")
                .pathParam("id", userId)
                .contentType(ContentType.JSON)
                .body(builder.build())
                .when()
                .put("/{id}");
    }
}

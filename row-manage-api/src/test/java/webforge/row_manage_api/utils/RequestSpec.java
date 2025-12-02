package webforge.row_manage_api.utils;

import io.restassured.RestAssured;

public class RequestSpec {
    public static void on(int port){
        RestAssured.port = port;
    }
}

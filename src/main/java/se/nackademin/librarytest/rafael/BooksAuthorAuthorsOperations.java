/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.librarytest.rafael;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

/**
 *
 * @author rafael
 */
public class BooksAuthorAuthorsOperations {
    private static final String BASE_URL = "http://localhost:8080/librarytest/rest/";
    
    
    private String jsonString = "";
    
    public void setJsonString(String string){
        this.jsonString = string;
    }
    public String getJsonString(){
        return this.jsonString;
    }
    
    public Response getBooksByAuthorId(int id){
        String resourceName = "books/byauthor/"+id;
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
}


package se.nackademin.librarytest.rafael;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;


public class AuthorOperations {
    private static final String BASE_URL = "http://localhost:8080/librarytest/rest/";
     //supporting method for  getting all the books 
    public Response getAllAuthors(){
        String resourceName = "authors";
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
    
}

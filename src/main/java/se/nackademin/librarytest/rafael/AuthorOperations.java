
package se.nackademin.librarytest.rafael;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.UUID;


public class AuthorOperations {
    private static final String BASE_URL = "http://localhost:8080/librarytest/rest/";
    
    
    private String jsonString = "";
    
    public void setJsonString(String string){
        this.jsonString = string;
    }
    public String getJsonString(){
        return this.jsonString;
    }
    
    
     //supporting method for  getting all the books 
    public Response getAllAuthors(){
        String resourceName = "authors";
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
    
     public Response getAuthorById(int id){
        String resourceName = "authors/"+id;
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
     
    public int getIdOfTheLastAddedAuthor(){
        Response getResponse = getAllAuthors();           
        int fetchedId = getResponse.jsonPath().getInt("authors.author[-1].id");         
        return fetchedId;
    }
    
    public String getNameOfTheLastAddedAuthor(){
        Response getResponse = getAllAuthors();           
        String fetchedName = getResponse.jsonPath().getString("authors.author[-1].name");         
        return fetchedName;
    }
    public Response createRandomAuthorWithNoId(){
        String resourceName = "authors";          
        String name = UUID.randomUUID().toString();
        String postBodyTemplate = ""
                + "{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\"\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;        
    } 
    public Response createRandomAuthorWithId(){
        String resourceName = "authors";          
        String name = UUID.randomUUID().toString();
        String postBodyTemplate = ""
                + "{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name, new Random().nextInt(500));
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;        
    }
    
    public Response createInvalidAuthorExistingIdOnTheDatabase(int id){
        String resourceName = "authors";          
        String name = UUID.randomUUID().toString();
        String postBodyTemplate = ""
                + "{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name, id);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;        
    }
    
     //supporting method for deleting an author
    public Response deleteAuthor(int id){
        String deleteResourceName = "authors/"+id;
        Response deleteResponse = delete(BASE_URL+deleteResourceName);
        return deleteResponse;
    }
}

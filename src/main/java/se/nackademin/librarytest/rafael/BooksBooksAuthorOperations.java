
package se.nackademin.librarytest.rafael;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author rafael
 */
public class BooksBooksAuthorOperations {
    private static final String BASE_URL = "http://localhost:8080/librarytest/rest/";
    
    
    private String jsonString = "";
    
    public void setJsonString(String string){
        this.jsonString = string;
    }
    public String getJsonString(){
        return this.jsonString;
    }
    
    public Response getAllBooks(){
        String resourceName = "books";
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
    
    public Response getAuthorsByBookId(int id){
        String resourceName = "books/"+id+"/authors";
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
    
    public Response getAllAuthors(){
        String resourceName = "authors";
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
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
        
        String postBody = String.format(postBodyTemplate, name, new Random().nextInt(10000));
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;        
    }
    
     public Response createRandomBook(){
        String resourceName = "books";
          
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String isbn = UUID.randomUUID().toString();
               
        String postBodyTemplate = ""
                + "{\n" +
                "\"book\":\n" +
                "  {\n" +
                "    \"description\":\"%s\",\n" +
                "    \"isbn\":\"%s\",\n" +
                "    \"nbOfPage\":%s,\n" +
                "    \"title\":\"%s\"\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, description,isbn,""+new Random().nextInt(500),title);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;
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
    
    
    
    public int getIdOfTheLastAddedBook(){
        Response getResponse = getAllBooks();           
        int fetchedId = getResponse.jsonPath().getInt("books.book[-1].id");         
        return fetchedId;
    }
    public Response createRandomBookWithAuthor(String authorName, int authorId){
        String resourceName = "books";
                      
        String postBodyTemplate = ""
                + "{\n" +
                "\"book\":\n" +
                "  {\n" +
                "    \"description\":\"Description.\",\n" +
                "    \"isbn\":\"0-7710-0868-6\",\n" +
                "    \"nbOfPage\":411,\n" +
                "    \"title\":\"title\",\n" +
                "    \"author\":\n" +
                "    {\n" +
                "      \"name\":\"%s\",\n" +
                "      \"id\":%s,\n" +
                "    }\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, authorName, authorId);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;
    } 
    
    public Response postAuthorToRandomlyCreatedBook(int bookId, String authorName, int authorId){
        String resourceName = "books/"+bookId+"/authors";
                      
        String postBodyTemplate =""
                + "{\n" +
                "    \"author\": {\n" +
                "        \"id\": %s,\n" +
                "        \"name\":%s,\n" +
                "    }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, authorId,authorName);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;
    } 
    
    public Response putAuthorToRandomlyCreatedBookWithAuthor(int bookId, String authorName, int authorId){
        String resourceName = "books/"+bookId+"/authors";
        
        String postBodyTemplate = "{\n" +
                "    \"authors\": {\n" +
                "        \"author\": {\n" +
                "            \"id\": %s,\n" +
                "            \"name\":%s,\n" +
                "        }\n" +
                "    }\n" +
                "}";
        
        //String postBody = String.format(postBodyTemplate, authorId,authorName);
        String postBody = String.format(postBodyTemplate,authorId,authorName);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).put(BASE_URL +resourceName);
        return postResponse;
    } 
    
    public Response putAuthorToRandomlyCreatedBookWithWrongAuthor(int bookId){
        String resourceName = "books/"+bookId+"/authors";
        /**              
        String postBodyTemplate =""
                + "{\n" +
                "    \"author\": {\n" +
                "        \"id\": %s,\n" +
                "        \"name\":%s,\n" +
                "    }\n" +
                "}";*/
        
        String postBodyTemplate = "{\n" +
                "    \"authors\": {\n" +
                "        \"author\": {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"rafaeaaaaaaaaaaaaaaaaa\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        
        //String postBody = String.format(postBodyTemplate, authorId,authorName);
        String postBody = String.format(postBodyTemplate);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).put(BASE_URL +resourceName);
        return postResponse;
    } 
    
    
}

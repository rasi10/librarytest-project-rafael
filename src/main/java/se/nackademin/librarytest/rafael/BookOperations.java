package se.nackademin.librarytest.rafael;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.path.json.JsonPath.from;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author rafael
 */
public class BookOperations {
    private static final String BASE_URL = "http://localhost:8080/librarytest/rest/";
    
    private String jsonString = "";
    
    public void setJsonString(String string){
        this.jsonString = string;
    }
    public String getJsonString(){
        return this.jsonString;
    }
    
    public BookOperations(){
        
    }
    
    //supporting method for  getting all the books 
    public Response getAllBooks(){
        String resourceName = "books";
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
    
    //supporting method for  getting all the books 
    public Response getABookById(int id){
        String resourceName = "books/"+id;
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);//.prettyPeek();
        return getResponse;
    }
    
        
    
    //supporting method for deleting a book
    public Response deleteBook(int id){
        String deleteResourceName = "books/"+id;
        Response deleteResponse = delete(BASE_URL+deleteResourceName);
        return deleteResponse;
    }
       
    
    //supporting method for returning the status code of a response
    public int getStatusCodeFromResponse(Response response){
        return response.getStatusCode();
    }
    
    public ArrayList<String> bookProperties(String jsonString){
        ArrayList returnArray = new ArrayList<String>();
        String expectedTitle = from(jsonString).getString("book.title");
        String expectedDescription = from(jsonString).getString("book.description");
        String expectedIsbn = from(jsonString).getString("book.isbn");
                
        returnArray.add(expectedTitle);
        returnArray.add(expectedDescription);
        returnArray.add(expectedIsbn);
        
        return returnArray;
        
    }
    
    public int getIdOfTheLastAddedBook(){
        Response getResponse = getAllBooks();           
        int fetchedId = getResponse.jsonPath().getInt("books.book[-1].id");         
        return fetchedId;
    }
    
    public ArrayList<String> lastCreatedBookProperties(){
        ArrayList returnArray = new ArrayList<String>();
        Response getResponse = getAllBooks();
        String fetchedTitle = getResponse.jsonPath().getString("books.book[-1].title");
        String fetchedDescription = getResponse.jsonPath().getString("books.book[-1].description");
        String fetchedIsbn = getResponse.jsonPath().getString("books.book[-1].isbn");    
                
        returnArray.add(fetchedTitle);
        returnArray.add(fetchedDescription);
        returnArray.add(fetchedIsbn);
        
        return returnArray;
        
    }
    
    //Supporting method for creating a new random book
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
    
    
    //Supporting method for creating a new book with a id that already exists
    public Response createInvalidBookExistingID(int bookId){
        String resourceName = "books"; 
               
        String postBodyTemplate = "{\n" +
                "\"book\":\n" +
                "  {\n" +
                "    \"description\":\"description\",\n" +
                "    \"isbn\":\"isbn\",\n" +
                "    \"nbOfPage\":411,\n" +
                "    \"title\":\"wrong book\"\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";      
        
        String postBody = String.format(postBodyTemplate, bookId);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;
    } 
    
     //Supporting method for creating a new book with an author that does not exist in the database
    public Response createInvalidBookWithAuthorThatDoesNotExist(int authorId){
        String resourceName = "books";                   
        String postBodyTemplate = ""
                + "{\n" +
                "\"book\":\n" +
                "  {\n" +
                "    \"description\":\"description\",\n" +
                "    \"isbn\":\"isbn\",\n" +
                "    \"nbOfPage\":411,\n" +
                "    \"title\":\"title of the book\"\n" +
                "    \"author\":\n" +
                "    {\n" +
                "      \"name\":\"author\",\n" +
                "      \"id\":%s\n" +
                "    }\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, authorId);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL +resourceName);
        return postResponse;
    } 
    
    public Response editBookByIdWithNoAuthors(int id){
       String resourceName = "books";          
        String name = UUID.randomUUID().toString();
        String postBodyTemplate = ""
                + " {\n" +
                "    \"book\": {        \n" +
                "        \"description\": \"EditedBook description\",\n" +
                "        \"id\":%s,\n" +
                "        \"isbn\": \"edited isbn\",\n" +
                "        \"nbOfPage\": 288,\n" +
                "        \"title\": \"edited title\"\n" +
                "    }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, id);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).put(BASE_URL +resourceName);
        return postResponse; 

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
    
    public Response editBookByIdWithAuthors(int bookId, String authorName, int authorId){        
        String resourceName = "books";
        String postBodyTemplate = ""
                + "{\n" +
                "\"book\":\n" +
                "  {\n" +
                "    \"description\":\"Description edited 2.\",\n" +
                "    \"isbn\":\"isbnEdited 2\",\n" +
                "    \"nbOfPage\":411,\n" +
                "    \"title\":\"title edited 2\",\n" +
                "     \"id\":%s,\n" +
                "    \"author\":\n" +
                "    {\n" +
                "      \"name\":\"%s\",\n" +
                "      \"id\":%s,\n" +
                "    }\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, bookId, authorName, authorId);
        setJsonString(postBody);
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).put(BASE_URL +resourceName);
        return postResponse; 

    }
    
}

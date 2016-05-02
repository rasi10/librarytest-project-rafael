package se.nackademin.librarytest.rafael;

import static com.jayway.restassured.path.json.JsonPath.from;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael
 */
public class TestSuiteEndPointAuthors {
    TestNames testName = new TestNames();
    
    public TestSuiteEndPointAuthors() {
    }
    @Test
    public void testExecutionInOrder(){
       System.out.println("\n\n\n###TEST SUITE - ENDPOINT AUTHORS###");
       //test001GetAllAuthors();
       //test002CreateARandomAuthorWithNoId();
       //test003CreateARandomAuthorWithId();
       //test004CreatingAnInvalidAuthorIdExists();    
       //test005GettingAnAuthorById();
       //test006GettingAnAuthorByIdInvalidID();
       //test007DeleteAuthorExistingId();
       //test008DeleteAuthorNotExistingId();
       //test009EditTheNameOfAnAuthor();
       //test010EditTheNameOfAnAuthorInvalidAuthorId();
    }  
    
    @Test
    public void test001GetAllAuthors(){
        testName.builTestName("Test 001 - Get all authors"); 
        AuthorOperations authorOperations = new AuthorOperations();
        
        //Making a GET request to retrieve all existing authors
        Response response = authorOperations.getAllAuthors();
        System.out.println("Status code for GET: "+ response.statusCode()); 
        assertEquals("Status code should be 200",200, response.getStatusCode());
    }
    
    @Test
    public void test002CreateARandomAuthorWithNoId(){
        testName.builTestName("Test 002 - create a new author with no ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        
        //Creating a new random author and checking the status code of the response
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        //Saving the data of the last author that was included in the database and making an assertion with the expected value
        String lastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");
        assertEquals("The name of the author should be the same!",expectedAuthor,lastAddedAuthor);
    }
    
    @Test
    public void test003CreateARandomAuthorWithId(){
        testName.builTestName("Test 003 - create a new author with an ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        
        //Creating a random author with no id passed as parameter
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());       
        
        //Saving data about the last author and making an assertion with the expected result
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");            
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
        
    }
    
    @Test
    public void test004CreatingAnInvalidAuthorIdExists(){
        testName.builTestName("Test 004 - create a new author with an ID that already exists");               
        AuthorOperations authorOperations = new AuthorOperations();        
        
        //Creating a new author without passing the id and making an assertion of the status code
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        //saving data of the author and making an assertion with the expected value
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");        
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);        
        
        //Savign the id of the last created author
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        
        //Creating an author who already exists on the database and checking the status code of the response
        postResponse = authorOperations.createInvalidAuthorExistingIdOnTheDatabase(idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Should return 400 as the authors id already exists!",400,postResponse.statusCode());        
    }
    
    @Test
    public void test005GettingAnAuthorById(){
        testName.builTestName("Test 005 - Getting an author by ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        
        //Creating a new author with no id and checking the status code
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());
        
        //Saving the id of the last created author and making an assertion for the status code when trying to retrieve the author
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        Response getResponse = authorOperations.getAuthorById(idOfTheLastCreatedAuthor);
        System.out.println("Status code for GET: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 200!",200,getResponse.getStatusCode());          
    }
     
    @Test
    public void test006GettingAnAuthorByIdInvalidID(){
        testName.builTestName("Test 006 - Getting an author by ID - invalid ID");
        
        //Making a request for an author that does not exist on the database and checking the status code.
        AuthorOperations authorOperations = new AuthorOperations();
        Response getResponse = authorOperations.getAuthorById(-11111);
        System.out.println("Status code for GET: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 404!",404,getResponse.getStatusCode());
        
    }
    
    @Test
    public void test007DeleteAuthorExistingId(){
        testName.builTestName("Test 007 - Deleting an author by ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        
        //Creating an author and making an assertion of the status code
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());
        
        //saving the id of the author and performing a delete
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        Response getResponse = authorOperations.deleteAuthor(idOfTheLastCreatedAuthor);
        
        //Checking the status code for the delete operation
        System.out.println("Status code for DELETE: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 204!",204,getResponse.getStatusCode());
    }
    
    @Test
    public void test008DeleteAuthorNotExistingId(){
        testName.builTestName("Test 008 - Deleting an author by ID - Invalid ID");               
        
        //Attempting to delete an author that does not exist on the database
        AuthorOperations authorOperations = new AuthorOperations();        
        Response getResponse = authorOperations.deleteAuthor(-11111);
        System.out.println("Status code for DELETE: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 404!",404,getResponse.getStatusCode());
    }
    
    @Test
    public void test009EditTheNameOfAnAuthor(){
        testName.builTestName("Test 009 - Edit the name of the author to my own");       
        AuthorOperations authorOperations = new AuthorOperations();        
        
        //creating an author and checking the status code of the operation
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        //Ssaving data related to the last author inserted into the database and asserting it with the expected value
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
        
        //Saving the id of the last created author, editing information about the author and checking the statuscode
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();                
        Response putResponse = authorOperations.editAuthorWithMyName(idOfTheLastCreatedAuthor);
        System.out.println("Status code for PUT: "+putResponse.getStatusCode());
        assertEquals("Post response should have status code 200",200,putResponse.statusCode()); 
        
        //Making an assertion of the names of the authors. 
        nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();
        expectedAuthor = "Rafael Silva";
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
    }
    
    @Test
    public void test010EditTheNameOfAnAuthorInvalidAuthorId(){
        testName.builTestName("Test 010 - Edit the name of the author - Invalid Id ");               
        AuthorOperations authorOperations = new AuthorOperations(); 
        
        //creating an author and asserting the status code of the operation
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        //Making an assertion of the name of the author
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
                    
        //Editing the name of the author with an invalid ID
        Response putResponse = authorOperations.editAuthorWithMyName(-111111);
        System.out.println("Status code for PUT: "+putResponse.getStatusCode());
        assertEquals("PUT response should have status code 400",404,putResponse.statusCode());        
        
    }
}

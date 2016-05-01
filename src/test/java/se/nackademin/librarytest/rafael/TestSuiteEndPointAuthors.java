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
       test001GetAllAuthors();
       test002CreateARandomAuthorWithNoId();
       test003CreateARandomAuthorWithId();
       test004CreatingAnInvalidAuthorIdExists();    
       test005GettingAnAuthorById();
       test006GettingAnAuthorByIdInvalidID();
       test007DeleteAuthorExistingId();
       test008DeleteAuthorNotExistingId();
       test009EditTheNameOfAnAuthor();
       test010EditTheNameOfAnAuthorInvalidAuthorId();
    }  
    
    
    
    public void test001GetAllAuthors(){
        testName.builTestName("Test 001 - Get all authors"); 
        AuthorOperations authorOperations = new AuthorOperations();
        Response response = authorOperations.getAllAuthors();
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 200",200, response.getStatusCode());
    }
    
    public void test002CreateARandomAuthorWithNoId(){
        testName.builTestName("Test 002 - create a new author with no ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        String lastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");
        assertEquals("The name of the author should be the same!",expectedAuthor,lastAddedAuthor);
    }
    public void test003CreateARandomAuthorWithId(){
        testName.builTestName("Test 003 - create a new author with an ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");
        
        //int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
            
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
        
    }
    public void test004CreatingAnInvalidAuthorIdExists(){
        testName.builTestName("Test 004 - create a new author with an ID that already exists");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");        
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);        
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        
        postResponse = authorOperations.createInvalidAuthorExistingIdOnTheDatabase(idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Should return 400 as the authors id already exists!",400,postResponse.statusCode());        
    }
    
     public void test005GettingAnAuthorById(){
        testName.builTestName("Test 005 - Getting an author by ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());
        
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        Response getResponse = authorOperations.getAuthorById(idOfTheLastCreatedAuthor);
        System.out.println("Status code for GET: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 200!",200,getResponse.getStatusCode());          
    }
     
      public void test006GettingAnAuthorByIdInvalidID(){
        testName.builTestName("Test 006 - Getting an author by ID - invalid ID");
        AuthorOperations authorOperations = new AuthorOperations();
        Response getResponse = authorOperations.getAuthorById(-11111);
        System.out.println("Status code for GET: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 404!",404,getResponse.getStatusCode());
        
    }
      
      public void test007DeleteAuthorExistingId(){
        testName.builTestName("Test 007 - Deleting an author by ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());
        
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        Response getResponse = authorOperations.deleteAuthor(idOfTheLastCreatedAuthor);
        System.out.println("Status code for DELETE: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 204!",204,getResponse.getStatusCode());
    }
        
    public void test008DeleteAuthorNotExistingId(){
        testName.builTestName("Test 008 - Deleting an author by ID - Invalid ID");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response getResponse = authorOperations.deleteAuthor(-11111);
        System.out.println("Status code for DELETE: "+getResponse.getStatusCode()); 
        assertEquals("The status code should be 404!",404,getResponse.getStatusCode());
    }
    
    public void test009EditTheNameOfAnAuthor(){
        testName.builTestName("Test 009 - Edit the name of the author to my own");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
        
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        //System.out.println("IDD of the last "+idOfTheLastCreatedAuthor);
        
        Response putResponse = authorOperations.editAuthorWithMyName(idOfTheLastCreatedAuthor);
        System.out.println("Status code for PUT: "+putResponse.getStatusCode());
        assertEquals("Post response should have status code 200",200,putResponse.statusCode()); 
        
        nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();
        expectedAuthor = "Rafael Silva";
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
    }
    
    public void test010EditTheNameOfAnAuthorInvalidAuthorId(){
        testName.builTestName("Test 010 - Edit the name of the author - Invalid Id ");               
        AuthorOperations authorOperations = new AuthorOperations();        
        Response postResponse = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = authorOperations.getNameOfTheLastAddedAuthor();                       
        String expectedAuthor = from(authorOperations.getJsonString()).getString("author.name");
        assertEquals("The name of the author should be the same!",expectedAuthor,nameOfThelastAddedAuthor);
        
        int idOfTheLastCreatedAuthor = authorOperations.getIdOfTheLastAddedAuthor();
        //System.out.println("IDD of the last "+idOfTheLastCreatedAuthor);
        
        Response putResponse = authorOperations.editAuthorWithMyName(-111111);
        System.out.println("Status code for PUT: "+putResponse.getStatusCode());
        assertEquals("Post response should have status code 400",404,putResponse.statusCode());        
        
    }
}

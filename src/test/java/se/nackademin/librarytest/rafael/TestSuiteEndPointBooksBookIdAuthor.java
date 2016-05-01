
package se.nackademin.librarytest.rafael;

import static com.jayway.restassured.path.json.JsonPath.from;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael
 */
public class TestSuiteEndPointBooksBookIdAuthor {
    TestNames testName = new TestNames();
    
    public TestSuiteEndPointBooksBookIdAuthor() {
    }
    
    @Test
    public void testExecutionInOrder(){
        System.out.println("\n\n\n###TEST SUITE - ENDPOINT BOOKS/BOOKID/AUTHORS###");
       //test001(); //THIS METHOD SHOUDL BE EDITED
       test();            
    }         
    
    public void test(){
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
        System.out.println("IDDDDD Author: "+idOfTheLastCreatedAuthor);        
        //now I have the authors name and id. Now I need to create a random book with the authors information
                
        postResponse = booksBooksOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());
        
        int idOfTheLastBookAdded = booksBooksOperations.getIdOfTheLastAddedBook();
        System.out.println("IDDDD last book "+idOfTheLastBookAdded);
        
        
    }
    
       
    
    public void test001(){
        //THIS METHOD SHOULD BE EDITED
        
        testName.builTestName("Test 001 - Get authors by book ID");
        BooksBooksAuthorOperations booksBooksOperation = new BooksBooksAuthorOperations();        
        Response response = booksBooksOperation.getAuthorsByBookId(1);
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 200",200, response.getStatusCode());
    }
}


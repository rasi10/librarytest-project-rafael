
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
       test001GetTheAuthorsOfABook();            
       test001GetTheAuthorsOfABookInvalidBookID();
    }         
    
    
    public void test001GetTheAuthorsOfABook(){
        testName.builTestName("Test 001 - Get authors by book ID");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
        //System.out.println("IDDDDD Author: "+idOfTheLastCreatedAuthor);                
                
        postResponse = booksBooksOperations.createRandomBookWithAuthor(nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());     
        
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        
        Response getResponse = booksBooksOperations.getAuthorsByBookId(idOfTheLastBookCreated);
        System.out.println("Status code for GET: "+ getResponse.statusCode());
        assertEquals("GET response should have status code 200!",200,getResponse.statusCode());       
    }
    
    public void test001GetTheAuthorsOfABookInvalidBookID(){       
        testName.builTestName("Test 001 - Get authors by book ID - Invalid book ID");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
                      
        postResponse = booksBooksOperations.createRandomBookWithAuthor(nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());     
        
        Response getResponse = booksBooksOperations.getAuthorsByBookId(-111111);
        System.out.println("Status code for GET: "+ getResponse.statusCode());
        assertEquals("GET response should have status code 404 ",404,getResponse.statusCode());  
    }
       
    
    
}


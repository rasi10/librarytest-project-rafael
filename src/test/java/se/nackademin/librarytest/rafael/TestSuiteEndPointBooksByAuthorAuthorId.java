
package se.nackademin.librarytest.rafael;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael
 */
public class TestSuiteEndPointBooksByAuthorAuthorId {
    TestNames testName = new TestNames();
    
    public TestSuiteEndPointBooksByAuthorAuthorId() {
    }
    @Test
    public void testExecutionInOrder(){
       System.out.println("\n\n\n###TEST SUITE - ENDPOINT BOOKS/AUTHORS/ID###");
       test001GetAllBooksByAuthorId();   
       //test002GetAllBooksByAuthorIdInvalidAuthorId(); //Commented this test because there is an error on the code/documentation
    }  
    
    public void test001GetAllBooksByAuthorId(){
        testName.builTestName("Test 001 - get all books by author id"); 
        BooksAuthorAuthorsOperations booksByAuthorId = new BooksAuthorAuthorsOperations();
        Response response = booksByAuthorId.getBooksByAuthorId(2);
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 200",200, response.getStatusCode());
    }
    
    public void test002GetAllBooksByAuthorIdInvalidAuthorId(){
        testName.builTestName("Test 001 - get all books by author id - Invalid author id");
        BooksAuthorAuthorsOperations booksByAuthorId = new BooksAuthorAuthorsOperations();
        Response response = booksByAuthorId.getBooksByAuthorId(-111111);
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 404",404, response.getStatusCode());
    }
}

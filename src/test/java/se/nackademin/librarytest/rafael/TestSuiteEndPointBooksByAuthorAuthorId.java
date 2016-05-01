
package se.nackademin.librarytest.rafael;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael
 */
public class TestSuiteEndPointBooksByAuthorAuthorId {
    
    public TestSuiteEndPointBooksByAuthorAuthorId() {
    }
    @Test
    public void testExecutionInOrder(){
       System.out.println("\n\n\n###TEST SUITE - ENDPOINT BOOKS/AUTHORS/ID###");
       test001GetAllBooksByAuthorId();   
       //test002GetAllBooksByAuthorIdInvalidAuthorId();
    }  
    
    public void test001GetAllBooksByAuthorId(){
        BooksAuthorAuthorsOperations booksByAuthorId = new BooksAuthorAuthorsOperations();
        Response response = booksByAuthorId.getBooksByAuthorId(2);
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 200",200, response.getStatusCode());
    }
    
    public void test002GetAllBooksByAuthorIdInvalidAuthorId(){
        BooksAuthorAuthorsOperations booksByAuthorId = new BooksAuthorAuthorsOperations();
        Response response = booksByAuthorId.getBooksByAuthorId(-111111);
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 404",404, response.getStatusCode());
    }
}

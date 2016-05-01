
package se.nackademin.librarytest.rafael;

import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael
 */
public class TestSuiteEndPointBooks {
    TestNames testName = new TestNames();
    
        
    public TestSuiteEndPointBooks() {
    }
    
    @Test
    public void testExecutionInOrder(){
       test001GetAllBooks();
       test002CreateANewBook();
       test003DeletingABookRecentlyCreated();
       test004GetABookById();
       test005GetInvalidBook();
       
    }    
    
    public void test001GetAllBooks(){ 
        testName.builTestName("Test 001 - get all books"); 
        BookOperations bookOperations = new BookOperations();
        Response response = bookOperations.getAllBooks();
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 200",200, bookOperations.getStatusCodeFromResponse(response));
    }
    
    public void test002CreateANewBook(){
        testName.builTestName("Test 002 - create a new book");        
        ArrayList<String> newBookFields       = new ArrayList<String>();
        ArrayList<String> lastAddedBookFields = new ArrayList<String>();         
       
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                
        
        newBookFields = bookOperations.bookProperties(bookOperations.getJsonString());        
        lastAddedBookFields = bookOperations.lastCreatedBookProperties();        
        
        assertEquals (newBookFields.get(0), lastAddedBookFields.get(0));
        assertEquals (newBookFields.get(1), lastAddedBookFields.get(1));
        assertEquals (newBookFields.get(2), lastAddedBookFields.get(2));
               
    }
    
    public void test003DeletingABookRecentlyCreated(){
        testName.builTestName("Test 003 - Deleting a book that was recently created");       
               
        ArrayList<String> newBookFields       = new ArrayList<String>();
        ArrayList<String> lastAddedBookFields = new ArrayList<String>();         
       
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                
        
        newBookFields = bookOperations.bookProperties(bookOperations.getJsonString());        
        lastAddedBookFields = bookOperations.lastCreatedBookProperties();        
        
        assertEquals (newBookFields.get(0), lastAddedBookFields.get(0));
        assertEquals (newBookFields.get(1), lastAddedBookFields.get(1));
        assertEquals (newBookFields.get(2), lastAddedBookFields.get(2));
        
        int idOfTheBookToBeDeleted = bookOperations.getIdOfTheLastAddedBook(); 
              
        Response deleteResponse = new BookOperations().deleteBook(idOfTheBookToBeDeleted);
        System.out.println("Status code for DELETE when existing: "+ deleteResponse.statusCode());
        assertEquals("Delete method should return 204",204,deleteResponse.statusCode());
        
        deleteResponse = new BookOperations().deleteBook(idOfTheBookToBeDeleted);
        System.out.println("Status code for DELETE when not existing: "+ deleteResponse.statusCode());
        assertEquals("Delete method should return 404",404,deleteResponse.statusCode());
        
    }
    
    public void test004GetABookById(){
        testName.builTestName("Test 002 - create a new book");                
        ArrayList<String> lastAddedBookFields = new ArrayList<String>();         
       
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());
        
        int idOfTheLastCreatedBook = bookOperations.getIdOfTheLastAddedBook();
                
        Response response = bookOperations.getABookById(idOfTheLastCreatedBook);
        System.out.println("Status code for GET a book that was recently created: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 200",200, bookOperations.getStatusCodeFromResponse(response));
        
    }    
    
    public void test005GetInvalidBook(){
        testName.builTestName("Test 005 - get a book with an invalid ID"); 
        BookOperations bookOperations = new BookOperations();
        Response response = bookOperations.getABookById(-1111);
        System.out.println("Status code for GET a book that does not exist: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 404",404, bookOperations.getStatusCodeFromResponse(response));
    }
    
}
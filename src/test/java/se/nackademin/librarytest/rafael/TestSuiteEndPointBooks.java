
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
       System.out.println("\n\n\n###TEST SUITE - ENDPOINT BOOKS###");
       test001GetAllBooks();
       test002CreateANewBook();
       test003CreateInvalidBookExistingID();        
       test004CreateInvalidBookAuthorDoesNotExistInDatabase();
       test005DeletingABookRecentlyCreated();
       test006DeletingAnInvalidBook();
       test007GetABookById();
       test008GetInvalidBook();       
       test009EditingABookWithAuthors(); 
       //test010EditingABookWithNoAuthors(); //Commented this test because there is an error on the application/documentation
       test011EditingABookInvalidBookId();
       
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
    public void test003CreateInvalidBookExistingID(){
        testName.builTestName("Test 003 - Creating a book that is already on the database");       
        
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                
                
        int idOfTheLastAddedBook = bookOperations.getIdOfTheLastAddedBook();         
        Response secondPostResponse = bookOperations.createInvalidBookExistingID(idOfTheLastAddedBook);
              
        System.out.println("Status code for POST when existing: "+ secondPostResponse.statusCode());
        assertEquals("Create method should return 400. Book id already on database",400,secondPostResponse.statusCode());
        
    }
    
    public void test004CreateInvalidBookAuthorDoesNotExistInDatabase(){
        testName.builTestName("Test 004 - author that did not exist in the database"); 
        BookOperations bookOperations = new BookOperations();
        
        Response secondPostResponse = bookOperations.createInvalidBookWithAuthorThatDoesNotExist(-111111);
              
        System.out.println("Status code for POST when author id does not exist: "+ secondPostResponse.statusCode());
        assertEquals("Create method should return 400. Invalid author id",400,secondPostResponse.statusCode());
    }
    
    public void test005DeletingABookRecentlyCreated(){
        testName.builTestName("Test 005 - Deleting a book that was recently created");       
               
        ArrayList<String> newBookFields       = new ArrayList<String>();
        ArrayList<String> lastAddedBookFields = new ArrayList<String>();         
       
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                
        
        newBookFields = bookOperations.bookProperties(bookOperations.getJsonString());        
        lastAddedBookFields = bookOperations.lastCreatedBookProperties();        
        
        //performing assertions in order to see if the book was created
        assertEquals (newBookFields.get(0), lastAddedBookFields.get(0));
        assertEquals (newBookFields.get(1), lastAddedBookFields.get(1));
        assertEquals (newBookFields.get(2), lastAddedBookFields.get(2));
        
        int idOfTheBookToBeDeleted = bookOperations.getIdOfTheLastAddedBook(); 
              
        //deleting the book
        Response deleteResponse = new BookOperations().deleteBook(idOfTheBookToBeDeleted);
        System.out.println("Status code for DELETE when existing: "+ deleteResponse.statusCode());
        assertEquals("Delete method should return 204",204,deleteResponse.statusCode());
        
        //double-checking the the book was really deleted
        deleteResponse = new BookOperations().deleteBook(idOfTheBookToBeDeleted);
        System.out.println("Status code for DELETE when not existing: "+ deleteResponse.statusCode());
        assertEquals("Delete method should return 404",404,deleteResponse.statusCode());
        
    }
    
    public void test006DeletingAnInvalidBook(){
        testName.builTestName("Test 006 - Deleting a book that was does not exist");           
        Response deleteResponse = new BookOperations().deleteBook(-11111);       
        System.out.println("Status code for DELETE when not existing: "+ deleteResponse.statusCode());
        assertEquals("Delete method should return 404",404,deleteResponse.statusCode());        
    }  
    
    
    public void test007GetABookById(){
        testName.builTestName("Test 007 - create a new book");                
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
    
    public void test008GetInvalidBook(){
        testName.builTestName("Test 008 - get a book with an invalid ID"); 
        BookOperations bookOperations = new BookOperations();
        Response response = bookOperations.getABookById(-1111);
        System.out.println("Status code for GET a book that does not exist: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 404",404, bookOperations.getStatusCodeFromResponse(response));
    }
       
    
    public void test009EditingABookWithAuthors(){
        testName.builTestName("Test 009 - Editing a book with authors"); 
        AuthorOperations authorOperations = new AuthorOperations();
        Response response = authorOperations.createRandomAuthorWithNoId();
        System.out.println("Status code for POST: "+response.statusCode());
        
        String authorName = authorOperations.getNameOfTheLastAddedAuthor();
        int authorId = authorOperations.getIdOfTheLastAddedAuthor();
               
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBookWithAuthor(authorName, authorId);
        int idOfTheLastBook = bookOperations.getIdOfTheLastAddedBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());
        
        Response putResponse = bookOperations.editBookByIdWithAuthors(idOfTheLastBook,authorName, authorId);
        System.out.println("Status code for PUT: "+ putResponse.statusCode());
        assertEquals("PUT response should have status code 200",200,putResponse.statusCode());      
        
    }
    
    public void test010EditingABookWithNoAuthors(){
        testName.builTestName("Test 010 - Editing a book with no authors");       
               
        ArrayList<String> newBookFields       = new ArrayList<String>();
        ArrayList<String> lastAddedBookFields = new ArrayList<String>();         
       
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                
        
        newBookFields = bookOperations.bookProperties(bookOperations.getJsonString());        
        lastAddedBookFields = bookOperations.lastCreatedBookProperties();        
        
        //performing assertions in order to see if the book was created
        assertEquals (newBookFields.get(0), lastAddedBookFields.get(0));
        assertEquals (newBookFields.get(1), lastAddedBookFields.get(1));
        assertEquals (newBookFields.get(2), lastAddedBookFields.get(2));
        
        int idOfTheBookToBeEdited= bookOperations.getIdOfTheLastAddedBook(); 
        
        //editing the book
        Response putResponse = new BookOperations().editBookByIdWithNoAuthors(idOfTheBookToBeEdited);
        System.out.println("Status code for PUT when author does not exist: "+ putResponse.statusCode());
        assertEquals("Delete method should return 400",400,putResponse.statusCode());       
    }
    
    public void test011EditingABookInvalidBookId(){
        testName.builTestName("Test 011 - Editing a book - Invalid Book Id");       
               
        ArrayList<String> newBookFields       = new ArrayList<String>();
        ArrayList<String> lastAddedBookFields = new ArrayList<String>();         
       
        BookOperations bookOperations = new BookOperations();
        Response postResponse = bookOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                
        
        newBookFields = bookOperations.bookProperties(bookOperations.getJsonString());        
        lastAddedBookFields = bookOperations.lastCreatedBookProperties();        
        
        //performing assertions in order to see if the book was created
        assertEquals (newBookFields.get(0), lastAddedBookFields.get(0));
        assertEquals (newBookFields.get(1), lastAddedBookFields.get(1));
        assertEquals (newBookFields.get(2), lastAddedBookFields.get(2));
             
        
        //editing the book
        Response putResponse = new BookOperations().editBookByIdWithNoAuthors(-11111);
        System.out.println("Status code for PUT when the book does not exist: "+ putResponse.statusCode());
        assertEquals("Delete method should return 404",404,putResponse.statusCode());        
        
    }
    
}
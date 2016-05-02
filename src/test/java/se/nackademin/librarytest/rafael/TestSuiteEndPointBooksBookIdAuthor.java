
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
       //test001GetTheAuthorsOfABook();            
       //test002GetTheAuthorsOfABookInvalidBookID();
       //test003PostAddAuthorToABookByBookId();
       //test005PostAddAuthorToBookByBookIdWithInvalidBookId
       test006PostAddAuthorToBookByIdAuthorAlreadyAddedOnce();
    }         
    
   public void test006PostAddAuthorToBookByIdAuthorAlreadyAddedOnce(){
       testName.builTestName("Test 006 - Add author to a book by book Id - Author already added once");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
        System.out.println("IDDDDD Author: "+idOfTheLastCreatedAuthor);                
                
        postResponse = booksBooksOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());     
        
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        System.out.println("IDDDDD book: "+idOfTheLastBookCreated);    
        
        Response response = booksBooksOperations.postAuthorToRandomlyCreatedBook(idOfTheLastBookCreated, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+response.statusCode());
        assertEquals("The status code should be 200!",200,response.statusCode());
        
        response = booksBooksOperations.postAuthorToRandomlyCreatedBook(idOfTheLastBookCreated, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST : "+response.statusCode());
        assertEquals("The status code should be 400. The author was already added to this book!",400,response.statusCode());
   }
    
    
    public void test001GetTheAuthorsOfABook(){
        testName.builTestName("Test 001 - Get authors by book ID");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();                        
                
        postResponse = booksBooksOperations.createRandomBookWithAuthor(nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());     
        
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        
        Response getResponse = booksBooksOperations.getAuthorsByBookId(idOfTheLastBookCreated);
        System.out.println("Status code for GET: "+ getResponse.statusCode());
        assertEquals("GET response should have status code 200!",200,getResponse.statusCode());       
    }
    
    public void test002GetTheAuthorsOfABookInvalidBookID(){       
        testName.builTestName("Test 002 - Get authors by book ID - Invalid book ID");
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
    
   public void test003PostAddAuthorToABookByBookId(){
        testName.builTestName("Test 003 - Add author to a book by book Id");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
        System.out.println("IDDDDD Author: "+idOfTheLastCreatedAuthor);                
                
        postResponse = booksBooksOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());     
        
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        System.out.println("IDDDDD book: "+idOfTheLastBookCreated);    
        
        Response response = booksBooksOperations.postAuthorToRandomlyCreatedBook(idOfTheLastBookCreated, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST should be 200: "+response.statusCode());
        assertEquals("The status code should be 200!",200,response.statusCode());
    }
       
     public void test005PostAddAuthorToBookByBookIdWithInvalidBookId(){
        testName.builTestName("Test 004 - Add author to a book by book Id - Invalid Book Id");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("Post response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
               
        Response response = booksBooksOperations.postAuthorToRandomlyCreatedBook(-111111, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST book does not exist: "+response.statusCode());
        assertEquals("The POST status code should be 404!",404,response.statusCode());
    }
    
}


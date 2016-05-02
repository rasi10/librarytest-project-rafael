
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
       //test004PostAddAuthorToBookByBookIdWithInvalidBookId();
       //test005PostAddAuthorToBookByIdAuthorAlreadyAddedOnce();
       //test006PutAuthorDoesNotExistOnDatabase();
       //test007PutAuthorExistsOnDatabase();
       //test008PutAuthorIsAnAuthorForTheBookAlready();
    }         
    
       
    @Test
    public void test001GetTheAuthorsOfABook(){
        testName.builTestName("Test 001 - Get authors by book ID");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        
        //Create and author and check the status code
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        //Save info about the last created author
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();                        
         
        //creating a book with an author
        postResponse = booksBooksOperations.createRandomBookWithAuthor(nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());     
        
        //Saving the id of the last created book
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        
        //getting the last book created and checking the status code.
        Response getResponse = booksBooksOperations.getAuthorsByBookId(idOfTheLastBookCreated);
        System.out.println("Status code for GET: "+ getResponse.statusCode());
        assertEquals("GET response should have status code 200!",200,getResponse.statusCode());       
    }
    @Test
    public void test002GetTheAuthorsOfABookInvalidBookID(){       
        testName.builTestName("Test 002 - Get authors by book ID - Invalid book ID");
        
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        //Creating a book
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        //Saving information about the book
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
              
        //Creating a book with the author and checking the status code.
        postResponse = booksBooksOperations.createRandomBookWithAuthor(nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());     
        
        //trying to retrieve a book with an id that doesnt exist and checking the status code.
        Response getResponse = booksBooksOperations.getAuthorsByBookId(-111111);
        System.out.println("Status code for GET: "+ getResponse.statusCode());
        assertEquals("GET response should have status code 404 ",404,getResponse.statusCode());  
    }
    @Test
    public void test003PostAddAuthorToABookByBookId(){
        testName.builTestName("Test 003 - Add author to a book by book Id");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        
        //Creating an author and checking the status code.
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        //Saving data about the author
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
        
        //Creating a book and checking the status code.
        postResponse = booksBooksOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());     
        
        //saving the id of the last created book
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();           
        
        //Adding the author to a book
        Response response = booksBooksOperations.postAuthorToRandomlyCreatedBook(idOfTheLastBookCreated, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST should be 200: "+response.statusCode());
        assertEquals("The status code should be 200!",200,response.statusCode());
    }
    
    @Test
    public void test004PostAddAuthorToBookByBookIdWithInvalidBookId(){
        testName.builTestName("Test 004 - Add author to a book by book Id - Invalid Book Id");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        //creating an author
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
          
        //adding the author to a book that does not exist
        Response response = booksBooksOperations.postAuthorToRandomlyCreatedBook(-111111, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST book does not exist: "+response.statusCode());
        assertEquals("The POST status code should be 404!",404,response.statusCode());
    }
    
    @Test
    public void test005PostAddAuthorToBookByIdAuthorAlreadyAddedOnce(){
       testName.builTestName("Test 005 - Add author to a book by book Id - Author already added once");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        
        //Creating an author
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
           
        //Creating a book
        postResponse = booksBooksOperations.createRandomBook();
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());     
        
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        
        //Adding the author to the book
        Response response = booksBooksOperations.postAuthorToRandomlyCreatedBook(idOfTheLastBookCreated, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+response.statusCode());
        assertEquals("The status code should be 200!",200,response.statusCode());
        
        //Adding the author to the book again
        response = booksBooksOperations.postAuthorToRandomlyCreatedBook(idOfTheLastBookCreated, nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST : "+response.statusCode());
        assertEquals("The status code should be 400. The author was already added to this book!",400,response.statusCode());
    }
    @Test
    public void test006PutAuthorDoesNotExistOnDatabase(){
        testName.builTestName("Test 006 - Editing the author of a book - Invalid Author");
        
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        //creating a book
        Response postResponse = booksBooksOperations.createRandomBook();        
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());               
        //saving information about the book
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        //Editing the author with an author that does not exist.
        Response response = booksBooksOperations.putAuthorToRandomlyCreatedBookWithWrongAuthor(idOfTheLastBookCreated);
        System.out.println(response.asString());
        System.out.println("Status code for PUT:"+response.statusCode());
        assertEquals("Status code for PUT should be 400!",400, response.statusCode());
    }
    
    @Test
    public void test007PutAuthorExistsOnDatabase(){
        testName.builTestName("Test 007 - Edit author for a book - Author already exists on the database");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        
        //creating  a book
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();    
       
        //Creating an author
        postResponse = booksBooksOperations.createRandomBookWithAuthor(nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("Post response should have status code 201",201,postResponse.statusCode()); 
        
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();
        
        //Creating an author
        postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();       
        
        Response response = booksBooksOperations.putAuthorToRandomlyCreatedBookWithAuthor(idOfTheLastBookCreated, nameOfThelastAddedAuthor,idOfTheLastCreatedAuthor );
        System.out.println(response.asString());
        System.out.println("Status code for PUT:"+response.statusCode());
        assertEquals("Status code for PUT should be 200!",200, response.statusCode());
    }
    
    @Test
    public void test008PutAuthorIsAnAuthorForTheBookAlready(){
        testName.builTestName("Test 008 - Edit author for a book - Author is already an author for the especific book");
        BooksBooksAuthorOperations booksBooksOperations = new BooksBooksAuthorOperations();
        Response postResponse = booksBooksOperations.createRandomAuthorWithId();
        System.out.println("Status code for POST: "+postResponse.getStatusCode());              
        assertEquals("POST response should have status code 201",201,postResponse.statusCode());                        
        
        String nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        int idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();   
        
        postResponse = booksBooksOperations.createRandomBookWithAuthor(nameOfThelastAddedAuthor, idOfTheLastCreatedAuthor);
        System.out.println("Status code for POST: "+ postResponse.statusCode());
        assertEquals("POST response should have status code 201",201,postResponse.statusCode()); 
        int idOfTheLastBookCreated = booksBooksOperations.getIdOfTheLastAddedBook();                       
        
        nameOfThelastAddedAuthor = booksBooksOperations.getNameOfTheLastAddedAuthor();                       
        idOfTheLastCreatedAuthor = booksBooksOperations.getIdOfTheLastAddedAuthor();
               
        Response response = booksBooksOperations.putAuthorToRandomlyCreatedBookWithAuthor(idOfTheLastBookCreated, nameOfThelastAddedAuthor,idOfTheLastCreatedAuthor );
        System.out.println(response.asString());
        System.out.println("Status code for PUT:"+response.statusCode());
        assertEquals("Status code for PUT should be 400!",400, response.statusCode());
    
    }
}


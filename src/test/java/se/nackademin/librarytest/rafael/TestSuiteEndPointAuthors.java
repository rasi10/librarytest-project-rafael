/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.librarytest.rafael;

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
       
    }  
    
    public void test001GetAllAuthors(){
        testName.builTestName("Test 001 - Get all authors"); 
        AuthorOperations authorOperations = new AuthorOperations();
        Response response = authorOperations.getAllAuthors();
        System.out.println("Status code for GET: "+ response.statusCode()); //printing the statuscode.
        assertEquals("Status code should be 200",200, response.getStatusCode());
    }
}

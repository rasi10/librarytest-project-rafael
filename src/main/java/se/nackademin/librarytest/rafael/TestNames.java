/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.librarytest.rafael;

/**
 *
 * @author rafael
 */
public class TestNames {
    private String nameOfTheTest = "";
    
    public String builTestName(String testName){
        String testNameToBeDisplayed = "##Test name: "+testName;
        System.out.println(testNameToBeDisplayed);
        return testNameToBeDisplayed;
    }
    
}

package org.schemaanalyst.util;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;

public class TestSuiteReduction {
    private TestSuite nonReducdedTS;
    private TestSuite reducedTS;
    private TestRequirements testRequirements;

    public TestSuiteReduction(TestSuite nonReducdedTS, TestRequirements testRequirements) {
	this.nonReducdedTS = nonReducdedTS;
	this.testRequirements = testRequirements;
    }

    private void combiningDuplicateTestCases() {
	
	this.copyNonReducedTS();
	
	for (int i = 0; i < reducedTS.getTestCases().size(); i++) {
	    for (int j = i + 1; j < reducedTS.getTestCases().size(); j++) {
		Data state1 = reducedTS.getTestCases().get(i).getState();
		Data data1 = reducedTS.getTestCases().get(i).getData();
		Data state2 = reducedTS.getTestCases().get(j).getState();
		Data data2 = reducedTS.getTestCases().get(j).getData();
		if (state1.toString().equals(state2.toString()) && data1.toString().equals(data2.toString())) {
		    for (TestRequirementDescriptor d : reducedTS.getTestCases().get(j).getTestRequirement()
			    .getDescriptors()) {
			reducedTS.getTestCases().get(i).getTestRequirement().addDescriptor(d);
		    }
		    reducedTS.removeTestCase(j);
		    j--;
		}
	    }
	}
    }
    
    private void copyNonReducedTS() {
	reducedTS = new TestSuite(); 
	
	for (TestCase tc : nonReducdedTS.getTestCases()) {
	    reducedTS.addTestCase(tc);
	}
    }

    private void reduceByPredicate() {
	
	this.copyNonReducedTS();
	
	for (int i = 0; i < reducedTS.getTestCases().size(); i++) {
	    for (int j = i + 1; j < reducedTS.getTestCases().size(); j++) {
		// Check if they have equal tables
		boolean linkedTablesCheck = this.checkRequiredTables(
			reducedTS.getTestCases().get(j),
			reducedTS.getTestCases().get(i).getTestRequirement());
		// Check if the test requirements results is the same
		TestCase a = reducedTS.getTestCases().get(i);
		TestCase b = reducedTS.getTestCases().get(j);
		boolean equalTestResults = this.checkEqualResults(a, b);
		
		//Does both require the same row comparsion?
		boolean comparsionRowsRequired = this.checkRequiresComparisonRow(a,b);
		
		// if all is success full check predicates on the current test data
		if (linkedTablesCheck && equalTestResults && comparsionRowsRequired) {
		    
		    boolean predicateCheck = this.isTestDataCompatible(
			    a, b, 
			    a.getTestRequirement().getPredicate(), 
			    b.getTestRequirement().getPredicate());
		    
		    if (predicateCheck) {

			for (TestRequirementDescriptor d : reducedTS.getTestCases().get(j).getTestRequirement()
				.getDescriptors()) {
			    reducedTS.getTestCases().get(i).getTestRequirement().addDescriptor(d);
			}
			reducedTS.removeTestCase(j);
			j--;
		    }
		}
	    }
	}

    }
    
    private boolean checkRequiredTables(TestCase tc, TestRequirement tr) {
	for (Table table : tr.getTables()) {
	    for (Table tableTC : tc.getTestRequirement().getTables()) {
		if (!table.equals(tableTC)) {
		    return false;
		}
	    }
	}
	return true;
    }
    
    private boolean checkRequiresComparisonRow(TestCase tc1, TestCase tc2) {
	return tc1.getTestRequirement().getRequiresComparisonRow() == tc2.getTestRequirement().getRequiresComparisonRow();
    }
    
    private boolean checkEqualResults(TestCase tc1, TestCase tc2) {
	return tc1.getTestRequirement().getResult() == tc2.getTestRequirement().getResult();
    }
    
    private boolean isTestDataCompatible(TestCase tc1, TestCase tc2,
	    Predicate predicateTCOne, Predicate predicateTCTwo) {
	// check test data 1 with predicate 2
	Data state1 = tc1.getState();
	Data data1 = tc1.getData();
	PredicateChecker predicateChecker;
	predicateChecker = PredicateCheckerFactory.instantiate(
		predicateTCTwo, true, data1, state1);
	// check test data 2 with predicate 1
	Data state2 = tc2.getState();
	Data data2 = tc2.getData();
	PredicateChecker predicateCheckerTwo;
	predicateCheckerTwo = PredicateCheckerFactory.instantiate(
		predicateTCOne, true, data2, state2);
	
	boolean successOne = predicateChecker.check();
	
	boolean successTwo = predicateCheckerTwo.check();
	
	return successOne && successTwo;
    }    
    
    
    public void reducedTestSuiteByEqualTCs() {
	this.combiningDuplicateTestCases();
    }

    public void reducedTestSuiteByEqualPredicateData() {
	this.reduceByPredicate();
	//this.predicateReduce();
    }

    public TestSuite getReducedTestSuite() {
	return reducedTS;
    }
    
    public TestSuite getNonReducedTestSuite() {
	return nonReducdedTS;
    }

}

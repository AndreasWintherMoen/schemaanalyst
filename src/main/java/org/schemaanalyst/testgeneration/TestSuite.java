package org.schemaanalyst.testgeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/07/2014.
 */
public class TestSuite implements Serializable {

    private static final long serialVersionUID = -7221865547415541154L;

    private List<TestCase> testCases;
    private int generatedInserts = 0;
	private int reducedInsertsCount = 0;

    public TestSuite() {
        testCases = new ArrayList<>();
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }
    
    public void removeTestCase(int index) {
	testCases.remove(index);
    }

    public List<TestCase> getTestCases() {
        return new ArrayList<>(testCases);
    }

	public int getGeneratedInserts() {
		return generatedInserts;
	}

	public void addGeneratedInserts(int originalInsertsCount) {
		this.generatedInserts = originalInsertsCount + this.generatedInserts;
	}

	public int getReducedInsertsCount() {
		return reducedInsertsCount;
	}

	public void addReducedInsertsCount(int reducedInsertsCount) {
		this.reducedInsertsCount = reducedInsertsCount + reducedInsertsCount;
	}
	
	public int countNumberOfInserts() {
		int counter = 0;
		
		for (TestCase tc : testCases) {
			counter = counter + tc.getData().getNumRows() + tc.getState().getNumRows();
		}
		
		return counter;
	}

}

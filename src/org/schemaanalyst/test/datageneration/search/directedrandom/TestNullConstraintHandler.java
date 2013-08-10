package org.schemaanalyst.test.datageneration.search.directedrandom;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;
import static junitparams.JUnitParamsRunner.$;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.datageneration.search.directedrandom.NullConstraintHandler;
import org.schemaanalyst.datageneration.search.objective.data.NullConstraintObjectiveFunction;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestNullConstraintHandler {

    OneColumnMockDatabase database = new OneColumnMockDatabase();
    
    Object[] testValues() {
        return $(
                $(false, false, false, true),
                $(false, true, true, true),
                $(true, true, true, true),
                $(false, false, false, false),
                $(false, true, true, false),
                $(true, true, true, false)
                );
    }
    
    @Test 
    @Parameters(method = "testValues")
    public void test(boolean val1IsNull, boolean val2IsNull, boolean val3IsNull, boolean goalIsToSatisfy) {
        
        NullConstraintObjectiveFunction objFun = 
                new NullConstraintObjectiveFunction(database.column, "", goalIsToSatisfy);        
        
        database.createData(3);
        database.setDataValues(1, 2, 3);                
        List<Cell> dataValues = database.data.getCells();
        if (val1IsNull) dataValues.get(0).setNull(true);
        if (val2IsNull) dataValues.get(1).setNull(true);
        if (val3IsNull) dataValues.get(2).setNull(true);
        
        NullConstraintHandler nch = new NullConstraintHandler(objFun);
        
        nch.attemptToFindSolution(database.data);
        
        if (goalIsToSatisfy) {
            assertNull(dataValues.get(0).getValue());
            assertNull(dataValues.get(1).getValue());
            assertNull(dataValues.get(2).getValue());
        } else {
            assertNotNull(dataValues.get(0).getValue());
            assertNotNull(dataValues.get(1).getValue());
            assertNotNull(dataValues.get(2).getValue());
        }
    }   
}

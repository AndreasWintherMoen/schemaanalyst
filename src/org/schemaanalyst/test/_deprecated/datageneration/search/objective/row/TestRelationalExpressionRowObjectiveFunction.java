package org.schemaanalyst.test._deprecated.datageneration.search.objective.row;

import _deprecated.datageneration.search.objective.ObjectiveFunction;
import _deprecated.datageneration.search.objective.ObjectiveValue;
import _deprecated.datageneration.search.objective.row.RelationalExpressionRowObjectiveFunction;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestRelationalExpressionRowObjectiveFunction {

    RelationalExpression trueExp = 
            new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));

    RelationalExpression falseExp = 
            new RelationalExpression(
                    new ConstantExpression(new NumericValue(2)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));

    RelationalExpression nullExp = 
            new RelationalExpression(
                    new ConstantExpression(null), RelationalOperator.EQUALS,
                    new ConstantExpression(new NumericValue(1)));
    
    Object[] testValues() {
        return $(
                $(trueExp, true, false, true),
                $(trueExp, false, false, false),
                $(trueExp, true, true, true),
                $(trueExp, false, true, false),

                $(falseExp, true, false, false),                
                $(falseExp, false, false, true),
                $(falseExp, true, true, false),
                $(falseExp, false, true, true),
                
                $(nullExp, true, true, true),
                $(nullExp, false, true, true),                
                $(nullExp, true, false, false),
                $(nullExp, false, false, false)
        );
    }    
        
    @Test
    @Parameters(method = "testValues")    
    public void testExpression(RelationalExpression exp, boolean goalIsToSatisfy, boolean allowNull, boolean optimal) {
        ObjectiveFunction<Row> objFun = 
                new RelationalExpressionRowObjectiveFunction(
                        exp, goalIsToSatisfy, allowNull);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertNonOptimal(objVal);
        }                
    }
}

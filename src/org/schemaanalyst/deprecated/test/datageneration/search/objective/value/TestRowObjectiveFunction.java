package org.schemaanalyst.deprecated.test.datageneration.search.objective.value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.deprecated.datageneration.objective.CellListObjectiveFunction;
import org.schemaanalyst.deprecated.datageneration.objective.value.NumericValueObjectiveFunction;
import org.schemaanalyst.test.testutil.mock.MockCell;

import static org.schemaanalyst.deprecated.datageneration.objective.value.NumericValueObjectiveFunction.K;
import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.GREATER;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertEquivalent;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

public class TestRowObjectiveFunction {

    List<Cell> newRowType(int... values) {
        List<Cell> row = new ArrayList<>();
        for (int value : values) {
            row.add(new MockCell(new NumericValue(value)));
        }
        return row;
    }

    List<Value> newRowType2() {
        List<Value> row = new ArrayList<>();
        row.add(new NumericValue(100));
        row.add(new NumericValue(100));
        return row;
    }

    @Test
    public void equals_RowEquals() {
        assertOptimal(
                CellListObjectiveFunction.compute(
                newRowType(100, 100), EQUALS, newRowType(100, 100)));
    }

    @Test
    public void equals_RowNotEquals() {
        ObjectiveValue actualObjVal =
                CellListObjectiveFunction.compute(
                newRowType(100, 100), EQUALS, newRowType(100, 200));

        SumOfMultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level obj val");
        DistanceObjectiveValue distanceObjVal = new DistanceObjectiveValue("Value distance of 100");
        distanceObjVal.setValueUsingDistance(new BigDecimal(100).add(NumericValueObjectiveFunction.K));
        expectedObjVal.add(distanceObjVal);

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test
    public void notEquals_RowNotEquals() {
        assertOptimal(
                CellListObjectiveFunction.compute(
                newRowType(100, 100), NOT_EQUALS, newRowType(100, 200)));
    }

    @Test
    public void notEquals_RowEquals() {
        ObjectiveValue actualObjVal =
                CellListObjectiveFunction.compute(
                newRowType(100, 100), NOT_EQUALS, newRowType(100, 100));

        BestOfMultiObjectiveValue expectedObjVal = new BestOfMultiObjectiveValue("Top level obj val");
        DistanceObjectiveValue sameObjVal = new DistanceObjectiveValue("Value equal");
        sameObjVal.setValueUsingDistance(K);
        expectedObjVal.add(sameObjVal);
        expectedObjVal.add(sameObjVal);

        assertEquivalent(expectedObjVal, actualObjVal);
    }

    @Test(expected = ObjectiveFunctionException.class)
    public void nonEqualRowSizesThrowsException() {
        CellListObjectiveFunction.compute(newRowType(100, 100), EQUALS, newRowType(100, 100, 100));
    }

    @Test(expected = ObjectiveFunctionException.class)
    public void greater_ThrowsException() {
        CellListObjectiveFunction.compute(newRowType(100, 100), GREATER, newRowType(100, 100, 100));
    }
}
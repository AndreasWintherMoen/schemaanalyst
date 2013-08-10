package org.schemaanalyst.datageneration.search.directedrandom;

import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.objective.data.ReferenceConstraintObjectiveFunction;
import org.schemaanalyst.util.random.Random;

public class ReferenceConstraintHandler extends ConstraintHandler<ReferenceConstraintObjectiveFunction> {

    private CellRandomiser cellRandomizer;
    private Random random;
    
    public ReferenceConstraintHandler(ReferenceConstraintObjectiveFunction objFun,
                            Random random,
                            CellRandomiser cellRandomizer) {
        super(objFun);
        this.random = random;
        this.cellRandomizer = cellRandomizer;
    }

    @Override
    protected void attemptToSatisfy(List<Row> rows) {
        List<Row> referenceRows = objFun.getReferenceRows();
        int numReferenceRows = referenceRows.size();

        if (numReferenceRows > 0) { 
            for (Row row : rows) {                
                int referenceIndex = random.nextInt(numReferenceRows - 1);
                Row referenceRow = referenceRows.get(referenceIndex);
                row.copyValues(referenceRow);
            }
        }
    }
    
    @Override
    protected void attemptToFalsify(List<Row> rows) {
        for (Row row : rows) {
            cellRandomizer.randomiseCells(row, objFun.isNullAllowed());
        }
    }
}
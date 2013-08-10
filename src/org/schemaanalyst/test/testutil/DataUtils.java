package org.schemaanalyst.test.testutil;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;

public class DataUtils {

    public static Integer[] dataToIntegerValues(Data data) {
        List<Integer> values = new ArrayList<>();
        
        for (Cell cell : data.getCells()) {
            Value value = cell.getValue();
            if (value == null) {
                values.add(null);
            } else {
                values.add(((NumericValue) value).get().intValue());           
            }
        }
        
        return values.toArray(new Integer[values.size()]);
    }
    
}

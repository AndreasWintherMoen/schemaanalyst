package org.schemaanalyst.testgeneration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;

public class ReduecTestCase {

    private Schema schema;
    
    
	public void reduceData(Data data, Data state, Schema schema) {
	List<Table> tables = data.getTables();
	
	for (Table table : tables) {
	    List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints(table);
	    
	    for (ForeignKeyConstraint fk : fks) {
	    	// a hack for self-ref
	    	if (!fk.getTable().equals(fk.getReferenceTable())) {
				// Cookies (child)
				List<Column> columns = fk.getColumns();
				// Parent
				List<Column> refCol = fk.getReferenceColumns();
				
				List<Row> childData = data.getRows(table, columns);
				List<Row> parentData = data.getRows(fk.getReferenceTable(), refCol);
				
				// State table values
				List<Row> parentState = state.getRows(fk.getReferenceTable(), refCol);
				
				List<Value> childValues = new ArrayList<>();
				List<Value> parentValues = new ArrayList<>();
				List<Value> parentStateValues = new ArrayList<>();
				
				// Getting the values rather than just the names as the column names differ
				for (Row row : childData) {
					for (Cell cell : row.getCells()) {
						childValues.add(cell.getValue());
					}
				}
				
				for (Row row : parentData) {
					for (Cell cell : row.getCells()) {
						parentValues.add(cell.getValue());
					}
				}
				
				for (Row row : parentState) {
					for (Cell cell : row.getCells()) {
						parentStateValues.add(cell.getValue());
					}
				}
				
				boolean aretheValueEqual = CollectionUtils.isEqualCollection(childValues, parentValues);
				boolean aretheValueEqualState = CollectionUtils.isEqualCollection(childValues, parentStateValues);

				if(!aretheValueEqual) {
				    data.removeTable(fk.getReferenceTable());
				}
				
				if(aretheValueEqualState) {
				    data.removeTable(fk.getReferenceTable());
				}
				
				// Check equality of prints but this raises and issue of different column names
				//if(!childData.toString().equals(parentData.toString())) {
				    //data.removeTable(fk.getReferenceTable());
				//}
	    	}
	    }
	}
	
    }
}

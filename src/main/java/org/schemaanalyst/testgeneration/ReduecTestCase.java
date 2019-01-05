package org.schemaanalyst.testgeneration;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;

public class ReduecTestCase {

    private Schema schema;
    
    
    public void reduceData(Data data, Schema schema) {
	List<Table> tables = data.getTables();
	
	for (Table table : tables) {
	    List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints(table);
	    
	    for (ForeignKeyConstraint fk : fks) {
		// Cookies (child)
		List<Column> columns = fk.getColumns();
		// Parent
		List<Column> refCol = fk.getReferenceColumns();
		
		List<Row> childData = data.getRows(table, columns);
		List<Row> parentData = data.getRows(fk.getReferenceTable(), refCol);
		
		if(!childData.toString().equals(parentData.toString())) {
		    //System.out.println("Equal");
		    //System.out.println(childData + " == " + parentData);
		    data.removeTable(fk.getReferenceTable());
		} //else {
		    //System.out.println("Not Equal");
		    //System.out.println(childData + " != " + parentData);
		//}
		
		//System.out.println(fk);		
	    }
	    
	    //List<Table> parentTables = schema.getConnectedTables(table);
	}
	
    }
}

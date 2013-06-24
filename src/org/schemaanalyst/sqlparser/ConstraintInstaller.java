package org.schemaanalyst.sqlparser;

import java.util.ArrayList;
import java.util.List;

import gudusoft.gsqlparser.EConstraintType;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TObjectName;
import gudusoft.gsqlparser.nodes.TObjectNameList;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

class ConstraintInstaller {

	Schema schema;
	Table currentTable;
	Column currentColumn;
	
	ConstraintInstaller(Schema schema, Table currentTable, Column currentColumn) {
		this.schema = schema;
		this.currentTable = currentTable;
		this.currentColumn = currentColumn;		
	}
	
	void performInstallation(TConstraint node) {
    	EConstraintType constraintType = node.getConstraint_type(); 

    	if (constraintType == EConstraintType.check) {
    		installCheckConstraint(node);
    		return;
    	} 
    	
    	if (constraintType == EConstraintType.foreign_key || 
    			constraintType == EConstraintType.reference) {
    		installForeignKeyConstraint(node);
    		return;
    	} 
    	
    	if (constraintType == EConstraintType.notnull) {
    		installNotNullConstraint(node);
    		return;
    	}
    	
    	if (constraintType == EConstraintType.primary_key) {
    		installPrimaryKeyConstraint(node);
    		return;
    	}
    	
    	if (constraintType == EConstraintType.unique) {
    		installUniqueConstraint(node);
    		return;
    	} 
    	
    	throw new UnsupportedFeatureException(node);
	}
		
	String getConstraintName(TConstraint node) {
		String constraintName = null;
		TObjectName constraintNameObject = node.getConstraintName();
		if (constraintNameObject != null) {
			constraintName = constraintNameObject.toString();
		}
		return constraintName;
	}
	
	List<Column> getConstraintColumns(TConstraint node) {
		List<Column> columns = new ArrayList<>();
    	
    	TObjectNameList nodeColumns = node.getColumnList();
    	if (nodeColumns == null) {
    		if (currentColumn != null) {
    			columns.add(currentColumn);
    		} else {
    			throw new SQLParseException(
    					"No columns defined for constraint for \"" + node + "\"");
    		}
    	} else {
    		for (int i=0; i < nodeColumns.size(); i++) {
    			String columnName = QuoteStripper.stripQuotes(nodeColumns.getObjectName(i));
    			Column column = currentTable.getColumn(columnName);
    			columns.add(column);
    		}
    	}		
		
		return columns;
	}
	
	void installCheckConstraint(TConstraint node) {    			
		Expression expression = ExpressionMapper.map(currentTable, node.getCheckCondition());		
		currentTable.addCheckConstraint(getConstraintName(node), expression);
	}
	
	void installNotNullConstraint(TConstraint node) {
		List<Column> columns = getConstraintColumns(node);
		
		if (columns.size() > 1) {
			throw new SQLParseException(
					"Cannot make more than one column NOT NULL for \"" + node + "\"");
		}
		
		Column column = columns.get(0);
		currentTable.addNotNullConstraint(getConstraintName(node), column);
	}
	
	void installPrimaryKeyConstraint(TConstraint node) {
		Column[] columns = getConstraintColumns(node).toArray(new Column[0]);
		currentTable.setPrimaryKeyConstraint(getConstraintName(node), columns);
	}
	
	void installForeignKeyConstraint(TConstraint node) {
		String referenceTableName = node.getReferencedObject().toString();
		Table referenceTable = schema.getTable(referenceTableName);

		if (referenceTable == null) {
			throw new SQLParseException(
					"Unknown table \"" + referenceTableName + 
					"\" referenced by foreign key for table \"" + currentTable + 
					"\" for \"" + node + "\"");
		}
		
		List<Column> columns = getConstraintColumns(node);
		
		List<String> referenceColumnNames = new ArrayList<>();
		TObjectNameList referenceColumnList = node.getReferencedColumnList();		
		if (referenceColumnList == null) {
			for (Column column : columns) {
				referenceColumnNames.add(column.getName());
			}			
		} else {
			for (int i=0; i < referenceColumnList.size(); i++) {
				referenceColumnNames.add(referenceColumnList.getObjectName(i).toString());
			}
		}
		
		for (String columnName : referenceColumnNames) {
			columns.add(referenceTable.getColumn(columnName));
		}
		
		currentTable.addForeignKeyConstraint(getConstraintName(node), referenceTable, columns.toArray(new Column[0]));
	}
	
	void installUniqueConstraint(TConstraint node) {
		Column[] columns = getConstraintColumns(node).toArray(new Column[0]);
		currentTable.addUniqueConstraint(getConstraintName(node), columns);		
	}
	
	static void install(Schema schema, Table currentTable, Column currentColumn, TConstraint node) {
		new ConstraintInstaller(schema, currentTable, currentColumn).performInstallation(node);
	}	
}
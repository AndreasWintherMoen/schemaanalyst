package org.schemaanalyst.javawriter;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.DataTypeCategoryVisitor;
import org.schemaanalyst.sqlrepresentation.datatype.LengthLimited;
import org.schemaanalyst.sqlrepresentation.datatype.PrecisionedAndScaled;
import org.schemaanalyst.sqlrepresentation.datatype.Signed;

public class SchemaJavaWriter {

	// variable name prefixes
	static final String TABLE_VAR_NAME_PREFIX = "table";
	
	// method call names
	static final String TABLE_ADD_COLUMN_METHOD_NAME = "addColumn",
						TABLE_GET_COLUMN_METHOD_NAME = "getColumn",
						TABLE_MAKE_COLUMN_LIST_METHOD_NAME = "makeColumnList",
						TABLE_ADD_FOREIGN_KEY_CONSTRAINT_METHOD_NAME = "addForeignKeyConstraint",
						TABLE_ADD_NOT_NULL_CONSTRAINT_METHOD_NAME = "addNotNullConstraint",
						TABLE_SET_PRIMARY_KEY_CONSTRAINT_METHOD_NAME = "setPrimaryKeyConstraint",
						TABLE_ADD_UNIQUE_CONSTRAINT_METHOD_NAME = "addUniqueConstraint";

	
	protected Schema schema;
	protected ImportManager importManager;
	protected JavaBuffer jb;
	
	public SchemaJavaWriter(Schema schema) {
		this.schema = schema;
	}
	
	public String writeSchema() {
		return writeSchema(null);
	}
	
	public String writeSchema(String packageName) {
		// initialise	
		importManager = new ImportManager();		
		jb = new JavaBuffer();		
			
		// get schema info
		importManager.addImportFor(Schema.class);		
		String schemaClassName = Schema.class.getSimpleName();
		String schemaName = schema.getName();
		
		// start class
		jb.addln("public class " + schemaName + " extends " + schemaClassName + " {");
		
		// start constructor
		jb.addln();		
		jb.addln(1, "public " + schemaName + "() {");		
		jb.addln(2, "super(\"" + schemaName + "\");");
		
		// write table statements
		List<Table> tables = schema.getTables();
		for (Table table : tables) {
			addTableCode(table);
		} 
		
		// end constructor		
		jb.addln(1, "}");
		
		// end class
		jb.addln(0, "}");		

		// get final Java code
		String code = getPackageStatement(packageName) + importManager.writeImportStatements(); 
		if (code != "") code += "\n";		
		code += jb.getCode();
		return code;
	}
	
	protected void addTableCode(Table table) {
		
		importManager.addImportFor(table);

		jb.addln();				
		
		String className = Table.class.getSimpleName();
		String tableName = table.getName();
		String tableVarName = getTableVariableName(table);
		
		String tableConstruction = className + " " + tableVarName + " = new " + className + "(\"" + tableName + "\");";		
		jb.addln(tableConstruction);
		
		List<Column> columns = table.getColumns();
		for (Column column : columns) {
			addColumnCode(tableVarName, column);
		}
		
		List<Constraint> constraints = table.getConstraints();
		for (Constraint constraint : constraints) {
			addConstraintCode(tableVarName, constraint);
		}
	}
	
	protected void addColumnCode(String tableVarName, Column column) {
		
		String columnName = column.getName();
		
		
		String statement = tableVarName + "." + TABLE_ADD_COLUMN_METHOD_NAME + 
						   "(\"" + columnName + "\", " + writeDataTypeConstruction(column.getType()) + ");";
		
		jb.addln(statement);
	}
	
	protected String writeDataTypeConstruction(DataType dataType) {
		
		class SchemaWriterDataTypeCategoryVisitor implements DataTypeCategoryVisitor {

			String code;
			
			String writeParams(DataType type) {
				code = "";
				type.accept(this);
				return "(" + code + ")";
			}
			
			public void visit(DataType type) {
				// do nothing
			}

			public void visit(LengthLimited type) {
				code += type.getLength();
			}

			public void visit(PrecisionedAndScaled type) {
				Integer precision = type.getPrecision(); 
				if (precision != null) {
					code += precision;
					Integer scale = type.getScale();
					if (scale != null) {
						code += ", " + scale;
					}
				}
			}

			public void visit(Signed type) {
				boolean isSigned = type.isSigned();
				if (!isSigned) {
					code += "false";
				}
			}
		}
		
		importManager.addImportFor(dataType);
		String dataTypeClassName = dataType.getClass().getSimpleName();
		return "new " + dataTypeClassName + new SchemaWriterDataTypeCategoryVisitor().writeParams(dataType);
	}
	
	protected void addConstraintCode(String tableVarName, Constraint constraint) {
		
		class SchemaWriterContraintVisitor implements ConstraintVisitor {

			String tableVarName, code;
			
			String writeConstraint(String tableVarName, Constraint constraint) {
				importManager.addImportFor(constraint);
				this.tableVarName = tableVarName;
				code = "";
				constraint.accept(this);				
				return code;
			}
			
			public void visit(CheckConstraint constraint) {
				// TODO: add code for check constraints ...
			}

			public void visit(ForeignKeyConstraint constraint) {
				code =  writeMethodCall(TABLE_ADD_FOREIGN_KEY_CONSTRAINT_METHOD_NAME)  + "(";				
				
				code += writeConstraintName(constraint);								
				code += writeGetColumnListCode(tableVarName, constraint.getColumns(), true);
				
				String refTableVarName = getTableVariableName(constraint.getReferenceTable());
				code += refTableVarName + ", ";
				code += writeGetColumnListCode(refTableVarName, constraint.getReferenceColumns(), true);
				
				code += ");";								
			}

			public void visit(NotNullConstraint constraint) {				
				code =  writeMethodCall(TABLE_ADD_NOT_NULL_CONSTRAINT_METHOD_NAME)  + "(";				
				code += writeConstraintName(constraint);								
				code += writeGetColumnCode(tableVarName, constraint.getColumn());
				code += ");";				
			}

			public void visit(PrimaryKeyConstraint constraint) {
				code =  writeMethodCall(TABLE_SET_PRIMARY_KEY_CONSTRAINT_METHOD_NAME)  + "(";				
				code += writeConstraintName(constraint);								
				code += writeGetColumnListCode(tableVarName, constraint.getColumns());
				code += ");";
			} 

			public void visit(UniqueConstraint constraint) {				
				code =  writeMethodCall(TABLE_ADD_UNIQUE_CONSTRAINT_METHOD_NAME)  + "(";				
				code += writeConstraintName(constraint);				
				code += writeGetColumnListCode(tableVarName, constraint.getColumns());
				code += ");";				
			}	
			
			String writeConstraintName(Constraint constraint) {
				return constraint.hasName() ? "\"" + constraint.getName() + "\", " : "";
			}
			
			String writeMethodCall(String methodName) {
				return tableVarName + "." + methodName;
			}
		}
		
		String code = new SchemaWriterContraintVisitor().writeConstraint(tableVarName, constraint);
		jb.addln(code);
	}
	
	protected String writeGetColumnCode(String tableVarName, Column column) {
		return tableVarName + "." + TABLE_GET_COLUMN_METHOD_NAME + "(\"" + column.getName() + "\")";
	}
	
	protected String writeGetColumnListCode(String tableVarName, List<Column> columns) {
		return writeGetColumnListCode(tableVarName, columns, false);
	}
	
	protected String writeGetColumnListCode(String tableVarName, List<Column> columns, boolean wrapInMethod) {
		String code = "";
		
		boolean doWrapInMethod = wrapInMethod && columns.size() > 2; 
		if (doWrapInMethod) {
			code = Table.class.getSimpleName() + "." + TABLE_MAKE_COLUMN_LIST_METHOD_NAME + "(";
		}
		
		boolean first = true;
		for (Column column : columns) {
			if (first) {
				first = false;
			} else {
				code += ", ";
			}
			code += writeGetColumnCode(tableVarName, column);
		}
		
		if (doWrapInMethod) {
			code += ")";
		}
		
		return code;
	}
	
	protected String getPackageStatement(String packageName) {
		return (packageName == null) ? "" : "package " + packageName + ";\n\n";
	}
	
	protected String getTableVariableName(Table table) {
		return getJavaVariableName(TABLE_VAR_NAME_PREFIX, table.getName());
	}
	
	protected String getJavaVariableName(String prefix, String originalIdentifier) {
		// TODO - camel case the original identifier to make it neater ...
		return prefix + originalIdentifier;
	}
}

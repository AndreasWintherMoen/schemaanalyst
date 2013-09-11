/*
 */
package org.schemaanalyst.test.mutation.equivalence;

import org.junit.Test;
import static org.junit.Assert.*;
import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.equivalence.CheckEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.ColumnEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.ForeignKeyEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.NotNullEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.PrimaryKeyEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalanceWithNotNullCheckChecker;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.TableEquivalenceChecker;
import org.schemaanalyst.mutation.equivalence.UniqueEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 *
 * @author Chris J. Wright
 */
public class TestSchemaEquivalanceWithNotNullCheckChecker {
    
    @Test
    public void testNotNullWithCheckEquivalence() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addNotNullConstraint(new NotNullConstraint(t1, a1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        s2.addCheckConstraint(new CheckConstraint(t2, new NullExpression(new ColumnExpression(t2, a2), true)));
        assertTrue("A schema with a NOT NULL should be equivalent to a schema "
                + "with a check that enforces the same behaviour as the NOT "
                + "NULL", tester.areEquivalent(s1, s2));
        assertTrue("A schema with a NOT NULL should be equivalent to a schema "
                + "with a check that enforces the same behaviour as the NOT "
                + "NULL", tester.areEquivalent(s2, s1));
    }
    
    @Test
    public void testNotNullWithParensCheckEquivalence() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addNotNullConstraint(new NotNullConstraint(t1, a1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        s2.addCheckConstraint(new CheckConstraint(t2, new ParenthesisedExpression(new NullExpression(new ColumnExpression(t2, a2), true))));
        assertTrue("A schema with a NOT NULL should be equivalent to a schema "
                + "with a check that enforces the same behaviour as the NOT "
                + "NULL", tester.areEquivalent(s1, s2));
        assertTrue("A schema with a NOT NULL should be equivalent to a schema "
                + "with a check that enforces the same behaviour as the NOT "
                + "NULL", tester.areEquivalent(s2, s1));
    }
    
    @Test
    public void testNotNullWithInvalidCheckEquivalence() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addNotNullConstraint(new NotNullConstraint(t1, a1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        Column b2 = t2.createColumn("b", new IntDataType());
        s2.addCheckConstraint(new CheckConstraint(t2, new NullExpression(new ConstantExpression(new NumericValue(1)), true)));
        assertFalse("A schema with a NOT NULL should not be equivalent to a "
                + "schema with a check with a NULL expression if that NULL "
                + "expression contains a ConstantExpression rather than a "
                + "ColumnExpression", tester.areEquivalent(s1, s2));
        s2.getCheckConstraints().get(0).setExpression(new AndExpression(new ConstantExpression(new BooleanValue(true))));
        assertFalse("A schema with a NOT NULL should not be equivalent to a "
                + "schema with a check with an expression that does not contain"
                + " a NULL expression", tester.areEquivalent(s1, s2));
        s2.getCheckConstraints().get(0).setExpression(new NullExpression(new ColumnExpression(t2, a2), false));
        assertFalse("A schema with a NOT NULL should not be equivalent to a "
                + "schema with a check with a NULL expression if that NULL "
                + "expression is set to NULL rather than NOT NULL",
                tester.areEquivalent(s1, s2));
        s2.getCheckConstraints().get(0).setExpression(new NullExpression(new ColumnExpression(t2, b2), false));
        assertFalse("A schema with a NOT NULL should not be equivalent to a "
                + "schema with a check with a NULL expression if that NULL "
                + "expression refers to the wrong table", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testNotNullWithCheckManyConstraints() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Column b1 = t1.createColumn("b", new IntDataType());
        Column c1 = t1.createColumn("c", new IntDataType());
        s1.addNotNullConstraint(new NotNullConstraint(t1, b1));
        s1.addNotNullConstraint(new NotNullConstraint(t1, c1));
        s1.addCheckConstraint(new CheckConstraint(t1, new NullExpression(new ColumnExpression(t1, a1), true)));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        Column b2 = t2.createColumn("b", new IntDataType());
        Column c2 = t2.createColumn("c", new IntDataType());
        s2.addNotNullConstraint(new NotNullConstraint(t2, b2));
        s2.addCheckConstraint(new CheckConstraint(t2, new NullExpression(new ColumnExpression(t2, c2), true)));
        s2.addCheckConstraint(new CheckConstraint(t2, new NullExpression(new ColumnExpression(t2, a2), true)));
        assertTrue("A schema with a NOT NULL should be equivalent to a schema "
                + "with a check that enforces the same behaviour as the NOT "
                + "NULL", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testConstructor() {
        SchemaEquivalenceChecker tester1 = new SchemaEquivalanceWithNotNullCheckChecker();
        SchemaEquivalenceChecker tester2 = new SchemaEquivalanceWithNotNullCheckChecker(
                new TableEquivalenceChecker(new ColumnEquivalenceChecker()),
                new ColumnEquivalenceChecker(),
                new PrimaryKeyEquivalenceChecker(),
                new ForeignKeyEquivalenceChecker(),
                new UniqueEquivalenceChecker(),
                new CheckEquivalenceChecker(),
                new NotNullEquivalenceChecker());
        Schema s = new Schema("s");
        Table t = s.createTable("t");
        Column a = t.createColumn("a", new IntDataType());
        assertEquals("A SchemaEquivalenceTester should give the same results, "
                + "when constructed by the default constructor and by the "
                + "parameterised constructor (given the same sub-component "
                + "EquivalenceTester classes as the defaults)",
                tester1.areEquivalent(s, s), tester2.areEquivalent(s, s));
    }
    
    @Test
    public void testSameInstance() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s = new Schema("s");
        Table t = s.createTable("t");
        Column a = t.createColumn("a", new IntDataType());
        assertTrue("A schema should be equivalent to itself",
                tester.areEquivalent(s, s));
    }

    @Test
    public void testDifferentInstance() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertTrue("Two schemas with the same tables and columns should be "
                + "equivalent", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testDifferentIdentifiers() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("schemaOne");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Schema s2 = new Schema("schemaTwo");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("Two schemas with different identifiers should not be "
                + "equivalent", tester.areEquivalent(s1, s2));
        s2.setName("schemaOne");
        assertTrue("Changing the identifier of a schema should be able to make "
                + "two otherwise identical schemas equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testDifferentTables() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t1");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("Two schemas containing different tables should not be "
                + "equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testDifferentTableCount() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Table u1 = s1.createTable("u");
        Column a1 = t1.createColumn("a", new IntDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("Two schemas containing different numbers of tables should "
                + "not be equivalent", tester.areEquivalent(s1, s2));
        Table u2 = s2.createTable("u");
        assertTrue("Adding a table to a schema should be able to make two "
                + "schemas equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testDifferentColumnsInTables() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new CharDataType());
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("Two schemas with tables containing columns with different "
                + "datatypes should not be equivalent", tester.areEquivalent(s1, s2));
        a2.setDataType(new CharDataType());
        assertTrue("Changing the datatype of a column should be able to make "
                + "two schemas equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testDifferentTableOrdering() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Table u1 = s1.createTable("u");
        Schema s2 = new Schema("s");
        Table u2 = s2.createTable("u");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertTrue("The ordering of tables in a schema should not affect the "
                + "detection of equivalence", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testMissingPrimaryKey() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.setPrimaryKeyConstraint(new PrimaryKeyConstraint(t1, a1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("A schema with a primary key should not be equivalent to a "
                + "schema without a primary key", tester.areEquivalent(s1, s2));
        s2.setPrimaryKeyConstraint(new PrimaryKeyConstraint(t2, a2));
        assertTrue("Adding a primary key should be able to make two schemas "
                + "equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testMultiplePrimaryKeys() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Table u1 = s1.createTable("u");
        Column b1 = u1.createColumn("b", new IntDataType());
        s1.setPrimaryKeyConstraint(new PrimaryKeyConstraint(t1, a1));
        s1.setPrimaryKeyConstraint(new PrimaryKeyConstraint(u1, b1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        Table u2 = s2.createTable("u");
        Column b2 = u2.createColumn("b", new IntDataType());
        s2.setPrimaryKeyConstraint(new PrimaryKeyConstraint(t2, a2));
        assertFalse("A schema with two primary keys should not be equivalent "
                + "to a schema with one primary key", tester.areEquivalent(s1, s2));
        s2.setPrimaryKeyConstraint(new PrimaryKeyConstraint(u2, b2));
        assertTrue("Adding a primary key should be able to make two schemas "
                + "equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testMissingUnique() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addUniqueConstraint(new UniqueConstraint(t1, a1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("A schema with a unique constraint should not be equivalent"
                + " to a schema without a unique constraint",
                tester.areEquivalent(s1, s2));
        s2.addUniqueConstraint(new UniqueConstraint(t2, a2));
        assertTrue("Adding a unique constraint to a schema should be able to "
                + "make two schemas equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testMultipleUniques() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Column b1 = t1.createColumn("b", new IntDataType());
        s1.addUniqueConstraint(new UniqueConstraint(t1, a1));
        s1.addUniqueConstraint(new UniqueConstraint(t1, b1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        Column b2 = t2.createColumn("b", new IntDataType());
        s2.addUniqueConstraint(new UniqueConstraint(t2, a2));
        assertFalse("A schema with two uniques should not be equivalent to a "
                + "schema with one unique", tester.areEquivalent(s1, s2));
        s2.addUniqueConstraint(new UniqueConstraint(t2, b2));
        assertTrue("Two schemas with matching tables, columns and uniques "
                + "should be equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testMissingForeignKey() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Table u1 = s1.createTable("u");
        Column b1 = u1.createColumn("b", new IntDataType());
        s1.addForeignKeyConstraint(new ForeignKeyConstraint(t1, a1, u1, b1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        Table u2 = s2.createTable("u");
        Column b2 = u2.createColumn("b", new IntDataType());
        assertFalse("A schema with a foreign key should not be equivalent to "
                + "one without a foreign key", tester.areEquivalent(s1, s2));
        s2.addForeignKeyConstraint(new ForeignKeyConstraint(t2, a2, u2, b2));
        assertTrue("Adding a foreign key constraint should be able to make two "
                + "otherwise identical schemas equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testMultipleForeignKeys() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Column c1 = t1.createColumn("c", new IntDataType());
        Table u1 = s1.createTable("u");
        Column b1 = u1.createColumn("b", new IntDataType());
        Column d1 = u1.createColumn("d", new IntDataType());
        s1.addForeignKeyConstraint(new ForeignKeyConstraint(t1, a1, u1, b1));
        s1.addForeignKeyConstraint(new ForeignKeyConstraint(t1, c1, u1, d1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        Column c2 = t2.createColumn("c", new IntDataType());
        Table u2 = s2.createTable("u");
        Column b2 = u2.createColumn("b", new IntDataType());
        Column d2 = u2.createColumn("d", new IntDataType());
        s2.addForeignKeyConstraint(new ForeignKeyConstraint(t2, a2, u2, b2));
        assertFalse("A schema with two foreign keys should not be equivalent to"
                + " a schema with one foreign key", tester.areEquivalent(s1, s2));
        s2.addForeignKeyConstraint(new ForeignKeyConstraint(t2, c2, u2, d2));
        assertTrue("Adding a foreign key constraint should be able to make two "
                + "otherwise identical schemas equivalent", tester.areEquivalent(s1, s2));
    }

    @Test
    public void testMissingCheck() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addCheckConstraint(new CheckConstraint(t1, new RelationalExpression(
                new ColumnExpression(t1, a1),
                RelationalOperator.LESS,
                new ConstantExpression(new NumericValue(5)))));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("A schema with a check constraint should not be equivalent "
                + "to a schema without a check constraint", tester.areEquivalent(s1, s2));
        s2.addCheckConstraint(new CheckConstraint(t2, new RelationalExpression(
                new ColumnExpression(t2, a2),
                RelationalOperator.LESS,
                new ConstantExpression(new NumericValue(5)))));
        assertTrue("Adding a check constraint should be able to make two "
                + "otherwise identical schemas equivalent", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testMultipleChecks() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addCheckConstraint(new CheckConstraint(t1, new RelationalExpression(
                new ColumnExpression(t1, a1),
                RelationalOperator.LESS,
                new ConstantExpression(new NumericValue(5)))));
        s1.addCheckConstraint(new CheckConstraint(t1, new RelationalExpression(
                new ColumnExpression(t1, a1),
                RelationalOperator.GREATER_OR_EQUALS,
                new ConstantExpression(new NumericValue(0)))));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        s2.addCheckConstraint(new CheckConstraint(t2, new RelationalExpression(
                new ColumnExpression(t2, a2),
                RelationalOperator.LESS,
                new ConstantExpression(new NumericValue(5)))));
        assertFalse("A schema with two check constraints should not be "
                + "equivalent to a schema without a check constraint", tester.areEquivalent(s1, s2));
        s2.addCheckConstraint(new CheckConstraint(t2, new RelationalExpression(
                new ColumnExpression(t2, a2),
                RelationalOperator.GREATER_OR_EQUALS,
                new ConstantExpression(new NumericValue(0)))));
        assertTrue("Adding a check constraint should be able to make two "
                + "otherwise identical schemas equivalent", tester.areEquivalent(s1, s2));
    }
    
    @Test
    public void testMissingNotNull() {
        SchemaEquivalenceChecker tester = new SchemaEquivalanceWithNotNullCheckChecker();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addNotNullConstraint(new NotNullConstraint(t1, a1));
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        assertFalse("A schema with one not null constraint should not be "
                + "equivalent to a schema without a check constraint",
                tester.areEquivalent(s1, s2));
        s2.addNotNullConstraint(new NotNullConstraint(t2, a2));
        assertTrue("Adding a not null should be able to make two otherwise "
                + "identical schemas equivalent", tester.areEquivalent(s1, s2));
    }
    
}

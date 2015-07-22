package org.schemaanalyst.mutation.quasimutant;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.util.DataCapturer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by phil on 22/07/2015.
 */
public abstract class StaticDBMSRemover extends MutantRemover<Schema> {

    protected Map<Class<?>, Set<Class<?>>> compatibleTypes;

    public StaticDBMSRemover() {
        initializeCompatibleTypes();
    }

    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {
        for (Iterator<Mutant<Schema>> it = mutants.iterator(); it.hasNext();) {
            Mutant<Schema> mutant = it.next();
            Schema schema = mutant.getMutatedArtefact();
            for (ForeignKeyConstraint fkey : schema.getForeignKeyConstraints()) {
                boolean fUnique = isUnique(schema, fkey.getReferenceTable(), fkey.getReferenceColumns());
                boolean fPrimary = isPrimary(schema, fkey.getReferenceTable(), fkey.getReferenceColumns());
                boolean compatibleTypes = areCompatible(fkey.getColumns(), fkey.getReferenceColumns());

                if ((!fUnique && !fPrimary) || !compatibleTypes) {
                    DataCapturer.capture("removedmutants", "quasimutant", schema + "-" + mutant.getSimpleDescription());
                    it.remove();
                    break;
                }
            }
        }
        return mutants;
    }

    protected boolean isUnique(Schema schema, Table table, List<Column> columns) {
        return schema.isUnique(table, columns.toArray(new Column[columns.size()]));
    }

    protected boolean isPrimary(Schema schema, Table table, List<Column> columns) {
        PrimaryKeyConstraint pkey = schema.getPrimaryKeyConstraint(table);
        if (pkey != null) {
            List<Column> pkeyColumns = pkey.getColumns();
            if (columns.size() == pkeyColumns.size()) {
                return pkeyColumns.containsAll(columns);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected boolean areCompatible(List<Column> columns, List<Column> referenceColumns) {

        Iterator<Column> columnsIterator = columns.iterator();
        Iterator<Column> referenceColumnsIterator = referenceColumns.iterator();

        while (columnsIterator.hasNext()) {
            Column column = columnsIterator.next();
            Column referenceColumn = referenceColumnsIterator.next();

            if (!compatibleTypes(column.getDataType(), referenceColumn.getDataType())) {
                return false;
            }
        }

        return true;
    }

    protected boolean compatibleTypes(DataType dataType1, DataType dataType2) {
        return compatibleTypes.get(dataType1.getClass()).contains(dataType2.getClass());
    }

    protected abstract void initializeCompatibleTypes();
}

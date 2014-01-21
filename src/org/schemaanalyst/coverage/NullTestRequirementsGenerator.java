package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.predicate.Clause;
import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.function.NullFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 21/01/2014.
 */
public class NullTestRequirementsGenerator {

    private Schema schema;
    private Table table;

    public NullTestRequirementsGenerator(Schema schema, Table table) {
        this.schema = schema;
        this.table = table;
    }

    public LinkedHashSet<Predicate> generateRequirements() {
        DefaultPredicateGenerator predicateGenerator = new DefaultPredicateGenerator();
        LinkedHashSet<Predicate> predicates = new LinkedHashSet<>();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {

            Predicate predicate = predicateGenerator.generatePredicate(schema, table);
            predicate.addClause(new NullFunction(table, column));
            predicates.add(predicate);
        }

        return predicates;
    }
}

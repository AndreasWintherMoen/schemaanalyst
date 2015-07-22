package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.mutation.operator.*;
import org.schemaanalyst.mutation.quasimutant.HyperSQLRemover;
import org.schemaanalyst.mutation.quasimutant.PostgresRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyUniqueOverlapConstraintRemover;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.quasimutant.SQLiteClassifier;
import org.schemaanalyst.mutation.redundancy.EquivalentMutantClassifier;
import org.schemaanalyst.mutation.redundancy.RedundantMutantClassifier;

/**
 *
 * @author Chris J. Wright
 */
public class AllOperatorsWithClassifiersPipeline extends MutationPipeline<Schema> {

    private static final Logger LOGGER = Logger.getLogger(AllOperatorsWithClassifiersPipeline.class.getName());

    public AllOperatorsWithClassifiersPipeline(Schema schema) {
        addProducer(new CCNullifier(schema));
        addProducer(new CCInExpressionRHSListExpressionElementR(schema));
        addProducer(new CCRelationalExpressionOperatorE(schema));
        addProducer(new FKCColumnPairA(schema));
        addProducer(new FKCColumnPairR(schema));
        addProducer(new FKCColumnPairE(schema));
        // Implement FKCColumnE ('one sided')?
        addProducer(new PKCColumnA(schema));
        addProducer(new PKCColumnR(schema));
        addProducer(new PKCColumnE(schema));
        addProducer(new NNCA(schema));
        addProducer(new NNCR(schema));
        addProducer(new UCColumnA(schema));
        addProducer(new UCColumnR(schema));
        addProducer(new UCColumnE(schema));

        addRemover(new EquivalentMutantClassifier<>(new SchemaEquivalenceChecker(), schema));
        addRemover(new RedundantMutantClassifier<>(new SchemaEquivalenceChecker()));
    }

    public void addDBMSSpecificRemovers(String dbms) {
        switch (dbms) {
            case "Postgres":
                addRemoverToFront(new PrimaryKeyUniqueOverlapConstraintRemover());
                addRemoverToFront(new PostgresRemover());
                addRemoverToFront(new PrimaryKeyColumnNotNullRemover());
                break;
            case "SQLite":
                addRemoverToFront(new PrimaryKeyUniqueOverlapConstraintRemover());
                addRemoverToFront(new SQLiteClassifier());
                break;
            case "HyperSQL":
                addRemoverToFront(new HyperSQLRemover());
                addRemoverToFront(new PrimaryKeyColumnNotNullRemover());
                break;
            default:
                LOGGER.log(Level.WARNING, "Unknown DBMS name in pipeline");
        }
    }

}

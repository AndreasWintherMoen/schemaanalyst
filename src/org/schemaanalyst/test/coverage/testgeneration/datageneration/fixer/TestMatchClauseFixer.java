package org.schemaanalyst.test.coverage.testgeneration.datageneration.fixer;

import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.testgeneration.datageneration.checker.MatchClauseChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.fixer.MatchClauseFixer;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.test.testutil.mock.MockCell;
import org.schemaanalyst.test.testutil.mock.MockCellValueGenerator;
import org.schemaanalyst.test.testutil.mock.MockRandom;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.tuple.MixedPair;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 01/03/2014.
 */
public class TestMatchClauseFixer {

    class MockMatchClauseChecker extends MatchClauseChecker {

        List<MixedPair<Row, List<Row>>> nonMatchingCells;
        List<Cell> matchingCells;

        MockMatchClauseChecker(MatchClause matchClause,
                               List<MixedPair<Row, List<Row>>> nonMatchingCells,
                               List<Cell> matchingCells) {
            super(matchClause, false, null);
            this.nonMatchingCells = nonMatchingCells;
            this.matchingCells = matchingCells;
        }

        public List<MixedPair<Row, List<Row>>> getNonMatchingCells() {
            return nonMatchingCells;
        }

        public List<Cell> getMatchingCells() {
            return matchingCells;
        }
    }

    class MockMatchClause extends MatchClause {

        MockMatchClause(MatchClause.Mode mode) {
            super(new Table("dummytable"),
                    MatchClause.EMPTY_COLUMN_LIST,
                    MatchClause.EMPTY_COLUMN_LIST,
                    mode,
                    false);
        }
    }

    @Test
    public void testMatchingMakeNonMatching() {
        // 1 match
        testMatchingMakeNonMatching(
                MatchClause.Mode.AND,
                Arrays.asList(1),  // initial value
                Arrays.asList(2),  // check changed to this
                Arrays.asList(2),  // (values to be generated by cell value generator)
                Arrays.asList(0)); // (values to be generated by random value generator)

        testMatchingMakeNonMatching(
                MatchClause.Mode.OR,
                Arrays.asList(1),  // initial value
                Arrays.asList(2),  // check changed to this
                Arrays.asList(2),  // (values to be generated by cell value generator)
                Arrays.asList(0)); // (values to be generated by random value generator)

        // two rows to choose from to make the match
        testMatchingMakeNonMatching(
                MatchClause.Mode.AND,
                Arrays.asList(10, 20), // initial values
                Arrays.asList(30, 40), // check changed to these
                Arrays.asList(30, 40), // (values to be generated by cell value generator)
                Arrays.asList(0));     // (values to be generated by random value generator)
        testMatchingMakeNonMatching(
                MatchClause.Mode.OR,
                Arrays.asList(10, 20), // initial values
                Arrays.asList(10, 40), // check changed to these (only one change required as OR)
                Arrays.asList(40),     // (values to be generated by cell value generator)
                Arrays.asList(1));     // (values to be generated by random value generator)
    }

    public void testMatchingMakeNonMatching(MatchClause.Mode mode,
                                            List<Integer> initialValues,
                                            List<Integer> changedValues,
                                            List<Integer> cellGeneratorValues,
                                            List<Integer> randomValues) {

        List<Cell> cells = new ArrayList<>();
        for (int initialValue : initialValues) {
            cells.add(new MockCell(new NumericValue(initialValue)));
        }

        CellValueGenerator cellValueGenerator = new MockCellValueGenerator(cellGeneratorValues);
        Random random = new MockRandom(randomValues);
        MatchClause matchClause = new MockMatchClause(mode);

        MatchClauseFixer fixer = new MatchClauseFixer(
                new MockMatchClauseChecker(
                        matchClause,
                        new ArrayList<MixedPair<Row, List<Row>>>(),
                        cells),
                random,
                cellValueGenerator);

        fixer.attemptFix();

        for (int i=0; i < cells.size(); i++) {
            assertEquals((int) changedValues.get(i), ((NumericValue) cells.get(i).getValue()).get().intValue());
        }
    }

    @Test
    public void testNonMatchingMakeMatching() {
        // 1 match
        testNonMatchingMakeMatching(
                MatchClause.Mode.AND,
                Arrays.asList(1), Arrays.asList(2), // initial value and ref value
                Arrays.asList(2),                   // test for no change ??
                Arrays.asList(0));
        testNonMatchingMakeMatching(
                MatchClause.Mode.OR,
                Arrays.asList(1), Arrays.asList(2),
                Arrays.asList(2),
                Arrays.asList(0, 0));

        // multi-match
        testNonMatchingMakeMatching(
                MatchClause.Mode.AND,
                Arrays.asList(100, 200), Arrays.asList(20, 40),
                Arrays.asList(20, 40),
                Arrays.asList(0));

        testNonMatchingMakeMatching(
                MatchClause.Mode.OR,
                Arrays.asList(100, 200), Arrays.asList(20, 40),
                Arrays.asList(100, 40),
                Arrays.asList(0, 1));
    }

    public void testNonMatchingMakeMatching(MatchClause.Mode mode,
                                            List<Integer> initialValues,
                                            List<Integer> initialRefValues,
                                            List<Integer> changedValues,
                                            List<Integer> randomValues) {

        List<MixedPair<Row, List<Row>>> nonMatchingCells = new ArrayList<>();

        List<Cell> initialCells = new ArrayList<>();
        for (Integer integer : initialValues) {
            initialCells.add(new MockCell(new NumericValue(integer)));
        }

        List<Cell> initialRefCells = new ArrayList<>();
        for (Integer integer : initialRefValues) {
            initialRefCells.add(new MockCell(new NumericValue(integer)));
        }

        nonMatchingCells.add(
                new MixedPair<Row, List<Row>>(
                    new Row(initialCells),
                    new ArrayList<>(Arrays.asList(new Row(initialRefCells)))));

        Random random = new MockRandom(randomValues);
        MatchClause matchClause = new MockMatchClause(mode);

        MatchClauseFixer fixer = new MatchClauseFixer(
                new MockMatchClauseChecker(
                        matchClause,
                        nonMatchingCells,
                        new ArrayList<Cell>()),
                random,
                null);

        fixer.attemptFix();

        for (int i=0; i < initialValues.size(); i++) {
            Cell firstCell = nonMatchingCells.get(0).getFirst().getCells().get(i);
            int actualValue = ((NumericValue) firstCell.getValue()).get().intValue();
            int expectedValue = changedValues.get(i);
            assertEquals("For pair no. " + i + " value should be " + expectedValue + " was " + actualValue, expectedValue, actualValue);
        }
    }
}

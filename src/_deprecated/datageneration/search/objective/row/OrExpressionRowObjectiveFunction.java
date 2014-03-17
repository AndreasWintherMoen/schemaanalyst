package _deprecated.datageneration.search.objective.row;

import _deprecated.datageneration.search.objective.BestOfMultiObjectiveValue;
import _deprecated.datageneration.search.objective.MultiObjectiveValue;
import _deprecated.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;

public class OrExpressionRowObjectiveFunction extends
        ComposedExpressionRowObjectiveFunction {

    public OrExpressionRowObjectiveFunction(OrExpression expression,
            boolean goalIsToSatisfy, boolean allowNull) {
        super(expression, goalIsToSatisfy, allowNull);
    }

    @Override
    protected MultiObjectiveValue instantiateMultiObjectiveValue() {
        return goalIsToSatisfy 
                ? new BestOfMultiObjectiveValue()
                : new SumOfMultiObjectiveValue();
    }
}

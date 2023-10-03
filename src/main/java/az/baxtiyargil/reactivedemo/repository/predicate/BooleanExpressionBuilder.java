package az.baxtiyargil.reactivedemo.repository.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.function.Function;

public class BooleanExpressionBuilder {

    private final BooleanExpression booleanExpression;

    public BooleanExpressionBuilder(BooleanExpression booleanExpression) {
        this.booleanExpression = booleanExpression;
    }

    public <T> BooleanExpressionBuilder notNullAnd(Function<T, BooleanExpression> expressionFunction, T value) {
        if (value != null) {
            return new BooleanExpressionBuilder(booleanExpression.and(expressionFunction.apply(value)));
        }
        return this;
    }

    public BooleanExpressionBuilder notEmptyAnd(Function<String, BooleanExpression> expressionFunction,
                                                String value) {
        if (StringUtils.hasText(value)) {
            return new BooleanExpressionBuilder(booleanExpression.and(expressionFunction.apply(value)));
        }
        return this;
    }

    public <T> BooleanExpressionBuilder notEmptyAnd(Function<Collection<T>, BooleanExpression> expressionFunction,
                                                    Collection<T> collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return new BooleanExpressionBuilder(booleanExpression.and(expressionFunction.apply(collection)));
        }
        return this;
    }

    public BooleanExpression build() {
        return booleanExpression;
    }

}

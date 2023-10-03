package az.baxtiyargil.reactivedemo.repository.predicate.filter;

import az.baxtiyargil.reactivedemo.model.entity.QBerry;
import az.baxtiyargil.reactivedemo.repository.predicate.BooleanExpressionBuilder;
import com.querydsl.core.types.Predicate;

public class BerryPredicateBuilder {
    private static final QBerry berry = QBerry.berry;

    public static Predicate build(BerryFilter filter) {
        return new BooleanExpressionBuilder(berry.isNotNull())
                .notNullAnd(berry.id::eq, filter.getId())
                .notEmptyAnd(berry.name::like, filter.getName())
                .notNullAnd(berry.growthTime::goe, filter.getGrowthTimeFrom())
                .notNullAnd(berry.growthTime::loe, filter.getGrowthTimeTo())
                .notNullAnd(berry.moveId::eq, filter.getMoveId())
                .build();
    }

}

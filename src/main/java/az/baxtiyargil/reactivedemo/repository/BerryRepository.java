package az.baxtiyargil.reactivedemo.repository;

import az.baxtiyargil.reactivedemo.model.entity.Berry;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BerryRepository extends CrudRepository<Berry, Integer>, QuerydslPredicateExecutor<Berry> {
}

package az.baxtiyargil.reactivedemo.model.request;

import az.baxtiyargil.reactivedemo.repository.predicate.filter.BerryFilter;
import az.baxtiyargil.reactivedemo.repository.predicate.filter.MoveFilter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BerrySearchDto {

    BerryFilter berryFilter;
    MoveFilter moveFilter;

}

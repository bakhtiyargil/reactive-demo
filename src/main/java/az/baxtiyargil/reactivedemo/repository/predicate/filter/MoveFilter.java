package az.baxtiyargil.reactivedemo.repository.predicate.filter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MoveFilter {

    Long id;
    Long accuracy;
    String name;

}

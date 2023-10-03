package az.baxtiyargil.reactivedemo.repository.predicate.filter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BerryFilter implements Serializable {

    Long id;
    String name;
    Integer size;
    Integer growthTimeFrom;
    Integer growthTimeTo;
    Long moveId;

}

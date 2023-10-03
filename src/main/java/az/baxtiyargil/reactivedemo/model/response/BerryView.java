package az.baxtiyargil.reactivedemo.model.response;

import az.baxtiyargil.reactivedemo.client.move.MoveResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BerryView {

    Long id;
    String name;
    Integer size;
    Integer growthTime;
    Integer soilDryness;
    MoveResponse moveResponse;

}

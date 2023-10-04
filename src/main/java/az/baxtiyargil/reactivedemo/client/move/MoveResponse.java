package az.baxtiyargil.reactivedemo.client.move;

import az.baxtiyargil.reactivedemo.model.reactive.ReactiveResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MoveResponse extends ReactiveResponse {

    Long accuracy;
    String name;

}

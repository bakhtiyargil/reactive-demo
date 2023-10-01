package az.baxtiyargill.reactivedemo.client.move;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MoveResponse {

    Long id;
    Long accuracy;
    String name;

}

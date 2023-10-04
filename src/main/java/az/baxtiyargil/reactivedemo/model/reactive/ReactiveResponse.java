package az.baxtiyargil.reactivedemo.model.reactive;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactiveResponse {

    Long id;

}

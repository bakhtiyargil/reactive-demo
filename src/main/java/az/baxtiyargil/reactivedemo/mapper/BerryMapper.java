package az.baxtiyargil.reactivedemo.mapper;

import az.baxtiyargil.reactivedemo.model.entity.Berry;
import az.baxtiyargil.reactivedemo.model.response.BerryView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BerryMapper {

    BerryView mapToBerryView(Berry berry);

}

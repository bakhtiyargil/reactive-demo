package az.baxtiyargil.reactivedemo.mapper;

import az.baxtiyargil.reactivedemo.model.entity.Berry;
import az.baxtiyargil.reactivedemo.model.response.BerryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BerryMapper {


    @Mapping(target = "id", source = "id")
    BerryView mapToBerryView(Berry berry);

}

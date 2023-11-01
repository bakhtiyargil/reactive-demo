package az.baxtiyargil.reactivedemo.mapper;

import az.baxtiyargil.reactivedemo.model.entity.Berry;
import az.baxtiyargil.reactivedemo.model.response.BerryView;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BerryMapper {


    BerryView mapToBerryView(Berry berry);

}

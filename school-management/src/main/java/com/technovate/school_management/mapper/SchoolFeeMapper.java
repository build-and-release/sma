package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.SchoolFeeListDto;
import com.technovate.school_management.entity.SchoolFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {FeeMapper.class})
public interface SchoolFeeMapper {
    @Mapping(source = "schoolFee.term.name", target = "term")
    @Mapping(source = "schoolFee.fee.description", target = "description")
    SchoolFeeListDto toSchoolFeeListDto(SchoolFee schoolFee);
}

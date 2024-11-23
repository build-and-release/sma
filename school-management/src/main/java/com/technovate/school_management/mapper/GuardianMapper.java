package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.CreateGuardianDto;
import com.technovate.school_management.dto.GuardianDto;
import com.technovate.school_management.entity.Guardian;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GuardianMapper {
    Guardian toGuardian(CreateGuardianDto createGuardianDto);
    GuardianDto toGuardianDto(Guardian guardian);
    List<GuardianDto> toGuardianDtos(List<Guardian> guardians);
    default Page<GuardianDto> toGuardianDto(Page<Guardian> guardians) {
        return guardians.map(this::toGuardianDto);
    }
}

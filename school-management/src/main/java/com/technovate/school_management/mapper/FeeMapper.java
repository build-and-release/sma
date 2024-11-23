package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.CreateFeeDto;
import com.technovate.school_management.dto.FeeDto;
import com.technovate.school_management.dto.FeeListDto;
import com.technovate.school_management.entity.Fee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = FeeItemMapper.class)
public interface FeeMapper {
    Fee toFee(CreateFeeDto createFeeDto);
    @Mapping(target = "feeItems", source = "feeItems")
    FeeDto toFeeDto(Fee fee);
    FeeListDto toFeeListDto(Fee fee);
    default Page<FeeListDto> toFeeListDto(Page<Fee> fees) {
        if (fees == null) {
            return null;
        }
        return fees.map(this::toFeeListDto);
    }
}

package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.CreateFeeItemDto;
import com.technovate.school_management.dto.FeeItemListDto;
import com.technovate.school_management.entity.FeeItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeeItemMapper {
    FeeItem toFeeItem(CreateFeeItemDto createFeeItemDto);
    @Mapping(source = "feeItem.fee.id", target = "feeId")
    FeeItemListDto toFeeItemDto(FeeItem feeItem);
}

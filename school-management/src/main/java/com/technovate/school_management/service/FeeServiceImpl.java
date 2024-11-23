package com.technovate.school_management.service;

import com.technovate.school_management.dto.CreateFeeDto;
import com.technovate.school_management.dto.CreateFeeItemDto;
import com.technovate.school_management.dto.FeeDto;
import com.technovate.school_management.dto.FeeListDto;
import com.technovate.school_management.entity.Fee;
import com.technovate.school_management.entity.FeeItem;
import com.technovate.school_management.exception.BadRequestException;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.FeeItemMapper;
import com.technovate.school_management.mapper.FeeMapper;
import com.technovate.school_management.repository.FeeItemRepository;
import com.technovate.school_management.repository.FeeRepository;
import com.technovate.school_management.service.contracts.FeeService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {
    private final FeeRepository feeRepository;
    private final FeeItemRepository feeItemRepository;
    private final FeeItemMapper feeItemMapper;
    private final FeeMapper feeMapper;

    @Override
    public void createFeeWithFeeItems(CreateFeeDto createFeeDto) {
//        Validate dto
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        var violations = validator.validate(createFeeDto);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new BadRequestException(stringBuilder.toString());
        }

        List<CreateFeeItemDto> createFeeItemDtos = createFeeDto.getFeeItems();
        if (createFeeItemDtos.isEmpty()) {
            throw new BadRequestException("Fee Items cannot be empty");
        }

//        Get total amount
        double total = 0;
//        Validate fee items
        for (CreateFeeItemDto createFeeItemDto : createFeeItemDtos) {
            var feeItemViolations = validator.validate(createFeeItemDto);

            if (!feeItemViolations.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Fee Item with name ").append(createFeeItemDto.getDescription() == null ? " " : createFeeItemDto.getDescription()).append(" throw the following validation errors;");
                feeItemViolations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
                throw new BadRequestException(stringBuilder.toString());
            }
            total += createFeeItemDto.getAmount();
        }

//        create fee
        Fee fee = feeMapper.toFee(createFeeDto);
       fee.setTotal(total);
        feeRepository.save(fee);
        List<FeeItem> feeItems = new ArrayList<>();
        for (CreateFeeItemDto createFeeItemDto : createFeeItemDtos) {
            FeeItem feeItem = feeItemMapper.toFeeItem(createFeeItemDto);
            feeItem.setFee(fee);
            feeItemRepository.save(feeItem);
        }
    }

    @Override
    public FeeDto findFeeById(Long id) {
        Optional<Fee> feeOpt = feeRepository.findById(id);
        if (feeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Fee with the id " + id + " not found");
        }
        Fee fee = feeOpt.get();
        return feeMapper.toFeeDto(feeOpt.get());
    }

    @Override
    public Page<FeeListDto> findAll(int page, int size) {
        Page<Fee> fees = feeRepository.findAll(PageRequest.of(page, size));
        return feeMapper.toFeeListDto(fees);
    }
}

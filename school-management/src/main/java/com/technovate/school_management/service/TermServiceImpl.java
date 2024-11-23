package com.technovate.school_management.service;

import com.technovate.school_management.dto.CreateTermDto;
import com.technovate.school_management.dto.TermDto;
import com.technovate.school_management.entity.Term;
import com.technovate.school_management.mapper.TermMapper;
import com.technovate.school_management.repository.TermRepository;
import com.technovate.school_management.service.contracts.TermService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService  {
    private final TermRepository termRepository;
    private final TermMapper termMapper;

    @Override
    public void createNewTerm(CreateTermDto createTermDto) {
//        valid payload
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        var violations = validator.validate(createTermDto);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
//        find current active term and disable it
        Optional<Term> activeTermOpt = termRepository.findByIsCurrentTrue();
        if (activeTermOpt.isPresent()) {
            Term activeTerm = activeTermOpt.get();
            activeTerm.setCurrent(false);
            termRepository.save(activeTerm);
        }

        Term newTerm = termMapper.toTerm(createTermDto);
        newTerm.setCurrent(true);
        termRepository.save(newTerm);
    }

    @Override
    public Page<TermDto> findTerms(int page, int size) {
        Page<Term> terms = termRepository.findAll(PageRequest.of(page, size));
        return termMapper.toTermDtos(terms);
    }
}

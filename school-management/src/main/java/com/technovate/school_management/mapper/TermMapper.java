package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.CreateTermDto;
import com.technovate.school_management.dto.TermDto;
import com.technovate.school_management.entity.Term;
import com.technovate.school_management.entity.enums.SchoolTerm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TermMapper {
    @Mapping(source = "name", target = "name")
    Term toTerm(CreateTermDto createTermDto);

    @Mapping(source = "name", target = "name")
    TermDto toTermDto(Term term);

   default String schoolTermToString(SchoolTerm schoolTerm) {
       if (schoolTerm == null) {
           return null;
       }
       String lower = schoolTerm.name().toLowerCase();
       return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
   }

   default SchoolTerm stringToSchoolTerm(String term) {
       if (term == null) {
           return null;
       }
       return SchoolTerm.valueOf(term.toUpperCase());
   }

   default Page<TermDto> toTermDtos(Page<Term> terms) {
       if (terms == null) {
           return null;
       }
       return terms.map(this::toTermDto);
   }
}

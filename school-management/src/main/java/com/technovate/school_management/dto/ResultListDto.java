package com.technovate.school_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultListDto {
 public Long id;
 private String studentIdNumber;
 private String schoolClass;
 private String term;
}

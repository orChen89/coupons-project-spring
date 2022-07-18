package com.or.couponsproject.couponsproject.dto;

import com.or.couponsproject.couponsproject.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListDtoWrapper {

    private List<CompanyDto> companyDtoList;
}

package com.or.couponsproject.couponsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListDtoWrapper {

    //Returning a list of companies as an object
    private List<CompanyDto> companyDtoList;

}
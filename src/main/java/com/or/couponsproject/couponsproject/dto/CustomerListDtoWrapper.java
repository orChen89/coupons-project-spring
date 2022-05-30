package com.or.couponsproject.couponsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListDtoWrapper {

    private Set<CustomerDto> customerDtoList;
}

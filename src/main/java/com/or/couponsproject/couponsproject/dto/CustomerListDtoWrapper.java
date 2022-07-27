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
public class CustomerListDtoWrapper {

    //Returning a list of customers as an object
    private List<CustomerDto> customerDtoList;
}

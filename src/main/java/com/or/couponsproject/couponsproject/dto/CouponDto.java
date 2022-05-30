package com.or.couponsproject.couponsproject.dto;

import com.or.couponsproject.couponsproject.enums.CouponCategory;
import lombok.*;


import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponDto {

    private Long id;
    private Long companyID;
    private CouponCategory category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer amount;
    private Double price;
    private String image;
}

package com.or.couponsproject.couponsproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@ToString
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

    public CouponDto(Long id, Long companyID, CouponCategory category, String title, String description,
                     LocalDate startDate, LocalDate endDate, Integer amount, Double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate =  startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public CouponDto(Long companyID, CouponCategory category, String title, String description, LocalDate startDate,
                  LocalDate endDate, Integer amount, Double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
}

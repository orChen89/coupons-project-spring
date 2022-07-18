package com.or.couponsproject.couponsproject;

import com.or.couponsproject.couponsproject.dto.AdminDto;
import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.model.Admin;
import com.or.couponsproject.couponsproject.service.AdminService;
import com.or.couponsproject.couponsproject.service.CompanyService;
import com.or.couponsproject.couponsproject.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class CouponsProjectApplication {

	public static void main(String[] args) throws ApplicationException {

		ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);

		AdminService admin = ctx.getBean(AdminService.class);

		CompanyService companyService = ctx.getBean(CompanyService.class);

		CustomerService customerService = ctx.getBean(CustomerService.class);
/*
		admin.createAdmin(AdminDto.builder().email("admin@admin.com").password("admin").build());

		admin.createCompany(CompanyDto.builder().
				name("Shualim").
				email("com5@gmail.com").
				password("Com5").
				build());

		admin.createCustomer(CustomerDto.builder().
				firstName("Kadosh").
				lastName("Rezach").
				email("user2@gmail.com").
				password("User2").
				build());

		companyService.createCoupon(CouponDto.builder().
				companyId(1L).
				category(CouponCategory.ELECTRICITY).
				title("Oled LG TV").
				description("Metoraf").
				startDate(LocalDate.of(2022, 4, 4)).
				endDate(LocalDate.of(2022, 9, 29)).
				amount(22).
				price(4500.0).
				image("jdbc:mysql://localhost:3306/coupons_project/6").
				build());

		customerService.addCouponPurchase(1L, 1L);
	}

 */
	}
}
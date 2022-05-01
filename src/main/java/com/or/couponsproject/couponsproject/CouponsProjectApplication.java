package com.or.couponsproject.couponsproject;

import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.AdminService;
import com.or.couponsproject.couponsproject.service.CompanyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponsProjectApplication {

	public static void main(String[] args) throws ApplicationException {

		ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);
			}
}

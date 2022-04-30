package com.or.couponsproject.couponsproject.service;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.enums.EntityType;
import com.or.couponsproject.couponsproject.errors.Constraint;
import com.or.couponsproject.couponsproject.errors.exceptions.*;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.repo.CompanyRepository;
import com.or.couponsproject.couponsproject.repo.CouponRepository;
import com.or.couponsproject.couponsproject.util.CouponUtil;
import com.or.couponsproject.couponsproject.util.InputUserValidation;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import com.or.couponsproject.couponsproject.util.OptionalToEntityConvertorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

//-------------------------------------Creating a new coupon--------------------------------------------------

    public Coupon createCoupon(final CouponDto couponDto) throws ApplicationException {

        //Checking if the specific coupon is already exist to current company
        for (Coupon c : couponRepository.findAll()) {
            if (c.getTitle().equals(couponDto.getTitle()) &&
                    Objects.equals(c.getCompany().getId(), couponDto.getCompanyID())) {
                throw new EntityExistException(EntityType.COUPON, Constraint.ENTITY_ALREADY_EXISTS);
            }
        }

        //Checking if the date format is valid according to Date REGEX
        if (!InputUserValidation.isDateValid((couponDto.getStartDate()))) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if the date format is valid according to Date REGEX
        if (!InputUserValidation.isDateValid((couponDto.getEndDate()))) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if the end date entered is valid according to today's Date
        if (CouponUtil.isCouponExpired(couponDto.getEndDate())) {
            throw new CouponExpirationDateArrived(ObjectMappingUtil.couponDtoToCoupon(couponDto));
        }

        //Checking if the end date entered is before start Date
        if (couponDto.getEndDate().isBefore(couponDto.getStartDate())) {
            throw new EndDateBeforeStartDateException(Constraint.START_DATE_AT_OR_BEFORE_END_DATE);
        }

        //Converting couponDto to spring coupon entity
        Coupon coupon = ObjectMappingUtil.couponDtoToCoupon(couponDto);

        //Creating the coupon entity on our database
        Coupon newCoupon = couponRepository.save(coupon);
        log.info("The new coupon: " + newCoupon.getTitle() + " has been created successfully!");

        //Setting a company object to a variable
        Company company = OptionalToEntityConvertorUtil.
                optionalCompany(companyRepository.
                        findById(newCoupon.
                                getCompany().
                                getId()));

        List<Coupon> companyCoupons = new ArrayList<>();
        //Adding and setting the company coupons
        companyCoupons.add(newCoupon);
        company.setCoupons(companyCoupons);

        return newCoupon;
    }

    //-------------------------------------Updating an existing coupon----------------------------------------------

    public void updateCoupon(final CouponDto couponDto) throws ApplicationException {

        //Checking if user didn't enter id - Incorrect Update format
        if (couponDto.getId() == null) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if the specific coupon is not exist
        if (couponDto == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        //Checking if the date format is valid according to Date REGEX
        if (!InputUserValidation.isDateValid((couponDto.getStartDate()))) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if the date format is valid according to Date REGEX
        if (!InputUserValidation.isDateValid((couponDto.getEndDate()))) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if the end date entered is valid according to today's Date
        if (CouponUtil.isCouponExpired(couponDto.getEndDate())) {
            throw new InvalidCouponDate(Constraint.INVALID_DATE);
        }

        //Checking if the end date entered is before start Date
        if (couponDto.getEndDate().isBefore(couponDto.getStartDate())) {
            throw new EndDateBeforeStartDateException(Constraint.START_DATE_AT_OR_BEFORE_END_DATE);
        }

        //Converting the couponDto object to spring coupon entity
        Coupon coupon = ObjectMappingUtil.couponDtoToCouponUpdate(couponDto);

        //Updating the coupon
        couponRepository.save(coupon);
        log.info("The selected coupon: " + coupon.getTitle() + " has been updated and created successfully!");
    }

    //-------------------------------------Deleting an existing coupon----------------------------------------------

    public void deleteCoupon(final Long couponId) throws ApplicationException {

        //Setting a coupon according to the coupon id
        Coupon coupon = OptionalToEntityConvertorUtil.optionalCoupon(couponRepository.findById(couponId));

        //Checking if the specific coupon is not exist
        if (coupon == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        //Converting a coupon to a couponDto entity
        CouponDto couponDto = ObjectMappingUtil.couponToCouponDto(coupon);

        log.info("The selected coupon: " + couponDto.getTitle() + "," + " has been deleted!");

        //Deleting the coupon from our database by its id
        couponRepository.deleteById(couponId);
    }

    //-------------------------------------Getting an existing coupon-----------------------------------------------

    public CouponDto getCoupon(final Long couponId) throws ApplicationException {

        //Setting a coupon according to the coupon id
        Coupon coupon = OptionalToEntityConvertorUtil.optionalCoupon(couponRepository.findById(couponId));

        //Checking if the coupon is not exists
        if (coupon == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        //Converting a coupon to a couponDto entity
        CouponDto couponDto = ObjectMappingUtil.couponToCouponDto(coupon);

        //Getting the specific coupon
        return couponDto;
    }

    //-------------------------------------Getting all coupons------------------------------------------------------

    public List<CouponDto> getAllCoupons(final Long companyId) throws ApplicationException {

        //Setting a company according to the company id
        Company company = OptionalToEntityConvertorUtil.optionalCompany(companyRepository.findById(companyId));

        //Checking if the specific company is not exists
        if (company == null) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the specific company its coupons by id
        List<Coupon> companyCoupons = couponRepository.getCouponsByCompanyId(companyId);

        //Converting the coupons to a Dto coupon list
        List<CouponDto> companyDtoCoupons = ObjectMappingUtil.couponsToCouponsDto(companyCoupons);

        return companyDtoCoupons;
    }

    //-------------------------------------Getting all coupons by specific category---------------------------------

    public List<CouponDto> getCouponsByCategory(final Long companyId, final CouponCategory category) throws ApplicationException {

        List<CouponDto> couponsOfCompanyByCategory = new ArrayList<>();

        //Setting a specific company
        Company company = OptionalToEntityConvertorUtil.optionalCompany(companyRepository.findById(companyId));

        //Checking if company is not exists
        if (company == null) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the coupons of specific company
        List<CouponDto> coupons = getAllCoupons(companyId);

        //Checking if the coupons are not exists
        if (coupons == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        for (CouponDto coupon : coupons) {
            //Checking for each coupon if it is from same category
            if (coupon.getCategory().equals(category)) {
                //Adding the specific coupon to a list
                couponsOfCompanyByCategory.add(coupon);
                break;
            }
            log.info("The company: " + company.getName() + " doesn't holds coupons from this category!");
        }
        return couponsOfCompanyByCategory;
    }

    //-------------------------------------Getting all coupons according to price----------------------------------

    public List<CouponDto> getCouponsByMaxPrice(final Long companyId, final double maxPrice) throws ApplicationException {

        List<CouponDto> couponsByMax = new ArrayList<>();

        //Setting a specific company
        Company company = OptionalToEntityConvertorUtil.optionalCompany(companyRepository.findById(companyId));

        //Checking if company is not exists
        if (company == null) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the coupons of specific company
        List<CouponDto> coupons = getAllCoupons(companyId);

        //Checking if the coupons are not exists
        if (coupons == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        for (CouponDto coupon : coupons) {
            if (coupon.getPrice() <= maxPrice) {
                //Adding the specific coupons according the selected price limit
                couponsByMax.add(coupon);
            } else {
                log.info("There is no any coupons less or equal the inserted price!");
            }
        }
        return couponsByMax;
    }

    //-------------------------------------Get a specific company-------------------------------------------------

    public CompanyDto getCompany(final Long companyId) throws ApplicationException {

        //Setting a specific company
        Company company = OptionalToEntityConvertorUtil.optionalCompany(companyRepository.findById(companyId));

        //Checking if the company is not exists
        if (company == null) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the company coupons list from our database
        company.setCoupons(couponRepository.getCouponsByCompanyId(companyId));

        List<CouponDto> dtoCoupons;

        dtoCoupons = ObjectMappingUtil.couponsToCouponsDto(company.getCoupons());

        //Converting company to companyDto entity
        CompanyDto companyDto = ObjectMappingUtil.companyToCompanyDto(company);
        //Setting the converted coupons company coupons list
        companyDto.setCoupons(dtoCoupons);

        return companyDto;
    }
}



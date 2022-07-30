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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

//-------------------------------------Creating a new coupon--------------------------------------------------

    public Coupon createCoupon(final CouponDto couponDto) throws ApplicationException {

        //Checking if the date format is valid according to Date REGEX
        if (!InputUserValidation.isDateValid((couponDto.getStartDate()))
            || !InputUserValidation.isDateValid((couponDto.getEndDate()))) {
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

        //Checking if the specific coupon is already exist to current company
        if (couponRepository.existsByTitle(couponDto.getTitle())) {
            List<Coupon> couponList = couponRepository.findByTitle(couponDto.getTitle());
            for (Coupon c : couponList) {
                if (c.getTitle().equals(couponDto.getTitle()) &&
                        Objects.equals(c.getCompany().getId(), couponDto.getCompanyId())) {
                    throw new EntityExistException(EntityType.COUPON, Constraint.ENTITY_ALREADY_EXISTS);
                }
            }
        }

        //Converting couponDto to spring coupon entity
        //Creating the coupon entity on our database
        Coupon coupon = couponRepository.save(ObjectMappingUtil.couponDtoToCoupon(couponDto));

        log.info("The new coupon: " + coupon.getTitle() + " has been created successfully!");

        return coupon;
    }

    //-------------------------------------Updating an existing coupon----------------------------------------------

    public void updateCoupon(final CouponDto couponDto) throws ApplicationException {

        Coupon coupon = OptionalToEntityConvertorUtil.optionalCoupon(couponRepository.findById(couponDto.getId()));

        //Checking if user didn't enter id - Incorrect Update format
        if (couponDto.getId() == null) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if the specific coupon is not exist
        if (couponDto == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        CouponDto newCoupon = ObjectMappingUtil.mappingCouponWhenUpdate(couponDto, coupon);

        //Checking if the date format is valid according to Date REGEX
        if (!InputUserValidation.isDateValid((couponDto.getStartDate()))
            || !InputUserValidation.isDateValid((couponDto.getEndDate()))) {
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
        //Updating the coupon
        couponRepository.save(ObjectMappingUtil.couponDtoToCouponUpdate(newCoupon));
        log.info("The selected coupon: " + newCoupon.getTitle() + " has been updated and created successfully!");
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

        //Checking if the coupon is not exists
        if (!couponRepository.existsById(couponId)) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting a coupon according to the coupon id
        Coupon coupon = OptionalToEntityConvertorUtil.optionalCoupon(couponRepository.findById(couponId));

        //Converting a coupon to a couponDto entity
        //Getting the specific coupon
        return ObjectMappingUtil.couponToCouponDto(coupon);
    }

    //-------------------------------------Getting all coupons------------------------------------------------------

    public List<CouponDto> getAllCoupons(final Long companyId) throws ApplicationException {

        //Checking if the specific company is not exists
        if (!companyRepository.existsById(companyId)) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the specific company its coupons by id
        //Converting the coupons to a Dto coupon list
        return ObjectMappingUtil.couponsToCouponsDto(couponRepository.getCouponsByCompanyId(companyId));
    }

    //-------------------------------------Getting all coupons by specific category---------------------------------

    public List<CouponDto> getCouponsByCategory(final Long companyId, final CouponCategory category) throws ApplicationException {

        //Checking if the specific company is not exists
        if (!companyRepository.existsById(companyId)) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Returning a list of coupons according to specific company and category
        return ObjectMappingUtil.couponsToCouponsDto(couponRepository.findByCategoryAndCompanyId(category, companyId));

    }

    //-------------------------------------Getting all coupons according to price----------------------------------

    public List<CouponDto> getCouponsByMaxPrice(final Long companyId, final double maxPrice) throws ApplicationException {

        //Checking if the specific company is not exists
        if (!companyRepository.existsById(companyId)) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the coupons of specific company
        List<CouponDto> couponsByMax = getAllCoupons(companyId);

        //Checking if the coupons are not exists
        if (couponsByMax.isEmpty()) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_LIST_NOT_EXISTS);
        }

        //Checking for each coupon if it is according to inserted price
        return couponsByMax.
                stream().
                filter(couponDto -> couponDto.getPrice() <= maxPrice).
                collect(Collectors.toList());
    }

    //-------------------------------------Get a specific company-------------------------------------------------

    public CompanyDto getCompany(final Long companyId) throws ApplicationException {

        //Checking if the specific company is not exists
        if (!companyRepository.existsById(companyId)) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting a specific company
        Company company = OptionalToEntityConvertorUtil.optionalCompany(companyRepository.findById(companyId));

        List<CouponDto> dtoCoupons = ObjectMappingUtil.couponsToCouponsDto(company.getCoupons());

        //Converting company to companyDto entity
        CompanyDto companyDto = ObjectMappingUtil.companyToCompanyDto(company);
        //Setting the converted coupons company coupons list
        companyDto.setCoupons(dtoCoupons);

        return companyDto;
    }
}



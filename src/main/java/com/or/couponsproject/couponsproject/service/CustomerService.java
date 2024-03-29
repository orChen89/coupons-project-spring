package com.or.couponsproject.couponsproject.service;

import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.enums.EntityType;
import com.or.couponsproject.couponsproject.errors.Constraint;
import com.or.couponsproject.couponsproject.errors.exceptions.*;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.model.Customer;
import com.or.couponsproject.couponsproject.repo.CouponRepository;
import com.or.couponsproject.couponsproject.repo.CustomerRepository;
import com.or.couponsproject.couponsproject.util.CouponUtil;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import com.or.couponsproject.couponsproject.util.OptionalToEntityConvertorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.or.couponsproject.couponsproject.constants.Constants.ZERO;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    //------------------------------------------Creating new purchase------------------------------------------------

    public CouponDto addCouponPurchase(final Long customerId, final Long couponId) throws ApplicationException {

        //Checking if the customer is not exist
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Checking if the coupon is not exist
        if (!couponRepository.existsById(couponId)) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the specific customer
        Customer currentCustomer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerId));

        //Setting the specific coupon
        Coupon currentCoupon = OptionalToEntityConvertorUtil.optionalCoupon(couponRepository.findById(couponId));

        //Checking if the customer already purchased the current coupon
        for (Coupon c : currentCustomer.getCoupons()) {
            if (Objects.equals(c.getId(), couponId)) {
                throw new CouponAlreadyPurchased(EntityType.COUPON, currentCoupon, EntityType.CUSTOMER);
            }
        }

        //Checking if the coupon is not in stock
        if (currentCoupon.getAmount() <= ZERO) {
            throw new CouponNotInStock(EntityType.COUPON, currentCoupon);
        }

        //Checking if the coupon is already expired
        if (CouponUtil.isCouponExpired(currentCoupon.getEndDate())) {
            throw new CouponExpirationDateArrived(currentCoupon);
        }

        //Getting the customer's coupons
        List<Coupon> updatedCustomerCoupons = couponRepository.getCouponsByCustomersId(customerId);

        //Decreasing coupon amount
        CouponUtil.decreaseCouponAmount(currentCoupon);
        //Adding the updated coupon to a list
        updatedCustomerCoupons.add(currentCoupon);
        //Setting the updated coupons list of the specific customer
        currentCustomer.setCoupons(updatedCustomerCoupons);
        //Creating a new coupon purchase with updated info
        customerRepository.save(currentCustomer);

        log.info("The coupon: " + "~" + currentCoupon.getTitle() + "~" + " with id: " + couponId + "," +
                " has been purchased by customer: " + "~" + currentCustomer.getFirstName() + " " +
                currentCustomer.getLastName() + "~" + " with id: " + customerId);

        return ObjectMappingUtil.couponToCouponDto(currentCoupon);
    }

    //--------------------------Getting all coupons of specific customer-------------------------------------------

    public List<CouponDto> getAllCouponsByCustomerId(final Long customerId) throws ApplicationException {

        //Checking if Customer Id is not exists
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Converting the coupons list to a list of coupons Dto according the specific customer
        List<CouponDto> dtoCoupons = ObjectMappingUtil.
                couponsToCouponsDto(couponRepository.
                        getCouponsByCustomersId(customerId));


        return dtoCoupons;
    }

    //-----------------------------------Getting a specific existing customer---------------------------------------

    public CustomerDto getCustomer(final Long customerId) throws ApplicationException {

        //Checking if the customer is not exists
        if(!customerRepository.existsById(customerId)) {

            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting a specific customer and returning it
        return ObjectMappingUtil.
                customerToCustomerDto(OptionalToEntityConvertorUtil.
                        optionalCustomer(customerRepository.findById(customerId)));
    }

    //-----------------------------------Getting all coupons by category-------------------------------------------

    public List<CouponDto> getCouponsByCategory(final Long customerId, final CouponCategory category) throws ApplicationException {

        //Checking if the customer is not exists
        if(!customerRepository.existsById(customerId)) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the customer's coupons of specific customer
        List<CouponDto> coupons = getAllCouponsByCustomerId(customerId);

        //Checking if the coupons are not exists
        if (coupons.isEmpty()) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_LIST_NOT_EXISTS);
        }

        //Checking for each coupon if it is from same category
        return coupons.
                stream().
                filter(couponDto -> couponDto.getCategory().equals(category)).
                collect(Collectors.toList());
    }

    //-----------------------------------Getting all coupons according to price------------------------------------

    public List<CouponDto> getCouponsByMaxPrice(final Long customerId, final double maxPrice) throws ApplicationException {

        //Checking if the customer is not exists
        if(!customerRepository.existsById(customerId)) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the customer's coupons according to specific customer
        List<CouponDto> couponsByMax = getAllCouponsByCustomerId(customerId);

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
}

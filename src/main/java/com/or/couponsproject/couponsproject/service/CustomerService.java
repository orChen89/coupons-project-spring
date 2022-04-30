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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    //------------------------------------------Creating new purchase------------------------------------------------

    public Customer addCouponPurchase(final Long customerID, final Long couponID) throws ApplicationException {

        //Setting the specific customer
        Customer currentCustomer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerID));

        //Setting the specific coupon
        Coupon currentCoupon = OptionalToEntityConvertorUtil.optionalCoupon(couponRepository.findById(couponID));

        //Checking if the customer is not exist
        if (currentCustomer == null) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Checking if the coupon is not exist
        if (currentCoupon == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        //Checking if the customer already purchased the current coupon
        for (CouponDto c : getAllCouponsByCustomerId(customerID)) {
            if (Objects.equals(c.getId(), couponID)) {
                throw new CouponAlreadyPurchased(EntityType.COUPON, currentCoupon, EntityType.CUSTOMER);
            }
        }

        //Checking if the coupon is not in stock
        if (currentCoupon.getAmount() <= 0) {
            throw new CouponNotInStock(EntityType.COUPON, currentCoupon);
        }

        //Checking if the coupon is already expired
        if (CouponUtil.isCouponExpired(currentCoupon.getEndDate())) {
            throw new CouponExpirationDateArrived(currentCoupon);
        }

        //Getting the customer's coupons
        List<Coupon> updatedCustomerCoupons = couponRepository.getCouponsByCustomersId(customerID);

        //Decreasing coupon amount
        CouponUtil.decreaseCouponAmount(currentCoupon);
        //Adding the updated coupon to a list
        updatedCustomerCoupons.add(currentCoupon);
        //Setting the updated coupons list of the specific customer
        currentCustomer.setCoupons(updatedCustomerCoupons);

        //Creating a new coupon purchase with updated info
        customerRepository.save(currentCustomer);

        log.info("The coupon: " + "~" + currentCoupon.getTitle() + "~" + " with id: " + couponID + "," +
                " has been purchased by customer: " + "~" + currentCustomer.getFirstName() + " " +
                currentCustomer.getLastName() + "~" + " with id: " + customerID);

        return currentCustomer;
    }

    //--------------------------Getting all coupons of specific customer-------------------------------------------

    public List<CouponDto> getAllCouponsByCustomerId(final Long customerId) throws ApplicationException {

        //Setting the coupons list according the specific customer
        List<Coupon> customerCoupons = couponRepository.getCouponsByCustomersId(customerId);

        //Checking if coupons are not exist
        if (customerCoupons == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }

        //Converting the coupons list to a list of coupons Dto according the specific customer
        List<CouponDto> dtoCoupons = ObjectMappingUtil.couponsToCouponsDto(customerCoupons);

        return dtoCoupons;
    }

    //-----------------------------------Getting a specific existing customer---------------------------------------

    public CustomerDto getCustomer(final Long customerId) throws ApplicationException {

        //Setting a specific customer
        Customer customer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerId));

        //Checking if the customer is not exists
        if (customer == null) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        List<CouponDto> customerCouponsDto = getAllCouponsByCustomerId(customerId);

        List<Coupon> customerCoupons = ObjectMappingUtil.couponsDtoToCoupons(customerCouponsDto);

        customer.setCoupons(customerCoupons);

        CustomerDto customerDto = ObjectMappingUtil.customerToCustomerDto(customer);

        return customerDto;
    }

    //-----------------------------------Getting all coupons by category-------------------------------------------

    public List<CouponDto> getCouponsByCategory(final Long customerId, final CouponCategory category) throws ApplicationException {

        List<CouponDto> couponsOfCustomerByCategory = new ArrayList<>();

        //Setting a specific customer
        Customer customer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerId));

        //Checking if the customer is not exists
        if(customer == null) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the customer's coupons of specific customer
        List<CouponDto> coupons = getAllCouponsByCustomerId(customerId);

        //Checking if the coupons are not exists
        if (coupons == null) {
            throw new EntityNotExistException(EntityType.COUPON, Constraint.ENTITY_NOT_EXISTS);
        }
        for (CouponDto coupon : coupons) {
            //Checking for each coupon if it is from same category
            if (coupon.getCategory().equals(category)) {
                //Adding the specific coupon to a list
                couponsOfCustomerByCategory.add(coupon);
                break;
            }
            log.info("The customer: " + customer.getFirstName() +
                    " " + customer.getLastName() +
                    " doesn't holds coupons from this category!");
        }
        return couponsOfCustomerByCategory;
    }

    //-----------------------------------Getting all coupons according to price------------------------------------

    public List<CouponDto> getCouponsByMaxPrice(final Long customerId, final double maxPrice) throws ApplicationException {

        List<CouponDto> couponsByMax = new ArrayList<>();

        //Setting a specific customer
        Customer customer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerId));

        //Checking if the customer is not exists
        if(customer == null) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the customer's coupons according to specific customer
        List<CouponDto> coupons = getAllCouponsByCustomerId(customerId);

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
}

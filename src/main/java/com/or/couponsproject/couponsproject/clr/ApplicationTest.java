package com.or.couponsproject.couponsproject.clr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationTest implements CommandLineRunner {

    private final AdminTest adminTest;
    private final CompanyTest companyTest;
    private final CustomerTest customerTest;
    private final ExceptionsTest exceptionsTest;

    @Override
    public void run(String... args) {

        System.out.println(TestColorsConstants.ANSI_PINK_BACKGROUND +
                TestColorsConstants.ANSI_BLACK +
                "Entering to Application Tests -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);

        try {

            //Activating the application test
            System.err.println("Application test result: " + TestAll());

            //Catching all Exceptions
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean TestAll() {

        boolean testAdmin = false;

        boolean testCompany = false;

        boolean testCustomer = false;

        boolean testException = false;


        try {

        testAdmin = adminTest.adminTest();

        testCompany =  companyTest.companyTest();

        testCustomer = customerTest.customerTest();

        testException = exceptionsTest.advancedTests();

            //Catching all Exceptions
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //Checking if all test returned true and passed successfully
        if (testAdmin && testCompany && testCustomer && testException) {

            System.out.println(TestColorsConstants.ANSI_GREEN +
                    "Application Test has been passed successfully!!!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

        } else {

            System.out.println(TestColorsConstants.ANSI_GREEN +
                    "Application Test has been Failed!!!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return false;
        }
        return true;
    }
}






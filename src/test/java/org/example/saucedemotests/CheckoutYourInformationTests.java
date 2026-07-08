package org.example.saucedemotests;

import org.example.pages.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutYourInformationTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private CheckoutYourInformationPage checkoutYourInformationPage;
    private CheckoutOverview checkoutOverviewPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        yourCartPage = new YourCartPage(driver);
        checkoutYourInformationPage = new CheckoutYourInformationPage(driver);
        checkoutOverviewPage = new CheckoutOverview(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void navigateToCheckoutInformationPage() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productsPage.addBackpackToCart();
        productsPage.openCart();
        yourCartPage.clickCheckout();
    }

    @Test
    public void successfulCheckoutInformationEntryTest() {
        navigateToCheckoutInformationPage();

        checkoutYourInformationPage.enterFirstName("Tamara");
        checkoutYourInformationPage.enterLastName("Petrova");
        checkoutYourInformationPage.enterPostalCode("1700");
        checkoutYourInformationPage.clickContinue();

        assertTrue(checkoutOverviewPage.isCheckoutOverviewPageDisplayed());
    }

    @Test
    public void emptyFirstNameShowsErrorMessageTest() {
        navigateToCheckoutInformationPage();

        checkoutYourInformationPage.enterLastName("Petrova");
        checkoutYourInformationPage.enterPostalCode("1700");
        checkoutYourInformationPage.clickContinue();

        assertEquals(
                "Error: First Name is required",
                checkoutYourInformationPage.getErrorMessage("Error: First Name is required")
        );
    }

    @Test
    public void emptyLastNameShowsErrorMessageTest() {
        navigateToCheckoutInformationPage();

        checkoutYourInformationPage.enterFirstName("Tamara");
        checkoutYourInformationPage.enterPostalCode("1700");
        checkoutYourInformationPage.clickContinue();

        assertEquals(
                "Error: Last Name is required",
                checkoutYourInformationPage.getErrorMessage("Error: Last Name is required")
        );
    }

    @Test
    public void emptyPostalCodeShowsErrorMessageTest() {
        navigateToCheckoutInformationPage();

        checkoutYourInformationPage.enterFirstName("Tamara");
        checkoutYourInformationPage.enterLastName("Petrova");
        checkoutYourInformationPage.clickContinue();

        assertEquals(
                "Error: Postal Code is required",
                checkoutYourInformationPage.getErrorMessage("Error: Postal Code is required")
        );
    }

    @Test
    public void cancelButtonReturnsToCartTest() {
        navigateToCheckoutInformationPage();

        checkoutYourInformationPage.clickCancel();

        assertTrue(yourCartPage.isYourCartPageDisplayed());
    }
}

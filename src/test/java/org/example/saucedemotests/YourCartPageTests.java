package org.example.saucedemotests;

import org.example.pages.CheckoutYourInformationPage;
import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.example.pages.YourCartPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class YourCartPageTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private CheckoutYourInformationPage checkoutYourInformationPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        yourCartPage = new YourCartPage(driver);
        checkoutYourInformationPage = new CheckoutYourInformationPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @Test
    public void verifyAddedProductIsDisplayedInCartTest() {
        login();

        productsPage.addBackpackToCart();
        productsPage.openCart();

        assertTrue(yourCartPage.isBackpackDisplayed());
    }

    @Test
    public void verifyRemoveProductFromCartTest() {
        login();

        productsPage.addBackpackToCart();
        productsPage.openCart();

        assertTrue(yourCartPage.isBackpackDisplayed());

        yourCartPage.removeBackpack();

        assertTrue(yourCartPage.isBackpackDisplayed());
    }

    @Test
    public void verifyContinueShoppingButtonNavigatesToProductsPageTest() {
        login();

        productsPage.addBackpackToCart();
        productsPage.openCart();

        yourCartPage.clickContinueShopping();

        assertTrue(productsPage.isProductsTextDisplayed());
    }

    @Test
    public void verifyCheckoutButtonNavigatesToCheckoutInformationPageTest() {
        login();

        productsPage.addBackpackToCart();
        productsPage.openCart();

        yourCartPage.clickCheckout();

        assertTrue(checkoutYourInformationPage.isCheckoutInformationPageDisplayed());
    }

    @Test
    public void verifyCorrectProductNameIsDisplayedTest() {
        login();

        productsPage.addBackpackToCart();
        productsPage.openCart();

        assertEquals("Sauce Labs Backpack",
                yourCartPage.getBackpackName("Sauce Labs Backpack"));
    }

    @Test
    public void verifyCorrectProductPriceIsDisplayedTest() {
        login();

        productsPage.addBackpackToCart();
        productsPage.openCart();

        assertEquals("$29.99",
                yourCartPage.getBackpackPrice("$29.99"));
    }
}

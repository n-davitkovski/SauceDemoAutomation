package org.example.saucedemotests;

import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void successfulLoginTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertEquals("Products", productsPage.productsTextDisplayed());
        assertTrue(productsPage.isProductsTextDisplayed());
    }

    @Test
    public void loginFormInitialStateUITest() {
        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif",
                loginPage.getUserNameFieldFontType());
        assertEquals("14px",
                loginPage.getUserNameFieldFontSize());

        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif",
                loginPage.getPassWordFieldFontType());
        assertEquals("14px",
                loginPage.getPassWordFieldFontSize());

        assertEquals("Login",
                loginPage.getLoginButtonText());
        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif",
                loginPage.getLoginButtonFontType());
        assertEquals("16px",
                loginPage.getLoginButtonFontSize());
        assertEquals("#3ddc91",
                loginPage.getLoginButtonColor());
    }

    @Test
    public void errorMessageEmptyUsernameAndPasswordTest() {
        loginPage.clickLogin();

        assertEquals(
                "Epic sadface: Username is required",
                loginPage.getErrorMessage());
    }

    @Test
    public void errorMessageValidUsernameInvalidPasswordTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("123");
        loginPage.clickLogin();

        assertEquals(
                "Epic sadface: Username and password do not match any user in this service",
                loginPage.getErrorMessage());
    }

    @Test
    public void errorMessageInvalidUsernameEmptyPasswordTest() {
        loginPage.enterUsername("invalid_username");
        loginPage.clickLogin();

        assertEquals(
                "Epic sadface: Password is required",
                loginPage.getErrorMessage());
    }

    @Test
    public void errorMessageValidUsernameEmptyPasswordTest() {
        loginPage.enterUsername("standard_user");
        loginPage.clickLogin();

        assertEquals(
                "Epic sadface: Password is required",
                loginPage.getErrorMessage());
    }

    @Test
    public void removingErrorMessageTest() {
        loginPage.clickLogin();

        assertTrue(loginPage.isErrorMessageDisplayed());

        loginPage.clickErrorMessageXButton();

        assertFalse(loginPage.isErrorMessageDisplayed());
    }

    @Test
    public void lockedOutUserLoginTest() {
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertEquals(
                "Epic sadface: Sorry, this user has been locked out.",
                loginPage.getErrorMessage()
        );
    }

    @Test
    public void problemUserLoginTest() {
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertTrue(productsPage.isProductsTextDisplayed());
    }

    @Test
    public void performanceGlitchUserLoginTest() {
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertTrue(productsPage.isProductsTextDisplayed());
    }

    @Test
    public void errorUserLoginTest() {
        loginPage.enterUsername("error_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertTrue(productsPage.isProductsTextDisplayed());
    }

    @Test
    public void visualUserLoginTest() {
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertTrue(productsPage.isProductsTextDisplayed());
    }
}

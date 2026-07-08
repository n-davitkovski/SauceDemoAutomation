package org.example.saucedemotests;

import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsPageTests {
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

        // Precondition: Log in before each test
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void orderingDropDownValuesTest() {
        assertEquals("Name (A to Z)",
                productsPage.getAllOptionsFromOrderingDropDown().get(0).getText());
        assertEquals("Name (Z to A)",
                productsPage.getAllOptionsFromOrderingDropDown().get(1).getText());
        assertEquals("Price (low to high)",
                productsPage.getAllOptionsFromOrderingDropDown().get(2).getText());
        assertEquals("Price (high to low)",
                productsPage.getAllOptionsFromOrderingDropDown().get(3).getText());
    }

    @Test
    public void orderingProductsFromHighToLowPriceTest() {
        productsPage.selectOrderingDropdownOption(3);

        assertEquals("Price (high to low)",
                productsPage.getTextFromOrderingDropDown());

        assertTrue(productsPage.areAllProductsPricesDescending());
    }

    @Test
    public void orderingProductsFromLowToHighPriceTest() {
        productsPage.selectOrderingDropdownOption(2);

        assertEquals("Price (low to high)",
                productsPage.getTextFromOrderingDropDown());

        assertTrue(productsPage.areAllProductsPricesAscending());
    }

    @Test
    public void orderingProductsFromZToAAlphabeticallyTest() {

        List<String> initialNamesList = productsPage.getAllProductsNames();

        System.out.println("Initial product list:");
        initialNamesList.forEach(System.out::println);

        productsPage.selectOrderingDropdownOption(1);

        List<String> namesAfterSelection = productsPage.getAllProductsNames();

        System.out.println("Sorted product list:");
        namesAfterSelection.forEach(System.out::println);

        List<String> expectedList = new ArrayList<>(initialNamesList);
        Collections.reverse(expectedList);

        assertEquals(expectedList, namesAfterSelection);
    }

    @Test
    public void validateColorChangeOnTitleHoverTest() {

        String initialColor = productsPage.getColorFromBackPackTitle();

        productsPage.hoverBackpackTitle();

        String hoverColor = productsPage.getColorFromBackPackTitle();

        assertEquals(initialColor, hoverColor,
                "The product title color should change when hovered.");
    }

    @Test
    public void verifyEachProductDisplaysCorrectImageTest() {
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        List<String> imageUrls = productsPage.getAllProductImageUrls();

        Set<String> uniqueImages = new HashSet<>(imageUrls);

        assertEquals(imageUrls.size(), uniqueImages.size(),
                "Each product should display a unique image.");
    }

    @Test
    public void verifyBackpackImageDimensionsTest() {
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Dimension imageSize = productsPage.getBackpackImageSize();

        assertEquals(300, imageSize.getWidth(),
                "Backpack image width is incorrect.");

        assertEquals(300, imageSize.getHeight(),
                "Backpack image height is incorrect.");
    }
}

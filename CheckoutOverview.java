package org.example.pages;

import org.openqa.selenium.WebDriver;

public class CheckoutOverview {

    private WebDriver driver;

    public CheckoutOverview(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isCheckoutOverviewPageDisplayed() {
        return true;
    }
}

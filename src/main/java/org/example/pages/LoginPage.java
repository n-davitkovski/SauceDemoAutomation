package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;

public class LoginPage {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    //Components -> Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.xpath("//*[contains(@id, 'login_button_container')]/div/form/div[3]/h3");
    private By errorMessageXButton = By.className("error-button");

    //Actions
    public void enterUsername(String value){
        driver.findElement(usernameField).sendKeys(value);
    }

    public void enterPassword(String value){
        driver.findElement(passwordField).sendKeys(value);
    }

    public void enterLogin(){
        driver.findElement(loginButton).click();
    }


    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getLoginButtonText(){
        return driver.findElement(loginButton).getAttribute("value");
    }

    public String getLoginButtonFontType(){
        return driver.findElement(loginButton).getCssValue("font-family");
    }

    public String getLoginButtonFontSize(){
        return driver.findElement(loginButton).getCssValue("font-size");
    }

    public String getLoginElementSize(){
        return driver.findElement(loginButton).getAttribute("value");
    }

    public String getUserNameFieldFontType() {
        return driver.findElement(usernameField).getCssValue("font-family");
    }

    public String getUserNameFieldFontSize(){
        return driver.findElement(usernameField).getCssValue("font-size");
    }

    public String getPassWordFieldFontType(){
        return driver.findElement(passwordField).getCssValue("font-family");
    }

    public String getPassWordFieldFontSize(){
        return driver.findElement(passwordField).getCssValue("font-size");
    }

    public String getLoginButtonColor(){
        Color loginBtnBackgroundColor = Color.fromString(driver.findElement(loginButton).getCssValue("background-color"));
        return loginBtnBackgroundColor.asHex();
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

    public void clickErrorMessageXButton(){
        driver.findElement(errorMessageXButton).click();
    }

    public Boolean isErrorMessageDisplayed(){
        try{
            driver.findElement(errorMessage).getText();
            return true;
        } catch(NoSuchElementException e){
            return false;
        }
    }
}

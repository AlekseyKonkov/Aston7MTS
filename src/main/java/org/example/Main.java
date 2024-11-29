package org.example;

import org.openqa.selenium.WebDriver;

public class Main {
    public static void main(String[] args) {
        WebDriverSetup webDriverSetup = new WebDriverSetup();
        WebDriver driver = webDriverSetup.initializeDriver();

        try {
            MtsPage mtsPage = new MtsPage(driver);
            mtsPage.navigateToSite();
            mtsPage.acceptCookies();
            mtsPage.verifyBlockTitle();
            mtsPage.checkLogosPresence();
            mtsPage.verifyMoreInfoLink();
            mtsPage.fillFormAndSubmit();
            mtsPage.printResults();
        } finally {
            driver.quit();
        }
    }
}

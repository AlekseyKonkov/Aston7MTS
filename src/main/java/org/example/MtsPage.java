package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MtsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<String> results = new ArrayList<>();

    public MtsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void navigateToSite() {
        driver.get("https://www.mts.by/");
        results.add("Переход на сайт успешно выполнен");
    }

    public void acceptCookies() {
        try {
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cookie-agree\"]")));
            acceptCookiesButton.click();
            results.add("Куки успешно приняты");
        } catch (Exception e) {
            results.add("Не удалось принять куки.");
        }
    }

    public void verifyBlockTitle() {
        try {
            WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2")));
            if ("Онлайн пополнение без комиссии".equals(blockTitle.getText())) {
                results.add("Название блока корректное");
            } else {
                results.add("Название блока отличается от ожидаемого");
            }
        } catch (Exception e) {
            results.add("Не удалось проверить название блока.");
        }
    }

    public void checkLogosPresence() {
        String[] xpaths = {
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[1]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[2]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[3]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[4]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[5]/img"
        };

        boolean allLogosPresent = true;

        for (String xpath : xpaths) {
            try {
                WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                if (logo == null) {
                    allLogosPresent = false;
                    break;
                }
            } catch (Exception e) {
                allLogosPresent = false;
                break;
            }
        }

        if (allLogosPresent) {
            results.add("Все логотипы платежных систем присутствуют");
        } else {
            results.add("Некоторые логотипы платежных систем отсутствуют");
        }
    }

    public void verifyMoreInfoLink() {
        try {
            WebElement moreInfoLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Подробнее о сервисе")));
            moreInfoLink.click();
            wait.until(ExpectedConditions.urlToBe("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/"));
            results.add("Ссылка 'Подробнее о сервисе' работает");
            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe("https://www.mts.by/"));
        } catch (Exception e) {
            results.add("Проблема с переходом по ссылке 'Подробнее о сервисе'");
        }
    }

    public void fillFormAndSubmit() {
        WebElement serviceField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[1]/div[1]/div[2]/button/span[1]")));
        WebElement phoneNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"connection-phone\"]")));
        WebElement amountField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"connection-sum\"]")));
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"connection-email\"]")));
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pay-connection\"]/button")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", phoneNumberField);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='Услуги связи';", serviceField);
        js.executeScript("arguments[0].value='297777777';", phoneNumberField);
        js.executeScript("arguments[0].value='100';", amountField);
        js.executeScript("arguments[0].value='aaa.aa@aaa.ru';", emailField);

        js.executeScript("arguments[0].click();", continueButton);

        results.add("Кнопка 'Продолжить' работает");
    }

    public void printResults() {
        System.out.println("Результаты проверки:");
        for (String result : results) {
            System.out.println(result);
        }
    }
}

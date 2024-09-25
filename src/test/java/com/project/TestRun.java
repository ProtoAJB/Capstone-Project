package com.project;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project.pom.LoginPage;
import com.project.pom.PlanetariumPage;
import com.project.pom.RegPage;

import java.time.Duration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = "com.project.step", plugin = {
        "pretty",
        "html:src/test/resources/reports/html-report.html",
        "json:src/test/resources/reports/json-report.json"
})

public class TestRun {
    public static WebDriver driver;

    public static WebDriverWait wait;

    public static LoginPage startPage;
    public static PlanetariumPage planetariumPage;
    public static RegPage regPage;

    @BeforeClass
    public static void setup() {
        Setup.resetTestDatabase();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        startPage = new LoginPage(driver);
        regPage = new RegPage(driver);
        planetariumPage = new PlanetariumPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterClass
    public static void teardown() {
        if (driver != null)
            driver.quit();
    }

    /*
     * public static void main(String[] args) {
     * // this is to check if you land in planetarium login
     * setup();
     * startPage.goToStartPage();
     * System.out.println(driver.getTitle());
     * teardown();
     * }
     */

}

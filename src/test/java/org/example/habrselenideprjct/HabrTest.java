package org.example.habrselenideprjct;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HabrTest {

    HabrMainPage habrPg = new HabrMainPage();

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        Configuration.browser = "chrome";
        Configuration.webdriverLogsEnabled = true;
        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 10000; //// Установите таймаут для ожидания элементов

        Selenide.open("https://www.habr.com/");
    }

    @AfterEach
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    public void clickLoginButton() {
        WebDriverRunner.getWebDriver().manage().window().maximize(); // Максимизирует в полноэкранный режим, можно отключить.
        habrPg.loginButton.click();
        $(By.xpath("//*[@class='shadow-box__title']")).shouldHave(text("Вход"));
    }

    @Test
    public void changingTheThemeToDark() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
        $(By.xpath("//button[contains(@class, 'tm-footer__link')]")).click();// добавить логи, кликаем по настроки языка

        $($$(By.xpath("//*[@class='tm-input-radio-labeled__fake']")).get(4)).click();
        //$x("(//*[@class='tm-input-radio-labeled__fake'])[5]").click();;// добавить логи, кликаем по настроки языка

        $(By.xpath("//button[contains(@type,'submit')]")).click();

        $$(By.xpath("//nav/a")).get(0).shouldBe(visible);

    }
}

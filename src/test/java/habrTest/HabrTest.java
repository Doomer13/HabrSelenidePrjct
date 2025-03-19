package habrTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import habrPages.HabrMainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.MutableCapabilities;

import java.io.File;
import java.io.IOException;

public class HabrTest {

    HabrMainPage habrPg;

    @BeforeAll
    public static void setUpAll() {
        // Настройка браузера
        Configuration.browserSize = "1280x800";

        // Настройка ChromeOptions
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        // Преобразование ChromeOptions в MutableCapabilities
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    public void setUp() {
        // Открытие страницы перед каждым тестом
        open("https://habr.com/ru/feed/");

        // Проверка видимости элемента body
        $("body").shouldBe(visible);
        habrPg =page();
    }


    @AfterEach
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    private boolean deleteScreenshots = true;
    // Флаг. Включить выключить. По умолчанию включено.
    @AfterEach
    public void removeScreenAllShorts() {
        if (deleteScreenshots) {
            String screenshotsFolder = "screenshots";
            File folder = new File(screenshotsFolder);

            if (folder.exists()) {
                for (File file : folder.listFiles()) {
                    if (file.getName().endsWith(".png")) {
                        file.delete();
                    }
                }
            }
        }
    }

    @Test
    public void clickLoginButton() {
        habrPg.clickingLoginButton();
        habrPg.checkLoginDialog();
    }

    @Test
    public void changingTheThemeRefreshPage() {

      habrPg.hideBannerIfPresent();
      habrPg.clickLanguageOptionButton();
      habrPg.selectedDarkTheme();
      habrPg.doSubmit();
      habrPg.chekinMainPage();
        // добавить логи, кликаем по настроки языка
    }

    @Test
    public void checkColorThemAfterChang(){
        habrPg.hideBannerIfPresent();
        System.out.println("Светлая тема: " + "атрибут seashell: " + habrPg.getSeashellColor());
        habrPg.clickLanguageOptionButton();
        habrPg.selectedDarkTheme();
        habrPg.doSubmit();
        sleep(1000);
        System.out.println("Темная тема: " + "атрибут seashell: " + habrPg.getSeashellColor());
        Assertions.assertEquals("#080808",habrPg.getSeashellColor(), "Цветовая палитра не изменилась");
        assertThat(habrPg.getSeashellColor(), is("#080808"));

    }

    @Test
    public void comparingScreenshotsAfterChangeTheme() throws IOException {
        habrPg.hideBannerIfPresent();
        habrPg.testThemeChange();

    }



}

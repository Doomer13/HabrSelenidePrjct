package habrPages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class HabrMainPage {

    private final SelenideElement BUNNER =
            $(By.xpath("//*[@class = \"fixed-banner-wrapper\"]"));

    /////////////////////////////
    private final SelenideElement LOGIN_BUTTON =
            $x("//button[contains(@class,'btn')]");

    private final SelenideElement LOGIN_DIALOG_TITLE =
            $(By.xpath("//*[@class='shadow-box__title']"));

    //////////////////////////////
    private final SelenideElement BUTTON_OPTION_LANGUAGE =
            $(By.xpath("//button[contains(@class, 'tm-footer__link')]"));

    private final SelenideElement CHOSEN_DARK_THEM =
            $($$(By.xpath("//*[@class='tm-input-radio-labeled__fake']")).get(4));

    private final SelenideElement SUBMIT =
            $(By.xpath("//button[contains(@type,'submit')]"));

    private final SelenideElement MAIN_PAGE_CHECK =
            $$(By.xpath("//nav/a")).get(0);


    // Метод для скрытия рекламы, если она существует
    ///////////////////////
    public void hideBannerIfPresent() {
        if (BUNNER.exists()) { // Проверяем, существует ли элемент
            ((JavascriptExecutor) WebDriverRunner.getWebDriver())
                    .executeScript("arguments[0].style.display='none';", BUNNER);
        }
    }

    public void clickLanguageOptionButton() {
        BUTTON_OPTION_LANGUAGE.shouldBe(Condition.visible).click(); // Ждем, пока кнопка станет видимой, и кликаем на неё
    }

    public void selectedDarkTheme() {
        CHOSEN_DARK_THEM.click();
    }

    public void doSubmit() {
        SUBMIT.click();
    }

    public void chekinMainPage() {
        MAIN_PAGE_CHECK.shouldBe(visible);
    }


    /////////////////////////////
    ///////////////////////////
    public void clickingLoginButton() {
        LOGIN_BUTTON.click();
    }

    public void checkLoginDialog() {
        LOGIN_DIALOG_TITLE.shouldHave(text("Вход"));
    }


    //////////////////////////////////
    public void checkingColorThemAfterChang() {


    }

    public String getSeashellColor() {

        return Selenide.executeJavaScript(
                "return getComputedStyle(document.documentElement)" +
                        ".getPropertyValue('--seashell').trim();"
        );
    }

    public void testThemeChange() throws IOException {


        // Захватываем скриншот до смены темы
        WebDriver driver = WebDriverRunner.getWebDriver();
        Screenshot initialScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);

        // Меняем тему
        // Добавьте код для смены темы здесь
        clickLanguageOptionButton();
        selectedDarkTheme();
        doSubmit();


        // Захватываем скриншот после смены темы
        Screenshot newScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);

        // Сохраняем скриншоты в файлы


        String projectRoot = System.getProperty("user.dir");
        String screenshotsFolder = projectRoot + "/screenshots";
        File folder = new File(screenshotsFolder);
        if (!folder.exists()) {
            folder.mkdir(); // Создаем папку, если она не существует
        }
        ImageIO.write(initialScreenshot.getImage(), "png", new File(screenshotsFolder + "/initial_screenshot.png"));
        ImageIO.write(newScreenshot.getImage(), "png", new File(screenshotsFolder + "/new_screenshot.png"));

        // Сравниваем скриншоты
        BufferedImage initialImage = ImageIO.read(new File(screenshotsFolder + "/initial_screenshot.png"));
        BufferedImage newImage = ImageIO.read(new File(screenshotsFolder + "/new_screenshot.png"));

        ImageDiff diff = new ImageDiffer().makeDiff(initialImage, newImage);

        if (diff.hasDiff()) {
            System.out.println("Темы различаются");
            // Сохраняем скриншот с выделенными различиями
            ImageIO.write(diff.getMarkedImage(), "png", new File(screenshotsFolder + "/diff_screenshot.png"));
        } else {
            System.out.println("Темы идентичны");
        }
    }
}



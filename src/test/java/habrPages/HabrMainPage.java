package habrPages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class HabrMainPage {



    private final SelenideElement LOGIN_BUTTON =
            $x("//button[contains(@class,'btn')]");
    private final SelenideElement LOGIN_DIALOG_TITLE =
            $(By.xpath("//*[@class='shadow-box__title']"));


    public void clickingLoginButton() {
        LOGIN_BUTTON.click();
    }

    public void checkLoginDialog() {
        LOGIN_DIALOG_TITLE.shouldHave(text("Вход"));
    }

    public void checkIngColorThemAfterChang(){


    }

}

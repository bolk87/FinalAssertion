package pageObjects;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final String userNameFieldSelector = "#user-name";
    private final String passwordNameFieldSelector = "#password";
    private final String loginButtonSelector = "#login-button";

    public void open () {
        //открываем страницу входа
        Selenide.open("https://www.saucedemo.com/");
    }

    public void enterUsername (String username) {
        //Заполняем поля имени юзера
        $(userNameFieldSelector).val(username);
    }

    public void enterPassword (String password) {
        //Заполняем поля имени юзера
        $(passwordNameFieldSelector).val(password);
    }

    public void clickLogin () {
        //Нажимаем кнопку входа
        $(loginButtonSelector).click();
    }
}

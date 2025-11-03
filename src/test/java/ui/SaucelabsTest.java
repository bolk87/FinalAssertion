package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pageObjects.CartPage;
import pageObjects.CheckOutPage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("UI автотесты")
public class SaucelabsTest {

    @BeforeEach
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
                  .includeSelenideSteps(true)
        );
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.browserSize = "1280x1024";
        Configuration.browserPosition = "1600x0";
        Configuration.headless = true;
    }

    @Test
    @DisplayName("Успешная авторизация (standard_user)")
    @Description("Авторизация стандартного пользователя")
    @Tags({@Tag("позитивный"), @Tag("авторизация"), @Tag("ui")})
    public void loginTest () {
        step("Открыть сайт", () -> new LoginPage().open());
        step("Заполнить имя юзера - standard_user", () -> new LoginPage().enterUsername("standard_user"));
        step("Заполнить password", () -> new LoginPage().enterPassword("secret_sauce"));
        step("Нажать кнопку входа", () -> new LoginPage().clickLogin());

        step("Проверяем, что отображается страница с товарами", () -> {
            assertEquals(6, new ProductsPage().productNames.size());
        });
    }

    @Test
    @DisplayName("Авторизация заблокированного пользователя (locked_out_user)")
    @Description("Авторизация заблокированного пользователя")
    @Tags({@Tag("негативный"), @Tag("авторизация"), @Tag("ui")})
    public void lockedLoginTest () {
        step("Открыть сайт", () -> new LoginPage().open());
        step("Заполнить имя юзера - standard_user", () -> new LoginPage().enterUsername("locked_out_user"));
        step("Заполнить password", () -> new LoginPage().enterPassword("secret_sauce"));
        step("Нажать кнопку входа", () -> new LoginPage().clickLogin());

       // assertEquals(true, $(byText("Epic sadface: Sorry, this user has been locked out.")).isDisplayed());

        step("Проверяем, что на экране есть текст: Epic sadface: Sorry, this user has been locked out.", () -> {
            assertEquals(true, $(byText("Epic sadface: Sorry, this user has been locked out.")).isDisplayed());
        });
    }

    @Test
    @DisplayName("E2E сценарий для standard_user")
    @Description("Проверка бизнесс-сценария покупки товаров")
    @Tags({@Tag("позитивный"), @Tag("бизнес-сценарий"), @Tag("ui")})
    @Severity(SeverityLevel.CRITICAL)
    public void e2eScenarioStandardUser() {
        performE2eScenario("standard_user");
    }

    @Test
    @DisplayName("E2E сценарий для performance_glitch_user")
    @Description("Проверка бизнесс-сценария покупки товаров")
    @Tags({@Tag("позитивный"), @Tag("бизнес-сценарий")})
    @Severity(SeverityLevel.CRITICAL)
    public void e2eScenarioPerformanceUser() {
        performE2eScenario("performance_glitch_user");
    }

    private void performE2eScenario(String username) {
        step("Авторизуемся пользователем: " + username, () -> {
            new LoginPage().open();
            new LoginPage().enterUsername(username);
            new LoginPage().enterPassword("secret_sauce");
            new LoginPage().clickLogin();
        });

        step("Добавляем товары в корзину 3 товара", () -> {
            var page = new ProductsPage();
            page.addProductsToCart(page.produtsToAdd);
            page.addToCart();
        });

        var cartPage = new CartPage();

        step("Переходим в корзину и проверяем наличие 3-х товаров", () -> {
            assertEquals(3, cartPage.CountItemsInTheCart());
        });

        step("Нажимаем кнопку Checkout", () -> {
            cartPage.proceedToCheckout();
        });

        var checkoutPage = new CheckOutPage();

        step("Оформляем покупку на сайте", () -> {
            checkoutPage.fillForm("Kate", "Ivanova", "12345");
        });

        step("Нажимаем кнопку Continue", () -> {
            checkoutPage.clickContinue();
        });

        step("Проверяем, что общая стоимость равна $58.29", () -> {
            double totalPrice = checkoutPage.checkTotalPrice();
            assertEquals(58.29, totalPrice, 0.01);
        });

        step("Нажимаем кнопку Finish", () -> {
            checkoutPage.completeOrder();
        });
    }
}

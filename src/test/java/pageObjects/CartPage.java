package pageObjects;
import static com.codeborne.selenide.Selenide.*;

public class CartPage {
    private final String checkoutButton = "#checkout";

    public int CountItemsInTheCart () {
        return $$(".cart_item").size();
    }

    public void proceedToCheckout () {
        $(checkoutButton).click();
    }
}

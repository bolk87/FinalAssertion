package pageObjects;

import com.codeborne.selenide.ElementsCollection;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage {
    public ElementsCollection productNames = $$(".inventory_item_name");
    public List<String> produtsToAdd = List.of(
            "Sauce Labs Backpack",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Onesie"
    );

    public boolean isProductDisplayed (String name) {
        return productNames.stream().anyMatch(el -> el.getText().equals(name));
    }

    public void addProductsToCart (List <String> products) {
        for (String product : products) {
     //       if (isProductDisplayed(product)) continue;

            //Добавляем товар в корзину
            switch (product) {
                case "Sauce Labs Backpack":
                    $("#add-to-cart-sauce-labs-backpack").click();
                    break;
                case "Sauce Labs Bolt T-Shirt":
                    $("#add-to-cart-sauce-labs-bolt-t-shirt").click();
                    break;
                default:
                    $("#add-to-cart-sauce-labs-onesie").click();
            }
        }
    }

    public void addToCart () {
        //Переходим в таблицу
        $("#shopping_cart_container").click();
    }
}

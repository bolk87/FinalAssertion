package pageObjects;

import static com.codeborne.selenide.Selenide.$;

public class CheckOutPage {
    private final String firstNameInputSelector = "#first-name";
    private final String lastNameInputSelector = "#last-name";
    private final String postalCodeSelector = "#postal-code";
    private final String continueButtonSelector = "#continue";
    private final String finishButtonSelector = "#finish";

    public void fillForm (String firstName, String lastName, String postalCode) {
        $(firstNameInputSelector).val(firstName);
        $(lastNameInputSelector).val(lastName);
        $(postalCodeSelector).val(postalCode);
    }

    public void clickContinue () {
        $(continueButtonSelector).click();
    }

    public double checkTotalPrice () {
        return Double.parseDouble($(".summary_total_label").getText().replace("Total: $", ""));
    }

    public void completeOrder () {
        $(finishButtonSelector).click();
    }
}

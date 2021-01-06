package io.cosmosoftware.kite.amazon.pages;

import io.cosmosoftware.kite.amazon.Utils.Utils;
import io.cosmosoftware.kite.exception.KiteInteractionException;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.usrmgmt.Account;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

  @FindBy(id = "buy-now-button")
  private WebElement buy_now_button;

  @FindBy(id = "productTitle")
  private WebElement product_title;

  @FindBy(id = "nav-link-accountList")
  private WebElement sign_in_btn;

  @FindBy(id = "ap_email")
  private WebElement email_input;

  @FindBy(id = "continue")
  private WebElement continue_btn;

  @FindBy(id = "ap_password")
  private WebElement pw_input;

  @FindBy(id = "signInSubmit")
  private WebElement submit_btn;

  public MainPage(Runner runner) {
    super(runner);
  }


  public void sign_in(Account account) throws KiteInteractionException {
    waitUntilVisibilityOf(sign_in_btn, 5);
    this.webDriver.get(sign_in_btn.getAttribute("href"));
    email_input.sendKeys(account.getEmail());
    continue_btn.click();
    waitUntilVisibilityOf(pw_input, 5);
    pw_input.sendKeys(account.getPassword());
    submit_btn.click();
  }

  public void open(String url) {
    this.webDriver.get(url);
  }

  public void checkButton(String url, String token, String chatId) throws KiteTestException {
      try {
        waitUntilVisibilityOf(product_title, 3);
        waitUntilVisibilityOf(buy_now_button, 5);
        Utils.sendToTelegram(product_title.getText() + " is available " + url, token, chatId);
        logger.info("Stock available, text sent!");
      } catch (KiteInteractionException e) {
        logger.info("No stock available " + java.time.LocalTime.now());
      }
  }

  public void waitX() throws InterruptedException {
    wait(500);
  }
}

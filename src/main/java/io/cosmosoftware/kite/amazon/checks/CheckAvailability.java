package io.cosmosoftware.kite.amazon.checks;

import io.cosmosoftware.kite.amazon.pages.MainPage;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;

import static io.cosmosoftware.kite.entities.Timeouts.TEN_SECOND_INTERVAL;
import static io.cosmosoftware.kite.util.TestUtils.waitAround;

public class CheckAvailability extends TestStep {
  private final MainPage mainPage;
  private final String url;
  private final String token;
  private final String chatId;

  public CheckAvailability(Runner runner, String url, String token, String chatId) {
    super(runner);
    this.mainPage = new MainPage(runner);
    this.setGetConsoleLog(false);
    this.url = url;
    this.chatId = chatId;
    this.token = token;
  }

  @Override
  protected void step() throws KiteTestException {
    mainPage.checkButton(url, token, chatId);
    mainPage.reload();
    waitAround(TEN_SECOND_INTERVAL);
  }

  @Override
  public String stepDescription() {
    return "Check the availability";
  }

}

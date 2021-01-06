package io.cosmosoftware.kite.amazon.steps;

import io.cosmosoftware.kite.amazon.pages.MainPage;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;

public class OpenURLStep extends TestStep {

  private final MainPage mainPage;
  private final String url;

  public OpenURLStep(Runner runner, String url) {
    super(runner);
    this.mainPage = new MainPage(runner);
    this.url = url;
    this.setGetConsoleLog(false);
  }

  @Override
  protected void step() throws KiteTestException {
    mainPage.open(url);
  }

  @Override
  public String stepDescription() {
    return "Open URL";
  }
}

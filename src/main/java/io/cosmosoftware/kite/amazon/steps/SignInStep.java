package io.cosmosoftware.kite.amazon.steps;

import io.cosmosoftware.kite.amazon.pages.MainPage;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.TestStep;
import io.cosmosoftware.kite.usrmgmt.Account;

public class SignInStep extends TestStep {

  private final MainPage mainPage;
  private final Account account;

  public SignInStep(Runner runner, Account account) {
    super(runner);
    this.mainPage = new MainPage(runner);
    this.setGetConsoleLog(false);
    this.account = account;
  }

  @Override
  protected void step() throws KiteTestException {
    mainPage.sign_in(account);
  }

  @Override
  public String stepDescription() {
    return "Sign in";
  }
}

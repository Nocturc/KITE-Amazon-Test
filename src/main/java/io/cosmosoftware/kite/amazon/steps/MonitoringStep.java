package io.cosmosoftware.kite.amazon.steps;

import io.cosmosoftware.kite.amazon.checks.CheckAvailability;
import io.cosmosoftware.kite.amazon.pages.MainPage;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import io.cosmosoftware.kite.usrmgmt.Account;
import org.webrtc.kite.tests.TestRunner;


public class MonitoringStep  extends TestStep {
  private final MainPage mainPage;
  private final String url;
  private final Account account;
  private final String token;
  private final String chatId;
  public MonitoringStep(TestRunner runner, String url, Account account, String token, String chatId) {
    super(runner);
    this.mainPage = new MainPage(runner);
    this.setGetConsoleLog(false);
    this.url = url;
    this.account = account;
    this.token = token;
    this.chatId = chatId;
  }

  @Override
  protected void step() throws KiteTestException {
    openPage();
    signIn();
    while (true) {
      checkAvailability();
    }
  }

  @Override
  public String stepDescription() {
    return "Monitoring";
  }

  private void openPage() {
    OpenURLStep openUrlStep = new OpenURLStep(runner, url);
    openUrlStep.setReporter(this.reporter);
    openUrlStep.processTestStep(this.getStepPhase(), this.report, false);
  }

  private void signIn() {
    SignInStep signInStep = new SignInStep(runner, account);
    signInStep.setReporter(this.reporter);
    signInStep.processTestStep(this.getStepPhase(), this.report, false);
  }

  private void checkAvailability() {
    CheckAvailability checkAvailability = new CheckAvailability(runner, url, token, chatId);
    checkAvailability.setReporter(reporter);
    checkAvailability.processTestStep(this.getStepPhase(), this.report, false);
  }


}

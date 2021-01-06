package io.cosmosoftware.kite.amazon.tests;

import io.cosmosoftware.kite.amazon.steps.*;
import io.cosmosoftware.kite.report.Reporter;
import io.cosmosoftware.kite.usrmgmt.Account;
import org.webrtc.kite.tests.KiteBaseTest;
import org.webrtc.kite.tests.TestRunner;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class AmazonTest extends KiteBaseTest {
  private Account account;
  private String token;
  private String chatId;
  @Override
  protected void payloadHandling() {
    super.payloadHandling();
    retrieveCredentials();
    retrieveTelegramInfos();

    // One year in minutes
    this.setExpectedTestDuration(60*24*365);
  }

  @Override
  protected void populateTestSteps(TestRunner testRunner) {
    testRunner.addStep(new MonitoringStep(testRunner, this.url, this.account, token, chatId));
  }

  private void retrieveCredentials() {
    JsonObject credentials = payload.getJsonObject("credentials");
      account = new Account(
                      credentials.getString("email", null),
                      credentials.getString("email", null),
                      credentials.getString("password", null),
                      credentials.getString("email", null));
    }

  private void retrieveTelegramInfos() {
    JsonObject telegramInfos = payload.getJsonObject("telegram");
    token = telegramInfos.getString("token", null);
    chatId = telegramInfos.getString("chatId", null);
  }
}

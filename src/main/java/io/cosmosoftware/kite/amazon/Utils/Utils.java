package io.cosmosoftware.kite.amazon.Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Utils {
  public static void sendToTelegram(String text, String apiToken, String chatId) {
    String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
    urlString = String.format(urlString, apiToken, chatId, text.replace(" ", "%20"));

    try {
      URL url = new URL(urlString);
      URLConnection conn = url.openConnection();
      InputStream is = new BufferedInputStream(conn.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

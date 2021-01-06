package io.cosmosoftware.kite.amazon;

import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.KiteLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Coordinator {

  private final int participantCount;
  private String sessionURL;
  private KiteLogger logger;
  private HashMap<Runner, String> participantMap = new HashMap<>();
  private List<String> participants = new ArrayList<>();
  private HashMap<String, List<String>> peerConnectionIdMap = new HashMap<>();
  private JsonObject getStatsConfig;

  /**
   * Instantiates a new Coordinator.
   *
   * @param participantCount the participant count
   */
  public Coordinator(int participantCount) {
    this.participantCount = participantCount;
  }

  /**
   * Gets session url.
   *
   * @return the session url
   */
  public synchronized String getSessionURL() {
    return sessionURL;
  }

  /**
   * Sets session url.
   *
   * @param sessionURL the session url
   */
  public synchronized void setSessionURL(String sessionURL) {
    this.sessionURL = sessionURL;
  }

  /**
   * All participants connected boolean.
   *
   * @return the boolean
   */
  public synchronized boolean allParticipantsConnected() {
    return this.participants.size() == this.participantCount;
  }

  /**
   * Add participant.
   *
   * @param runner the runner
   * @param id     the id
   */
  public synchronized void addParticipant(Runner runner, String id) {
    this.participantMap.put(runner, id);
    this.participants.add(id);
  }

  /**
   * Gets participant id.
   *
   * @param runner the runner
   * @return the participant id
   */
  public synchronized String getParticipantId(Runner runner) {
    return this.participantMap.get(runner);
  }

  /**
   * Remove participant.
   *
   * @param runner the runner
   */
  public synchronized void removeParticipant(Runner runner) {
    String id = this.participantMap.get(runner);
    this.participantMap.remove(runner, id);
    this.participants.remove(id);
  }

  /**
   * All participants left boolean.
   *
   * @return the boolean
   */
  public boolean allParticipantsLeft() {
    return this.participants.size() == 1; // except for host
  }

  /**
   * Gets no of participants.
   *
   * @return the no of participants
   */
  public int getNoOfParticipants() {
    return this.participants.size();
  }

  /**
   * Add peer connection id.
   *
   * @param runnerId          the runner id
   * @param peerConnectionIds the peer connection ids
   */
  public void addPeerConnectionId(String runnerId, List<String> peerConnectionIds) {
    logger.debug("adding peer connection(s) for " + runnerId + " - " + peerConnectionIds);
    this.peerConnectionIdMap.put(runnerId, peerConnectionIds);
  }

  /**
   * Gets peer connection ids.
   *
   * @param runnerId the runner id
   * @return the peer connection ids
   */
  public synchronized List<String> getPeerConnectionIds(String runnerId) {
    return this.peerConnectionIdMap.get(runnerId);
  }

  /**
   * Gets stats config.
   *
   * @param runnerId the runner id
   * @return the stats config
   */
  public synchronized JsonObject getStatsConfig(String runnerId) {
    JsonObjectBuilder builder = Json.createObjectBuilder();
    for (String key: this.getStatsConfig.keySet()) {
      if (key.equals("peerConnections")) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String pcId: this.peerConnectionIdMap.get(runnerId)) {
          arrayBuilder.add("window.peerConnectionsById['" + pcId + "'][0].getPeerConnection()");
        }
        builder.add(key, arrayBuilder);
      } else {
        builder.add(key, this.getStatsConfig.get(key));
      }
    }
    return builder.build();
  }

  /**
   * Sets get stats config.
   *
   * @param getStatsConfig the get stats config
   */
  public void setGetStatsConfig(JsonObject getStatsConfig) {
    this.getStatsConfig = getStatsConfig;
  }

  public void setLogger(KiteLogger logger) {
    this.logger = logger;
  }
}

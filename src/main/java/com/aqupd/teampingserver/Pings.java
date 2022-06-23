package com.aqupd.teampingserver;

import com.google.gson.JsonObject;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressWarnings("InfiniteLoopStatement")
public class Pings {
  public static BlockingQueue<JsonObject> blockingQueue = new LinkedBlockingDeque<>();
  private static JsonObject currentPing = new JsonObject();

  public static void addPings(JsonObject ping) {
    blockingQueue.add(ping);
  }

  public static JsonObject getPing(){
    return currentPing;
  }

  public static class PingsCleaner extends Thread {
    PingsCleaner() { super(); }

    public void run() {
      try {
        do {
          if(blockingQueue.size() != 0) {
            currentPing = blockingQueue.take();
          }
          Thread.sleep(25);
        } while(true);

      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

package com.pivotal;

/**
 * Created by jhuynh on 6/30/17.
 */
public class StopLocatorAndServers {

  public static void main(String[] args) throws Exception {
    GemFireTest test = new GemFireTest();
    test.stopServer("server");
    test.stopServer("server2");
    test.stopLocator();
  }
}

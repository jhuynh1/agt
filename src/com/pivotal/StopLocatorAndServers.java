package com.pivotal;

/**
 * Created by jhuynh on 6/30/17.
 */
public class StopLocatorAndServers {

  public static void main(String[] args) throws Exception {
    GemFireTest test = new GemFireTest();
    test.stopServer("server1");
    test.stopServer("server2");
    test.stopServer("server3");
//    test.stopServer("server4");
//    test.stopServer("server5");
//    test.stopServer("server6");
    test.stopLocator();
  }
}

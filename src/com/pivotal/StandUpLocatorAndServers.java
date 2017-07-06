package com.pivotal;

/**
 * Created by jhuynh on 6/30/17.
 */
public class StandUpLocatorAndServers {

  public static void main(String[] args) throws Exception {
    GemFireTest test = new GemFireTest();
    test.startLocator();
    test.startServer("server1", 48888);
    test.startServer("server2", 48889);
    test.startServer("server3", 48890);
//    test.startServer("server4", 48891);
//    test.startServer("server5", 48892);
//    test.startServer("server6", 48893);

  }
}

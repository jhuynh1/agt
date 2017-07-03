package com.pivotal;

import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Created by jhuynh on 6/29/17.
 */
public class GemFireTest {

  private ClientCache cache;

  private static String GFSH_LOCATION = "/Users/jhuynh/Pivotal/gemfire/gemfire/open/geode-assembly/build/install/apache-geode/bin/";


  public GemFireTest() {

  }

//  public static void main(String[] args) throws Exception {
//    GemFireTest test = new GemFireTest();
//    test.startLocator();
//    test.createClientCache();
//    Region region = test.createClientRegion("region", ClientRegionShortcut.CACHING_PROXY);
//    test.registerInterest(region);
//    test.doClientPuts((i)-> region.put(i, ""));
//    test.stopServer();
//  }

  protected void createClientCache() {
    ClientCacheFactory ccf = new ClientCacheFactory();
    ccf.addPoolLocator("localhost", 10333);
    ccf.setPoolSubscriptionEnabled(true);
    cache = ccf.create();
  }

  protected Region createClientRegion(String regionName, ClientRegionShortcut regionShortcut) {
    return cache.createClientRegionFactory(regionShortcut).create(regionName);
  }

  protected Region createClientRegion(String regionName, ClientRegionShortcut regionShortcut, CacheListener listener) {
    return cache.createClientRegionFactory(regionShortcut).addCacheListener(listener).create(regionName);
  }

  protected void registerInterest(Region region) {
    region.registerInterest(".*");
  }

  protected void doClientPuts(int startIndex, int endIndex, IntConsumer action) {
    IntStream.range(startIndex, endIndex).forEach(i -> action.accept(i));
  }

  protected  void startLocator() throws Exception {
//    ServerLauncher serverLauncher  = new ServerLauncher.Builder()
//        .setMemberName("server1")
//        .setServerPort(40405)
//        .set("jmx-manager", "true")
//        .set("jmx-manager-start", "true")
//        .build();
//
//    ServerLauncher.ServerState start = serverLauncher.start();
//    cache = new CacheFactory().create();
    executeCommand(GFSH_LOCATION+ "gfsh start locator --name=locator --port=10333");

  }

  protected void startServer(String serverName, int serverPort) throws Exception {
    executeCommand(GFSH_LOCATION + "gfsh start server --name=" + serverName + " --locators=localhost[10333] --server-port=" + serverPort + " --cache-xml-file=./resources/cache.xml");

  }

  protected void stopServer(String dir) throws Exception {
    executeCommand(GFSH_LOCATION+ "gfsh stop server --dir=" + dir);
  }

  protected void stopLocator() throws Exception {
    executeCommand(GFSH_LOCATION+ "gfsh stop locator --dir=locator");
  }

  protected void executeCommand(String command) throws Exception {
    ProcessBuilder pb = new ProcessBuilder();
    Process pr = pb.command(command.split(" ")).start();
    pr.waitFor();
    InputStream is = pr.getInputStream();
    BufferedReader bis = new BufferedReader(new InputStreamReader(is));
    String line = bis.readLine();
    while (line != null) {
      System.out.println(line);
      line =  bis.readLine();
    }
  }


}

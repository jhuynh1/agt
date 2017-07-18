package com.pivotal;

import java.util.HashMap;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionShortcut;

/**
 * Created by jhuynh on 6/30/17.
 */
public class ClientPutter {

  private static int batchSize = 1000;

  public static void main(String[] args) throws Exception {
    GemFireTest test = new GemFireTest();
    test.createClientCache();
    Region region = test.createClientRegion("region", ClientRegionShortcut.PROXY);

    for (int i = 0; i < 2000; i++) {
      doPutAlls(region, i * batchSize, i * batchSize + batchSize);
    }
  }

  private static void doPutAlls(Region region, int startIndex, int endIndex) {
    System.out.println("Putting entries: " + startIndex + "-" + endIndex);
    HashMap map = new HashMap();
    for (int i = startIndex; i < endIndex; i++) {
      map.put(i, new Object[]{System.nanoTime(), new long[16]});
    }
    region.putAll(map);
  }
}
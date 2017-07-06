package com.pivotal;

import com.pivotal.GemFireTest;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionShortcut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Created by jhuynh on 6/30/17.
 */
public class ClientPutter {

  private static int batchSize = 1000;
  public static void main(String[] args) throws Exception {
    GemFireTest test = new GemFireTest();
    test.createClientCache();
    Region region = test.createClientRegion("region", ClientRegionShortcut.PROXY);
    //clearRegion(region);

//    test.doClientPuts(0, 1000, (i)-> {
//      System.out.println("S");
//      region.put(i, System.nanoTime());
//    });
    for (int i = 0; i < 2000; i++) {
      doPutAlls(region, i * batchSize, i * batchSize + batchSize);
    }
  }

  private static void clearRegion(Region region) {
    System.out.println("Destroying entries/clearing region");
    List keys = new LinkedList();

    IntStream.range(0, 2000000).forEach(i -> {
      if (i % 50000 == 0) {
        System.out.println("still creating keys list...");
        region.removeAll(keys);
        keys.clear();
      }
      keys.add(i);
    });
    System.out.println("Done destroying entries/clearing region");
  }

  private static void doPutAlls(Region region, int startIndex, int endIndex) {
    System.out.println("Putting entries" + startIndex + ":" + endIndex);
    HashMap map = new HashMap();
    for (int i = startIndex; i < endIndex; i++) {
      map.put(i, new Object[]{System.nanoTime(), new long[16]});
    }
    region.putAll(map);
  }
}
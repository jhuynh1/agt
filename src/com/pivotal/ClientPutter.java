package com.pivotal;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionShortcut;

/**
 * Created by jhuynh on 6/30/17.
 */
public class ClientPutter {

  public static void main(String[] args) throws Exception {
    GemFireTest test = new GemFireTest();
    test.createClientCache();
    Region region = test.createClientRegion("region", ClientRegionShortcut.CACHING_PROXY);
    test.doClientPuts(0, 1000, (i)-> region.put(i, System.nanoTime()));
  }
}

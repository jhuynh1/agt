package com.pivotal;

import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionEvent;
import org.apache.geode.cache.client.ClientRegionShortcut;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * Created by jhuynh on 6/30/17.
 */
public class ClientRegisterInterest {

  private static boolean batchOps = true;

  public static void main(String[] args) throws Exception {
    GemFireTest test = new GemFireTest();
    test.createClientCache();

    PutTimeListener listener = new PutTimeListener();

    Region region = test.createClientRegion("region", ClientRegionShortcut.PROXY, listener);
    region.registerInterestRegex(".*");

    IntStream.range(0,1000).forEach(i -> {
      final int batchSize = 100;
      doTest(test, region, listener, i * batchSize, i * batchSize + batchSize);
    });
    InputStreamReader isr = new InputStreamReader(System.in);
    isr.read();
  }

  private static void doTest(GemFireTest test, Region region, PutTimeListener listener, int startIndex, int endIndex) {
    listener.reset();
    if (!batchOps) {
      test.doClientPuts(startIndex, endIndex, (i) -> region.put(i, System.nanoTime()));
    }
    else {
      HashMap map = new HashMap();
      for (int i = startIndex; i < endIndex; i++) {
        map.put(i, System.nanoTime());
      }
      region.putAll(map);
    }
    while (listener.numOps < endIndex - startIndex) {
    }
    System.out.println("AVG DIFF:" + listener.getAverageDelta());
  }

  private static class PutTimeListener implements CacheListener {
    private long totalDiff = 0;
    private long numOps = 0;


    public PutTimeListener() {
    }

    public void reset() {
      totalDiff = 0;
      numOps = 0;
    }
    public long getTotalDiff() {
      return totalDiff;
    }

    public long getAverageDelta() {
      return totalDiff / numOps;
    }

      @Override
      public void afterCreate(final EntryEvent entryEvent) {
       // System.out.println("after create:" + entryEvent);
        long putTime = (Long) entryEvent.getNewValue();
        long currentTime = System.nanoTime();
        totalDiff += (currentTime - putTime);
        numOps++;
      }

      @Override
      public void afterUpdate(final EntryEvent entryEvent) {
        //System.out.println("after update:" + entryEvent);
      }

      @Override
      public void afterInvalidate(final EntryEvent entryEvent) {

      }

      @Override
      public void afterDestroy(final EntryEvent entryEvent) {

      }

      @Override
      public void afterRegionInvalidate(final RegionEvent regionEvent) {

      }

      @Override
      public void afterRegionDestroy(final RegionEvent regionEvent) {

      }

      @Override
      public void afterRegionClear(final RegionEvent regionEvent) {

      }

      @Override
      public void afterRegionCreate(final RegionEvent regionEvent) {

      }

      @Override
      public void afterRegionLive(final RegionEvent regionEvent) {

      }

      @Override
      public void close() {

      }
    };
}

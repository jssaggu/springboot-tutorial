package com.saggu.eshop.hashing;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ConsistentHash {

    static final int numberOfSites = 2;
    static final int totalTransactions = 100000;
    //SiteId, TId, SiteId given by Hash
    static Table<String, String, Long> siteIdByTidAndAnswer = HashBasedTable.create();

    public static void main(String[] args) {
        HashFunction hash = Hashing.sha256();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < totalTransactions; i++) {
            list.add(UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString());
        }

        for (int i = 1; i <= numberOfSites; i++) {
            System.out.println("Site " + i + " Start ------");
            print(i, list, hash);
            System.out.println("");
        }

        verify();
    }

    private static void verify() {
        System.out.println("Checking for Hashing issues...");
        Map<String, Map<String, Long>> siteIdToTrIdMap = siteIdByTidAndAnswer.rowMap();

        Multimap<String, Long> uniqueTrIdWithSite = HashMultimap.create();

        for (Map.Entry<String, Map<String, Long>> entry : siteIdToTrIdMap.entrySet()) {
            Map<String, Long> trIdSiteId = entry.getValue();
            trIdSiteId.forEach((k, v) -> uniqueTrIdWithSite.put(k, v));
        }

        final AtomicBoolean found = new AtomicBoolean(false);
        Map<String, Collection<Long>> stringCollectionMap = uniqueTrIdWithSite.asMap();
        stringCollectionMap.forEach((k, v) -> {
            if (v.size() > 1) {
                System.out.println("Problem with " + k);
                found.set(true);
            }
        });

        if (!found.get()) {
            System.out.println("  Result: There is no issue");
        }
    }

    public static void print(int siteId, List<String> list, HashFunction hash) {
        list.forEach(tId -> {
            long h = hash.hashString(tId, UTF_8).asLong();
            long i = Math.abs(h);
            long forSiteBitwise = (h & numberOfSites);
            long forSite = i % numberOfSites;
            System.out.println(numberOfSites + " TId '" + tId + "' is for site " + forSite + " Bit[" + forSiteBitwise + "]");
            siteIdByTidAndAnswer.put(siteId + "", tId, forSite);
        });
    }
}
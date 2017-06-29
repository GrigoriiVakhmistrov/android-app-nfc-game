package com.example.tzadmin.nfc_reader_writer.network;

import android.support.test.runner.AndroidJUnit4;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class NetworkManagerTest {
    @Test
    @Ignore//Use this test with care
    public void getShopsTest() throws Exception {
        final List<Shop> shopList = new ArrayList<>();
        NetworkManager.getShops(new Callback<List<Shop>>() {
            @Override
            public void receive(List<Shop> shops) {
                shopList.addAll(shops);
            }
        });
        Thread.sleep(2000);
        assertFalse(shopList.isEmpty());
    }
}
package com.example.tzadmin.nfc_reader_writer.network;

import com.example.tzadmin.nfc_reader_writer.Models.Event;
import com.example.tzadmin.nfc_reader_writer.Models.Group;
import com.example.tzadmin.nfc_reader_writer.Models.GroupActivity;
import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.Models.UserMorda;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONTest {
    @Test
    public void jsonTest() throws Exception {
        TestClass testClass = new TestClass("test", "asd");
        String json = JSON.toString(testClass);
        TestClass formJson = JSON.fromString(json, TestClass.class);
        assertEquals(testClass, formJson);
    }

    @Test
    public void shopSerializeDeserializeTest() throws Exception {
        Shop shop = new Shop();
        shop.id = 1;
        shop.price = 100;
        shop.pic = 2;
        shop.description = "test";
        shop.name = "ASD";
        String str = JSON.toString(shop);
        assertEquals(shop, JSON.fromString(str, Shop.class));
    }

    @Test
    public void eventSerializeDeserializeTest() throws Exception {
        Event event = new Event();
        event.id = 1;
        event.price = 2;
        event.description = "test";
        event.name = "asr";

        String json = JSON.toString(event);
        assertEquals(event, JSON.fromString(json, Event.class));
    }

    @Test
    public void mordaSerializeDeserializeTest() throws Exception {
        Morda morda = new Morda();
        morda.id = 1;
        morda.pic = "sdf";
        morda.fio = "test!";
        morda.description = "desc";

        String json = JSON.toString(morda);
        assertEquals(morda, JSON.fromString(json, Morda.class));
    }

    @Test
    public void groupSerializeDeserializeTest() throws Exception {
        Group group = new Group();
        group.id = 1;
        group.name = "test";
        group.description = "desc";
        group.vip = 2;
        group.price = 3;
        group.colorhex = "dsf";
        group.color = "sdfa";
        group.totemname = "tes";
        group.totemimage = "dafasdfsadffas";

        String json = JSON.toString(group);
        assertEquals(group, JSON.fromString(json, Group.class));
    }

    @Test
    public void routeSerializeDeserializeTest() throws Exception {
        Route route = new Route();
        route.price = 1;
        route.id = 2;
        route.capacity = 3;
        route.description = "4";
        route.name = "5";

        String json = JSON.toString(route);
        assertEquals(route, JSON.fromString(json, Route.class));
    }

    @Test
    public void userSerializeDeserializeTest() throws Exception {
        User user = new User();
        user.id = 1;
        user.routeid = 2;
        user.groupid = 3;
        user.rfcid = "dsfa";
        user.firstname = "test";
        user.lastname = "sdfdsfa";
        user.patronymic = "sdffdsdfsdfsdsffds";
        user.iscap = 123;

        String json = JSON.toString(user);
        assertEquals(user, JSON.fromString(json, User.class));
    }

    @Test
    public void moneyLogsSerializeDeserializeTest() throws Exception {
        MoneyLogs logs = new MoneyLogs();
        logs.description = "afsd";
        logs.type = "gfdggfddf";
        logs.money = 1;
        logs.userid = 3;
        logs.id = 2;

        String json = JSON.toString(logs);
        assertEquals(logs, JSON.fromString(json, MoneyLogs.class));
    }

    @Test
    public void groupActivitySerializeDeserializeTest() throws Exception {
        GroupActivity activity = new GroupActivity();
        activity.id = 1;
        activity.groupid = 2;
        activity.p1 = 3;
        activity.p2 = 4;
        activity.p3 = 5;
        activity.p4 = 6;

        String json = JSON.toString(activity);
        assertEquals(activity, JSON.fromString(json, GroupActivity.class));
    }

    @Test
    public void userMordaSerializeDeserializeTest() throws Exception {
        UserMorda morda = new UserMorda();
        morda.mordaid = 1;
        morda.userid = 2;
        morda.id = 3;

        String json = JSON.toString(morda);
        assertEquals(morda, JSON.fromString(json, UserMorda.class));
    }

    private static final class TestClass {
        private final String a;
        private final String b;

        TestClass(String a, String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof TestClass)) return false;

            TestClass testClass = (TestClass) o;

            if(a != null ? !a.equals(testClass.a) : testClass.a != null) return false;
            return b != null ? b.equals(testClass.b) : testClass.b == null;
        }

        @Override
        public int hashCode() {
            int result = a != null ? a.hashCode() : 0;
            result = 31 * result + (b != null ? b.hashCode() : 0);
            return result;
        }
    }
}
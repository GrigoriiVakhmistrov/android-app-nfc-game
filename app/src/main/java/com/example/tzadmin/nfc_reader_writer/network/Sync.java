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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by velor on 6/27/17.
 *
 *
 * У нс есть сл. таблицы для работы:
 *
 * Изменяемые:
 *
 * User, UserMorda, MoneyLog
 *
 * Все остальное нужно подтягивать
 *
 * 1- Добавить атрибут isnew
 * 2 - Синхронизировать только все пользователи совместно с его записями
 *      Либо что еще лучше просто синхронизировать сначало только пользователя, получить новый ID, заменить его в текущей базе
 *      затем заменить все записи соответствующие
 *
 *
 *
 * ЗАДАЧИ
 *
 * 1 - синхронизировать все записи со сл. моделей
 *
 *      GROUP; (А нужно ли......)
 *      GroupActivity (точно нужно)
 *      SHOP (Необходимо)
 *      Morda (Необходимо)
 */

@SuppressWarnings("Duplicates")
@Deprecated
public class Sync implements RequestDelegate {
    private static final String apiUrl = "http://194.67.194.82/sync/";

    private static final String shopURL = apiUrl + "get-shop";
    private static final String eventURL = apiUrl + "get-events";
    private static final String mordaURL = apiUrl + "get-morda";
    private static final String groupURL = apiUrl + "get-group";
    private static final String routeURL = apiUrl + "get-route";
    private static final String userURL = apiUrl + "get-user";
    private static final String moneyURL = apiUrl + "get-money";
    private static final String priorityURL = apiUrl + "get-priority";
    private static final String userMordaURL = apiUrl + "get-user-morda";

    private static final String addUserUrl = apiUrl + "add-user";
    private static final String addPriorityUrl = apiUrl + "add-priority";
    private static final String addMoneyUrl = apiUrl + "add-money";
    private static final String addUserMordaUrl = apiUrl + "add-user-morda";

    private static final String setUserUrl = apiUrl + "set-user";
    private static final String setPriorityUrl = apiUrl + "set-priority";
    private static final String setMoneyUrl = apiUrl + "set-money";
    private static final String setUserMordaUrl = apiUrl + "set-user-morda";


    /* стадия 1 -
        поиск и синхронизация таблиц пользователя
        1 - поиск таблицы пользователей и перелив их в основное хранилище
            с замещением айдишников
        2 - заливка всех остальных данных с привязкой к уже новому id

        Стадия 2

            Получение всего остального
     */
    private int stage = 0;

    public Sync() {
        stage1();
    }

    //Синхроним новых пользователей
    private void stage1() {
        stage = 1;
        User newUser = new User();
        newUser.syncFlag = 1;
        Collection<User> users = (Collection<User>) newUser.selectAllByParams();

        if (users.size() == 0) {
            stage2();
            return;
        }

        RequestNode[] nodes = new RequestNode[users.size()];

        int i = 0;
        for (User u : users) {
            RequestNode node = new RequestNode(addUserUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);

    }

    private void stage1Done(String url, String body, Object backParam) {
        User u = (User) backParam;

        Integer newId = Integer.getInteger(body, -1);

        if (!newId.equals(-1)) {

            Collection<UserMorda> mordas = u.getUserMordas();
            for (UserMorda m : mordas) {
                m.userid = newId;
                m.update(m.syncFlag.toString());
            }

            Collection<MoneyLogs> logs = u.getMoneyLog();
            for (MoneyLogs l : logs) {
                l.userid = newId;
                l.update(l.syncFlag.toString());
            }

            u.id = newId;
            u.update("0");

        }
    }

    //Синхроним измененных пользователей
    private void stage2() {
        stage = 2;
        User newUser = new User();
        newUser.syncFlag = 2;
        Collection<User> users = (Collection<User>) newUser.selectAllByParams();

        if (users.size() == 0) {
            stage3();
            return;
        }

        RequestNode[] nodes = new RequestNode[users.size()];

        int i = 0;
        for (User u : users) {
            RequestNode node = new RequestNode(setUserUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);

    }

    private void stage2Done(String url, String body, Object backParam) {
        User u = (User) backParam;

        Integer newId = Integer.getInteger(body, -1);

        if (!newId.equals(-1)) {

            if (!newId.equals(u.id)) {
                Collection<UserMorda> mordas = u.getUserMordas();
                for (UserMorda m : mordas) {
                    m.userid = newId;
                    m.update(m.syncFlag.toString());
                }

                Collection<MoneyLogs> logs = u.getMoneyLog();
                for (MoneyLogs l : logs) {
                    l.userid = newId;
                    l.update(l.syncFlag.toString());
                }

                u.id = newId;
            }
            u.update("0");

        }
    }

    //Синхроним добавленные морды
    private void stage3() {
        stage = 3;
        UserMorda newUserMorda = new UserMorda();
        newUserMorda.syncFlag = 1;
        Collection<UserMorda> users = (Collection<UserMorda>) newUserMorda.selectAllByParams();

        if (users.size() == 0) {
            stage4();
            return;
        }

        RequestNode[] nodes = new RequestNode[users.size()];

        int i = 0;
        for (UserMorda u : users) {
            RequestNode node = new RequestNode(addUserMordaUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);

    }

    private void stage3_4Done(String url, String body, Object backParam) {
        UserMorda u = (UserMorda) backParam;

        Integer newId = Integer.getInteger(body, -1);

        if (!newId.equals(-1)) {
            u.id = newId;
            u.update("0");
        }
    }


    //Синхроним обновленные морды
    private void stage4() {
        stage = 4;
        UserMorda newUserMorda = new UserMorda();
        newUserMorda.syncFlag = 2;
        Collection<UserMorda> users = (Collection<UserMorda>) newUserMorda.selectAllByParams();

        if (users.size() == 0) {
            stage5();
            return;
        }

        RequestNode[] nodes = new RequestNode[users.size()];

        int i = 0;
        for (UserMorda u : users) {
            RequestNode node = new RequestNode(setUserMordaUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);

    }

    //Синхроним добавленные money
    private void stage5() {
        stage = 5;
        MoneyLogs l = new MoneyLogs();
        l.syncFlag = 1;
        Collection<MoneyLogs> logs = (Collection<MoneyLogs>) l.selectAllByParams();

        if (logs.size() == 0) {
            stage6();
            return;
        }

        RequestNode[] nodes = new RequestNode[logs.size()];

        int i = 0;
        for (MoneyLogs u : logs) {
            RequestNode node = new RequestNode(addMoneyUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);
    }


    private void stage5_6Done(String url, String body, Object backParam) {
        MoneyLogs u = (MoneyLogs) backParam;

        Integer newId = Integer.getInteger(body, -1);

        if (!newId.equals(-1)) {
            u.id = newId;
            u.update("0");
        }
    }

    //Синхроним обновленные money
    private void stage6() {
        stage = 6;
        MoneyLogs l = new MoneyLogs();
        l.syncFlag = 2;
        Collection<MoneyLogs> logs = (Collection<MoneyLogs>) l.selectAllByParams();

        if (logs.size() == 0) {
            stage7();
            return;
        }

        RequestNode[] nodes = new RequestNode[logs.size()];

        int i = 0;
        for (MoneyLogs u : logs) {
            RequestNode node = new RequestNode(setMoneyUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);
    }

    private void stage7() {
        stage = 7;
        GroupActivity l = new GroupActivity();
        l.syncFlag = 1;
        Collection<GroupActivity> acivityes = (Collection<GroupActivity>) l.selectAllByParams();

        if (acivityes.size() == 0) {
            stage8();
            return;
        }

        RequestNode[] nodes = new RequestNode[acivityes.size()];

        int i = 0;
        for (GroupActivity u : acivityes) {
            RequestNode node = new RequestNode(addPriorityUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);
    }

    private void stage8() {
        stage = 8;
        GroupActivity l = new GroupActivity();
        l.syncFlag = 2;
        Collection<GroupActivity> acivityes = (Collection<GroupActivity>) l.selectAllByParams();

        if (acivityes.size() == 0) {
            stage9();
            return;
        }

        RequestNode[] nodes = new RequestNode[acivityes.size()];

        int i = 0;
        for (GroupActivity u : acivityes) {
            RequestNode node = new RequestNode(setPriorityUrl, RequestMethod.POST, u, u.getMap());
            nodes[i] = node;
            i++;
        }

        new HttpRequester(this, stage).execute(nodes);
    }

    private void stage7_8Done(String url, String body, Object backParam) {
        GroupActivity u = (GroupActivity) backParam;

        Integer newId = Integer.getInteger(body, -1);

        if (!newId.equals(-1)) {
            u.id = newId;
            u.update("0");
        }
    }

    private void stage9Done(String url, String body, Object backParam) {
        if (!body.startsWith("[")) return;

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(body).getAsJsonArray();

        if (url.equals(shopURL)) {
            List<Shop> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                Shop e = new Shop();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.name = o.getAsJsonObject().get("name").getAsString();
                e.description = o.getAsJsonObject().get("description").getAsString();
                e.pic = Integer.getInteger(o.getAsJsonObject().get("pic").getAsString(), -1);
                e.price = Integer.getInteger(o.getAsJsonObject().get("price").getAsString(), 0);

                objects.add(e);
            }
            new Shop().deleteAll();

            for (Shop i : objects)
                i.insert();
        } else if (url.equals(eventURL)) {
            List<Event> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                Event e = new Event();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.name = o.getAsJsonObject().get("name").getAsString();
                e.description = o.getAsJsonObject().get("description").getAsString();
                e.price = Integer.getInteger(o.getAsJsonObject().get("price").getAsString(), 0);

                objects.add(e);
            }
            new Event().deleteAll();

            for (Event i : objects)
                i.insert();
        } else if (url.equals(mordaURL)) {
            List<Morda> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                Morda e = new Morda();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.fio = o.getAsJsonObject().get("fio").getAsString();
                e.description = o.getAsJsonObject().get("description").getAsString();
                e.pic = o.getAsJsonObject().get("pic").getAsString();

                objects.add(e);
            }
            new Morda().deleteAll();

            for (Morda i : objects)
                i.insert();
        } else if (url.equals(groupURL)) {
            List<Group> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                Group e = new Group();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.name = o.getAsJsonObject().get("name").getAsString();
                e.description = o.getAsJsonObject().get("description").getAsString();
                e.totemname = o.getAsJsonObject().get("totemname").getAsString();
                e.totemimage = o.getAsJsonObject().get("totemimage").getAsString();
                e.color = o.getAsJsonObject().get("color").getAsString();
                e.colorhex = o.getAsJsonObject().get("colorhex").getAsString();
                e.price = Integer.getInteger(o.getAsJsonObject().get("price").getAsString(), 0);
                e.vip = Integer.getInteger(o.getAsJsonObject().get("vip").getAsString(), 0);

                objects.add(e);
            }
            new Group().deleteAll();

            for (Group i : objects)
                i.insert();
        } else if (url.equals(routeURL)) {
            List<Route> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                Route e = new Route();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.name = o.getAsJsonObject().get("name").getAsString();
                e.description = o.getAsJsonObject().get("description").getAsString();
                e.capacity = Integer.getInteger(o.getAsJsonObject().get("capacity").getAsString(), 0);
                e.price = Integer.getInteger(o.getAsJsonObject().get("price").getAsString(), 0);

                objects.add(e);
            }
            new Route().deleteAll();

            for (Route i : objects)
                i.insert();
        } else if (url.equals(userURL)) {
            List<User> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                User e = new User();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.firstname = o.getAsJsonObject().get("firstname").getAsString();
                e.lastname = o.getAsJsonObject().get("lastname").getAsString();
                e.patronymic = o.getAsJsonObject().get("patronymic").getAsString();
                e.rfcid = o.getAsJsonObject().get("rfcid").getAsString();
                if (!o.getAsJsonObject().get("groupid").isJsonNull())
                    e.groupid = Integer.getInteger(o.getAsJsonObject().get("groupid").getAsString(), -1);
                if (!o.getAsJsonObject().get("routeid").isJsonNull())
                    e.routeid = Integer.getInteger(o.getAsJsonObject().get("routeid").getAsString(), -1);
                e.iscap = o.getAsJsonObject().get("iscap").getAsInt();

                objects.add(e);
            }
            new User().deleteAll();

            for (User i : objects)
                i.insert();
        } else if (url.equals(moneyURL)) {
            List<MoneyLogs> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                MoneyLogs e = new MoneyLogs();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.userid = Integer.getInteger(o.getAsJsonObject().get("userid").getAsString(), -1);
                e.money = Integer.getInteger(o.getAsJsonObject().get("money").getAsString(), 0);
                e.type = o.getAsJsonObject().get("type").getAsString();
                e.description = o.getAsJsonObject().get("description").getAsString();

                objects.add(e);
            }
            new MoneyLogs().deleteAll();

            for (MoneyLogs i : objects)
                i.insert();
        } else if (url.equals(priorityURL)) {
            List<GroupActivity> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                GroupActivity e = new GroupActivity();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.groupid = Integer.getInteger(o.getAsJsonObject().get("groupid").getAsString(), -1);
                e.p1 = Integer.getInteger(o.getAsJsonObject().get("p1").getAsString(), 0);
                e.p2 = Integer.getInteger(o.getAsJsonObject().get("p2").getAsString(), 0);
                e.p3 = Integer.getInteger(o.getAsJsonObject().get("p3").getAsString(), 0);
                e.p4 = Integer.getInteger(o.getAsJsonObject().get("p4").getAsString(), 0);

                objects.add(e);
            }
            new GroupActivity().deleteAll();

            for (GroupActivity i : objects)
                i.insert();
        } else if (url.equals(userMordaURL)) {
            List<UserMorda> objects = new ArrayList<>();

            for (JsonElement o : array) {
                if (!o.isJsonObject()) continue;
                UserMorda e = new UserMorda();

                e.id = o.getAsJsonObject().get("id").getAsInt();
                e.userid = Integer.getInteger(o.getAsJsonObject().get("userid").getAsString(), -1);
                e.mordaid = Integer.getInteger(o.getAsJsonObject().get("mordaid").getAsString(), -1);

                objects.add(e);
            }
            new GroupActivity().deleteAll();

            for (UserMorda i : objects)
                i.insert();
        }
    }

    private void stage9() {
        stage = 9;

        RequestNode[] nodes = new RequestNode[9];

        nodes[0] = new RequestNode(shopURL, RequestMethod.GET);
        nodes[1] = new RequestNode(eventURL, RequestMethod.GET);
        nodes[2] = new RequestNode(mordaURL, RequestMethod.GET);
        nodes[3] = new RequestNode(groupURL, RequestMethod.GET);
        nodes[4] = new RequestNode(routeURL, RequestMethod.GET);
        nodes[5] = new RequestNode(userURL, RequestMethod.GET);
        nodes[6] = new RequestNode(moneyURL, RequestMethod.GET);
        nodes[7] = new RequestNode(priorityURL, RequestMethod.GET);
        nodes[8] = new RequestNode(userMordaURL, RequestMethod.GET);

        new HttpRequester(this, stage).execute(nodes);
    }


    @Override
    public void RequestDone(String url, String body, Object backParam, Integer stage) {
        if (stage.equals(1)) {
            stage1Done(url, body, backParam);
        } else if (stage.equals(2)) {
            stage2Done(url, body, backParam);
        } else if (stage.equals(3) || stage.equals(4)) {
            stage3_4Done(url, body, backParam);
        } else if (stage.equals(5) || stage.equals(6)) {
            stage5_6Done(url, body, backParam);
        } else if (stage.equals(7) || stage.equals(8)) {
            stage7_8Done(url, body, backParam);
        } else if (stage.equals(9)) {
            stage9Done(url, body, backParam);
        }
    }


    @Override
    public void TaskDone(Integer success) {
        if (success == 1) {
            stage2();
        } else if (success == 2) {
            stage3();
        } else if (success == 3) {
            stage4();
        } else if (success == 4) {
            stage5();
        } else if (success == 5) {
            stage6();
        } else if (success == 6) {
            stage7();
        } else if (success == 7) {
            stage8();
        } else if (success == 8) {
            stage9();
        }
    }

}

package com.example.tzadmin.nfc_reader_writer.NET;

import com.example.tzadmin.nfc_reader_writer.Models.Event;
import com.example.tzadmin.nfc_reader_writer.Models.Group;
import com.example.tzadmin.nfc_reader_writer.Models.GroupActivity;
import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.Models.UserMorda;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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

public class Sync implements RequestDelegate {

    private static final String shopURL = "http://194.67.194.82/sync/get-shop";
    private static final String eventURL = "http://194.67.194.82/sync/get-events";
    private static final String mordaURL = "http://194.67.194.82/sync/get-morda";
    private static final String groupURL = "http://194.67.194.82/sync/get-group";
    private static final String routeURL = "http://194.67.194.82/sync/get-route";
    private static final String userURL = "http://194.67.194.82/sync/get-user";
    private static final String moneyURL = "http://194.67.194.82/sync/get-money";
    private static final String priorityURL = "http://194.67.194.82/sync/get-priority";
    private static final String userMordaURL = "http://194.67.194.82/sync/get-user-morda";


    private static final String addUserUrl = "http://194.67.194.82/sync/add-user";
    private static final String addPriorityUrl = "http://194.67.194.82/sync/add-priority";
    private static final String addMoneyUrl = "http://194.67.194.82/sync/add-money";
    private static final String addUserMordaUrl = "http://194.67.194.82/sync/add-user-morda";

    private static final String setUserUrl = "http://194.67.194.82/sync/set-user";
    private static final String setPriorityUrl = "http://194.67.194.82/sync/set-priority";
    private static final String setMoneyUrl = "http://194.67.194.82/sync/set-money";
    private static final String setUserMordaUrl = "http://194.67.194.82/sync/set-user-morda";


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
        if (url.equals(shopURL)) {
            Gson gson = new Gson();

            Shop[] objects = gson.fromJson(body, Shop[].class);
            new Shop().deleteAll();

            for (Shop i : objects)
                i.insert();
        } else if (url.equals(eventURL)) {
            Gson gson = new Gson();

            Event[] objects = gson.fromJson(body, Event[].class);
            new Event().deleteAll();

            for (Event i : objects)
                i.insert();
        } else if (url.equals(mordaURL)) {
            Gson gson = new Gson();

            Morda[] objects = gson.fromJson(body, Morda[].class);
            new Morda().deleteAll();

            for (Morda i : objects)
                i.insert();
        } else if (url.equals(groupURL)) {
            Gson gson = new Gson();

            Group[] objects = gson.fromJson(body, Group[].class);
            new Group().deleteAll();

            for (Group i : objects)
                i.insert();
        } else if (url.equals(routeURL)) {
            Gson gson = new Gson();

            Route[] objects = gson.fromJson(body, Route[].class);
            new Route().deleteAll();

            for (Route i : objects)
                i.insert();
        } else if (url.equals(userURL)) {
            Gson gson = new Gson();

            User[] objects = gson.fromJson(body, User[].class);
            new User().deleteAll();

            for (User i : objects)
                i.insert();
        } else if (url.equals(moneyURL)) {
            Gson gson = new Gson();

            MoneyLogs[] objects = gson.fromJson(body, MoneyLogs[].class);
            new MoneyLogs().deleteAll();

            for (MoneyLogs i : objects)
                i.insert();
        } else if (url.equals(priorityURL)) {
            Gson gson = new Gson();

            GroupActivity[] objects = gson.fromJson(body, GroupActivity[].class);
            new GroupActivity().deleteAll();

            for (GroupActivity i : objects)
                i.insert();
        } else if (url.equals(userMordaURL)) {
            Gson gson = new Gson();

            UserMorda[] objects = gson.fromJson(body, UserMorda[].class);
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

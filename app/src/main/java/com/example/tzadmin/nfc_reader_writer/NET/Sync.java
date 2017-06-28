package com.example.tzadmin.nfc_reader_writer.NET;

import com.example.tzadmin.nfc_reader_writer.Models.User;

import java.util.Collection;

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

//    public Sync() {
//        HttpRequester req = new HttpRequester();
//
//
//        Collection<User> users = (Collection<User>) new User().selectAll();
//
//        RequestNode[] nodes = new RequestNode[users.size()];
//
//        int i = 0;
//        for (User u : users) {
//            //RequestNode node = new RequestNode(setUserUrl, RequestMethod.POST, u, u.getMap());
//            i++;
//        }
//    }

    @Override
    public void RequestDone(String url, Object result) {

    }

    @Override
    public void TaskDone(Boolean success) {

    }

}

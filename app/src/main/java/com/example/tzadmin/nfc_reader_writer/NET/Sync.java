package com.example.tzadmin.nfc_reader_writer.NET;

import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Database.Event;
import com.example.tzadmin.nfc_reader_writer.Database.GroupActivity;
import com.example.tzadmin.nfc_reader_writer.Database.Group;
import com.example.tzadmin.nfc_reader_writer.Database.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Database.Morda;
import com.example.tzadmin.nfc_reader_writer.Database.Route;
import com.example.tzadmin.nfc_reader_writer.Database.Shop;
import com.example.tzadmin.nfc_reader_writer.Database.UserMorda;
import com.example.tzadmin.nfc_reader_writer.Database.User;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.SharedApplication;
import com.example.tzadmin.nfc_reader_writer.Utilites.Utilites;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by velor on 6/27/17.
 *
 *
 * У нс есть сл. таблицы для работы:
 *
 * Изменяемые:https://github.com/barbeu/android-app-nfc-game
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

public class Sync {

    private boolean isAutoSync = false;

    private static final String shopURL = "http://193.124.191.37/sync/get-shop";
    private static final String eventURL = "http://193.124.191.37/sync/get-events";
    private static final String mordaURL = "http://193.124.191.37/sync/get-morda";
    private static final String groupURL = "http://193.124.191.37/sync/get-group";
    private static final String routeURL = "http://193.124.191.37/sync/get-route";
    private static final String userURL = "http://193.124.191.37/sync/get-user";
    private static final String moneyURL = "http://193.124.191.37/sync/get-money";
    private static final String priorityURL = "http://193.124.191.37/sync/get-priority";
    private static final String userMordaURL = "http://193.124.191.37/sync/get-user-morda";


    private static final String addUserUrl = "http://193.124.191.37/sync/add-user";
    private static final String addPriorityUrl = "http://193.124.191.37/sync/add-priority";
    private static final String addMoneyUrl = "http://193.124.191.37/sync/add-money";
    private static final String addUserMordaUrl = "http://193.124.191.37/sync/add-user-morda";

    private static final String setUserUrl = "http://193.124.191.37/sync/set-user";
    private static final String setPriorityUrl = "http://193.124.191.37/sync/set-priority";
    private static final String setMoneyUrl = "http://193.124.191.37/sync/set-money";
    private static final String setUserMordaUrl = "http://193.124.191.37/sync/set-user-morda";


    /* стадия 1 -
        поиск и синхронизация таблиц пользователя
        1 - поиск таблицы пользователей и перелив их в основное хранилище
            с замещением айдишников
        2 - заливка всех остальных данных с привязкой к уже новому id

        Стадия 2

            Получение всего остального
     */
    private int stage = 0;

    /**
     * true - полная
     * false - частичная
     */
    private boolean isFullSync;

    public Sync() {
        isFullSync = true;
        if (!SharedApplication.get().syncActive) {
            SharedApplication.get().syncActive = true;
            //stage1(); TODO start sync
        }
    }

    public Sync(boolean isAutoSync) {
        isFullSync = false;
        if (!SharedApplication.get().syncActive) {
            SharedApplication.get().syncActive = true;
            this.isAutoSync = isAutoSync;
            //stage1(); TODO start sync
        }
    }
    public Sync(boolean isAutoSync, boolean isFullSync) {
        this.isFullSync = isFullSync;
        if (!SharedApplication.get().syncActive) {
            SharedApplication.get().syncActive = true;
            this.isAutoSync = isAutoSync;
            //stage1(); TODO start sync
        }
    }
}

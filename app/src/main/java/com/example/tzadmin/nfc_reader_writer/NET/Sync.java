package com.example.tzadmin.nfc_reader_writer.NET;

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

    private static final String ShopURL = "";
    private static final String eventURL = "";
    private static final String mordaURL = "";
    private static final String groupURL = "";
    private static final String routeURL = "";

    /* стадия 1 -
        поиск и синхронизация таблиц пользователя
        1 - поиск таблицы пользователей и перелив их в основное хранилище
            с замещением айдишников
        2 - заливка всех остальных данных с привязкой к уже новому id

        Стадия 2

            Получение всего остального
     */
    private int stage = 0;

    public Sync () {
        if (stage == 0) {
            
        }
    }





    @Override
    public void RequestDone(String url, Object result) {

    }

    @Override
    public void TaskDone(Boolean success) {

    }

}

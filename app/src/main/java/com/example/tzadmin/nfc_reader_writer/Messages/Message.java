package com.example.tzadmin.nfc_reader_writer.Messages;

import com.example.tzadmin.nfc_reader_writer.Models.Group;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.User;

/**
 * Created by forz on 14.06.17.
 */

public class Message {
    public static final String BRACER_ALREADY_EXIST = "Ошибка. Браслет уже зарегистрирован";
    public static final String FIELDS_NOT_FILLED = "Ошибка. Не все поля заполнены";
    public static final String DUBLICATE_USER = "Ошибка. Пользователь уже существует";
    public static final String USER_THIS_BRACER_NOT_FOUND = "Ошибка. Пользователь с таким браслетом не зарегистрирован";
    public static final String CLEAR_RFCID_SUCCESSFULLY = "Браслет успешно сброшен";
    public static final String DEVICE_NOT_FOUND_NFC_ADAPTHER = "Устройство не поддерживает NFC сканер браслета, либо NFC сканер вылючен";
    public static final String SUCCESSFULLY = "Успешно";
    public static final String REGISTER_ERROR_SUB_ALREADY = "Ошибка. Пользователь уже подписан на маршрут";
    public static final String MONEY_LOW = "Ошибка. Не достаточно средств";
    public static final String ACTIVITYS_NOT_FOUND = "Ошибка. Список активностей пуст";
    public static final String ITEMS_SHOP_NOT_FOUND = "Ошибка. Товаров в магазине нет";
    public static final String NOT_ALL_DATA_IS_SELECTED = "Не все данные выбраны";
    public static final String USER_NOT_SUBSCRUBE_TO_ROUTE = "Пользователь не был подписан на маршрут";
    public static final String ERROR_URL_IMAGE = "Ошибка URL картинки спикера, обратитесь к администратору";
    public static final String USER_NOT_SUBSCRUBE_TO_SPIKER = "Пользователь небыл подписан на семинар";
    public static final String SYNC_OK = "Синхронизация закончена";
    public static final String GROUP_NO_THROW_SELECTED = "Группа для броска не выбрана";
    public static final String NO_CLAN = "Не состоит в клане";
    public static final String NO_ROUTE = "Маршрут: Нет маршрута";
    public static final String NO_SPIKER = "Спикер: Не подписан";

    public static String getUserAlreadySubscrubeSpiker (Morda morda) {
        return "Пользователь уже подписан на спикера \"" + morda.fio + "\"";
    }

    public static String getUserAlreadySubscrubeClan (Group group) {
        return "Пользователь уже состоит в команде \"" + group.totemname + "\"";
    }

    public static String userVisitRoute(String routeName) {
        return "Маршрут - " + routeName;
    }

    public static String userVisitSpiker(String spikerName) {
        return "Семинар - " + spikerName;
    }

    public static String isUserHaveBraced(User user) {
        return "У пользователя: " + concatFio(user) + " - уже привязан браслет";
    }

    public static String userSuccessfullyRegistered(User user) {
        return "Пользователь " + concatFio(user) + " успешно зарегистрирован";
    }

    public static String concatFio(User user) {
        return user.lastname + " " + user.firstname + " " + user.patronymic;
    }
}

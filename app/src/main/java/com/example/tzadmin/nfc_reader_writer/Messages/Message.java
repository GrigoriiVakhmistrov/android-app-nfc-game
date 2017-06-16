package com.example.tzadmin.nfc_reader_writer.Messages;

import com.example.tzadmin.nfc_reader_writer.Models.User;

/**
 * Created by forz on 14.06.17.
 */

public class Message {

    public static final String BRACER_ALREADY_EXIST = "Ошибка. Браслет уже зарегистрирован";
    public static final String FIELDS_NOT_FILLED = "Ошибка. Не все поля заполнены";
    public static final String USER_THIS_BRACER_NOT_FOUND = "Ошибка. Пользователь с таким браслетом не зарегистрирован";
    public static final String CLEAR_RFCID_SUCCESSFULLY = "Браслет успешно сброшен";
    public static final String DEVICE_NOT_FOUND_NFC_ADAPTHER = "Данное устройство не поддерживает NFC сканер браслета";

    public static String USER_IS_HAVE_BRACER (User user) {
        return "У пользователя " + concatFio(user) + " уже привязан браслет";
    }

    public static String USER_SUCCESSFULLY_REGISTERED (User user) {
        return "Пользователь " + concatFio(user) + " успешно зарегистрирован";
    }

    public static String concatFio (User user) {
        return user.cFirstName + " " + user.cLastName + " " + user.cSurname;
    }
}

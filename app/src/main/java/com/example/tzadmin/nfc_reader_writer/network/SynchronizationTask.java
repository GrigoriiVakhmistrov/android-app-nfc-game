package com.example.tzadmin.nfc_reader_writer.network;

import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.Models.UserMorda;
import com.example.tzadmin.nfc_reader_writer.util.FutureWithParam;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.util.Collection;

//TODO check null problems for all methods
public final class SynchronizationTask implements Runnable {
    @Override
    public void run() {
        syncNewUsers();
    }

    private void syncNewUsers() {
        User newUser = new User();
        newUser.syncFlag = 1;
        Collection<User> users = (Collection<User>) newUser.selectAllByParams();

        if (users.size() == 0) {
//            stage2();
            return;
        }

        for (User u : users) {
            NetworkManager.addUser(u).setCallback(FirstStageCallback.INSTANCE);
        }
    }

    @SuppressWarnings("Duplicates")
    private static final class FirstStageCallback implements FutureWithParam.Callback<AsyncHttpResponse, User> {
        private static final FirstStageCallback INSTANCE = new FirstStageCallback();

        private FirstStageCallback() {}
        @Override
        public void completed(Exception e, AsyncHttpResponse result, User param) {
            Integer newId = Integer.getInteger(result.message(), -1);

            if (!newId.equals(-1)) {

                Collection<UserMorda> mordas = param.getUserMordas();
                for (UserMorda m : mordas) {
                    m.userid = newId;
                    m.update(m.syncFlag.toString());
                }

                Collection<MoneyLogs> logs = param.getMoneyLog();
                for (MoneyLogs l : logs) {
                    l.userid = newId;
                    l.update(l.syncFlag.toString());
                }

                param.id = newId;
                param.update("0");

            }
        }
    }
}

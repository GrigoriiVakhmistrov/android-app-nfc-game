package com.example.tzadmin.nfc_reader_writer.network;

import android.util.Log;
import com.example.tzadmin.nfc_reader_writer.Models.BaseModel;
import com.example.tzadmin.nfc_reader_writer.Models.GroupActivity;
import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.Models.UserMorda;
import com.example.tzadmin.nfc_reader_writer.Utils;
import com.example.tzadmin.nfc_reader_writer.util.FutureWithParam;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.util.Collection;
import java.util.List;

//TODO check null problems for all methods
public final class SynchronizationTask implements Runnable {
    @Override
    public void run() {
        syncNewUsers();
        syncChangedUsers();

        syncAddedMordas();
        syncUpdatedMordas();

        syncAddedMoney();
        syncUpdatedMoney();

        syncAddedActivities();
        syncUpdatedActivities();

        syncAllOther();
    }

    private void syncNewUsers() {
        User newUser = new User();
        newUser.syncFlag = 1;
        Collection<User> users = (Collection<User>) newUser.selectAllByParams();

        if (users.size() == 0)
            return;

        for (User u : users) {
            NetworkManager.addUser(u).setCallback(FirstStageCallback.INSTANCE);
        }
    }

    private void syncChangedUsers() {
        User newUser = new User();
        newUser.syncFlag = 2;
        Collection<User> users = (Collection<User>) newUser.selectAllByParams();

        if (users.size() == 0)
            return;

        for (User u : users) {
            FutureWithParam<AsyncHttpResponse, User> asyncHttpResponseUserFutureWithParam = NetworkManager.setUser(u);
            asyncHttpResponseUserFutureWithParam.setCallback(SecondStageCallback.INSTANCE);
            FutureWithParam.addFutureToCheck(asyncHttpResponseUserFutureWithParam);
        }
    }

    private void syncAddedMordas() {
        UserMorda newUserMorda = new UserMorda();
        newUserMorda.syncFlag = 1;
        Collection<UserMorda> users = (Collection<UserMorda>) newUserMorda.selectAllByParams();

        if (users.size() == 0)
            return;

        for (UserMorda u : users) {
            FutureWithParam<AsyncHttpResponse, UserMorda> asyncHttpResponseUserMordaFutureWithParam = NetworkManager.addUserMorda(u);
            asyncHttpResponseUserMordaFutureWithParam.setCallback(ThirdAndFourthStageCallback.INSTANCE);
            FutureWithParam.addFutureToCheck(asyncHttpResponseUserMordaFutureWithParam);
        }
    }

    private void syncUpdatedMordas() {
        UserMorda newUserMorda = new UserMorda();
        newUserMorda.syncFlag = 2;
        Collection<UserMorda> users = (Collection<UserMorda>) newUserMorda.selectAllByParams();

        if (users.size() == 0)
            return;


        for (UserMorda u : users) {
            FutureWithParam<AsyncHttpResponse, UserMorda> asyncHttpResponseUserMordaFutureWithParam = NetworkManager.setUserMorda(u);
            asyncHttpResponseUserMordaFutureWithParam.setCallback(ThirdAndFourthStageCallback.INSTANCE);
            FutureWithParam.addFutureToCheck(asyncHttpResponseUserMordaFutureWithParam);
        }
    }

    private void syncAddedMoney() {
        MoneyLogs l = new MoneyLogs();
        l.syncFlag = 1;
        Collection<MoneyLogs> logs = (Collection<MoneyLogs>) l.selectAllByParams();

        if (logs.size() == 0)
            return;


        for (MoneyLogs u : logs) {
            FutureWithParam<AsyncHttpResponse, MoneyLogs> asyncHttpResponseMoneyLogsFutureWithParam = NetworkManager.addMoney(u);
            asyncHttpResponseMoneyLogsFutureWithParam.setCallback(FifthAndSixthStageCallback.INSTANCE);
            FutureWithParam.addFutureToCheck(asyncHttpResponseMoneyLogsFutureWithParam);
        }
    }

    private void syncUpdatedMoney() {
        MoneyLogs l = new MoneyLogs();
        l.syncFlag = 2;
        Collection<MoneyLogs> logs = (Collection<MoneyLogs>) l.selectAllByParams();

        if (logs.size() == 0)
            return;


        for (MoneyLogs u : logs) {
            FutureWithParam<AsyncHttpResponse, MoneyLogs> asyncHttpResponseMoneyLogsFutureWithParam = NetworkManager.setMoney(u);
            asyncHttpResponseMoneyLogsFutureWithParam.setCallback(FifthAndSixthStageCallback.INSTANCE);
            FutureWithParam.addFutureToCheck(asyncHttpResponseMoneyLogsFutureWithParam);
        }
    }

    private void syncAddedActivities() {
        GroupActivity l = new GroupActivity();
        l.syncFlag = 1;
        Collection<GroupActivity> activities = (Collection<GroupActivity>) l.selectAllByParams();

        if (activities.size() == 0)
            return;

        for (GroupActivity u : activities) {
            FutureWithParam<AsyncHttpResponse, GroupActivity> asyncHttpResponseGroupActivityFutureWithParam = NetworkManager.addPriority(u);
            asyncHttpResponseGroupActivityFutureWithParam.setCallback(SeventhAndEightStageCallback.INSTANCE);
            FutureWithParam.addFutureToCheck(asyncHttpResponseGroupActivityFutureWithParam);
        }
    }

    private void syncUpdatedActivities() {
        GroupActivity l = new GroupActivity();
        l.syncFlag = 2;
        Collection<GroupActivity> activities = (Collection<GroupActivity>) l.selectAllByParams();

        if (activities.size() == 0)
            return;

        for (GroupActivity u : activities) {
            FutureWithParam<AsyncHttpResponse, GroupActivity> asyncHttpResponseGroupActivityFutureWithParam = NetworkManager.setPriority(u);
            asyncHttpResponseGroupActivityFutureWithParam.setCallback(SeventhAndEightStageCallback.INSTANCE);
            FutureWithParam.addFutureToCheck(asyncHttpResponseGroupActivityFutureWithParam);
        }
    }

    private void syncAllOther() {
        NetworkManager.getShops(FinalStageCallback.INSTANCE);
        NetworkManager.getEvents(FinalStageCallback.INSTANCE);
        NetworkManager.getMordas(FinalStageCallback.INSTANCE);
        NetworkManager.getGroups(FinalStageCallback.INSTANCE);
        NetworkManager.getRoutes(FinalStageCallback.INSTANCE);
        NetworkManager.getUsers(FinalStageCallback.INSTANCE);
        NetworkManager.getMoney(FinalStageCallback.INSTANCE);
        NetworkManager.getPriority(FinalStageCallback.INSTANCE);
        NetworkManager.getUserMordas(FinalStageCallback.INSTANCE);
    }

    private static final class FirstStageCallback implements FutureWithParam.Callback<AsyncHttpResponse, User> {
        private static final FirstStageCallback INSTANCE = new FirstStageCallback();

        private FirstStageCallback() {}
        @Override
        public void completed(Exception e, AsyncHttpResponse result, User param) {
            Integer newId = Utils.parseIntDefault(result.message(), 10, -1);
            if(newId.equals(-1))
                return;

            Log.d("[Sync][New]", "Synchronized user with id " + param.id);

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

    private static final class SecondStageCallback implements FutureWithParam.Callback<AsyncHttpResponse, User> {
        private static final SecondStageCallback INSTANCE = new SecondStageCallback();

        @Override
        public void completed(Exception e, AsyncHttpResponse result, User param) {
            Integer newId = Utils.parseIntDefault(result.message(), 10, -1);
            if(newId.equals(-1))
                return;

            Log.d("[Sync][Change]", "Synchronized user with id " + param.id);

            if (!newId.equals(param.id)) {
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
            }
            param.update("0");

        }
    }

    private static final class ThirdAndFourthStageCallback implements FutureWithParam.Callback<AsyncHttpResponse, UserMorda> {
        private static final ThirdAndFourthStageCallback INSTANCE = new ThirdAndFourthStageCallback();

        @Override
        public void completed(Exception e, AsyncHttpResponse result, UserMorda param) {
            Integer newId = Utils.parseIntDefault(result.message(), 10, -1);

            if (!newId.equals(-1)) {
                param.id = newId;
                param.update("0");
            }

            Log.d("[Sync]", "Synchronized morda with id " + param.id);
        }
    }

    private static final class FifthAndSixthStageCallback implements FutureWithParam.Callback<AsyncHttpResponse, MoneyLogs> {
        private static final FifthAndSixthStageCallback INSTANCE = new FifthAndSixthStageCallback();

        @Override
        public void completed(Exception e, AsyncHttpResponse result, MoneyLogs param) {
            Integer newId = Utils.parseIntDefault(result.message(), 10, -1);

            if (!newId.equals(-1)) {
                param.id = newId;
                param.update("0");
            }

            Log.d("[Sync]", "Synchronized moneyLog with id " + param.id);
        }
    }

    private static final class SeventhAndEightStageCallback implements FutureWithParam.Callback<AsyncHttpResponse, GroupActivity> {
        private static final SeventhAndEightStageCallback INSTANCE = new SeventhAndEightStageCallback();

        @Override
        public void completed(Exception e, AsyncHttpResponse result, GroupActivity param) {
            Integer newId = Utils.parseIntDefault(result.message(), 10, -1);

            if (!newId.equals(-1)) {
                param.id = newId;
                param.update("0");
            }

            Log.d("[Sync]", "Synchronized activity with id " + param.id);
        }
    }

    private static final class FinalStageCallback implements Callback<List<BaseModel>> {
        private static final Callback<List<BaseModel>> INSTANCE = new FinalStageCallback();

        @Override
        public void receive(List<BaseModel> baseModels) {
            if(baseModels.isEmpty()) //Can't get any base model
                return;

            BaseModel first = baseModels.get(0);
            first.deleteAll();

            for(BaseModel baseModel : baseModels) {
                baseModel.insert();
            }

            Log.d("[Sync]", "Synchronized class " + first.getClass().getCanonicalName());
        }
    }
}

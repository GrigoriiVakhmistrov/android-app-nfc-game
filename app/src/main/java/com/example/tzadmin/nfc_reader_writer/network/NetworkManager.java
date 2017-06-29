package com.example.tzadmin.nfc_reader_writer.network;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.tzadmin.nfc_reader_writer.Models.BaseModel;
import com.example.tzadmin.nfc_reader_writer.Models.Event;
import com.example.tzadmin.nfc_reader_writer.Models.Group;
import com.example.tzadmin.nfc_reader_writer.Models.GroupActivity;
import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.Models.UserMorda;
import com.example.tzadmin.nfc_reader_writer.util.FutureWithParam;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This class send requests to server and return callbacks.
 * If you want to use getters, you need to pass wanted type as T (<code>T extends {@link BaseModel}</code>):<br>
 * <pre>
 *     {@code NetworkManager.<Shop>getShops(Callback())}
 * </pre>
 *
 */
//TODO move server api to json
public final class NetworkManager {
    private static final CopyOnWriteArraySet<ErrorHandler> errorHandlers = new CopyOnWriteArraySet<>();

    private static final Type shopListType = new TypeToken<List<Shop>>(){}.getType();
    private static final Type eventListType = new TypeToken<List<Event>>(){}.getType();
    private static final Type mordaListType = new TypeToken<List<Morda>>(){}.getType();
    private static final Type groupListType = new TypeToken<List<Group>>(){}.getType();
    private static final Type routeListType = new TypeToken<List<Route>>(){}.getType();
    private static final Type userListType = new TypeToken<List<User>>(){}.getType();
    private static final Type moneyListType = new TypeToken<List<MoneyLogs>>(){}.getType();
    private static final Type priorityListType = new TypeToken<List<GroupActivity>>(){}.getType();
    private static final Type userMordaListType = new TypeToken<List<UserMorda>>(){}.getType();

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

    private NetworkManager() {
        throw new UnsupportedOperationException();
    }

    public static void addErrorHandler(ErrorHandler handler) {
        errorHandlers.add(handler);
    }

    public static void removeErrorhandler(ErrorHandler handler) {
        errorHandlers.remove(handler);
    }

    static <T extends BaseModel> void getShops(Callback<List<T>> callback) {
        RequestManager.get(shopURL, new JsonParseCallback<>(callback, shopListType));
    }

    public static <T extends BaseModel> void getEvents(Callback<List<T>> callback) {
        RequestManager.get(eventURL, new JsonParseCallback<>(callback, eventListType));
    }

    public static <T extends BaseModel> void getMordas(Callback<List<T>> callback) {
        RequestManager.get(mordaURL, new JsonParseCallback<>(callback, mordaListType));
    }

    public static <T extends BaseModel> void getGroups(Callback<List<T>> callback) {
        RequestManager.get(groupURL, new JsonParseCallback<>(callback, groupListType));
    }

    public static <T extends BaseModel> void getRoutes(Callback<List<T>> callback) {
        RequestManager.get(routeURL, new JsonParseCallback<>(callback, routeListType));
    }

    public static <T extends BaseModel> void getUsers(Callback<List<T>> callback) {
        RequestManager.get(userURL, new JsonParseCallback<>(callback, userListType));
    }

    public static <T extends BaseModel> void getMoney(Callback<List<T>> callback) {
        RequestManager.get(moneyURL, new JsonParseCallback<>(callback, moneyListType));
    }

    public static <T extends BaseModel> void getPriority(Callback<List<T>> callback) {
        RequestManager.get(priorityURL, new JsonParseCallback<>(callback, priorityListType));
    }

    public static <T extends BaseModel> void getUserMordas(Callback<List<T>> callback) {
        RequestManager.get(userMordaURL, new JsonParseCallback<>(callback, userMordaListType));
    }

    public static FutureWithParam<AsyncHttpResponse, User> addUser(User user) {
        String params = buildParams(user.getMap());
        return FutureWithParam.fromCache(RequestManager.post(addUserUrl.concat(params)), user);
    }

    public static FutureWithParam<AsyncHttpResponse, GroupActivity> addPriority(GroupActivity activity) {
        String params = buildParams(activity.getMap());
        return FutureWithParam.fromCache(RequestManager.post(addPriorityUrl.concat(params)), activity);
    }

    public static FutureWithParam<AsyncHttpResponse, MoneyLogs> addMoney(MoneyLogs money) {
        String params = buildParams(money.getMap());
        return FutureWithParam.fromCache(RequestManager.post(addMoneyUrl.concat(params)), money);
    }

    public static FutureWithParam<AsyncHttpResponse, UserMorda> addUserMorda(UserMorda morda) {
        String params = buildParams(morda.getMap());
        return FutureWithParam.fromCache(RequestManager.post(addUserMordaUrl.concat(params)), morda);
    }

    public static FutureWithParam<AsyncHttpResponse, User> setUser(User user) {
        String params = buildParams(user.getMap());
        return FutureWithParam.fromCache(RequestManager.post(setUserUrl.concat(params)), user);
    }

    public static FutureWithParam<AsyncHttpResponse, GroupActivity> setPriority(GroupActivity activity) {
        String params = buildParams(activity.getMap());
        return FutureWithParam.fromCache(RequestManager.post(setPriorityUrl.concat(params)), activity);
    }

    public static FutureWithParam<AsyncHttpResponse, MoneyLogs> setMoney(MoneyLogs money) {
        String params = buildParams(money.getMap());
        return FutureWithParam.fromCache(RequestManager.post(setMoneyUrl.concat(params)), money);
    }

    public static FutureWithParam<AsyncHttpResponse, UserMorda> setUserMorda(UserMorda morda) {
        String params = buildParams(morda.getMap());
        return FutureWithParam.fromCache(RequestManager.post(setUserMordaUrl.concat(params)), morda);
    }

    @NonNull
    static String buildParams(Map<String, String> map) {
        StringBuilder params = new StringBuilder("?");
        boolean notFirst = false;
        for(Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            if(notFirst) {
                params.append('&');
            } else {
                notFirst = true;
            }

            params.append(stringStringEntry.getKey());
            params.append('=');
            params.append(stringStringEntry.getValue());
        }
        return params.toString();
    }

    private static void reportError(int code, AsyncHttpResponse response) {
        for(ErrorHandler errorHandler : errorHandlers) {
            try {
                errorHandler.handleHttpError(code, response);
            } catch (Exception e) {
                Log.e("[NetworkManager]", "Detected exception in ErrorHandler!");
                e.printStackTrace();
            }
        }
    }

    private static final class JsonParseCallback<T> extends AsyncHttpClient.StringCallback {
        private final Callback<T> callback;
        private final Type cast;

        public JsonParseCallback(Callback<T> callback, Type cast) {
            this.callback = callback;
            this.cast = cast;
        }

        @Override
        public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
            if(source.code() != 200) {
                NetworkManager.reportError(source.code(), source);
                return;
            }

            T res = JSON.fromString(result, cast);
            callback.receive(res);
        }
    }
}

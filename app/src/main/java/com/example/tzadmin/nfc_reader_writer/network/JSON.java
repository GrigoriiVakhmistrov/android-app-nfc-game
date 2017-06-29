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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import static com.example.tzadmin.nfc_reader_writer.Utils.parseIntDefault;

/**
 * This class holds {@link Gson} instance for app. Instance contains all required serializers/deserializers for classes.
 */
public final class JSON {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Shop.class, new ShopDeserializer())
            .registerTypeAdapter(Shop.class, new ShopSerializer())
            .registerTypeAdapter(Event.class, new EventDeserializer())
            .registerTypeAdapter(Event.class, new EventSerializer())
            .registerTypeAdapter(Morda.class, new MordaDeserializer())
            .registerTypeAdapter(Morda.class, new MordaSerializer())
            .registerTypeAdapter(Group.class, new GroupSerializer())
            .registerTypeAdapter(Group.class, new GroupDeserializer())
            .registerTypeAdapter(Route.class, new RouteSerializer())
            .registerTypeAdapter(Route.class, new RouteDeserializer())
            .registerTypeAdapter(User.class, new UserDeserializer())
            .registerTypeAdapter(User.class, new UserSerializer())
            .registerTypeAdapter(MoneyLogs.class, new MoneyLogsDeserializer())
            .registerTypeAdapter(MoneyLogs.class, new MoneyLogsSerializer())
            .registerTypeAdapter(GroupActivity.class, new GroupActivitySerializer())
            .registerTypeAdapter(GroupActivity.class, new GroupActivityDeserialier())
            .registerTypeAdapter(UserMorda.class, new UserMordaDeserializer())
            .registerTypeAdapter(UserMorda.class, new UserMordaSerializer())
            .create();

    public static String toString(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromString(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static <T> T fromString(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    private static final class ShopSerializer implements JsonSerializer<Shop> {
        @Override
        public JsonElement serialize(Shop src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("name", new JsonPrimitive(src.name));
            object.add("description", new JsonPrimitive(src.description));
            object.add("pic", new JsonPrimitive(src.pic.toString()));
            object.add("price", new JsonPrimitive(src.price.toString()));
            return object;
        }
    }

    private static final class ShopDeserializer implements JsonDeserializer<Shop> {

        @Override
        public Shop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Shop shop = new Shop();
            shop.id = object.get("id").getAsInt();
            shop.name = object.get("name").getAsString();
            shop.description = object.get("description").getAsString();
            shop.pic = parseIntDefault(object.get("pic").getAsString(), 10, -1);
            shop.price = parseIntDefault(object.get("price").getAsString(), 10, 0);
            return shop;
        }
    }

    private static final class EventSerializer implements JsonSerializer<Event> {

        @Override
        public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("name", new JsonPrimitive(src.name));
            object.add("description", new JsonPrimitive(src.description));
            object.add("price", new JsonPrimitive(src.price.toString()));
            return object;
        }
    }

    private static final class EventDeserializer implements JsonDeserializer<Event> {

        @Override
        public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Event event = new Event();
            event.id = object.get("id").getAsInt();
            event.name = object.get("name").getAsString();
            event.description = object.get("description").getAsString();
            event.price = parseIntDefault(object.get("price").getAsString(), 10, 0);
            return event;
        }
    }

    private static final class MordaSerializer implements JsonSerializer<Morda> {

        @Override
        public JsonElement serialize(Morda src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("fio", new JsonPrimitive(src.fio));
            object.add("description", new JsonPrimitive(src.description));
            object.add("pic", new JsonPrimitive(src.pic));
            return object;
        }
    }

    private static final class MordaDeserializer implements JsonDeserializer<Morda> {

        @Override
        public Morda deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Morda(object.get("id").getAsInt(), object.get("fio").getAsString(), object.get("description").getAsString(), object.get("pic").getAsString());
        }
    }

    private static final class GroupSerializer implements JsonSerializer<Group> {

        @Override
        public JsonElement serialize(Group src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("name", new JsonPrimitive(src.name));
            object.add("description", new JsonPrimitive(src.description));
            object.add("totemname", new JsonPrimitive(src.totemname));
            object.add("totemimage", new JsonPrimitive(src.totemimage));
            object.add("color", new JsonPrimitive(src.color));
            object.add("colorhex", new JsonPrimitive(src.colorhex));
            object.add("price", new JsonPrimitive(src.price.toString()));
            object.add("vip", new JsonPrimitive(src.price.toString()));
            return object;
        }
    }

    private static final class GroupDeserializer implements JsonDeserializer<Group> {

        @Override
        public Group deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Group group = new Group();
            group.id = object.get("id").getAsInt();
            group.name = object.get("name").getAsString();
            group.description = object.get("description").getAsString();
            group.totemimage = object.get("totemimage").getAsString();
            group.totemname = object.get("totemname").getAsString();
            group.color = object.get("color").getAsString();
            group.colorhex = object.get("colorhex").getAsString();
            group.price = parseIntDefault(object.get("price").getAsString(), 10, 0);
            group.vip = parseIntDefault(object.get("vip").getAsString(), 10, 0);
            return group;
        }
    }

    private static final class RouteSerializer implements JsonSerializer<Route> {

        @Override
        public JsonElement serialize(Route src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("name", new JsonPrimitive(src.name));
            object.add("description", new JsonPrimitive(src.description));
            object.add("capacity", new JsonPrimitive(src.capacity.toString()));
            object.add("price", new JsonPrimitive(src.price.toString()));
            return object;
        }
    }

    private static final class RouteDeserializer implements JsonDeserializer<Route> {

        @Override
        public Route deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Route route = new Route();
            route.id = object.get("id").getAsInt();
            route.name = object.get("name").getAsString();
            route.description = object.get("description").getAsString();
            route.capacity = parseIntDefault(object.get("capacity").getAsString(), 10, 0);
            route.price = parseIntDefault(object.get("price").getAsString(), 10, 0);
            return route;
        }
    }

    private static final class UserSerializer implements JsonSerializer<User> {

        @Override
        public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("firstname", new JsonPrimitive(src.firstname));
            object.add("lastname", new JsonPrimitive(src.patronymic));
            object.add("rfcid", new JsonPrimitive(src.rfcid));
            if(src.groupid != null)
                object.add("groupid", new JsonPrimitive(src.groupid.toString()));
            if(src.routeid != null)
                object.add("routeid", new JsonPrimitive(src.routeid.toString()));
            object.add("iscap", new JsonPrimitive(src.iscap));
            return object;
        }
    }

    private static final class UserDeserializer implements JsonDeserializer<User> {

        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            User user = new User();
            user.id = object.get("id").getAsInt();
            user.firstname = object.get("firstname").getAsString();
            user.lastname = object.get("lastname").getAsString();
            user.patronymic = object.get("patronymic").getAsString();
            user.rfcid = object.get("rfcid").getAsString();
            if(!object.get("groupid").isJsonNull())
                user.groupid = parseIntDefault(object.get("groupid").getAsString(), 10, -1);
            if(!object.get("routeid").isJsonNull())
                user.routeid = parseIntDefault(object.get("routeid").getAsString(), 10, -1);
            user.iscap = object.get("iscap").getAsInt();
            return user;
        }
    }

    private static final class MoneyLogsSerializer implements JsonSerializer<MoneyLogs> {

        @Override
        public JsonElement serialize(MoneyLogs src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("userid", new JsonPrimitive(src.userid.toString()));
            object.add("money", new JsonPrimitive(src.money.toString()));
            object.add("type", new JsonPrimitive(src.type));
            object.add("description", new JsonPrimitive(src.description));
            return object;
        }
    }

    private static final class MoneyLogsDeserializer implements JsonDeserializer<MoneyLogs> {

        @Override
        public MoneyLogs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            MoneyLogs logs = new MoneyLogs();
            JsonObject object = json.getAsJsonObject();
            logs.id = object.get("id").getAsInt();
            logs.userid = parseIntDefault(object.get("userid").getAsString(), 10, -1);
            logs.money = parseIntDefault(object.get("money").getAsString(), 10, 0);
            logs.type = object.get("type").getAsString();
            logs.description = object.get("description").getAsString();
            return logs;
        }
    }

    private static final class GroupActivitySerializer implements JsonSerializer<GroupActivity> {

        @Override
        public JsonElement serialize(GroupActivity src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("groupid", new JsonPrimitive(src.groupid));
            object.add("p1", new JsonPrimitive(src.p1));
            object.add("p2", new JsonPrimitive(src.p2));
            object.add("p3", new JsonPrimitive(src.p3));
            object.add("p4", new JsonPrimitive(src.p4));
            return object;
        }
    }

    private static final class GroupActivityDeserialier implements JsonDeserializer<GroupActivity> {

        @Override
        public GroupActivity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            GroupActivity activity = new GroupActivity();
            JsonObject object = json.getAsJsonObject();
            activity.id = object.get("id").getAsInt();
            activity.groupid = parseIntDefault(object.get("groupid").getAsString(), 10, -1);
            activity.p1 = parseIntDefault(object.get("p1").getAsString(), 10, 0);
            activity.p2 = parseIntDefault(object.get("p2").getAsString(), 10, 0);
            activity.p3 = parseIntDefault(object.get("p3").getAsString(), 10, 0);
            activity.p4 = parseIntDefault(object.get("p4").getAsString(), 10, 0);
            return activity;
        }
    }

    private static final class UserMordaSerializer implements JsonSerializer<UserMorda> {

        @Override
        public JsonElement serialize(UserMorda src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(src.id));
            object.add("userid", new JsonPrimitive(src.userid));
            object.add("mordaid", new JsonPrimitive(src.mordaid));
            return object;
        }
    }

    private static final class UserMordaDeserializer implements JsonDeserializer<UserMorda> {

        @Override
        public UserMorda deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            UserMorda morda = new UserMorda();
            JsonObject object = json.getAsJsonObject();
            morda.id = object.get("id").getAsInt();
            morda.userid = parseIntDefault(object.get("userid").getAsString(), 10, -1);
            morda.mordaid = parseIntDefault(object.get("mordaid").getAsString(), 10, -1);
            return morda;
        }
    }
}

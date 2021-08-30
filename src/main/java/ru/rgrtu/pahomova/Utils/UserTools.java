package ru.rgrtu.pahomova.Utils;

import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.rgrtu.pahomova.Utils.Connect.exec;
import static ru.rgrtu.pahomova.Utils.Connect.exeq;

public class UserTools {

    public static int getUserID(String login, String pass) {
        if (login == null || pass == null) {
            throw new RuntimeException("Invalid login or password!");
        }
        String sql = "SELECT rowid FROM 'Пользователь' WHERE Логин LIKE '" + login + "' AND Пароль LIKE '" + pass + "';";
        ResultSet results = exeq(sql);
        return 1;
    }

    public static boolean checkLoginAndPass(String login, String pass) {

        if (login == null || pass == null) {
            return false;
        }
        try {
            String sql = "SELECT rowid FROM 'Пользователь' WHERE Логин LIKE '" + login + "' AND Пароль LIKE '" + pass + "';";
            ResultSet results = exeq(sql);

            assert results != null;
            return results.next();
        } catch (SQLException e) {
            LoggerFactory.getLogger(UserTools.class).error(e.toString());
            return false;
        }
    }

    public static UserDataHolder getUserData(String login, String pass) throws SQLException {
        String sql = "SELECT rowid FROM 'Пользователь' WHERE Логин LIKE '" + login + "' AND Пароль LIKE '" + pass + "';";
        int id = 0;
        ResultSet results = exeq(sql);

        assert results != null;
        if (results.next()) {
            id = results.getInt(1);
        }
        return getUserData(id);
    }

    public static UserDataHolder getUserData(int id) throws SQLException {
        UserDataHolder ret = null;
        boolean isWorker = false;
        int code = 0;

        String sql = "SELECT Сотрудник, Код_ч FROM Пользователь WHERE rowid LIKE " + id + ";";
        ResultSet results = exeq(sql);
        assert results != null;
        if (results.next()) {
            isWorker = results.getBoolean(1);
            code = results.getInt(2);
        }
        if (isWorker) {
            sql = "SELECT Код_сотр, Фамилия, Имя, Отчество, Телефон, Код_долж FROM Сотрудник WHERE PK_Сотрудник LIKE " + code + ";";
            results = exeq(sql);
            assert results != null;
            if (results.next()) {
                code = results.getInt(1);
                String sname = results.getString(2);
                String fname = results.getString(3);
                String tname = results.getString(4);
                String phone = results.getString(5);
                String profCode  = results.getString(6);
                ret = new UserDataHolder(code, fname, sname, tname, phone, profCode);
                ret.isWorker = true;
            }
        } else {
            sql = "SELECT Код_род, Фамилия, Имя, Отчество, Телефон FROM Родитель WHERE PK_Родитель LIKE " + code + ";";
            results = exeq(sql);
            assert results != null;
            if (results.next()) {
                code = results.getInt(1);
                String sname = results.getString(2);
                String fname = results.getString(3);
                String tname = results.getString(4);
                String phone = results.getString(5);
                ret = new UserDataHolder(code, fname, sname, tname, phone);
            }
        }
        return ret;
    }

    public static List<List<String>> getChatStory() {
        List<List<String>> ret = new ArrayList<>();

        try {
            String sql = "SELECT username, message FROM 'chat' join 'users' on 'chat'.'user_id'='users'.'rowid' LIMIT 100;";
            ResultSet results = exeq(sql);
            assert results != null;
            while (results.next()) {
                ret.add(asList(results.getString("username"), results.getString("message")));
            }

        } catch (SQLException e) {
            LoggerFactory.getLogger(UserTools.class).error(e.toString());
        }

        return ret;
    }

    public static int addNewListener(String firstName, String secondName, String lastName, String birthday, String sex) throws SQLException {
        int newListenerId = 0;

        String sql = "SELECT MAX(Код_сл) FROM 'Слушатель';";
        int maxCode = -1;
        ResultSet max = exeq(sql);
        assert max != null;
        if (max.next()) maxCode = max.getInt(1);

        sql = "INSERT INTO 'Слушатель' (Код_сл, Фамилия, Имя, Отчество, Дата_рожд, Пол) VALUES (" +
                (maxCode+1)+", '"+secondName+"', '"+firstName+"', '"+lastName+"' ,'"+birthday+"', '" + sex +"');";
        exec(sql);

        sql = "SELECT LAST_INSERT_ROWID();";
        ResultSet id = exeq(sql);
        assert id != null;
        if (id.next()) newListenerId = max.getInt(1);

        return newListenerId;
    }
}

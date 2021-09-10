package ru.rgrtu.pahomova.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.rgrtu.pahomova.Utils.Connect.exec;
import static ru.rgrtu.pahomova.Utils.Connect.exeq;

public class UserTools {

    private static final Logger logger = LoggerFactory.getLogger(UserTools.class);

    public static int getUserID(String login, String pass) throws SQLException {
        if (login == null || pass == null) {
            throw new RuntimeException("Invalid login or password!");
        }
        String sql = "SELECT rowid FROM 'Пользователь' WHERE Логин LIKE '" + login + "' AND Пароль LIKE '" + pass + "';";
        ResultSet results = exeq(sql);
        assert results != null;
        if (results.next()) return results.getInt(1);
        throw new RuntimeException("Invalid login or password");
    }

    public static boolean checkLoginAndPass(String login, String pass) {

        try {
            getUserID(login, pass);
            return true;
        } catch (RuntimeException | SQLException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public static UserDataHolder getUserData(String login, String pass) throws SQLException {
        return getUserData(getUserID(login, pass));
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
                String profCode = results.getString(6);
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

    public static int addNewListener(String firstName, String secondName, String lastName, String birthday, String sex) {
        int newListenerId = 0;

        String sql = "SELECT MAX(Код_сл) FROM 'Слушатель';";
        int maxCode = -1;
        ResultSet max = exeq(sql);
        assert max != null;
        try {
        if (max.next()) maxCode = max.getInt(1);

        sql = "INSERT INTO 'Слушатель' (Код_сл, Фамилия, Имя, Отчество, Дата_рожд, Пол) VALUES (" +
                (maxCode + 1) + ", '" + secondName + "', '" + firstName + "', '" + lastName + "' ,'" + birthday + "', '" + sex + "');";
        exec(sql);

        sql = "SELECT LAST_INSERT_ROWID();";
        ResultSet id = exeq(sql);
        assert id != null;
        if (id.next()) newListenerId = max.getInt(1);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return newListenerId;
    }

    public static List<List<String>> getListenersList(boolean flag) {
        List<List<String>> list = new ArrayList<>();

        String sql = "SELECT PK_Слушатель, Фамилия || ' ' || Имя || ' ' || Отчество || ' - ' || Дата_рожд FROM 'Слушатель' WHERE Активный == " + flag + ";";
        ResultSet users = exeq(sql);
        assert users != null;
        try {
            while (users.next()) {
                list.add(new ArrayList<>(asList(users.getString(1), users.getString(2))));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }

        return list;
    }

    public static List<List<String>> getSubjectsList() {
        List<List<String>> list = new ArrayList<>();
        //TODO: Remove hardset
        int teacherId = 1;

        String sql = "SELECT PK_Занятие, Название FROM Занятие WHERE Код_сотр = "+teacherId+";";
        ResultSet users = exeq(sql);
        assert users != null;
        try {
            while (users.next()) {
                list.add(new ArrayList<>(asList(users.getString(1), users.getString(2))));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }

        return list;
    }

    public static void handleStudentRequest(String id, String bool) {
        String sql;
        if ("true".equalsIgnoreCase(bool)) {
            sql = "UPDATE 'Слушатель' SET Активный = true WHERE PK_Слушатель LIKE " + id + ";";
            exec(sql);
            sql = "INSERT INTO 'Сл_гр'('Код_сл', 'Код_гр') VALUES (" + id + ", 1);";
            logger.debug("New listener was appended");
        } else {
            sql = "DELETE FROM 'Слушатель' WHERE PK_Слушатель LIKE " + id + ";";
            logger.debug("New listener was declined");
        }
        exec(sql);
    }

    public static void handleSetStudentMark(String studentId, String subjectId, String mark) {
        String sql;
        sql = "INSERT INTO Оценка VALUES("+subjectId+", "+studentId+", "+mark+");";
        logger.debug("Mark for {} is {}", studentId, mark);
        exec(sql);
    }

    public static List<String> getTeachersSubjects(String id) {
        List<String> subjects = new ArrayList<>();

        String sql = "SELECT Код_зан FROM Занятие WHERE Код_сотр LIKE " + id + ";";
        ResultSet results = exeq(sql);
        assert results != null;
        while (true) {
            try {
                if (!results.next()) break;
                subjects.add(results.getString(1));
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        }

        return subjects;
    }
}

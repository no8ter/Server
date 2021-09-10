package ru.rgrtu.pahomova.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Connect {

    private final static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\src\\main\\resources\\db\\db.db";
    private static final Logger logger = LoggerFactory.getLogger(Connect.class);
    private static Connection connect;
    private static Statement statement;

    static {
        connect();
    }

    public static synchronized void connect() {
        if (connect == null) {
            try {
                boolean addUsers = false;
                if (!Files.exists(Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\db"))) {
                    Files.createDirectory(Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\db"));
                    addUsers = true;
                }
                connect = DriverManager.getConnection(url);
                statement = connect.createStatement();
                createDB();
                if (addUsers) addTestData();
            } catch (SQLException | IOException e) {
                logger.error(e.toString());
            }
        }
    }

    public static void exec(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    public static ResultSet exeq(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            logger.error(e.toString());
            return null;
        }
    }

    public static void warmup() {
        logger.debug("Warming up database");
        connect();
        exec("SELECT * FROM 'Слушатель' LIMIT 100");
    }

    private static void createDB() throws SQLException {
        logger.debug("Creating not existing tables");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Должность'(\n" +
                "'PK_Должность' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "'Код_долж' INTEGER NOT NULL,\n" +
                "'Должность' TEXT NULL);");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Группа'(\n" +
                "'PK_Группа' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "'Код_гр' INTEGER NOT NULL,\n" +
                "'Номер_гр' INTEGER NULL);");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Родитель'(\n" +
                "'PK_Родитель' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "'Код_род' INTEGER NOT NULL,\n" +
                "'Фамилия' TEXT NOT NULL,\n" +
                "'Имя' TEXT NOT NULL,\n" +
                "'Отчество' TEXT NULL,\n" +
                "'Телефон' TEXT NOT NULL);");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Занятие'(\n" +
                "'PK_Занятие' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "'Код_зан' INTEGER NOT NULL,\n" +
                "'Название' TEXT NULL,\n" +
                "'Код_сотр' INTEGER NOT NULL," +
                "FOREIGN KEY (Код_сотр) REFERENCES Сотрудник(PK_Сотрудник));");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Оценка'(\n" +
                "'Код_зан' INTEGER NULL,\n" +
                "'Код_сл' INTEGER NULL,\n" +
                "'Балл' INTEGER NULL," +
                "FOREIGN KEY (Код_зан) REFERENCES Занятие(PK_Занятие)," +
                "FOREIGN KEY (Код_сл) REFERENCES Слушатель(PK_Слушатель));");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Сл_гр'(\n" +
                "'Код_сл' INTEGER NULL,\n" +
                "'Код_гр' INTEGER NULL," +
                "FOREIGN KEY (Код_сл) REFERENCES Слушатель(PK_Слушатель)," +
                "FOREIGN KEY (Код_гр) REFERENCES Группа(PK_Группа));");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Сл_род'(\n" +
                "'Код_сл' INTEGER NULL,\n" +
                "'Код_род' INTEGER NULL,\n" +
                "'Род_связь' TEXT NULL," +
                "FOREIGN KEY (Код_сл) REFERENCES Слушатель(PK_Слушатель)," +
                "FOREIGN KEY (Код_род) REFERENCES Родитель(PK_Родитель));");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Слушатель'(\n" +
                "'PK_Слушатель' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "'Код_сл' INTEGER NOT NULL,\n" +
                "'Фамилия' TEXT NOT NULL,\n" +
                "'Имя' TEXT NOT NULL,\n" +
                "'Отчество' TEXT NULL,\n" +
                "'Дата_рожд' DATE NOT NULL,\n" +
                "'Пол' TEXT NULL,\n" +
                "'Активный' BOOLEAN DEFAULT false);");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Сотрудник'(\n" +
                "'PK_Сотрудник' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "'Код_сотр' INTEGER NOT NULL,\n" +
                "'Фамилия' TEXT NOT NULL,\n" +
                "'Имя' TEXT NOT NULL,\n" +
                "'Отчество' TEXT NULL,\n" +
                "'Телефон' TEXT NOT NULL,\n" +
                "'Специальность' TEXT NULL,\n" +
                "'Код_долж' INTEGER NOT NULL," +
                "FOREIGN KEY (Код_долж) REFERENCES Должность(PK_Должность));");
        statement.execute("CREATE TABLE IF NOT EXISTS 'Пользователь'(\n" +
                "'Логин' TEXT NOT NULL PRIMARY KEY,\n" +
                "'Пароль' TEXT NOT NULL,\n" +
                "'Код_ч' INTEGER NOT NULL,\n" +
                "'Сотрудник' BOOLEAN NOT NULL);");
    }

    private static void addTestData() throws SQLException {
        logger.debug("Inserting test data");
        statement.execute("INSERT INTO 'Должность'(" +
                "Код_долж, Должность) VALUES (1, 'Учитель')");
        statement.execute("INSERT INTO 'Должность'(" +
                "Код_долж, Должность) VALUES (2, 'Сотрудник')");
        statement.execute("INSERT INTO 'Родитель'(" +
                "Код_род, Фамилия, Имя, Отчество, Телефон) VALUES (" +
                "1, 'Иванов', 'Иван', 'Иванович', '+79005003020')");
        statement.execute("INSERT INTO 'Сотрудник'(" +
                "Код_сотр, Фамилия, Имя, Отчество, Телефон, Специальность,Код_долж) VALUES (" +
                "1, 'Петров', 'Петр', 'Петрович', '+79006007080', 'Учитель', 1)");
        statement.execute("INSERT INTO 'Сотрудник'(" +
                "Код_сотр, Фамилия, Имя, Отчество, Телефон, Специальность,Код_долж) VALUES (" +
                "2, 'Киров', 'Кирилл', 'Петрович', '+79006007080', 'Куратор', 2)");
        statement.execute("INSERT INTO 'Пользователь'(" +
                "Логин, Пароль, Код_ч, Сотрудник) VALUES (" +
                "'curator', 'curator', 1, true);");
        statement.execute("INSERT INTO 'Пользователь'(" +
                "Логин, Пароль, Код_ч, Сотрудник) VALUES (" +
                "'admin', 'admin', 2, true);");
        statement.execute("INSERT INTO 'Пользователь'(" +
                "Логин, Пароль, Код_ч, Сотрудник) VALUES (" +
                "'parent', 'parent', 1, false);");
        statement.execute("INSERT INTO 'Занятие' (" +
                "    Код_зан, Название, Код_сотр) VALUES (" +
                "    1, 'Информатика', 1);");
        statement.execute("INSERT INTO 'Занятие' (" +
                "    Код_зан, Название, Код_сотр) VALUES (" +
                "    2, 'Математика', 1);");
        statement.execute("INSERT INTO 'Группа' (" +
                "    Код_гр, Номер_гр) VALUES (1, '044M');");
    }
}

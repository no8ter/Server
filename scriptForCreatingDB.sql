CREATE TABLE IF NOT EXISTS 'Должность'(
    'PK_Должность' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'Код_долж' INTEGER NOT NULL,
    'Должность' TEXT NULL
);

CREATE TABLE IF NOT EXISTS 'Группа'(
    'PK_Группа' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'Код_гр' INTEGER NOT NULL,
    'Номер_гр' INTEGER NULL
);

CREATE TABLE IF NOT EXISTS 'Родитель'(
    'PK_Родитель' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'Код_род' INTEGER NOT NULL,
    'Фамилия' TEXT NOT NULL,
    'Имя' TEXT NOT NULL,
    'Отчество' TEXT NULL,
    'Телефон' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'Занятие'(
    'PK_Занятие' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'Код_зан' INTEGER NOT NULL,
    'Название' TEXT NULL,
    'Код_сотр' INTEGER NOT NULL,
    FOREIGN KEY (Код_сотр) REFERENCES Сотрудник(PK_Сотрудник)
);

CREATE TABLE IF NOT EXISTS 'Оценка'(
    'Код_зан' INTEGER NULL,
    'Код_сл' INTEGER NULL,
    'Балл' INTEGER NULL,
    FOREIGN KEY (Код_зан) REFERENCES Занятие(PK_Занятие),
    FOREIGN KEY (Код_сл) REFERENCES Слушатель(PK_Слушатель));

CREATE TABLE IF NOT EXISTS 'Сл_гр'(
    'Код_сл' INTEGER NULL,
    'Код_гр' INTEGER NULL,
    FOREIGN KEY (Код_сл) REFERENCES Слушатель(PK_Слушатель),
    FOREIGN KEY (Код_гр) REFERENCES Группа(PK_Группа)
);

CREATE TABLE IF NOT EXISTS 'Сл_род'(
    'Код_сл' INTEGER NULL,
    'Код_род' INTEGER NULL,
    'Род_связь' TEXT NULL,
    FOREIGN KEY (Код_сл) REFERENCES Слушатель(PK_Слушатель),
    FOREIGN KEY (Код_род) REFERENCES Родитель(PK_Родитель)
);

CREATE TABLE IF NOT EXISTS 'Слушатель'(
    'PK_Слушатель' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'Код_сл' INTEGER NOT NULL,
    'Фамилия' TEXT NOT NULL,
    'Имя' TEXT NOT NULL,
    'Отчество' TEXT NULL,
    'Дата_рожд' DATE NOT NULL,
    'Пол' TEXT NULL,
    'Активный' BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS 'Сотрудник'(
    'PK_Сотрудник' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'Код_сотр' INTEGER NOT NULL,
    'Фамилия' TEXT NOT NULL,
    'Имя' TEXT NOT NULL,
    'Отчество' TEXT NULL,
    'Телефон' TEXT NOT NULL,
    'Специальность' TEXT NULL,
    'Код_долж' INTEGER NOT NULL,
    FOREIGN KEY (Код_долж) REFERENCES Должность(PK_Должность)
);

CREATE TABLE IF NOT EXISTS 'Пользователь'(
    'PK_Пользователь' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'Логин' TEXT NOT NULL,
    'Пароль' TEXT NOT NULL,
    'Код_ч' INTEGER NOT NULL,
    'Сотрудник' BOOLEAN NOT NULL
);

INSERT INTO 'Должность' (
    Код_долж, Должность) VALUES (
    1, 'Учитель');
INSERT INTO 'Должность' (
    Код_долж, Должность) VALUES (
    2, 'Сотрудник');
INSERT INTO 'Родитель' (
    Код_род, Фамилия, Имя, Отчество, Телефон) VALUES (
    000001, 'Иванов', 'Иван', 'Иванович', '+79005003020');
INSERT INTO 'Сотрудник' (
    Код_сотр, Фамилия, Имя, Отчество, Телефон, Специальность,Код_долж) VALUES (
    000001, 'Петров', 'Петр', 'Петрович', '+79006007080', 'Учитель', 1);
INSERT INTO 'Сотрудник' (
    Код_сотр, Фамилия, Имя, Отчество, Телефон, Специальность,Код_долж) VALUES (
    000002, 'Киров', 'Кирилл', 'Петрович', '+79006007080', 'Куратор', 2);
INSERT INTO 'Пользователь' (
    Логин, Пароль, Код_ч, Сотрудник) VALUES (
    'admin', 'admin', 000001, true);
INSERT INTO 'Пользователь' (
    Логин, Пароль, Код_ч, Сотрудник) VALUES (
    'curator', 'curator', 000002, true);
INSERT INTO 'Пользователь' (
    Логин, Пароль, Код_ч, Сотрудник) VALUES (
    'parent', 'parent', 000001, false);
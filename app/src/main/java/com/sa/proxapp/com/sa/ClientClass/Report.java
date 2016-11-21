/**
 * Created by Android on 05.10.2016.
 */
package com.sa.proxapp.com.sa.ClientClass;
public class Report {
    // Ошибки


    public static final int SQL_EXCEPTION = 601; // Ошибка выполнения запроса
    public static final int THE_USER_EXIST = 602; // Ошибка: Пользователь существует
    public static final int THE_USER_IS_NOT_EXIST = 603; // Такого пользователя несуществует
    public static final int COOKIE_FAIL = 604; // Ошибка: У пользователя нет куков или они не верны
    public static final int JSON_DECODE_FAIL = 605; // Ошибка декодирования json
    public static final int SUCCESSFUL_SQL = 200;


    // TYPE
    public static final int MESSAGE = 1; // Сообщение
    public static final int CONTACT = 2; // Контакт
    public static final int REGISTATION = 30; // Запрос регистрации
    public static final int AUTHORISATION = 31; // Запрос авторизации
    // Ответ сервера
    public static final int FIND_CONTACT = 15;
    public static final int NOT_FIND_CONTACT = 16;
    public static final int SUCCESSFUL_REG = 17; // Удачная регитрация
    public static final int SUCCESSFUL_AUTH = 18; // Удачная авторизация

    public int type;
    public Object data;
}

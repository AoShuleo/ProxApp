/**
 * Created by Android on 05.10.2016.
 */
package com.sa.proxapp.com.sa.ClientClass;
public class Report {
    static final public int SQL_EXCEPTION = 601; // Ошибка выполнения запроса
    static final public int THE_USER_EXIST = 602; // Ошибка: Пользователь существует
    static final public int THE_USER_IS_NOT_EXIST = 603; // Такого пользователя несуществует
    static final public int COOKIE_FAIL = 604; // Ошибка: У пользователя нет куков или они не верны
    static final public int JSON_DECODE_FAIL = 605; // Ошибка декодирования json
    static final public int LOG_OR_PASS_IS_NOT_COR = 606; // Логин или пароль неверные


    static final public int SUCCESSFUL_SQL = 200;


    // TYPE
    static final public int MESSAGE = 1; // Сообщение
    static final public int CONTACT = 2; // добавить Контакт
    static final public int GIVE_MY_FRIENDS = 32; // Запрос, дай мне список контактов
    static final public int REGISTATION = 30; // Запрос регистрации
    static final public int AUTHORISATION = 31; // Запрос авторизации
    // Ответ сервера
    static final public int FIND_CONTACT = 15;
    static final public int NOT_FIND_CONTACT = 16;
    static final public int SUCCESSFUL_REG = 17; // Удачная региcтрация
    static final public int SUCCESSFUL_AUTH = 18; // Удачная авторизация
    static final public int SUCCESSFUL_SEND_MES = 19; // Удачная отправка сообщения
    static final public int SUCCESSFUL_FRIENDS = 20; // Удачно отправленный список друзей

    public int type;
    public Object data;
}

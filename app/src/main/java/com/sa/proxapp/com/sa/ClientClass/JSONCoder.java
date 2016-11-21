package com.sa.proxapp.com.sa.ClientClass;

import com.sa.proxapp.com.sa.ClientClass.simple.*;
import com.sa.proxapp.com.sa.ClientClass.simple.parser.JSONParser;



public class JSONCoder {
    public static Object decode(String string, int t) // t = 1 - message, t = 2 - contact
    {
        JSONParser parser = new JSONParser();
        JSONObject jsonObj;
        try {
            Object obj = parser.parse(string);
            jsonObj = (JSONObject) obj;
            if (t == Report.MESSAGE) {
                Message message = new Message();
                message.date = (String) jsonObj.get("date");
                message.text = (String) jsonObj.get("text");
                message.time = (String) jsonObj.get("time");
                String a = (String) jsonObj.get("contact");
                message.contact = (Contact) decode(a, Report.CONTACT);
                return message;
            }
            if (t == Report.CONTACT) {
                Contact contact = new Contact();
                contact.login = (String) jsonObj.get("login");
                contact.name = (String) jsonObj.get("name");
                contact.password = (String) jsonObj.get("password");
                return contact;
            }
        } catch (Exception e) {
            System.out.println("Ошибка декодирования (Message)");
        }
        return null;
    }
    public static Report decode(String string)
    {
        JSONObject jsonObj;
        JSONParser parser = new JSONParser();
        Report report = new Report();
        try {
            Object obj = parser.parse(string);
            jsonObj = (JSONObject) obj;
            long a = (long) jsonObj.get("type");
            report.type = (int) a ;
            String d = (String) jsonObj.get("data");
            if(report.type == Report.MESSAGE)
                report.data = (Message) decode(d, report.type);
            if(report.type == Report.CONTACT)
                report.data = (Contact) decode(d, report.type);
        }
        catch(Exception e) {
            report.type = Report.JSON_DECODE_FAIL;
            System.out.println(report.type);
        }
        return report;
    }
    public static String encode(Message message)
    {
        JSONObject resultJson = new JSONObject();
        resultJson.put("date", message.date);
        resultJson.put("text", message.text);
        resultJson.put("time", message.time);
        resultJson.put("contact", encode(message.contact));
        return resultJson.toJSONString();
    }
    public static String encode(Contact contact)
    {
        JSONObject resultJson = new JSONObject();
        resultJson.put("login", contact.login);
        resultJson.put("name", contact.name);
        resultJson.put("password", contact.password);
        return resultJson.toJSONString();
    }
    public static String encode(Report report)
    {
        String data = "";
        if(report.data instanceof Contact)
            data = encode((Contact) report.data);
        else if(report.data instanceof Message)
            data = encode((Message) report.data);
        else if(report.data instanceof Report)
            data = encode((Report) report.data);
        else
            data = (String) report.data;
        JSONObject resultJson = new JSONObject();
        resultJson.put("type", report.type);
        resultJson.put("data", data);
        return resultJson.toJSONString();
    }



}

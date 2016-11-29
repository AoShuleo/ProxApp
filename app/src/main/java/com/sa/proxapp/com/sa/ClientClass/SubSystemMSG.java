package com.sa.proxapp.com.sa.ClientClass;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public class SubSystemMSG implements SubSystemMSGInterface{


    @Override
    public void loginMe(String login,String password, ReportListener reportListener) {
        Contact contact = new Contact();
        contact.login = login;
        contact.password = password;
        String string = JSONCoder.encode(contact); //получили JSON-строку контакта
        Report report = new Report();
        report.data = string;
        report.type = 31; //
        String stringReport = JSONCoder.encode(report);
        InputStream is = null;
        byte[] answerData = null;

        try //
        {

            URL url = new URL("http://25.19.168.170:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            System.out.println("Метод запроса: " +
                    connection.getRequestMethod());


            connection.setRequestProperty("User-Agent", "fff");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(stringReport);
            wr.flush();
            wr.close();
            OutputStream outputStream = connection.getOutputStream();

            //Прослушка ответа+
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = connection.getInputStream();

            byte[] buffer = new byte[8192]; // Задаем размер буфера
            // Далее читаем ответ
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            answerData = baos.toByteArray();
            String JSONstr = new String(answerData, "UTF-8");
            Report answerReport = JSONCoder.decode(JSONstr);
            //int answerCode = answerReport.type;
            int responseCode = connection.getResponseCode();

            System.out.println("Код ошибки : " + responseCode);
            System.out.println("Строка : " + stringReport);
            System.out.println("Строка : " + JSONstr);

            reportListener.handler(answerReport);
            //-Прослушка ответа



        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void addContact(Contact contact, ReportListener reportListener) {
        String string = JSONCoder.encode(contact); //получили JSON-строку контакта
        Report report = new Report();
        report.data = string;
        report.type = 2; //
        String stringReport = JSONCoder.encode(report);
        InputStream is = null;
        byte[] answerData = null;

        try //
        {

            URL url = new URL("http://25.19.168.170:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            System.out.println("Метод запроса: " +
                    connection.getRequestMethod());


            connection.setRequestProperty("User-Agent", "fff");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(stringReport);
            wr.flush();
            wr.close();
            OutputStream outputStream = connection.getOutputStream();

            //Прослушка ответа+
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = connection.getInputStream();

            byte[] buffer = new byte[8192]; // Задаем размер буфера
            // Далее читаем ответ
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            answerData = baos.toByteArray();
            String JSONstr = new String(answerData, "UTF-8");
            Report answerReport = JSONCoder.decode(JSONstr);
            //int answerCode = answerReport.type;
            reportListener.handler(answerReport);
            //-Прослушка ответа

            int responseCode = connection.getResponseCode();
            System.out.println("Код ошибки : " + responseCode);
            System.out.println("Строка : " + stringReport);
            System.out.println("Строка : " + JSONstr);

        }
        catch (Exception e) {}
    }

    @Override
    public void auth(Contact contact, ReportListener reportListener, String string) {

    }

    @Override
    public void delContact(Contact contact, ReportListener reportListener) {

    }

    @Override
    public void findContact(Contact contact, ReportListener reportListener, int I1, int I2) {

    }

    @Override
    public void registration(Contact contact, ReportListener reportListener) {
        String string = JSONCoder.encode(contact); //получили JSON-строку контакта
        Report report = new Report();
        report.data = string;
        report.type = 30; // указать тип для регистрации+
        String stringReport = JSONCoder.encode(report);
        InputStream is = null;
        byte[] answerData = null;
        try //
        {

            URL url = new URL("http://25.19.168.170:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            System.out.println("Метод запроса: " +
                    connection.getRequestMethod());


            connection.setRequestProperty("User-Agent", "fff");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(stringReport);
            wr.flush();
            wr.close();
            OutputStream outputStream = connection.getOutputStream();

            //Прослушка ответа+
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = connection.getInputStream();

            byte[] buffer = new byte[8192]; // Задаем размер буфера
            // Далее читаем ответ
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            answerData = baos.toByteArray();
            String JSONstr = new String(answerData, "UTF-8");
            Report answerReport = JSONCoder.decode(JSONstr);
            //int answerCode = answerReport.type;
            reportListener.handler(answerReport);
            //-Прослушка ответа

            int responseCode = connection.getResponseCode();
            System.out.println("Код ошибки : " + responseCode);
            System.out.println("Строка : " + stringReport);
            System.out.println("Строка : " + JSONstr);

        }
        catch (Exception e) {}
    }

    @Override
    public void registrListener(ReportListener reportListener) {

    }

    @Override
    public void sendMessage(Message message, ReportListener reportListener) {

    }
}

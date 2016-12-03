package com.sa.proxapp.com.sa.ClientClass;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */

public class SubSystemMSG implements SubSystemMSGInterface{

    //добавил 29.11
    static final String ADDRESS = "http://25.19.168.170:8080/";
    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    //добавил 29.11

    //add 02.12
    private String aggregateConnectionWithSession(Report report)
    {
        String JSONstr = null;
        String stringReport = JSONCoder.encode(report);
        InputStream is = null;
        byte[] answerData = null;
        try
        {
            URL url = new URL(ADDRESS + "login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("User-Agent", "fff");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                // System.out.println(msCookieManager.getCookieStore().getCookies().toString());
                connection.setRequestProperty(COOKIES_HEADER, msCookieManager.getCookieStore().getCookies().get(0).toString());
                connection.setRequestProperty("Cookie", msCookieManager.getCookieStore().getCookies().get(0).toString());

            }
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(stringReport);
            wr.flush();
            wr.close();

            connection.connect();

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

            //ответ
            JSONstr = new String(answerData, "UTF-8");//
        }
        catch (Exception e) {

        }
        if(JSONstr == null)
            System.out.println("null str in private String aggregateConnectionWithSession(Report report)");
        return JSONstr;
    }

    @Override
    public void requestListContacts(ReportListener reportListener) {
        Report report = new Report();
        report.data = null;
        report.type = Report.GIVE_MY_FRIENDS; //запрос на получение списка контактов

        String answerStr = aggregateConnectionWithSession(report);

        Report answerReport = JSONCoder.decode(answerStr);
        reportListener.handler(answerReport);
    }

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

            URL url = new URL(ADDRESS + "login");
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

            Map<String, List<String>> headerFields = connection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }
            String JSONstr = new String(answerData, "UTF-8");
            Report answerReport = JSONCoder.decode(JSONstr);
            reportListener.handler(answerReport);
        }
        catch (Exception e) {}
    }

    @Override
    public void addContact(Contact contact, ReportListener reportListener) {
        String string = JSONCoder.encode(contact); //получили JSON-строку контакта
        Report report = new Report();
        report.data = string;
        report.type = Report.CONTACT; //

        String answerStr = aggregateConnectionWithSession(report);

        Report answerReport = JSONCoder.decode(answerStr);
        reportListener.handler(answerReport);
    }

    @Override
    public void delContact(Contact contact, ReportListener reportListener) {
        Contact copContact = new Contact();
        copContact.login = contact.login;

        Report report = new Report();
        report.data = copContact;
        report.type = Report.DEL_FRIEND; //запрос на удаление контакта

        String answerStr = aggregateConnectionWithSession(report);

        Report answerReport = JSONCoder.decode(answerStr);
        reportListener.handler(answerReport);
    }

    @Override
    public void findContact(Contact contact, ReportListener reportListener) {
        Contact copContact = new Contact();
        copContact.login = contact.login;

        Report report = new Report();
        report.data = copContact;
        report.type = Report.FIND_CONTACTS; //запрос на получение списка найденных контактов

        String answerStr = aggregateConnectionWithSession(report);

        Report answerReport = JSONCoder.decode(answerStr);
        reportListener.handler(answerReport);
    }

    @Override
    public void registration(Contact contact, ReportListener reportListener) {
        String string = JSONCoder.encode(contact); //получили JSON-строку контакта
        Report report = new Report();
        report.data = string;
        report.type = 30; // указать тип для регистрации+

        String answerStr = aggregateConnectionWithSession(report);

        Report answerReport = JSONCoder.decode(answerStr);
        reportListener.handler(answerReport);
    }

    @Override
    public void registrListener(ReportListener reportListener) {

    }

    @Override
    public void sendMessage(Message message, ReportListener reportListener) {

    }
}


/*public class SubSystemMSG implements SubSystemMSGInterface{

    static final String ADDRESS = "http://25.19.168.170:8080/";
    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    @Override
    public void requestListContacts(ReportListener reportListener) {
        Report report = new Report();
        report.data = null;
        report.type = Report.GIVE_MY_FRIENDS; //запрос на получение списка контактов
        String stringReport = JSONCoder.encode(report);
        InputStream is = null;
        byte[] answerData = null;

        try
        {

            URL url = new URL(ADDRESS + "login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("User-Agent", "fff");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                System.out.println(msCookieManager.getCookieStore().getCookies().toString());
                System.out.println(msCookieManager.getCookieStore().getCookies().get(0).toString());
                // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                connection.setRequestProperty(COOKIES_HEADER, msCookieManager.getCookieStore().getCookies().get(0).toString());
                connection.setRequestProperty("Cookie", msCookieManager.getCookieStore().getCookies().get(0).toString());

            }
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(stringReport);
            wr.flush();
            wr.close();

            connection.connect();

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

            int responseCode = connection.getResponseCode();


            //ответ
            String JSONstr = new String(answerData, "UTF-8");//
            Report answerReport = JSONCoder.decode(JSONstr);
            //int answerCode = answerReport.type;
            reportListener.handler(answerReport);
            //-Прослушка ответа


            System.out.println("Код ошибки : " + responseCode);
            System.out.println("Строка : " + stringReport);
            System.out.println("Строка ответ: " + JSONstr);

        }
        catch (Exception e) {}
    }

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

            URL url = new URL(ADDRESS + "login");
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

            Map<String, List<String>> headerFields = connection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }
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

            URL url = new URL(ADDRESS + "login");
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

            URL url = new URL(ADDRESS + "login");
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
}*/

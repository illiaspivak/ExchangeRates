package sk.kosickaakademia.spivak.exchangerates.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sk.kosickaakademia.spivak.exchangerates.log.Log;
import sk.kosickaakademia.spivak.exchangerates.sequrity.Security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class APIRequest {
    Log log = new Log();

    /**
     * Connecting to the server from http protocol using API Access Key
     * Getting the exchange rate against the euro
     * @return String - Currency exchange rates received (all information is written in one line)
     */
    private String getRatesFromAPIServer(){
        try {
            //path to endpoint. Security.getKey() - API Access Key
            URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key="+ Security.getKey() +"&format=1");

            //connection over the HTTP network protocol
            log.info("HTTP network protocol connection");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            log.info("Request method: " + connection.getRequestMethod());
            log.info("Response code: " + connection.getResponseCode());
            log.info("Response message: " + connection.getResponseMessage());
            if (connection.getResponseCode() != 200) {
                log.error("HttpResponseCode: " + connection.getResponseCode());
            } else {
                String response = ""; //Response from the server
                Scanner scanner = new Scanner(url.openStream()); //initiates a new TCP connection to the server that the URL is accessing
                /*
                Вызов url.openStream() инициирует новое соединение TCP с сервером,
                к которому обращается URL. Затем по соединению отправляется запрос HTTP GET.
                Если все идет правильно (например, 200 OK),
                сервер отправляет обратно ответное сообщение HTTP,
                которое несет полезную нагрузку данных, которая подается в указанном URL.
                 */

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    response += scanner.nextLine();
                }
                //Close the scanner
                scanner.close();
                log.print("Currency exchange rates received");
                return response;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        log.error("No connection to the server");
        return null;
    }

    /**
     * Creating a HashMap for selected currencies
     * @param rates
     * @return HashMap: currency name - currency rate {USD=1.197411, ...}
     */
    public Map getExchangeRates(Set<String> rates){
        if(rates==null || rates.size() ==0) {
            log.error("Missing input data");
            return null;
        }
        log.info("getting all currency exchange rates in String");
        String allRates = getRatesFromAPIServer();
        if(allRates==null) {
            log.error("No connection to the server");
            return null;
        }
        try {
            log.info("Parse of the String to Json");
            JSONParser parse = new JSONParser();
            JSONObject objectAllInformation = (JSONObject) parse.parse(allRates);
            log.info("Full Json");
            System.out.println(objectAllInformation);

            log.info("Taking the only currency exchange rate from the whole Json");
            JSONObject objectOnlyRates = (JSONObject) objectAllInformation.get("rates");
            log.info("Json currency exchange rate");
            System.out.println(objectOnlyRates);

            log.info("Creating the HashMap: currency name - currency rate");
            Map<String,Double> mapRates = new HashMap<>();
            //Recording the exchange rates of the selected currencies in the Set
            for(String temp:rates){
                if(objectOnlyRates.containsKey(temp)){      //Does this currency exist in json?
                    double value= (double)objectOnlyRates.get(temp);
                    mapRates.put(temp,value);
                }
            }
            log.print("Received the HashMap: currency name - currency rate");
            return mapRates;
        }catch(Exception ex){
            log.error(ex.toString());
        }
        log.error("Couldn't get exchange rates");
        return null;
    }

    /**
     * Getting the exchange rate for a specific currency
     * @param currency
     * @return double rate
     */
    public double getExchangeRate(String currency){
        //path to endpoint. Security.getKey() - API Access Key
        String query = "http://api.exchangeratesapi.io/v1/latest?access_key=" + Security.getKey() + "&symbols=" + currency;
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.connect();

            StringBuilder response = new StringBuilder(); //Response from the server

            if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){ //response code 200
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while((line = in.readLine()) != null){
                    response.append(line);
                    response.append("\n");
                }
                JSONParser parse = new JSONParser();
                JSONObject jsonObject = (JSONObject) parse.parse(String.valueOf(response));

                JSONObject jsonRate = (JSONObject) jsonObject.get("rates");
                double value= (double)jsonRate.get(currency);
                log.print("Rate reserved");
                return value;
            }else{
                log.error("Response code: " + connection.getResponseCode());
            }
        }catch (Throwable cause){
            log.error(cause.toString());
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
        return -1;
    }
}

package sk.kosickaakademia.spivak.exchangerates.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sk.kosickaakademia.spivak.exchangerates.log.Log;
import sk.kosickaakademia.spivak.exchangerates.sequrity.Security;

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
    public String getRatesFromAPIServer(){
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

    public Map getExchangeRates(Set<String> rates){
        if(rates==null || rates.size() ==0)
            return null;

        return parseData(rates);
    }



    private Map parseData(Set<String> rates){
        String inline=getRatesFromAPIServer();
        if(inline==null)
            return null;

        try {
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline);

            JSONObject obj = (JSONObject) data_obj.get("rates");

            Map<String,Double> maps = new HashMap<>();

            for(String temp:rates){
                if(obj.containsKey(temp)){
                    double value= (double)obj.get(temp);
                    maps.put(temp,value);
                }
            }
            return maps;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}

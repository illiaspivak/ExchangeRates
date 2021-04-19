package sk.kosickaakademia.spivak.exchangerates.calculator;

import sk.kosickaakademia.spivak.exchangerates.api.APIRequest;
import sk.kosickaakademia.spivak.exchangerates.log.Log;

import java.util.*;

public class Calculator {
    Log log = new Log();

    /**
     * Multiplying the exchange rates with a certain amount. Getting a list: currency name-converted currencies
     * @param eur
     * @param rates
     * @return Map<String,Double>
     */
    public Map<String,Double> calculate(double eur, String[] rates) {
        if(eur<0 || rates == null){
            log.error("Incorrect input data");
            return null;
        }
        Set<String> set = new HashSet<>();
        Collections.addAll(set, rates); //Putting a list of rates in set

        APIRequest apiRequest = new APIRequest();
        Map map = apiRequest.getExchangeRates(set);//Creating a hash map: currency name - currency exchange rate

        log.info("Multiplying the exchange rates with a certain amount");
        Map<String,Double> values = new HashMap<>(); //Creating a hash map: currency name - the received value
        Iterator<Map.Entry<String, Double>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Double> entry = iterator.next();//the next() method returns Entry object containing a key-value pair»
            log.info("Key: " + entry.getKey());
            log.info("Value: " + entry.getValue());
            values.put(entry.getKey(),entry.getValue()*eur);
        }
        log.print("Converted currencies received");
        return values;
    }
}

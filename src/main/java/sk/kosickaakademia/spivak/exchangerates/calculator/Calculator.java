package sk.kosickaakademia.spivak.exchangerates.calculator;

import sk.kosickaakademia.spivak.exchangerates.api.APIRequest;

import java.util.*;

public class Calculator {
    private static final String[] rates= new String[]{"USD","UAH","RUB","BTC"};


    public void calculate(double eur){
        if(eur<0){
            System.out.println("Input param cannot be a negative value!");
            return;
        }

        Set<String> set = new HashSet<>();
        Collections.addAll(set, rates);

        APIRequest apiRequest=new APIRequest();
        Map map = apiRequest.getExchangeRates(set);

        for(String temp:rates){
            if(map.containsKey(temp)){
                double value = (double)map.get(temp);
                double result = eur*value;

                print("EUR",temp, eur, result, value);
            }
        }
    }

    private void print(String from, String to, double eur, double result, double rate){
        System.out.println(eur +" "+from+" = "+result+" "+to+" (exchange rate: "+rate+" )");
    }

    public Map<String,Double> calculate(double eur, String[] ratesGui) {
        if(eur<0){
            System.out.println("Input param cannot be a negative value!");
            return null;
        }

        Set<String> set = new HashSet<>();
        Collections.addAll(set, ratesGui);

        APIRequest apiRequest=new APIRequest();
        Map map = apiRequest.getExchangeRates(set);

        Map<String,Double> values = new HashMap<>();
        Iterator<Map.Entry<String, Double>> itr = map.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String, Double> entry = itr.next();
            values.put(entry.getKey(),entry.getValue()*eur);
        }

        return values;
    }
}

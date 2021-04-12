package sk.kosickaakademia.spivak.exchangerates;

import sk.kosickaakademia.spivak.exchangerates.api.APIRequest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main
{
    public static void main( String[] args )
    {
        Set<String> set = new HashSet<>();
        set.add("USD");
        set.add("UAH");
        set.add("RUB");
        set.add("ILA");
        set.add("BTC");

        Map map = new APIRequest().getExchangeRates(set);
        System.out.println(map.toString());
    }
}

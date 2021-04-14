package sk.kosickaakademia.spivak.exchangerates.database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.mongodb.MongoClient;
import org.json.simple.parser.ParseException;
import sk.kosickaakademia.spivak.exchangerates.log.Log;
import sk.kosickaakademia.spivak.exchangerates.util.Util;

public class Database {
    Log log = new Log();

    // this is the client that will provide a connection to the database
    private static final MongoClient mongoClient = new MongoClient();

    private DB db;
    private DBCollection table;

    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

    public Database(){
        log.info("Connecting to the database");
        //Create database and collection
        db = mongoClient.getDB("ExchangeRates");
        table = db.getCollection("statistics");
    }

    public boolean insertNewData(double eur, String[] ratesGui){
        BasicDBObject document = new BasicDBObject();
        Util util = new Util();

        document.put("date", util.getCurrentTime());
        document.put("amount", eur);
        document.put("currencies",ratesGui);

        table.insert(document);
        log.print("Data added to the database");
        return true;
    }


}

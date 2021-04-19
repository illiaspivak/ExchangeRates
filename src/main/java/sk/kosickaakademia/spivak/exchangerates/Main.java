package sk.kosickaakademia.spivak.exchangerates;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.spivak.exchangerates.api.APIRequest;
import sk.kosickaakademia.spivak.exchangerates.calculator.Calculator;
import sk.kosickaakademia.spivak.exchangerates.database.Database;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class Main extends Application
{
    private static final String[] rates= new String[]{"UAH","RUB","KZT","VEF","BTC"};

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GUI.fxml"));
        primaryStage.setTitle("Exchange Rates");
        primaryStage.setScene(new Scene(root, 350, 400));
        primaryStage.show();
    }


    public static void main( String[] args )
    {
        APIRequest apiRequest = new APIRequest();
        apiRequest.getExchangeRate("UAH");
        //SpringApplication.run(Main.class,args);
        //launch(args);
    }
}

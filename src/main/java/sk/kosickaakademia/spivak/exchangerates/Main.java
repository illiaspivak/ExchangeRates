package sk.kosickaakademia.spivak.exchangerates;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.kosickaakademia.spivak.exchangerates.api.APIRequest;
import sk.kosickaakademia.spivak.exchangerates.calculator.Calculator;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GUI.fxml"));
        primaryStage.setTitle("Exchange Rates");
        primaryStage.setScene(new Scene(root, 350, 200));
        primaryStage.show();
    }

    public static void main( String[] args )
    {
        launch(args);
    }
}

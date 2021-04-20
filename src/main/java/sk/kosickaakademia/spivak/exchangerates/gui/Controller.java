package sk.kosickaakademia.spivak.exchangerates.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sk.kosickaakademia.spivak.exchangerates.api.APIRequest;
import sk.kosickaakademia.spivak.exchangerates.calculator.Calculator;
import sk.kosickaakademia.spivak.exchangerates.database.Database;

import java.text.DecimalFormat;
import java.util.Map;

public class Controller {
    public TextField currency;
    APIRequest apiRequest = new APIRequest();

    private static final String[] rates= new String[]{"UAH","RUB","KZT","VEF","BTC"};
    public TextField amountOfMoney;
    public Button convert;
    public TextField convertUAH;
    public TextField convertRUB;
    public TextField convertKZT;
    public TextField convertVEF;
    public TextField convertBTC;
    public ComboBox selectCurrency;




    public void Convert(ActionEvent actionEvent) {
        String value = amountOfMoney.getText();
        double valueEur=Double.parseDouble(value);
        Calculator calculator =new Calculator();
        Database database = new Database();
        Map results = calculator.calculate(valueEur,rates);
        convertUAH.setText(convertTo2Decimal((double)results.get("UAH")));
        convertRUB.setText(convertTo2Decimal((double)results.get("RUB")));
        convertKZT.setText(convertTo2Decimal((double)results.get("KZT")));
        convertVEF.setText(convertTo2Decimal((double)results.get("VEF")));
        convertBTC.setText(results.get("BTC").toString());
        database.insertNewData(valueEur,rates);
    }

    private String convertTo2Decimal(double value){
        DecimalFormat df = new DecimalFormat("#.00");
        String angleFormated = df.format(value);
        return angleFormated;
    }

    @FXML
    private void initialize() {
        currency.setText("DOL");
        selectCurrency.getItems().setAll(apiRequest.getCurrency());
        selectCurrency.setValue("DOL");
    }
    public void SelectCurrency(ActionEvent actionEvent) {
        currency.setText((String) selectCurrency.getValue());
    }
}

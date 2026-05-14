package commands;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ListOfferingsCommand implements Command{
    private static String API_KEY = System.getenv("COINAPI_KEY");
    private static String[] ASSETS = {"BTC", "ETH", "XRP", "SOL", "ADA", "DOT", "DOGE", "AVAX", "LINK", "SHIB", "MATIC", "LTC", "UNI", "BCH", "STX", "FIL", "ARB", "VET", "NEAR", "OP"};
    private static String FILTER = String.join(",", ASSETS);
    private static String URL = "https://rest.coinapi.io/v1/assets?filter_asset_id=" + FILTER;
    

    @Override
    public String execute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("X-CoinAPI-Key", API_KEY)
                .GET()
                .build();

        String json = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        return json;

    }
}

package commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.Cryptocurrency;
import exceptions.WrongAssetIDException;
import helper.MyJDBC;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ListOfferCommand implements Command {
    private String asset_id;
    private final Map<String, Cryptocurrency> crypto;
    private static String API_KEY = System.getenv("COINAPI_KEY");
    private static Gson GSON = new Gson();

    public ListOfferCommand(String asset_id, Map<String, Cryptocurrency> crypto) {
        this.asset_id = asset_id;
        this.crypto = crypto;
    }


    @Override
    public String execute() throws IOException, InterruptedException {
        if (!crypto.containsKey(asset_id)) {
            throw new WrongAssetIDException("There isn't crypto with such asset_id!");
        }

        Cryptocurrency searchedCurrency = crypto.get(asset_id);

        if (searchedCurrency.isExpired()) {
            String URL = "https://rest.coinapi.io/v1/assets?filter_asset_id=" + asset_id;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("X-CoinAPI-Key", API_KEY)
                    .GET()
                    .build();

            String json = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            Type type = new TypeToken<List<Cryptocurrency>>() {
            }.getType();
            List<Cryptocurrency> resultList = GSON.fromJson(json, type);

            if (resultList != null && !resultList.isEmpty()) {
                searchedCurrency = resultList.get(0);
                searchedCurrency.setTimestamp(System.currentTimeMillis());

                MyJDBC.updateCrypto(searchedCurrency);
                crypto.put(asset_id, searchedCurrency);
            }
        }
        return searchedCurrency.print();

    }
}

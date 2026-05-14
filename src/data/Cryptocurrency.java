package data;

public class Cryptocurrency {
    private String asset_id;
    private String name;
    private double price_usd;
    private long timestamp;

    public Cryptocurrency(String asset_id, String name, double price_usd) {
        this.asset_id = asset_id;
        this.name = name;
        this.price_usd = price_usd;
        timestamp = System.currentTimeMillis();
    }

    public String getAsset_id() {
        return asset_id;
    }

    public String getName() {
        return name;
    }

    public double getPrice_usd() {
        return price_usd;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isExpired() {
        long thirtyMinutesInMs = 30 * 60 * 1000;
        return (System.currentTimeMillis() - this.timestamp) > thirtyMinutesInMs;
    }

    public void print() {
        System.out.println("------------------------------");
        System.out.println("Asset_ID: " + asset_id);
        System.out.println("Name: " + name);
        System.out.println("Price in USD: " + price_usd);
        System.out.println("------------------------------");
    }
}

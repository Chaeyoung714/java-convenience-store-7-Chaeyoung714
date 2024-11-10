package store.model.consumer;

public class PurchaseCost {
    private final int purchaseCost;
    private final int totalItemCost;

    public PurchaseCost(int purchaseCost, int totalItemCost) {
        this.purchaseCost = purchaseCost;
        this.totalItemCost = totalItemCost;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }

    public int getTotalItemCost() {
        return totalItemCost;
    }
}

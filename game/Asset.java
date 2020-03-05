// Copyright Cruceru Vlad 325CA

package game;

public enum Asset {
    Apple(true, 2, 2), Cheese(true, 3, 2), Bread(true, 4, 2), Chicken(true, 4, 2),
    Silk(false, 9, 4), Pepper(false, 8, 4), Barrel(false, 7, 4);
    private boolean legality;
    private int profit, penalty;

    Asset(final boolean legality, final int profit, final int penalty) {
        this.legality = legality;
        this.profit = profit;
        this.penalty = penalty;
    }

    // intoarce daca bunul e legal sau nu
    protected boolean isLegal() {
        return legality;
    }

    // intoarce profitul bunului
    protected int getProfit() {
        return profit;
    }

    // intoarce penalitatea bunului
    protected int getPenalty() {
        return penalty;
    }
}

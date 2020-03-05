// Copyright Cruceru Vlad 325CA

package game;

import java.util.LinkedList;

abstract class Player {
    protected String name; // numele jucatorului
    protected boolean sheriff; // true daca e serif, altfel false
    protected int coins, score; // monedele, scorul final
    protected Bag assetsInBag; // sacul jucatorului
    protected LinkedList<Asset> assetsInHand; // bunurile din mana
    protected int[] assetsOnMerchandStand; // bunurile de pe taraba
    static final int BAG_MAX_ASSETS = 5;
    static final int START_COINS = 50;
    static final int HAND_MAX_ASSETS = 6;
    static final int CONSTANT = 7;

    protected Player(final String name) {
        this.name = new String(name);
        sheriff = false;
        coins = START_COINS;
        assetsInBag = new Bag();
        assetsInHand = new LinkedList<Asset>();
        assetsOnMerchandStand = new int[CONSTANT];
        score = 0;
    }

    public final void setSheriff(final boolean sheriff) {
        this.sheriff = sheriff;
    }

    public final boolean isSheriff() {
        return sheriff;
    }

    protected final void getAssetsInHand(final LinkedList<Asset> gameAssets) {
        while (assetsInHand.size() < HAND_MAX_ASSETS && !gameAssets.isEmpty()) {
            assetsInHand.add(gameAssets.removeFirst());
        }
    }

    // pune bunurile din sac pe taraba
    protected final void fromBagToStand() {
        while (!assetsInBag.isEmpty()) {
            Asset asset = assetsInBag.extract();
            score += asset.getProfit();
            switch (asset) {
            case Apple:
                assetsOnMerchandStand[0]++;
                break;
            case Cheese:
                assetsOnMerchandStand[1]++;
                break;
            case Bread:
                assetsOnMerchandStand[2]++;
                break;
            case Chicken:
                assetsOnMerchandStand[3]++;
                break;
            case Silk:
                assetsOnMerchandStand[6]++;
                for (int i = 0; i <= 2; i++) {
                    assetsOnMerchandStand[1]++;
                    score += Asset.Cheese.getProfit();
                }
                break;
            case Pepper:
                assetsOnMerchandStand[5]++;
                for (int i = 0; i <= 1; i++) {
                    assetsOnMerchandStand[3]++;
                    score += Asset.Chicken.getProfit();
                }
                break;
            default:
                assetsOnMerchandStand[4]++;
                for (int i = 0; i <= 1; i++) {
                    assetsOnMerchandStand[2]++;
                    score += Asset.Bread.getProfit();
                }
                break;
            }
        }
    }

    // adauga banii la scor
    public void addCoins() {
        score += coins;
    }

    // adauga bonusul la scor
    public void addBonus(final int bonus) {
        score += bonus;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    /**
     * folosit pentru acordarea bonusului.
     *
     * @param asset bunul care trebuie verif
     * @return cate bunuri de acel tip sunt pe taraba
     */
    public int getAssetOnMerchandStand(final Asset asset) {
        switch (asset) {
        case Apple:
            return assetsOnMerchandStand[0];
        case Cheese:
            return assetsOnMerchandStand[1];
        case Bread:
            return assetsOnMerchandStand[2];
        default:
            return assetsOnMerchandStand[3];
        }

    }

    // umple sacul
    // metoda abstracta pt ca fiecare jucator are propria strategie de a-l umple
    protected abstract void bagFill();

    /**
     * verifica sacul jucatorilor.metoda abstracta fiindca fiecare jucator are
     * propria strategie.
     *
     * @param player jucatorul care trebuie verif
     * @return bunurile confiscate
     */
    public abstract LinkedList<Asset> bagInspect(Player player);
}

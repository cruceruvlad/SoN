// Copyright Cruceru Vlad 325CA

package game;

import java.util.LinkedList;

public final class Bag {
    private LinkedList<Asset> assets; // bunurile din sac
    private int bribe; // mita care se gaseste in sac
    private Asset declaredType; // bunul declarat al sacului

    protected Bag() {
        assets = new LinkedList<Asset>();
        bribe = 0;
        declaredType = null;
    }

    /**
     * adauga in sac.
     *
     * @param asset bunul ce trebuie adaugat
     */
    protected void add(final Asset asset) {
        assets.add(asset);
    }

    /**
     * scoate din sac.
     *
     * @return bunul ce trebuie scos
     */
    protected Asset extract() {
        return assets.removeFirst();
    }

    /**
     * declara bunul sacului.
     *
     * @param asset bunul ce trebuie declarat
     */
    protected void declareType(final Asset asset) {
        declaredType = asset;
    }

    /**
     * @return bunul declarat al sacului
     */
    protected Asset getDeclaredType() {
        return declaredType;
    }

    /**
     * @return daca sacul e gol
     */
    protected boolean isEmpty() {
        return assets.isEmpty();
    }

    /**
     * pune mita in sac.
     *
     * @param bribe mita ce trebuie pusa
     */
    protected void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    /**
     * @return mita din sac
     */
    protected int getBribe() {
        return bribe;
    }

    /**
     * @return cate bunuri sunt in sac
     */
    protected int size() {
        return assets.size();
    }
}

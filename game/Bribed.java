// Copyright Cruceru Vlad 325CA

package game;

import java.util.Collections;

//din enunt se inteege ca are strategie derivata din cea a lui basic
public class Bribed extends Basic {
    static final int FIRST_BRIBE = 5;
    static final int SECOND_BRIBE = 10;

    protected Bribed(final String name) {
        super(name);
    }

    /**
     * @Override
     */
    protected void bagFill() {
        int illegalitiesCount = Collections.frequency(assetsInHand, Asset.Silk);
        illegalitiesCount += Collections.frequency(assetsInHand, Asset.Pepper);
        illegalitiesCount += Collections.frequency(assetsInHand, Asset.Barrel);
        if (coins < FIRST_BRIBE || illegalitiesCount == 0) {
            super.bagFill();
            assetsInBag.setBribe(0);
        } else if ((coins >= FIRST_BRIBE && coins < SECOND_BRIBE) || illegalitiesCount <= 2) {
            while (assetsInHand.contains(Asset.Silk) && assetsInBag.size() < 2) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Silk)));
            }
            while (assetsInHand.contains(Asset.Pepper) && assetsInBag.size() < 2) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Pepper)));
            }
            while (assetsInHand.contains(Asset.Barrel) && assetsInBag.size() < 2) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Barrel)));
            }
            assetsInBag.declareType(Asset.Apple);
            assetsInBag.setBribe(FIRST_BRIBE);
            coins -= FIRST_BRIBE;
        } else {
            while (assetsInBag.size() < BAG_MAX_ASSETS && assetsInHand.contains(Asset.Silk)) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Silk)));
            }
            while (assetsInBag.size() < BAG_MAX_ASSETS && assetsInHand.contains(Asset.Pepper)) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Pepper)));
            }
            while (assetsInBag.size() < BAG_MAX_ASSETS && assetsInHand.contains(Asset.Barrel)) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Barrel)));
            }
            assetsInBag.declareType(Asset.Apple);
            assetsInBag.setBribe(SECOND_BRIBE);
            coins -= SECOND_BRIBE;
        }
    }
}

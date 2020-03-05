// Copyright Cruceru Vlad 325CA

package game;

import java.util.LinkedList;
import java.util.Collections;

public class Basic extends Player {

    protected Basic(final String name) {
        super(name);
    }

    /**
     * stabileste cu ce bun umple sacul.
     *
     * @return bunul cu care se umple sacul
     */
    private Asset fillingAsset() {
        // numara cate bunuri are in mana
        int appleCount = Collections.frequency(assetsInHand, Asset.Apple);
        int cheeseCount = Collections.frequency(assetsInHand, Asset.Cheese);
        int breadCount = Collections.frequency(assetsInHand, Asset.Bread);
        int chickenCount = Collections.frequency(assetsInHand, Asset.Chicken);
        int silkCount = Collections.frequency(assetsInHand, Asset.Silk);
        int pepperCount = Collections.frequency(assetsInHand, Asset.Pepper);
        int barrelCount = Collections.frequency(assetsInHand, Asset.Barrel);

        // returneaza ce bun trebuie pus in sac
        int max = Math.max(Math.max(appleCount, cheeseCount), Math.max(breadCount, chickenCount));
        if (max != 0) {
            if (max == chickenCount) {
                if (max == breadCount) {
                    int chickenIndex = assetsInHand.indexOf(Asset.Chicken);
                    int breadIndex = assetsInHand.indexOf(Asset.Bread);
                    if (chickenIndex < breadIndex) {
                        return Asset.Chicken;
                    }
                    return Asset.Bread;
                }
                return Asset.Chicken;
            }
            if (max == breadCount) {
                return Asset.Bread;
            }
            if (max == cheeseCount) {
                return Asset.Cheese;
            }
            return Asset.Apple;
        }
        if (silkCount != 0) {
            return Asset.Silk;
        }
        if (pepperCount != 0) {
            return Asset.Pepper;
        }
        if (barrelCount != 0) {
            return Asset.Barrel;
        }
        return null;
    }

    /**
     * @Override
     */
    protected void bagFill() {
        Asset fillingAsset = fillingAsset();
        if (fillingAsset.isLegal()) {
            while (assetsInBag.size() < BAG_MAX_ASSETS && assetsInHand.contains(fillingAsset)) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(fillingAsset)));
            }
            assetsInBag.declareType(fillingAsset);
        } else {
            assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(fillingAsset)));
            assetsInBag.declareType(Asset.Apple);
        }

    }

    /**
     * @Override
     */
    public LinkedList<Asset> bagInspect(final Player player) {
        LinkedList<Asset> confiscatedAssets = new LinkedList<Asset>();
        Asset declaredType = player.assetsInBag.getDeclaredType();
        int bagSize = player.assetsInBag.size();
        int coinsToReceive = 0;
        int coinsToGive = 0;
        player.coins += player.assetsInBag.getBribe();
        for (int i = 0; i < bagSize; i++) {
            Asset asset = player.assetsInBag.extract();
            if (declaredType != asset || !asset.isLegal()) {
                coinsToReceive += asset.getPenalty();
                confiscatedAssets.add(asset);
            } else {
                coinsToGive += asset.getPenalty();
                player.assetsInBag.add(asset);
            }
        }
        if (coinsToReceive > 0) {
            coins += coinsToReceive;
            player.coins -= coinsToReceive;
        } else {
            coins -= coinsToGive;
            player.coins += coinsToGive;
        }
        return confiscatedAssets;
    }
}

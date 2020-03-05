// Copyright Cruceru Vlad 325CA

package game;

import java.util.LinkedList;

// din enunt se inteege ca are strategie derivata din cea a lui basic
public class Greedy extends Basic {
    private int roundCount; // numara runda sa vada daca e para

    protected Greedy(final String name) {
        super(name);
        roundCount = 0;
    }

    /**
     * @Override
     */
    protected void bagFill() {
        super.bagFill();
        roundCount++;
        if (roundCount % 2 == 0 && assetsInBag.size() < BAG_MAX_ASSETS) {
            if (assetsInHand.contains(Asset.Silk)) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Silk)));
            } else if (assetsInHand.contains(Asset.Pepper)) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Pepper)));
            } else if (assetsInHand.contains(Asset.Barrel)) {
                assetsInBag.add(assetsInHand.remove(assetsInHand.indexOf(Asset.Barrel)));
            }
        }

    }

    /**
     * @Override
     */
    public LinkedList<Asset> bagInspect(final Player player) {
        if (player.assetsInBag.getBribe() == 0) {
            return super.bagInspect(player);
        }
        coins += player.assetsInBag.getBribe();
        return new LinkedList<Asset>();
    }
}

// Copyright Cruceru Vlad 325CA

package game;

import java.util.LinkedList;
import java.util.*;

public final class TimeToPlayTheGame {
    private Player[] players;
    private int round;
    private LinkedList<Asset> gameAssets;

    public TimeToPlayTheGame(final LinkedList<Strategy> strategies,
                            final LinkedList<Asset> gameAssets) {
        players = new Basic[strategies.size()];
        addPlayers(strategies);
        this.gameAssets = new LinkedList<Asset>();
        this.gameAssets.addAll(gameAssets);
        round = 0;
    }

    /**
     * adauga jucatori din lista.
     *
     * @param strategies lista cu jucatori
     */
    private void addPlayers(final LinkedList<Strategy> strategies) {
        Strategy strategy;
        int i = -1;
        while (!strategies.isEmpty()) {
            i++;
            strategy = strategies.removeFirst();
            switch (strategy) {
            case bribed:
                players[i] = new Bribed("BRIBED");
                break;
            case greedy:
                players[i] = new Greedy("GREEDY");
                break;
            default:
                players[i] = new Basic("BASIC");
                break;
            }
        }
    }

    public void nextRound() {
        round++;
    }

    // fiecare jucator isi completeaza mana
    public void getAssetsInHand() {
        for (int i = 0; i < players.length; i++) {
            players[i].getAssetsInHand(gameAssets);
        }
    }

    // atribuie rolul e sherif
    public void nextSheriff() {
        players[(players.length + (round - 2)) % players.length].setSheriff(false);
        players[(round - 1) % players.length].setSheriff(true);
    }

    // fiecare jucator cu exceptia sherifului isi umple sacul
    public void fillBags() {
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isSheriff()) {
                players[i].bagFill();
            }
        }
    }

    // seriful inspecteaza bunurile jucatorilor si pune cele confiscate in gramada
    public void sheriffInspect() {
        LinkedList<Asset> confiscatedAssets = new LinkedList<Asset>();
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isSheriff()) {
                confiscatedAssets = players[(round - 1) % players.length].bagInspect(players[i]);
                gameAssets.addAll(confiscatedAssets);
            }
        }
    }

    // fiecare jucator isi pune bunurile pe taraba
    public void fromBagToStand() {
        for (int i = 0; i < players.length; i++) {
            players[i].fromBagToStand();
        }
    }

    public void calculateScores() {
        for (int i = 0; i < players.length; i++) {
            players[i].addCoins();
        }
        addBonusFor(Asset.Apple, 20, 10);
        addBonusFor(Asset.Cheese, 15, 10);
        addBonusFor(Asset.Bread, 15, 10);
        addBonusFor(Asset.Chicken, 10, 5);
    }

    public void showScores() {
        sortByScore();
        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName() + ": " + players[i].getScore());
        }
    }

    /**
     * sorteaza vectorul de jucatori dupa tipul de bun si ofera bonusul.
     *
     * @param asset bunul dupa care se sorteaza
     * @param king  bonusul king
     * @param queen bonusul queen
     */
    private void addBonusFor(final Asset asset, final int king, final int queen) {
        Arrays.sort(players, new Comparator<Player>() {
            @Override
            public int compare(final Player o1, final Player o2) {
                return o2.getAssetOnMerchandStand(asset) - o1.getAssetOnMerchandStand(asset);
            }
        });
        players[0].addBonus(king);
        int i = 1;
        while (i < players.length) {
            if (players[i].getAssetOnMerchandStand(asset)
                    == players[0].getAssetOnMerchandStand(asset)) {
                players[i].addBonus(king);
            } else {
                players[i].addBonus(queen);
                int j = i + 1;
                while (j < players.length) {
                    if (players[i].getAssetOnMerchandStand(asset)
                            == players[j].getAssetOnMerchandStand(asset)) {
                        players[j].addBonus(queen);
                    } else {
                        break;
                    }
                    j++;
                }
                break;
            }
            i++;
        }
    }

    // sorteaza jucatorii dupa scor
    public void sortByScore() {
        Arrays.sort(players, new Comparator<Player>() {
            @Override
            public int compare(final Player o1, final Player o2) {
                return o2.getScore() - o1.getScore();
            }
        });
    }
}

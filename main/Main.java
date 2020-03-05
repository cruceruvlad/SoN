// Copyright Cruceru Vlad 325CA

package main;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

import game.TimeToPlayTheGame;
import game.Strategy;
import game.Asset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Main {
    private Main() {
    }

    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    /**
     * Se mapeaza id-urile cu numele bunurilor.
     *
     * @param m mapa in care se retine maparea
     */
    public static void mapInit(final Map<Integer, String> m) {
        final int appleID = 0;
        final int cheeseID = 1;
        final int breadID = 2;
        final int chickenID = 3;
        final int silkID = 10;
        final int pepperID = 11;
        final int barrelID = 12;

        m.put(appleID, "Apple");
        m.put(cheeseID, "Cheese");
        m.put(breadID, "Bread");
        m.put(chickenID, "Chicken");
        m.put(silkID, "Silk");
        m.put(pepperID, "Pepper");
        m.put(barrelID, "Barrel");
    }

    /**
     * Se convertesc id-urile in bunuri.
     *
     * @param gameAssets   lista cu bunuri a jocului
     * @param myAssetOrder lista cu id-uri citita de la tastatura
     * @param m            mapa folosita pt conversie
     */
    public static void id2assetConverter(final LinkedList<Asset> gameAssets,
                                        final List<Integer> myAssetOrder,
            final Map<Integer, String> m) {
        String auxiliary = new String();
        while (!myAssetOrder.isEmpty()) {
            auxiliary = m.get(myAssetOrder.remove(0));
            gameAssets.add(Asset.valueOf(auxiliary));
        }
    }

    /**
     * Se convertesc stringurile citite de la tastatura in strategii.
     *
     * @param strategies    lista de strategii
     * @param myPlayerOrder stringurile citite de la tastatura
     */
    public static void string2strategyConverter(final LinkedList<Strategy> strategies,
            final List<String> myPlayerOrder) {
        while (!myPlayerOrder.isEmpty()) {
            strategies.add(Strategy.valueOf(myPlayerOrder.remove(0)));
        }
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();

        Map<Integer, String> id = new HashMap<Integer, String>();
        mapInit(id);
        LinkedList<Asset> gameAssets = new LinkedList<Asset>();
        id2assetConverter(gameAssets, gameInput.getAssetIds(), id);
        LinkedList<Strategy> strategies = new LinkedList<Strategy>();
        string2strategyConverter(strategies, gameInput.getPlayerNames());

        // jocul in sine: fiecare jucator trebuie sa fie de 2 ori serif
        int gameLength = strategies.size();
        TimeToPlayTheGame game = new TimeToPlayTheGame(strategies, gameAssets);
        for (int i = 0; i < gameLength * 2; i++) {
            game.nextRound();
            game.getAssetsInHand();
            game.nextSheriff();
            game.fillBags();
            game.sheriffInspect();
            game.fromBagToStand();
        }
        game.calculateScores();
        game.showScores();
    }
}

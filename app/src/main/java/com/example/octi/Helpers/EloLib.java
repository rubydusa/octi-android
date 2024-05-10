package com.example.octi.Helpers;

import com.example.octi.Models.User;

public class EloLib {
    private static final int sensitivty = 20;

    public static void updateUserScores(User winner, User loser) {
        double winnerNewScore = EloLib.newScore(winner.getElo(), loser.getElo(), true);
        double loserNewScore = EloLib.newScore(loser.getElo(), winner.getElo(), false);

        winner.setElo((int) Math.floor(winnerNewScore));
        loser.setElo((int) Math.floor(loserNewScore));
    }

    private static double newScore(double myScore, double opponentScore, boolean isWin) {
        double expected = EloLib.expectedScore(myScore, opponentScore);
        double actual = isWin ? 1 : 0;
        return myScore + EloLib.sensitivty * (actual - expected);
    }

    private static double expectedScore(double myScore, double opponentScore) {
        double exponent = (opponentScore - myScore) / 400.0;
        double pow = Math.pow(10, exponent);

        return 1 / (1 + pow);
    }
}

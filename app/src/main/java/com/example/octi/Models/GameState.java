package com.example.octi.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GameState implements Cloneable {
    private Game.Team turn;

    private Integer redProngCount;
    private Integer greenProngCount;
    private ArrayList<Pod> pods;
    private ArrayList<ColoredCell> coloredCells;

    private boolean inMove = false;
    private Pod selectedPod = null;
    private ArrayList<Jump> inMoveJumps = new ArrayList<>();
    private static final ArrayList<Vector2D> prong2Direciton;

    static {
        prong2Direciton = new ArrayList<>(8);
        prong2Direciton.add(new Vector2D(1, 0));
        prong2Direciton.add(new Vector2D(1, -1));
        prong2Direciton.add(new Vector2D(0, -1));
        prong2Direciton.add(new Vector2D(-1, -1));
        prong2Direciton.add(new Vector2D(-1, 0));
        prong2Direciton.add(new Vector2D(-1, 1));
        prong2Direciton.add(new Vector2D(0, 1));
        prong2Direciton.add(new Vector2D(1, 1));
    }

    // default state
    public GameState() {
        turn = Game.Team.RED;

        redProngCount = 12;
        greenProngCount = 12;

        pods = new ArrayList<>();
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(1, 1)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(2, 1)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(3, 1)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(4, 1)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(1, 5)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(2, 5)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(3, 5)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(4, 5)));

        coloredCells = new ArrayList<>();
        coloredCells.add(new ColoredCell(new Vector2D(1, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(2, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(3, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(4, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(1, 5), Game.Team.RED));
        coloredCells.add(new ColoredCell(new Vector2D(2, 5), Game.Team.RED));
        coloredCells.add(new ColoredCell(new Vector2D(3, 5), Game.Team.RED));
        coloredCells.add(new ColoredCell(new Vector2D(4, 5), Game.Team.RED));
    }

    ///
    /// Getters
    ///
    public Game.Team getTurn() {
        return turn;
    }
    public List<Pod> getPods() {
        return pods;
    }
    public List<ColoredCell> getColoredCells() {
        return coloredCells;
    }
    public boolean isInMove() {
        return inMove;
    }
    public Pod getSelectedPod() {
        return selectedPod;
    }
    public ArrayList<Jump> getInMoveJumps() {
        return inMoveJumps;
    }

    // these getters are never used but without them prong counts
    // don't update in firebase apparently
    public int getRedProngCount() {
        return redProngCount;
    }

    public int getGreenProngCount() {
        return greenProngCount;
    }

    public int getTeamProngCount(Game.Team team) {
        if (team == Game.Team.RED) {
            return redProngCount;
        } else {
            return greenProngCount;
        }
    }

    @Nullable
    public Pod findPod(Vector2D position) {
        for (Pod pod:
                pods) {
            if (pod.getPosition().equals(position)) {
                return pod;
            }
        }

        return null;
    }

    ///
    /// Win Condition
    ///
    /// MUST BE CHECKED AFTER TURN TRANSFERRED
    @Nullable
    public Game.Team getWinner() {
        if (inMove) {
            return null;
        }
        // first scenario: a pod got to one of the finishing cells
        // doesn't take into account a board state where both teams have pods on the finishing cells
        // since is impossible

        HashMap<Vector2D, Game.Team> coloredCellsMap = new HashMap<>();
        for (ColoredCell coloredCell: coloredCells) {
            coloredCellsMap.put(coloredCell.getPosition(), coloredCell.getColor());
        }

        int redPods = 0;
        int greenPods = 0;

        for (Pod pod: pods) {
            if (pod.getTeam() == Game.Team.RED) {
                redPods++;
            } else {
                greenPods++;
            }

            if (coloredCellsMap.containsKey(pod.getPosition())) {
                Game.Team cellColor = coloredCellsMap.get(pod.getPosition());
                if (pod.getTeam() != cellColor) {
                    return pod.getTeam();
                }
            }
        }

        // second scenario: one team doesn't have pods, the other wins.
        // doesn't consider hypothetical impossible situation where both have zero pods
        if (greenPods == 0) {
            return Game.Team.RED;
        } else if (redPods == 0) {
            return Game.Team.GREEN;
        }

        // third scenario: check if the team currently playing exhausted every move. If so, other team wins
        if (!hasPossibleNextMoves(turn)) {
            if (turn == Game.Team.RED) {
                return Game.Team.GREEN;
            } else {
                return Game.Team.RED;
            }
        }

        // THERE ARE NOT TIES IN OCTI
        // OCTI IS A BATTLE, NOT A CONTEST

        return null;
    }

    ///
    /// Pod selection
    ///

    public void selectPod(Vector2D position) {
        Pod pod = findPod(position);
        if (pod == null) {
            throw new RuntimeException("could not select pod");
        }

        if (pod.getTeam() != turn) {
            throw  new RuntimeException("selected pod of incorrect team");
        }
        selectedPod = pod;
    }

    public void deselectPod() {
        selectedPod = null;
    }

    ///
    /// Next Possible Moves
    ///

    public ArrayList<Vector2D> nextPossibleMoves() {
        Pod pod = selectedPod;
        if (pod == null) {
            throw new RuntimeException("Pod not selected");
        }

        return nextPossibleMoves(pod);
    }

    private ArrayList<Vector2D> nextPossibleMoves(Pod pod) {
        ArrayList<Vector2D> result = new ArrayList<>();

        ArrayList<Boolean> prongs = pod.getProngs();
        outer:
        for (int i = 0; i < 8; i++) {
            boolean isProng = prongs.get(i);
            if (!isProng) {
                continue;
            }

            Vector2D direction = prong2Direciton.get(i);
            Vector2D next1 = pod.getPosition().add(direction);
            Vector2D next2 = next1.add(direction);

            // jumping twice over the same pod is not allowed
            for (Jump jump: inMoveJumps) {
                if (jump.getOver().equals(next1)) {
                    continue outer;
                }
            }

            // jump option
            if (findPod(next1) != null && findPod(next2) == null && GameState.inBound(next2)) {
                result.add(next2);
            }

            // simple move option
            if (!inMove) {
                if (findPod(next1) == null && GameState.inBound(next1)) {
                    result.add(next1);
                }
            }
        }

        return result;
    }

    ///
    /// Move Types
    ///

    public void advanceSelectedPod(Vector2D to) {
        Pod pod = selectedPod;
        if (pod == null) {
            throw new RuntimeException("no selected pod to advance");
        }

        int prong = inferProngFromChange(pod.getPosition(), to);

        boolean hasProng = pod.getProngs().get(prong);
        if (!hasProng) {
            throw new RuntimeException("selected pod doesn't have prong");
        }

        Vector2D direction = prong2Direciton.get(prong);
        Vector2D next1 = pod.getPosition().add(direction);
        Vector2D next2 = next1.add(direction);

        inMove = true;

        // jump option
        if (findPod(next1) != null && findPod(next2) == null) {
            inMoveJumps.add(new Jump(false, next1));
            selectedPod.setPosition(next2);
        }

        // simple move option
        if (findPod(next1) == null) {
            selectedPod.setPosition(next1);
            nextTurn();
            cleanUp();
        }
    }

    // caller needs to catch
    public void placeProng(Vector2D target, int prong) throws RuntimeException {
        int prongCount = getTeamProngCount(turn);
        if (prongCount <= 0) {
            throw new RuntimeException("no prongs to place");
        }

        // no target pod
        Pod targetPod = findPod(target);
        if (targetPod == null) {
            throw new RuntimeException("can't place prong cause pod does not exist");
        }

        // prong already exists
        if (targetPod.getProngs().get(prong)) {
            throw new RuntimeException("prong already exists");
        }

        targetPod.getProngs().set(prong, true);
        useProng(turn);
        nextTurn();

        cleanUp();
    }

    @NonNull
    @Override
    public GameState clone() {
        try {
            GameState clone = (GameState) super.clone();
            clone.pods = (ArrayList<Pod>) pods.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void finalizeState() {
        // clear eaten pods
        HashSet<Pod> deadPods = new HashSet<>();
        for (Jump jump: inMoveJumps) {
            Pod pod = findPod(jump.getOver());
            if (pod == null) {
                throw new IllegalStateException("can't find jumped over pod");
            }
            if (jump.isEat()) {
                int prongCount = 0;
                for (int i = 0; i < 8; i++) {
                    if (pod.getProngs().get(i)) {
                        prongCount++;
                    }
                }

                if (getTurn() == Game.Team.RED) {
                    redProngCount += prongCount;
                } else {
                    greenProngCount += prongCount;
                }

                deadPods.add(pod);
            }
        }

        ArrayList<Pod> newPods = new ArrayList<>();
        for (Pod pod: pods) {
            if (!deadPods.contains(pod)) {
                newPods.add(pod);
            }
        }

        pods = newPods;
        nextTurn();
        cleanUp();
    }

    private int inferProngFromChange(Vector2D base, Vector2D to) {
        for (int i = 0; i < 8; i++) {
            Vector2D direction = prong2Direciton.get(i);
            Vector2D next1 = base.add(direction);
            Vector2D next2 = next1.add(direction);

            if (to.equals(next1) || to.equals(next2)) {
                return i;
            }
        }

        throw new RuntimeException("no valid prong inferred");
    }

    private boolean hasPossibleNextMoves(Game.Team team) {
        int prongsLeft = getTeamProngCount(team);

        for (Pod pod: pods) {
            if (pod.getTeam() != team) {
                continue;
            }

            if (!nextPossibleMoves(pod).isEmpty()) {
                return true;
            }

            if (prongsLeft > 0) {
                for (boolean prongExists: pod.getProngs()) {
                    if (!prongExists) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void useProng(Game.Team team) {
        if (team == Game.Team.RED) {
            redProngCount--;
        } else {
            greenProngCount--;
        }
    }

    private void nextTurn() {
        if (turn == Game.Team.RED) {
            turn = Game.Team.GREEN;
        } else {
            turn = Game.Team.RED;
        }
    }

    private void cleanUp() {
        inMove = false;
        deselectPod();
        inMoveJumps.clear();
    }

    static private boolean inBound(Vector2D position) {
        return position.getX() >= 0 && position.getX() <= 5 && position.getY() >= 0 && position.getY() <= 6;
    }
}

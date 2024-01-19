package com.quarto.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;

import java.util.Arrays;

import java.util.Random;

import java.util.Random;

public class QLearning {

    private static double[] weights = {1.0, 1.0, 1.0, 1.0}; // w1, w2, w3, w4
    private static double alpha = 0.1;
    private static double gamma = 0.9;
    private static double epsilon = 0.1;

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();  // Instantiate the game board
        int episodes = 10000;

        for (int episode = 0; episode < episodes; episode++) {
            playGame(gameBoard);
        }

        // Print the learned weights
        printWeights();
    }

    static void playGame(GameBoard gameBoard) {
        Random rand = new Random();

        // Reset the game board for a new episode
        gameBoard = new GameBoard();

        // Perform actions in the environment
        while (!gameBoard.isGameFinished()) {
            // Choose action with epsilon-greedy policy
            Move action = epsilonGreedyPolicy(gameBoard);

            // Take action and observe next state and reward
            int[] nextState = takeAction(gameBoard, action);
            double reward = calculateReward(nextState);

            // Update weights
            updateWeights(nextState, reward);
        }
    }

    static Move epsilonGreedyPolicy(GameBoard gameBoard) {
        Random rand = new Random();
        Move action;

        if (rand.nextDouble() < epsilon) {
            // Exploration: choose a random action
            action = getRandomAction(gameBoard);
        } else {
            // Exploitation: choose the action with the highest Q-value
            action = getBestAction(gameBoard);
        }

        return action;
    }

    static Move getRandomAction(GameBoard gameBoard) {
        Random rand = new Random();
        int tileId = rand.nextInt(16);
        Piece piece = new Piece(rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean());
        return new Move(piece, tileId);
    }

    static Move getBestAction(GameBoard gameBoard) {
        Move bestAction = null;
        double maxValue = Double.NEGATIVE_INFINITY;

        // Iterate over all possible moves
        for (int i = 0; i < 16; i++) {
            for (Piece piece : gameBoard.getWhitesList()) {
                Move move = new Move(piece, i);
                if (gameBoard.isValidMove(move)) {
                    double value = calculateQValue(move);
                    if (value > maxValue) {
                        maxValue = value;
                        bestAction = move;
                    }
                }
            }
        }

        return bestAction;
    }

    static int[] takeAction(GameBoard gameBoard, Move action) {
        // Check if the selected move is valid
        while (!gameBoard.isValidMove(action)) {
            // If the move is invalid, choose another action
            action = epsilonGreedyPolicy(gameBoard);
        }

        // Apply the chosen action to the game board
        gameBoard.addPieceToBoard(action);

        // Convert the game board state to the format expected by Q-learning
        return convertGameBoardToState(gameBoard);
    }

    static int[] convertGameBoardToState(GameBoard gameBoard) {
        int[] state = new int[4];  // For counting 1's, 2's, 3's, and 4's
        Map<String, Integer> propertyCounts = new HashMap<>();  // For counting pieces with the same properties

        // Iterate over the board and count occurrences
        for (int i = 0; i < gameBoard.getBoard().length; i++) {
            for (int j = 0; j < gameBoard.getBoard()[i].length; j++) {
                Piece piece = gameBoard.getBoard()[i][j];

                if (piece != null) {
                    // Count 1's
                    state[0]++;

                    // Count pieces with the same properties
                    String propertiesKey = piece.toString();
                    propertyCounts.put(propertiesKey, propertyCounts.getOrDefault(propertiesKey, 0) + 1);
                }
            }
        }

        // Count occurrences based on propertyCounts
        for (int count : propertyCounts.values()) {
            if (count == 2) {
                // 2 same properties and 2 empty
                state[1]++;
            } else if (count == 3) {
                // 3 and one empty
                state[2]++;
            } else if (count == 4) {
                // 4 same properties
                state[3]++;
            }
        }

        return state;
    }

 

    static double calculateQValue(Move action) {
        // Calculate the Q-value based on the current state and weights
        int[] state = convertGameBoardToState(new GameBoard());  // Example: use a new empty game board for demonstration
        double qValue = weights[0] * state[0] + weights[1] * state[1] + weights[2] * state[2] + weights[3] * state[3];
        return qValue;
    }

    static double calculateReward(int[] nextState) {
        // Extract counts from nextState
        int count1 = nextState[0];
        int count2SameAnd2Empty = nextState[1];
        int count3And1Empty = nextState[2];
        int count4Same = nextState[3];
    
        // Assign different rewards based on counts
        double reward = 0.0;
    
        // 4 same properties in a row, column, or diagonal
        reward += count4Same * 100.0;
    
        // 3 same properties and 1 empty
        reward += count3And1Empty * 10.0;
    
        // 2 same properties and 2 empty
        reward += count2SameAnd2Empty * 1.0;
    
        // 1 property and 3 empty
        reward += count1 * 0.1;
    
        return reward;
    }

    static void updateWeights(int[] nextState, double reward) {
        double currentQValue = calculateQValue(new Move(new Piece(true, true, true, true), 0));  // Example: use a dummy move for demonstration
        double maxNextQValue = getMaxQValue(nextState);

        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + alpha * (reward + gamma * maxNextQValue - currentQValue) * nextState[i];
        }
    }

    static double getMaxQValue(int[] state) {
        double maxQValue = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    double value = calculateQValue(new Move(new Piece(true, true, true, true), 0));  // Example: use a dummy move for demonstration
                    if (value > maxQValue) {
                        maxQValue = value;
                    }
                }
            }
        }

        return maxQValue;
    }

    static void printWeights() {
        // Print the learned weights for debugging or analysis
        System.out.println("Learned Weights:");
        for (int i = 0; i < weights.length; i++) {
            System.out.println("w" + (i + 1) + ": " + weights[i]);
        }
    }
}

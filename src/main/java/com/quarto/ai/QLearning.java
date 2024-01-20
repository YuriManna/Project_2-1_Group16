package com.quarto.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.Player;


public class    QLearning {

    private static double[] weights = {1.0, 1.0, 1.0, 1.0}; // w1, w2, w3, w4
    private static double alpha = 0.1;
    private static double gamma = 0.9;
    private static double epsilon = 0.3;
    private static Random rand = new Random();



//    whitePlayer = new Player(isPlayer1Human, true, this.gameBoard.getWhitesList(), this);
//    blackPlayer = new Player(isPlayer2Human, false, this.gameBoard.getBlacksList(), this);
//    currentPlayer = whitePlayer;
//    opponent = blackPlayer;

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();  // Instantiate the game board
        int episodes = 10000;

        for (int episode = 0; episode < episodes; episode++) {
            System.out.println("Episode " + (episode + 1));
            playGame(gameBoard);
        }

        // Print the learned weights
        printWeights();
    }

    static void playGame(GameBoard gameBoard) {


        // Reset the game board for a new episode
        gameBoard = new GameBoard();


        // Perform actions in the environment
        while (!gameBoard.isGameFinished()) {
            // Whites' turn
            Move whiteAction = epsilonGreedyPolicy(gameBoard, true);
            int[] whiteNextState = takeAction(gameBoard, whiteAction, true);
            double whiteReward = calculateReward(whiteNextState);
            updateWeights(whiteNextState, whiteReward);

            if (gameBoard.isGameFinished()) {
                break;
            }

            // Blacks' turn (assuming a random opponent for illustration)
            Move blackAction = epsilonGreedyPolicy(gameBoard, false);
            int[] blackNextState = takeAction(gameBoard, blackAction, false);
            double blackReward = calculateReward(blackNextState);
            updateWeights(blackNextState, blackReward);
        }

    }

    static Move epsilonGreedyPolicy(GameBoard gameBoard, boolean isWhitesTurn) {

        Move action;


        if (rand.nextDouble() < epsilon) {
            // Exploration: choose a random action
            action = getRandomAction(gameBoard, isWhitesTurn);
        } else {
            // Exploitation: choose the action with the highest Q-value
            action = getBestAction(gameBoard, isWhitesTurn);
        }

        return action;
    }

    static Move getRandomAction(GameBoard gameBoard, boolean isWhitesTurn) {

        int tileId = rand.nextInt(16);
        Piece piece;
        if(isWhitesTurn) {
            System.out.println("Whites List: " + gameBoard.getWhitesList());
            piece = gameBoard.getWhitesList().get(rand.nextInt(gameBoard.getBlacksList().size()));
        } else {
            System.out.println("Blacks List: " + gameBoard.getBlacksList());
            piece = gameBoard.getBlacksList().get(rand.nextInt(gameBoard.getBlacksList().size()));
        }
        return new Move(piece, tileId);
    }



    static Move getBestAction(GameBoard gameBoard, boolean isWhitesTurn) {
        Move bestAction = null;
        double maxValue = Double.NEGATIVE_INFINITY;

        List<Piece> playerPieces = isWhitesTurn ? gameBoard.getWhitesList() : gameBoard.getBlacksList();

        // Iterate over all possible moves
        for (int i = 0; i < 16; i++) {
            for (Piece piece : playerPieces) {
                Move move = new Move(piece, i);
                if (gameBoard.isValidMove(move)) {
                    //double value = calculateQValue(move);
                    double value = calculateQValue(convertGameBoardToState(gameBoard));
                    if (value > maxValue) {
                        maxValue = value;
                        bestAction = move;
                    }
                }
            }
        }
        return bestAction;
    }

    static int[] takeAction(GameBoard gameBoard, Move action, boolean isWhitesTurn) {
        // Check if the selected move is valid
        while (!gameBoard.isValidMove(action)) {
            // If the move is invalid, choose another action
            action = epsilonGreedyPolicy(gameBoard, isWhitesTurn);
        }

        // Apply the chosen action to the game board
        System.out.println("Whites: " + gameBoard.getWhitesList());
        System.out.println("Blacks: " + gameBoard.getBlacksList());
        gameBoard.addPieceToBoard(action);

        //remove piece from list
        if (isWhitesTurn) {
            gameBoard.getWhitesList().remove(action.getPiece());
        } else {
            gameBoard.getBlacksList().remove(action.getPiece());
        }

        showBoard(gameBoard);
        // Convert the game board state to the format expected by Q-learning;
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

 

//    static double calculateQValue(Move action) {
//        // Calculate the Q-value based on the current state and weights
//        int[] state = convertGameBoardToState(new GameBoard());  // Example: use a new empty game board for demonstration
//
//        double qValue = weights[0] * state[0] + weights[1] * state[1] + weights[2] * state[2] + weights[3] * state[3];
//        return qValue;
//    }


    static double calculateQValue(int[] state) {
        // Calculate the Q-value based on the current state and weights
        double qValue = 0.0;
        for (int i = 0; i < weights.length; i++) {
            qValue += weights[i] * state[i];
        }
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
        //double currentQValue = calculateQValue(new Move(new Piece(true, true, true, true), 0));  // Example: use a dummy move for demonstration
        double currentQValue = calculateQValue(convertGameBoardToState(new GameBoard()));
        double maxNextQValue = getMaxQValue(nextState);

        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + alpha * (reward + gamma * maxNextQValue - currentQValue) * nextState[i];
        }
    }

//    static double getMaxQValue(int[] state) {
//        double maxQValue = Double.NEGATIVE_INFINITY;
//
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                for (int k = 0; k < 4; k++) {
//                    double value = calculateQValue(new Move(new Piece(true, true, true, true), 0));  // Example: use a dummy move for demonstration
//                    if (value > maxQValue) {
//                        maxQValue = value;
//                    }
//                }
//            }
//        }
//
//        return maxQValue;
//    }

    static double getMaxQValue(int[] state) {
        double maxQValue = Double.NEGATIVE_INFINITY;
    // Iterate over all possible moves to find the maximum Q-value
            for (int i = 0; i < 16; i++) {
                for (Piece piece : new GameBoard().getWhitesList()) {  // Use a new empty game board for demonstration
                    Move move = new Move(piece, i);
                    if (new GameBoard().isValidMove(move)) {  // Use a new empty game board for demonstration
                        double value = calculateQValue(convertGameBoardToState(new GameBoard()));  // Use a new empty game board for demonstration
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

    public static void showBoard(GameBoard board) {
        System.out.println("Current Board:");
        int id = 0;

        // Run through the board and display the pieces
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Piece piece = board.getBoard()[i][j];

                if (piece != null) {
                    // Display the piece details
                    System.out.print(piece.toString());
                } else {
                    // Display an empty space with an ID
                    System.out.printf("--%02d-", id);
                }

                System.out.print(" ");
                id++;
            }
            System.out.println(); // Move to the next row
        }

        System.out.println(); // Add an empty line for better readability
    }
}

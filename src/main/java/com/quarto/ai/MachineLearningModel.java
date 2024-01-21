//package com.quarto.ai;
//
//import com.quarto.Model.GameBoard;
//import com.quarto.Model.Piece;
//import org.deeplearning4j.nn.api.Model;
//import org.nd4j.linalg.api.ndarray.INDArray;
//
//public class MachineLearningModel {
//
//    private Model trainedModel;  // Your Deeplearning4j model
//
//    public MachineLearningModel(Model trainedModel) {
//        this.trainedModel = trainedModel;
//    }
//
//    // Method to predict a score using the trained machine learning model
//    public int predict(GameBoard board, Piece chosenPiece, int depth) {
//        double[] features = extractFeatures(board, chosenPiece, depth);
//        INDArray input = // Convert features to INDArray according to your model input requirements
//
//
//                INDArray output = trainedModel.output(input);
//        // Extract the predicted score from the output
//
//        return (int) output.getDouble(0);  // Adjust based on your actual output structure
//    }
//
//    // You need to implement this method to convert game state to feature vectors
//    private double[] extractFeatures(GameBoard board, Piece chosenPiece, int depth) {
//        // Implement feature extraction based on your specific game representation
//        // Convert features into a double array for the machine learning model input
//        // ...
//
//        return new double[]{/* feature values */};
//    }
//}




















package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;
import org.deeplearning4j.nn.api.Model;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.HashMap;
import java.util.Map;

public class MachineLearningModel {

    private Model trainedModel;  // Your Deeplearning4j model
    private Map<String, Double> weights;  // Linear regression weights
    private EvaluationFunction evalFunction;  // Evaluation function

    public MachineLearningModel(Model trainedModel, EvaluationFunction evalFunction) {
        this.trainedModel = trainedModel;
        this.weights = initializeWeights();
        this.evalFunction = evalFunction;
    }

    // Initialize weights for linear regression
    private Map<String, Double> initializeWeights() {
        // You can customize the initial weights based on your requirements
        Map<String, Double> initialWeights = new HashMap<>();
        // Example: initialize weights with small random values
        initialWeights.put("feature1", 0.1);
        initialWeights.put("feature2", 0.2);
        // Add more features and weights as needed
        return initialWeights;
    }

    // Method to predict a score using the linear regression model
    public double predict(GameBoard board, Piece chosenPiece, int depth) {
        double[] features = extractFeatures(board, chosenPiece, depth);
        return calculateLinearCombination(features, weights);
    }

    // Update weights based on the rewards obtained during the game
    public void updateWeights(GameBoard board, Piece chosenPiece, int depth) {
        double reward = evalFunction.evaluateBoard(board, chosenPiece, null);  // Use null for QuartoGame as it's not needed here
        double[] features = extractFeatures(board, chosenPiece, depth);
        updateLinearRegressionWeights(features, weights, reward);
    }

    // You need to implement this method to convert game state to feature vectors
    private double[] extractFeatures(GameBoard board, Piece chosenPiece, int depth) {
        // Implement feature extraction based on your specific game representation
        // Convert features into a double array for the linear regression model input
        // ...

        return new double[]{/* feature values */};
    }

    // Calculate the linear combination of features and weights
    private double calculateLinearCombination(double[] features, Map<String, Double> weights) {
        double result = 0.0;
        for (int i = 0; i < features.length; i++) {
            result += features[i] * weights.get("feature" + (i + 1));
        }
        return result;
    }

    // Update weights based on the rewards using simple gradient descent
    private void updateLinearRegressionWeights(double[] features, Map<String, Double> weights, double reward) {
        double learningRate = 0.01;  // Adjust based on your requirements
        double predicted = calculateLinearCombination(features, weights);
        double error = reward - predicted;

        // Update each weight
        for (int i = 0; i < features.length; i++) {
            String featureName = "feature" + (i + 1);
            weights.put(featureName, weights.get(featureName) + learningRate * error * features[i]);
        }
    }
}

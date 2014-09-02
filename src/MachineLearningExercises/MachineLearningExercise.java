/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MachineLearningExercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Dan
 */
public class MachineLearningExercise {
    
    private float[][] data;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            MachineLearningExercise exercise1 = new MachineLearningExercise();
            BufferedReader dataFile = new BufferedReader(new FileReader("ex1data1.txt"));
            String line;
            exercise1.data = new float[97][2];
            int i = 0;
            while( (line = dataFile.readLine()) != null)
            {
                String[] strArray = line.split(",");
                exercise1.data[i][0] = Float.parseFloat(strArray[0]);// population
                exercise1.data[i][1] = Float.parseFloat(strArray[1]);// profit
                i++;
            }
            //gradientDescent(learning rate, iterations);
            float[] params = exercise1.gradientDescent(0.01f,0);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a population:");
            String popStr = br.readLine();
            float hypothesis = exercise1.hypothesis(params,Float.parseFloat(popStr));
            System.out.printf("Hypothesized profit: %f\n",hypothesis);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    private float hypothesis(float[] theta, float x)
    {
        return theta[0] + (theta[1] * x);
    }
    
    private float computeCost(float[] theta)
    {
        float m = data.length;
        float sum = 0;
        for(int i = 0; i < data.length; i++)
            sum += hypothesis(theta, data[i][0]) - data[i][1];
        sum = (float)Math.pow(sum, 2);
        return ((1f/(2f*m))*sum);
    }
    
    private float thetaZeroSum(float[] theta)
    {
        float sum = 0;
        for(int i = 0; i < data.length; i++)
            sum += hypothesis(theta, data[i][0]) - data[i][1];
        return sum;
    }
    
    private float thetaOneSum(float[] theta)
    {
        float sum = 0;
        for(int i = 0; i < data.length; i++)
            sum += (hypothesis(theta, data[i][0]) - data[i][1]) * data[i][0];
        return sum;
    }
    
    private float[] gradientDescent(float learningRate, int iterations)
    {
        // set Initial cost function parameters(theta) to zero for cost function h(x) = 0 + 0x
        float[] theta = { 0, 0 };
        float m = data.length;
        
        float preCost,cost = 0;
        
        if(iterations > 0)
        {
            for(int i = 0; i < iterations; i++)
            {
                theta[0] = theta[0] - (learningRate*(1f/m)*thetaZeroSum(theta));
                theta[1] = theta[1] - (learningRate*(1f/m)*thetaOneSum(theta));
                cost = computeCost(theta);
            }
            
            System.out.printf("Final cost parameters are: %f, %f\n", theta[0], theta[1]);
        }
        else
        {
            do{
                preCost = cost;
                theta[0] = theta[0] - (learningRate*(1f/m)*thetaZeroSum(theta));
                theta[1] = theta[1] - (learningRate*(1f/m)*thetaOneSum(theta));
                cost = computeCost(theta);
            }while(!hasConverged(preCost,cost));

            System.out.printf("gradient descent has converged. Cost parameters are: %f, %f\n", theta[0], theta[1]);
        }
        System.out.printf("final cost: %f\n",cost);
        
        return theta;
    }
    
    // TODO
    private void featureNormalize()
    {
        
    }
    
    // TODO
    private void normalEquation()
    {
        
    }
    
    private boolean hasConverged(float preCost, float curCost)
    {
        if((preCost - curCost) < 0.001) // Close enough
            return true;
        return false;
    }
}

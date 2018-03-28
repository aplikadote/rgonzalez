/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lpsolve.assignment.model;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;

/**
 *
 * @author rgonzalez
 */
public class AssignmentLpModel {

    private LpSolve lp;

    private double[][] cost = {
        {1, 2, 1, 3},
        {1, 3, 4, 5},
        {1, 1, 5, 2},
        {7, 6, 4, 1}
    };

    public AssignmentLpModel() {
    }

    private void create() throws LpSolveException {
        int rows = cost.length;
        int cols = cost[0].length;
        lp = LpSolve.makeLp(rows, cols);
        //lp.setTimeout(timeout);
        //lp.setLpName(name);

        if (lp.getLp() == 0) {
            System.out.println("Couldn't construct a new model...");
            return;
        }

        // SETTING VARIABLE NAME AND BINARY
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int varIndex = cols * i + j + 1;
                lp.setColName(varIndex, String.format("X%s%s", (i + 1), (j + 1)));
                lp.setBinary(varIndex, true);
            }
        }

        // CONSTRAINTS
        lp.setAddRowmode(true);
        int[] varIndexs = new int[cols];
        double[] varValues = new double[cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                varIndexs[j] = cols * i + j + 1;
                varValues[j] = 1;
            }
            lp.addConstraintex(cols, varValues, varIndexs, LpSolve.EQ, 1);
        }

        varIndexs = new int[rows];
        varValues = new double[rows];
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                varIndexs[i] = j + cols * i + 1;
                varValues[i] = 1;
            }
            lp.addConstraintex(rows, varValues, varIndexs, LpSolve.EQ, 1);
        }

        lp.setAddRowmode(false);

        // OBJETIVE FUNCTION
        int[] varIndexArray = new int[rows * cols];
        double[] varValueArray = new double[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int varIndex = cols * i + j + 1;
                varIndexArray[cols * i + j] = varIndex;
                varValueArray[cols * i + j] = cost[i][j];
            }
        }

        lp.setObjFnex(rows * cols, varValueArray, varIndexArray);
        lp.setMinim();
        lp.writeLp("assignment");
    }

    private void solve() throws LpSolveException {
//        lp.setVerbose(LpSolve.FULL);
        int ret = lp.solve();

        switch (ret) {
            case -2:
                System.out.println("Out of Memory");
                break;
            case LpSolve.OPTIMAL:
                System.out.println("Optimal");
                print();
                if (lp.getLp() != 0) {
                    lp.deleteLp();
                }
                break;
            case LpSolve.SUBOPTIMAL:
                System.out.println("Suboptimal");
                print();
                if (lp.getLp() != 0) {
                    lp.deleteLp();
                }
                break;
            case 2:
                System.out.println("The model is infeasible");
                break;
            case 3:
                System.out.println("The model is unbounded");
                break;
            case 4:
                System.out.println("The model is degenerative");
                break;
            case 5:
                System.out.println("Numerical failure encountered");
                break;
            case 7:
                System.out.println("A timeout occurred");
                break;
            default:
                System.out.println("Error " + ret + ". Check lp_solve API reference");
        }
    }

    public void print() throws LpSolveException {
        System.out.println("Objective value: " + lp.getObjective());

        int rows = cost.length;
        int cols = cost[0].length;
        int size = rows * cols;
        double[] resultArray = new double[size];
        lp.getVariables(resultArray);
        for (int j = 0; j < size; j++) {
            System.out.println(lp.getColName(j + 1) + ": " + resultArray[j]);
        }

    }

    public static void main(String[] args) {
        try {
            AssignmentLpModel model = new AssignmentLpModel();
            model.create();
            model.solve();
        } catch (LpSolveException ex) {
            ex.printStackTrace();
        }
    }
}

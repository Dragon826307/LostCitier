package com.dragon826307.lostcity.client.light_puzzle;

import java.util.Arrays;

public class LightPuzzleSolver {
    //仅支持正方形网格
    protected static boolean[] lightPuzzleSolver(int[][] light) {
        int i = 0;
        int[] index = new int[light.length*light.length];
        int lightAmount = 0;
        //扫描棋盘 建立索引
        for (int[] ints : light) for (int col = 0; col < light.length; col++) {
            if (ints[col] != 2) {
                index[lightAmount++] = i;
            }
            i++;
        }
        index = Arrays.copyOfRange(index, 0, lightAmount);
        i = 0;
        //构建增广矩阵
        boolean[][] matrix = new boolean[lightAmount][light.length*light.length+1];
        for (int row = 0; row < light.length; row++) {
            for (int col = 0 ; col < light.length; col++) {
                if (light[row][col] != 2) {
                    boolean[] Neighbor = checkNeighbor(row, col, light);
                    if (Neighbor[0]) matrix[i][index[i]-LightPuzzleGui.Size]=true;
                    if (Neighbor[1]) matrix[i][index[i]+LightPuzzleGui.Size]=true;
                    if (Neighbor[2]) matrix[i][index[i]-1]=true;
                    if (Neighbor[3]) matrix[i][index[i]+1]=true;
                    matrix[i][light.length*light.length]= light[row][col] == 0;
                    matrix[i++][row*light.length+col]= true;
                }
            }
        }
        //高斯消元
        for (int row = 0; row < lightAmount; row++) {
            //检查主元是否为0
            if(!matrix[row][index[row]]){
                for (i = row; i < lightAmount; i++){
                    //寻找为1的元素
                    if(matrix[i][index[row]]){
                        boolean[] line = matrix[row];
                        matrix[row]=matrix[i];
                        matrix[i]=line;
                        break;
                    }
                }
                if(i==lightAmount)continue;
            }
            i= row;
            //主元为1或找到为1的元素，开始异或运算
            while (i < lightAmount-1) {
                i++;
                if (matrix[i][index[row]]) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        matrix[i][j]=matrix[row][j]^matrix[i][j];
                    }
                }
            }
        }
        //无解判断
        i = 0;
        for (boolean[] row : matrix) {
            if (Arrays.equals(Arrays.copyOfRange(row, 0, row.length - 2), new boolean[row.length - 2])&&row[row.length-1]) {
                i = 1;
                break;
            }
        }
        if (i == 1) return null;
        //反向遍历回代求解
        boolean[] solutionVector = new boolean[matrix[0].length];
        for (i = index.length-1; i >= 0; i--) {
            if (matrix[i][index[i]]) {
                boolean b = matrix[i][matrix[i].length-1];
                for (int j = matrix[i].length-1; j >= 0; j--) {
                    if (matrix[i][j]) {
                        b = solutionVector[j]^b;
                    }
                    if (index[i]==j) break;
                }
                solutionVector[index[i]]=b;
            }
        }
        return solutionVector;
    }
    //返回[上，下，左，右]
    private static boolean[] checkNeighbor(int row, int col, int[][] light){
        return new boolean[]{
                row - 1 >= 0 && light[row-1][col] != 2,
                row + 1 <= light.length-1 && light[row+1][col] != 2,
                col - 1 >= 0 && light[row][col-1] != 2,
                col + 1 <= light.length-1 && light[row][col+1] != 2
        };
    }
}

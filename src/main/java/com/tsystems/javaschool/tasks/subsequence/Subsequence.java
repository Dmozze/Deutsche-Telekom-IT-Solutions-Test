package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null){
            throw new IllegalArgumentException();
        }
        int ind_x = 0, ind_y = 0;
        int len_x = x.size(), len_y = y.size();
        while (ind_x < len_x && ind_y < len_y){
            if (x.get(ind_x).equals(y.get(ind_y))){
                ind_x++;
            }
            ind_y++;
        }
        return ind_x == len_x;
    }
}

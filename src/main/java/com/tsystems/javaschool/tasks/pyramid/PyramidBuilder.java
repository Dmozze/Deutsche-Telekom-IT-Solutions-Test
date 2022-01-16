package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        int log = number_of_lvls(inputNumbers.size());
        if (log == -1){
            throw new CannotBuildPyramidException();
        }
        for (int i = 0; i < inputNumbers.size(); i++){
            if (inputNumbers.get(i) == null){
                throw new CannotBuildPyramidException();
            }
        }
        int[][] res = new int[log][2 * log - 1];
        Collections.sort(inputNumbers);
        int l = (2 * log - 1) / 2;
        int r = l;
        int safe_l = l;
        int lvl = 0;
        for (int i = 0; i < inputNumbers.size(); i++){
            if (inputNumbers.get(i) == null){
                throw new CannotBuildPyramidException();
            }
            if (l > r){
                lvl++;
                safe_l--;
                l = safe_l;
                r++;
            }
            res[lvl][l] = inputNumbers.get(i);
            l+=2;
        }
        return res;
    }

    private int number_of_lvls(long s){
        long aprox = (long) Math.sqrt(2 * s);
        for (long i = aprox - 5; i <= aprox + 5; i++){
           if (i >= 0 &&  (1 + i) * i / 2 == s){
               return (int)i;
           }
        }
        return -1;
    }


}

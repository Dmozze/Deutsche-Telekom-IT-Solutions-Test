package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    int index = 0;
    Token cur_tok = Token.EMPTY;
    String cur_str = "";
    String inner_st;
    DecimalFormat df2;

    boolean getDouble(){
        int check_index = index;
        cur_tok = Token.DOUBLE;
        while (check_index < inner_st.length()){
            char cur_char = inner_st.charAt(check_index);
            if (cur_char >= '0' && cur_char <= '9' || cur_char == '.'){
                check_index++;
                continue;
            }
            break;
        }
        cur_str = inner_st.substring(index - 1, check_index);
        index = check_index;
        return true;
    }

    void nextToken(){
        if (cur_tok == Token.ERROR){
            return;
        }
        while (index < inner_st.length()){
            char cur_char = inner_st.charAt(index++);
            switch (cur_char){
                case(' '):
                    continue;
                case ('+'):
                    cur_tok = Token.ADD;
                    return;
                case('-'):
                    cur_tok = Token.SUB;
                    return;
                case('*'):
                    cur_tok = Token.MUL;
                    return;
                case('/'):
                    cur_tok = Token.DIV;
                    return;
                case('('):
                    cur_tok = Token.OPEN_BR;
                    return;
                case(')'):
                    cur_tok = Token.CLOSE_BR;
                    return;
                default:
                    if (cur_char >= '0' && cur_char <= '9' && getDouble()) {
                        return;
                    }
                        cur_tok = Token.ERROR;
                        return;
                    }
            }
        cur_tok = Token.EOF;
    }

    double first_lvl(){
        double first_value = second_lvl();
        while (true) {
            if (cur_tok == Token.ADD) {
                nextToken();
                double second_value = second_lvl();
                first_value = first_value + second_value;
                continue;
            }
            if (cur_tok == Token.SUB) {
                nextToken();
                double second_value = second_lvl();
                first_value = first_value - second_value;
                continue;
            }
            return first_value;
        }
    }

    double second_lvl(){
        double first_value = third_lvl();
        while (true) {
            if (cur_tok == Token.MUL) {
                nextToken();
                double second_value = third_lvl();
                first_value = first_value * second_value;
                continue;
            }
            if (cur_tok == Token.DIV) {
                nextToken();
                double second_value = third_lvl();
                if (second_value == 0){
                    cur_tok = Token.ERROR;
                    return -1;
                }
                first_value = first_value / second_value;
                continue;
            }
            return first_value;
        }
    }


    double third_lvl(){
        if (cur_tok == Token.OPEN_BR) {
            nextToken();
            double temp = first_lvl();
            if (cur_tok == Token.CLOSE_BR){
                nextToken();
                return temp;
            }
            cur_tok = Token.ERROR;
            return -1;
        }
        if (cur_tok == Token.DOUBLE){
            double save_val = 0;
            try {
                save_val = Double.parseDouble(cur_str);
                String ll = df2.format(save_val);
                save_val = Double.parseDouble(ll);
            }
             catch (Exception e){
                cur_tok = Token.ERROR;
             }
            nextToken();
            return save_val;
        }
        cur_tok = Token.ERROR;
        return -1;
    }

    public String evaluate(String statement) {
        df2 = new DecimalFormat("##.####");
        df2.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        if (statement == null || statement.equals("")){
            return null;
        }
        inner_st = statement;
        nextToken();
        double save_val = first_lvl();
        if (cur_tok == Token.EOF){
            return df2.format(save_val);
        }
        return null;
    }

}

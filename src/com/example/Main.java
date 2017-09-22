package com.example;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int QUARTER = 25;
    private static final int DIME = 10;
    private static final int NICKEL = 5;
    private static final int PENNY = 1;
//    private static final String MONEY_PATTERN = "(([1-9]\\d*)?\\d))(\\.\\d\\d)?$";

    public static void main(String[] args) {
        Scanner s = null;
        Double money;
        int dollars;
        int cents;
        try {
            s = new Scanner(System.in);
            System.out.print("input amount of change to make (e.g. 4.50): $");
            while (s.hasNext()) {
                String operator = s.next();
                if (operator.equals("quit")) {
                    break;
                }
                try {
//                    Pattern pattern = Pattern.compile(MONEY_PATTERN);
//                    Matcher matcher = pattern.matcher(operator);
//                    if(matcher.matches()){
//
//                    } else {
//
//                    }
                    DecimalFormat df = new DecimalFormat("#.00");
                    money = df.parse(operator).doubleValue();
                    dollars = (int) (money / 1);
                    String cent = String.format("%.0f",((money-dollars)*100));
                    cents = Integer.valueOf(cent);
                    if (money > 0) {
                        int mod = 0;
                        int numQuarters = (cents / QUARTER) + (dollars * 4);
                        System.out.println(numQuarters + " quarters");
                        mod = cents % QUARTER;
                        int numDimes = (mod / DIME);
                        System.out.println(numDimes + " dimes");
                        mod = mod % DIME;
                        int numNickels = (mod / NICKEL);
                        System.out.println(numNickels + " nickels");
                        mod = mod % NICKEL;
                        int numPennies = (mod / PENNY);
                        System.out.println(numPennies + " pennies");
                    } else {
                        System.out.println("invalid input, must be positive and greater than zero");
                    }
                } catch (NumberFormatException|ParseException e) {
                    System.out.println("invalid input, double required");
                }
                System.out.print("input amount of change to make (e.g. 4.50): $");
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}

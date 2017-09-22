package com.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int QUARTER = 25;
    private static final int DIME = 10;
    private static final int NICKEL = 5;
    private static final int PENNY = 1;
    private static final String MONEY_PATTERN = "^(\\$)?(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$";

    public static void main(String[] args) {
        Scanner s = null;
        Double money;
        int dollars;
        int cents;
        try {
            s = new Scanner(System.in);
            System.out.print("input amount of change to make (e.g. 4.50): ");
            while (s.hasNext()) {
                String operator = s.next();
                if (operator.equals("quit")) {
                    break;
                }
                try {
                    Pattern pattern = Pattern.compile(MONEY_PATTERN);
                    Matcher matcher = pattern.matcher(operator);
                    if(matcher.matches()){
                        money = Double.valueOf(operator.replaceAll("[^\\d.]+", ""));
                        dollars = (int) (money / 1);
                        cents = Integer.valueOf(String.format("%.0f",((money-dollars)*100)));
                            int mod;
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
                        System.out.println("invalid input, amount must be positive, valid and follow pattern $x.yy");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("invalid input, amount must be a number and follow pattern $x.yy");
                }
                System.out.print("\ninput amount of change to make (e.g. 4.50): ");
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}

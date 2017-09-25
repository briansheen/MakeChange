package com.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int QUARTER = 25;
    private static final int DIME = 10;
    private static final int NICKEL = 5;
    private static final int PENNY = 1;
    private static final String MONEY_PATTERN = "^(\\$)?(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$";

    public static void main(String[] args) {
        Set<Integer> coins = new HashSet<>();
        coins.add(25);
        coins.add(10);
        coins.add(5);
        coins.add(1);
        try (Scanner s = new Scanner(System.in)) {
            System.out.print("input amount of change to make (e.g. 4.50): ");
            System.out.println("Welcome to the Change Making Machine!");
            System.out.print("Would you like to make change with standard U.S. coins (standard) or with U.S. coins + up to two custom coin denominations (custom)? : ");
            while (s.hasNext()) {
                String operator = s.nextLine();
                if (operator.equals("quit")) {
                    break;
                }
                if (operator.equals("standard")) {
                    System.out.print("input amount of change to make (e.g. 4.50): ");
                    while (s.hasNext()) {
                        operator = s.nextLine();
                        if (operator.equals("back")) {
                            break;
                        }
                        getChange(operator);
                        System.out.print("\ninput amount of change to make (e.g. 4.50): ");
                    }
                }
                if (operator.equals("custom")) {
                    Integer coin1 = null;
                    Integer coin2 = null;
                    System.out.print("input first custom coin denomination in cents (e.g. 7): ");
                    while (s.hasNext()) {
                        operator = s.nextLine();
                        try {
                            coin1 = Integer.valueOf(operator);
                            if (coin1 <= 0 || coin1 > 99 || coin1 == QUARTER || coin1 == NICKEL || coin1 == DIME || coin1 == PENNY) {
                                coin1 = null;
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("invalid input, first cent value must be a non-standard U.S. coin denomination that is a positive integer between 1-99");
                            System.out.print("\ninput first custom coin denomination in cents (e.g. 7): ");
                        }
                        if (coin1 != null) {
                            break;
                        }
                    }
                    System.out.print("input second custom coin denomination in cents (e.g. 42 or n/a): ");
                    while (s.hasNext()) {
                        operator = s.nextLine();
                        if (operator.equals("n/a")) {
                            coin2 = 0;
                        } else {
                            try {
                                coin2 = Integer.valueOf(operator);
                                if (coin2 <= 0 || coin2 > 99 || coin2 == coin1 || coin2 == QUARTER || coin2 == NICKEL || coin2 == DIME || coin2 == PENNY) {
                                    coin2 = null;
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("invalid input, second cent value must be a unique non-standard U.S. coin denomination that is a positive integer between 1-99");
                                System.out.print("\ninput second custom coin denomination in cents (e.g. 42 or n/a): ");
                            }
                        }
                        if (coin2 != null) {
                            break;
                        }
                    }
                    if (coin1 != null && coin2 != null) {
                        coins.add(coin1);
                        coins.add(coin2);
                        System.out.print("input amount of change to make (e.g. 4.50): ");
                        while (s.hasNext()) {
                            operator = s.nextLine();
                            if (operator.equals("back")) {
                                break;
                            }
                            getCustomChange(operator, coins, coin1, coin2);
                            System.out.print("\ninput amount of change to make (e.g. 4.50): ");
                        }
                    }
                }
                System.out.print("\nWould you like to make change with standard U.S. coins (standard) or with U.S. coins + up to two custom coin denominations (custom)? : ");
            }
        }
    }


    public static void getChange(String operator) {
        Pattern pattern = Pattern.compile(MONEY_PATTERN);
        Matcher matcher = pattern.matcher(operator);
        if (matcher.matches()) {
            Double money = Double.valueOf(operator.replaceAll("[^\\d.]+", ""));
            int dollars = (int) (money / 1);
            int cents = Integer.valueOf(String.format("%.0f", ((money - dollars) * 100)));
            int numQuarters = (cents / QUARTER) + (dollars * 4);
            System.out.println(numQuarters + " quarters");
            int mod = cents % QUARTER;
            int numDimes = (mod / DIME);
            System.out.println(numDimes + " dimes");
            mod = mod % DIME;
            int numNickels = (mod / NICKEL);
            System.out.println(numNickels + " nickels");
            mod = mod % NICKEL;
            int numPennies = (mod / PENNY);
            System.out.println(numPennies + " pennies");
        } else {
            System.out.println("invalid input, amount must be a valid positive number and follow pattern $x.yy");
        }
    }

    public static void getCustomChange(String operator, Set<Integer> coins, Integer coin1, Integer coin2) {
        Pattern pattern = Pattern.compile(MONEY_PATTERN);
        Matcher matcher = pattern.matcher(operator);
        if (matcher.matches()) {
            Double money = Double.valueOf(operator.replaceAll("[^\\d.]+", ""));
            int total = Integer.valueOf(String.format("%.0f", ((money - 0) * 100)));
            List<Integer> values = new ArrayList<>();
            for (Integer i : coins) {
                values.add(i);
            }
            Collections.sort(values);
            Collections.reverse(values);
            for (Integer coin : values) {
                if (coin.equals(coin1)) {
                    int numCoin1 = (total/coin);
                    System.out.println(numCoin1 + " coin1(" + coin + ")");
                    total = total % coin1;
                }
                if (coin.equals(coin2)) {
                    int numCoin2 = (total/coin);
                    System.out.println(numCoin2 + " coin2(" + coin + ")");
                    total = total % coin2;
                }
                if (coin.equals(QUARTER)) {
                    int numQuarters = (total/coin);
                    System.out.println(numQuarters + " quarters");
                    total = total % QUARTER;
                }
                if (coin.equals(DIME)) {
                    int numDimes = (total/coin);
                    System.out.println(numDimes + " dimes");
                    total = total % DIME;
                }
                if (coin.equals(NICKEL)) {
                    int numNickels = (total/coin);
                    System.out.println(numNickels + " nickels");
                    total = total % NICKEL;
                }
                if (coin.equals(PENNY)) {
                    int numPennies = (total/coin);
                    System.out.println(numPennies + " pennies");
                    total = total % PENNY;
                }
            }
        } else {
            System.out.println("invalid input, amount must be a valid positive number and follow pattern $x.yy");
        }

    }
}

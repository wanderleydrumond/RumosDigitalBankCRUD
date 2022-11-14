package pt.drumond.rumos;

import pt.drumond.rumos.app.Bank;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        bank.loadDatabase();
        bank.run(scanner);
    }
}

package pt.drumond.rumos.app;

import pt.drumond.rumos.model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Application controller class.
 */
public class Bank {
    private ArrayList<Customer> customers = new ArrayList<>();

    /**
     * Contains the application core.
     *
     * @param scanner field to be filled on menu
     */
    public void run(Scanner scanner) {
        boolean flag = false;
        do {
            System.out.print("""
                        Welcome to RUMOS DIGITAL BANK
                                        
                        Choose your option:
                        0. Quit
                        1. Insert new client
                        2. Search client by NIF
                        3. Update client by NIF
                        4. Delete client by NIF
                        5. Display all clients
                                        
                        Option:\040""");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> { // CREATE CUSTOMER
                    Customer customer = new Customer();
                    boolean isValidated = false;

                    insertNif(scanner, customer, isValidated);
                    insertName(scanner, customer);
                    insertPassword(scanner, customer);
                    insertPhone(scanner, customer, isValidated);
                    insertMobile(scanner, customer, isValidated);
                    insertEmail(scanner, customer, isValidated);
                    insertProfession(scanner, customer);

                    System.out.print("Insert date of birth (yyyy/MM/dd): ");
                    String birthDate = scanner.nextLine();
                    customer.setBirthDate(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")));

                    customers.add(customer);
                    System.out.println("Client successfully created");
                    customers.forEach(System.out::println);
                }

                case 2 -> { // SEARCH CUSTOMER
                    System.out.print("Enter client NIF number: ");
                    System.out.println(findByNif(scanner.nextLine(), scanner));
                }

                case 3 -> { // UPDATE CUSTOMER
                    System.out.print("Insert the client NIF to be updated: ");
                    Customer customer = findByNif(scanner.nextLine(), scanner);
                    System.out.print("""
                                What do you want to update?
                                                        
                                0. Nothing, I changed my mind
                                1. Name
                                2. Password
                                3. Phone number
                                4. Mobile number
                                5. e-mail
                                6. Profession
                                                        
                                Option:\040""");

                    option = scanner.nextInt();
                    scanner.nextLine();
                    switch (option) {
                        case 1 -> insertName(scanner, customer);
                        case 2 -> insertPassword(scanner, customer);
                        case 3 -> insertPhone(scanner, customer, false);
                        case 4 -> insertMobile(scanner, customer, false);
                        case 5 -> insertEmail(scanner, customer, false);
                        case 6 -> insertProfession(scanner, customer);
                        default -> {
                            break;
                        }
                    }
                    customers.set(customers.indexOf(customer), customer);
                    customers.forEach(System.out::println);
                }

                case 4 -> { // DELETE CUSTOMER
                    System.out.print("Insert the client NIF to be deleted: ");
                    Customer customer = findByNif(scanner.nextLine(), scanner);
                    if (customers.removeIf(customerElement -> customerElement.getNif().equals(customer.getNif()))) {
                        System.out.println("Client successfully deleted");
                    }
                }

                case 5 -> { // DISPLAY ALL CUSTOMERS
                    customers.forEach(System.out::println);
                }

                default -> System.exit(0);
            }
            scanner.next();
            System.out.print("Do you want something else? (Y)es/(N)o: ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                flag = true;
            } else {
                scanner.close();
            }
        } while (flag);

    }

    /**
     * Fills the NIF field.
     *
     * @param scanner field to be filled
     * @param customer object that contains the attribute NIF to be inserted
     * @param isValidated flag that controns if the NIF is set or not
     */
    private void insertNif(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert nif: ");
            String nif = scanner.nextLine();
            isValidated = customer.setNif(nif);
        }
    }

    /**
     * Fills the Phone field.
     *
     * @param scanner field to be filled
     * @param customer object that contains the attribute phone to be inserted
     * @param isValidated flag that controns if the phone is set or not
     */
    private void insertPhone(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert phone number: ");
            isValidated = customer.setPhone(scanner.nextLine());
        }
    }

    /**
     * Fills the password field.
     *
     * @param scanner field to be filled
     * @param customer object that contains the attribute password to be inserted
     */
    private void insertPassword(Scanner scanner, Customer customer) {
        System.out.print("Insert password: ");
        customer.setPassword(scanner.nextLine());
    }

    /**
     * Fills the name field.
     *
     * @param scanner field to be filled
     * @param customer object that contains the attribute name to be inserted
     */
    private void insertName(Scanner scanner, Customer customer) {
        System.out.print("Insert name: ");
        customer.setName(scanner.nextLine());
    }

    /**
     * Fills the profession field.
     *
     * @param scanner field to be filled
     * @param customer object that contains the attribute profession to be inserted
     */
    private void insertProfession(Scanner scanner, Customer customer) {
        System.out.print("Insert profession: ");
        String profession = scanner.nextLine();
        customer.setProfession(profession);
    }

    /**
     * Fills the email field.
     *
     * @param scanner field to be filled
     * @param customer object that contains the attribute email to be inserted
     * @param isValidated flag that controns if the email is set or not
     */
    private void insertEmail(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert email: ");
            String email = scanner.nextLine();
            isValidated = customer.setEmail(email);
        }
    }

    /**
     * Fills the mobile field.
     *
     * @param scanner field to be filled
     * @param customer object that contains the attribute mobile to be inserted
     * @param isValidated flag that controns if the mobile is set or not
     */
    private void insertMobile(Scanner scanner, Customer customer, boolean isValidated) {
        while (!isValidated) {
            System.out.print("Insert mobile number: ");
            String mobile = scanner.nextLine();
            isValidated = customer.setMobile(mobile);
        }
    }

    /**
     * Finds a customer given a NIF number.
     *
     * @param nif customer key search
     * @param scanner field to be filled
     * @return the customer who owns that NIF number
     */
    public Customer findByNif(String nif, Scanner scanner) {
        boolean wasNifFound = false;
        String typedNif = nif;
        while (!wasNifFound) {
            for (Customer customerElement : customers) {
                if (customerElement.getNif().equalsIgnoreCase(typedNif)) {
                    wasNifFound = true;
                    return customerElement;
                }
            }
            if (!wasNifFound) {
                System.out.print("Does not exist a client with the given NIF number\nEnter a valid NIF number: ");
                typedNif = scanner.nextLine();
            }
        }
        return null;
    }

    /**
     * Generates initial data to fill the Arraylist that's serves as database.
     */
    public void loadDatabase() {
        Customer customer1 = new Customer("987456321", "Jane Doe", "123456", "321654987", "99887766", "something@email.com", "Garbage man", LocalDate.of(1983, 12, 24));
        Customer customer2 = new Customer("123456789", "John Doe", "123456", "321654987", "99887766", "something@email.com", "Garbage man", LocalDate.of(1983, 12, 24));
        Customer customer3 = new Customer("132456789", "Rosalvo Doe", "123456", "321654987", "99887766", "something@email.com", "Garbage man", LocalDate.of(1983, 12, 24));

        customers.addAll(Arrays.asList(customer1, customer2, customer3));
    }
}

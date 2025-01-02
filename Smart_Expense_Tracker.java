import java.io.*;
import java.util.*;

// Expense Class
class Expense {
    private String category;
    private double amount;
    private String date;
    private String futureDate;

    public Expense(String category, double amount, String date, String futureDate) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.futureDate = futureDate;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getFutureDate() {
        return futureDate;
    }

    @Override
    public String toString() {
        return category + "," + amount + "," + date + "," + (futureDate == null ? "" : futureDate);
    }

    public static Expense fromString(String line) {
        String[] parts = line.split(",");
        String category = parts[0];
        double amount = Double.parseDouble(parts[1]);
        String date = parts[2];
        String futureDate = parts.length > 3 ? parts[3] : null;
        return new Expense(category, amount, date, futureDate);
    }
}

// ExpenseManager Class
class ExpenseManager {
    private static final String FILE_NAME = "expenses.txt";

    // Add an expense
    public void addExpense(String category, double amount, String date, String futureDate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            Expense expense = new Expense(category, amount, date, futureDate);
            writer.write(expense.toString());
            writer.newLine();
            System.out.println("Expense added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    // View all expenses
    public List<Expense> viewExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                expenses.add(Expense.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading expenses: " + e.getMessage());
        }
        return expenses;
    }

    // Generate a report by category
    public void generateReport() {
        Map<String, Double> report = new HashMap<>();
        List<Expense> expenses = viewExpenses();
        for (Expense expense : expenses) {
            report.put(expense.getCategory(), report.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }
        System.out.println("\nExpense Report:");
        for (Map.Entry<String, Double> entry : report.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // View future expenses
    public void viewFutureExpenses() {
        List<Expense> expenses = viewExpenses();
        System.out.println("\nFuture Expenses:");
        for (Expense expense : expenses) {
            if (expense.getFutureDate() != null && !expense.getFutureDate().isEmpty()) {
                System.out.println(expense.getCategory() + " - " + expense.getAmount() + " - " + expense.getFutureDate());
            }
        }
    }
}

// Smart_Expense_Tracker Class
public class Smart_Expense_Tracker {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSmart Expense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Generate Expense Report");
            System.out.println("4. View Future Expenses");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Future Date (YYYY-MM-DD or leave blank): ");
                    String futureDate = scanner.nextLine();
                    if (futureDate.trim().isEmpty()) {
                        futureDate = null;
                    }
                    expenseManager.addExpense(category, amount, date, futureDate);
                    break;

                case 2:
                    List<Expense> expenses = expenseManager.viewExpenses();
                    System.out.println("\nExpenses:");
                    for (Expense expense : expenses) {
                        System.out.println("Category: " + expense.getCategory() + ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate() + ", Future Date: " + expense.getFutureDate());
                    }
                    break;

                case 3:
                    expenseManager.generateReport();
                    break;

                case 4:
                    expenseManager.viewFutureExpenses();
                    break;

                case 5:
                    System.out.println("Exiting Smart Expense Tracker. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

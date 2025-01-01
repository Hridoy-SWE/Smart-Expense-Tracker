import java.util.*;

class Expense {
    private int id;
    private String category;
    private double amount;
    private String date;
    private String futureDate;

    public Expense(int id, String category, double amount, String date, String futureDate) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.futureDate = futureDate;
    }

    public int getId() {
        return id;
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
        return id + "," + category + "," + amount + "," + date + "," + (futureDate == null ? "" : futureDate);
    }

    public static Expense fromString(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String category = parts[1];
        double amount = Double.parseDouble(parts[2]);
        String date = parts[3];
        String futureDate = parts.length > 4 ? parts[4] : null;
        return new Expense(id, category, amount, date, futureDate);
    }
}

class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();
    private int nextId;

    public ExpenseManager() {
        nextId = 1; // Initialize with the first ID
    }

    public void addExpense(String category, double amount, String date, String futureDate) {
        Expense expense = new Expense(nextId++, category, amount, date, futureDate);
        expenses.add(expense);
        System.out.println("Expense added successfully!");
    }

    public List<Expense> loadExpenses() {
        return expenses;
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            System.out.println("\nExpenses:");
            for (Expense expense : expenses) {
                System.out.println("ID: " + expense.getId() + ", Category: " + expense.getCategory() + ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate() + ", Future Date: " + expense.getFutureDate());
            }
        }
    }

    public void generateReport() {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            categoryTotals.put(expense.getCategory(), categoryTotals.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }

        System.out.println("\nExpense Report:");
        System.out.println("Category\tTotal");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    public void viewFutureExpenses() {
        System.out.println("\nFuture Expenses:");
        for (Expense expense : expenses) {
            if (expense.getFutureDate() != null && !expense.getFutureDate().isEmpty()) {
                System.out.println("ID: " + expense.getId() + ", Category: " + expense.getCategory() + ", Amount: " + expense.getAmount() + ", Date: " + expense.getDate() + ", Future Date: " + expense.getFutureDate());
            }
        }
    }
}

public class Main {
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

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Amount: ");
                    double amount;

                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                        continue;
                    }

                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Future Date (YYYY-MM-DD or leave blank): ");
                    String futureDate = scanner.nextLine();
                    expenseManager.addExpense(category, amount, date, futureDate);
                }
                case 2 -> expenseManager.viewExpenses();
                case 3 -> expenseManager.generateReport();
                case 4 -> expenseManager.viewFutureExpenses();
                case 5 -> {
                    System.out.println("Exiting Smart Expense Tracker. Goodbye!");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

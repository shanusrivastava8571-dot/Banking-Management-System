import java.awt.*;
import java.awt.event.*;

public class BankingSystemGUI extends Frame {

    static int pin;
    static float balance;
    static float rate;
    static String name, fathers_name, type;
    static int[] phone = new int[10]; // Array to store phone number digits

    TextArea displayArea;

    public BankingSystemGUI() {

        setLayout(null);
        setSize(800, 900);
        setTitle("Banking System GUI");
        setBackground(new Color(205, 133, 63)); // Light brown color
        setVisible(true);

        displayArea = new TextArea("", 10, 50);
        displayArea.setBounds(50, 100, 700, 350);
        displayArea.setFont(new Font("Arial", Font.BOLD, 20));
        displayArea.setEditable(false); // User cannot edit this area
        add(displayArea);

        Label welcome = new Label("WELCOME");
        welcome.setBounds(300, 30, 200, 70);
        welcome.setFont(new Font("Arial", Font.BOLD, 40));

        // Buttons for operations
        Button createAccountBtn = new Button("Create New Account");
        Button accountDetailsBtn = new Button("Account Details");
        Button withdrawBtn = new Button("Withdraw Money");
        Button depositBtn = new Button("Deposit Money");
        Button balanceCheckBtn = new Button("Balance Check");
        Button addInterestBtn = new Button("Add Interest");
        Button exitBtn = new Button("Exit");

        createAccountBtn.setBounds(150, 500, 200, 50);
        accountDetailsBtn.setBounds(450, 500, 200, 50);
        withdrawBtn.setBounds(150, 580, 200, 50);
        depositBtn.setBounds(450, 580, 200, 50);
        balanceCheckBtn.setBounds(150, 660, 200, 50);
        addInterestBtn.setBounds(450, 660, 200, 50);
        exitBtn.setBounds(300, 750, 200, 50); 

        createAccountBtn.setBackground(Color.GRAY);
        accountDetailsBtn.setBackground(Color.GRAY);
        withdrawBtn.setBackground(Color.GRAY);
        depositBtn.setBackground(Color.GRAY);
        addInterestBtn.setBackground(Color.GRAY);
        balanceCheckBtn.setBackground(Color.GRAY);
        exitBtn.setBackground(Color.GRAY);

        Font boldFont = new Font("Arial", Font.BOLD, 16);
        createAccountBtn.setFont(boldFont);
        accountDetailsBtn.setFont(boldFont);
        withdrawBtn.setFont(boldFont);
        depositBtn.setFont(boldFont);
        addInterestBtn.setFont(boldFont);
        balanceCheckBtn.setFont(boldFont);
        exitBtn.setFont(boldFont);

    
        add(createAccountBtn);
        add(accountDetailsBtn);
        add(withdrawBtn);
        add(depositBtn);
        add(balanceCheckBtn);
        add(addInterestBtn);
        add(exitBtn);
        add(welcome);

    
        createAccountBtn.addActionListener(e -> handleCreateAccount());
        accountDetailsBtn.addActionListener(e -> handleAccountDetails());
        withdrawBtn.addActionListener(e -> handleWithdraw());
        depositBtn.addActionListener(e -> handleDeposit());
        balanceCheckBtn.addActionListener(e -> handleBalanceCheck());
        addInterestBtn.addActionListener(e -> handleAddInterest());
        exitBtn.addActionListener(e -> handleExit());

    
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }


    private void handleCreateAccount() {
        createAccount();
    }

    private void handleAccountDetails() {
        if (isAccountCreated() && validatePin()) {
            accountDetails();
        }
    }

    private void handleWithdraw() {
        if (isAccountCreated() && validatePin()) {
            withdrawMoney();
        }
    }

    private void handleDeposit() {
        if (isAccountCreated() && validatePin()) {
            depositMoney();
        }
    }

    private void handleBalanceCheck() {
        if (isAccountCreated() && validatePin()) {
            balanceCheck();
        }
    }

    private void handleAddInterest() {
        if (isAccountCreated() && validatePin()) {
            addInterest();
        }
    }

    private void handleExit() {
        System.exit(0);  // Close the application
    }

    private boolean isAccountCreated() {
        if (name == null || name.isEmpty()) {
            displayArea.setText("Account hasn't been created. Please create an account first.");
            return false;
        }
        return true;
    }


    public void createAccount() {
        
        name = promptInput("Enter Name:");
        fathers_name = promptInput("Enter Father's Name:");
        type = promptInput("Enter Account Type (Savings/Current):");

        if (type.equalsIgnoreCase("Savings")) {
            rate = Float.parseFloat(promptInput("Enter Interest Rate (%):"));
        }

        String phoneStr = promptInput("Enter Phone Number (10 digits):");
        if (phoneStr.length() == 10) {
            for (int i = 0; i < 10; i++) {
                phone[i] = Character.getNumericValue(phoneStr.charAt(i)); // Store each digit in the array
            }
        } else {
            displayArea.setText("Invalid phone number. Please enter exactly 10 digits.");
            return;
        }

        balance = Float.parseFloat(promptInput("Enter Initial Deposit Amount:"));
        pin = Integer.parseInt(promptInput("Set a 4-digit PIN:"));

        displayArea.setText("Account created successfully!\n");
        displayArea.append("Name: " + name + "\n");
        displayArea.append("Father's Name: " + fathers_name + "\n");
        displayArea.append("Account Type: " + type + "\n");
        if (type.equalsIgnoreCase("Savings")) {
            displayArea.append("Interest Rate: " + rate + "%\n");
        }
        displayArea.append("Phone: " + phoneStr + "\n");
        displayArea.append("Balance: " + balance + "\n");
        displayArea.append("PIN set successfully.\n");
    }


    private String promptInput(String message) {
        Dialog dialog = new Dialog(this, "Input", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new GridLayout(3, 1, 10, 10));
        Label label = new Label(message);
        TextField inputField = new TextField();
        Button submitBtn = new Button("Submit");
        submitBtn.setBackground(Color.gray);

        dialog.add(label);
        dialog.add(inputField);
        dialog.add(submitBtn);

        submitBtn.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);

        return inputField.getText();
    }


    private boolean validatePin() {
        String enteredPin = promptInput("Enter your 4-digit PIN:");
        if (Integer.parseInt(enteredPin) == pin) {
            return true;
        } else {
            displayArea.setText("Incorrect PIN. Access denied.");
            return false;
        }
    }

    // Method to show account details
    public void accountDetails() {
        displayArea.setText("Account Details:\n");
        displayArea.append("Name: " + name + "\n");
        displayArea.append("Father's Name: " + fathers_name + "\n");
        displayArea.append("Account Type: " + type + "\n");
        if (type.equalsIgnoreCase("Savings")) {
            displayArea.append("Interest Rate: " + rate + "%\n");
        }
        displayArea.append("Phone: " + getPhoneNumberAsString() + "\n"); 
        displayArea.append("Balance: " + balance + "\n");
    }

    
    private String getPhoneNumberAsString() {
        StringBuilder phoneStr = new StringBuilder();
        for (int digit : phone) {
            phoneStr.append(digit);
        }
        return phoneStr.toString();
    }

    
    public void withdrawMoney() {
        Dialog dialog = new Dialog(this, "Withdraw Money", true);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.setSize(300, 150);

        Label amountLabel = new Label("Enter Amount:");
        TextField amountField = new TextField();
        Button withdrawBtn = new Button("Withdraw");

        dialog.add(amountLabel);
        dialog.add(amountField);
        dialog.add(new Label("")); 
        dialog.add(withdrawBtn);

        withdrawBtn.addActionListener(e -> {
            float amount = Float.parseFloat(amountField.getText());
            if (amount > balance) {
                displayArea.setText("Insufficient balance.");
            } else {
                balance -= amount;
                displayArea.setText("Withdrawal successful. New balance: " + balance);
            }
            dialog.dispose();
        });

        dialog.setVisible(true);
    }


    public void depositMoney() {
        Dialog dialog = new Dialog(this, "Deposit Money", true);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.setSize(300, 150);

        Label amountLabel = new Label("Enter Amount:");
        TextField amountField = new TextField();
        Button depositBtn = new Button("Deposit");

        dialog.add(amountLabel);
        dialog.add(amountField);
        dialog.add(new Label("")); 
        dialog.add(depositBtn);

        depositBtn.addActionListener(e -> {
            float amount = Float.parseFloat(amountField.getText());
            balance += amount;
            displayArea.setText("Deposit successful. New balance: " + balance);
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    // Method to check balance
    public void balanceCheck() {
        displayArea.setText("Current balance: " + balance);
    }


    public void addInterest() {
        if (type.equalsIgnoreCase("Savings")) {
            String yearsStr = promptInput("Enter number of years:");
            int years = Integer.parseInt(yearsStr);
            float interest = (balance * rate * years) / 100;
            balance += interest;
            displayArea.setText("Interest added for " + years + " years.\nNew balance: " + balance);
        } else {
            displayArea.setText("Interest feature is only valid for Savings accounts.");
        }
    }

    public static void main(String[] args) {
        new BankingSystemGUI();
    }
}

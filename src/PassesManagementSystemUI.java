import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PassesManagementSystemUI extends JFrame {
    private Queue<String> generalCustomer;
    private Stack<String> vipCustomer;
    private int totalGeneralServed;
    private int totalVIPServed;

    // UI Components
    private JTextArea outputArea;
    private JTextField customerNameField;
    private JButton addGeneralBtn, addVipBtn, serveGeneralBtn, serveVipBtn, reportBtn;

    // Color scheme
    private final Color GENERAL_COLOR = new Color(70, 130, 180);    // Steel Blue for general
    private final Color VIP_COLOR = new Color(218, 165, 32);        // Goldenrod for VIP
    private final Color SERVE_COLOR = new Color(50, 205, 50);       // Lime Green for serve
    private final Color REPORT_COLOR = new Color(169, 169, 169);    // Dark Gray for report
    private final Color TEXT_COLOR = Color.WHITE;

    public PassesManagementSystemUI() {
        // Initialize data structures
        generalCustomer = new LinkedList<>();
        vipCustomer = new Stack<>();
        totalGeneralServed = 0;
        totalVIPServed = 0;

        // Set up the main window
        setTitle("Passes Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        customerNameField = new JTextField(20);
        customerNameField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create styled buttons
        addGeneralBtn = createStyledButton("Add General Customer", GENERAL_COLOR);
        addVipBtn = createStyledButton("Add VIP Customer", VIP_COLOR);
        serveGeneralBtn = createStyledButton("Serve General Customer", SERVE_COLOR);
        serveVipBtn = createStyledButton("Serve VIP Customer", SERVE_COLOR);
        reportBtn = createStyledButton("Generate Report", REPORT_COLOR);

        // Create control panel
        JPanel controlPanel = new JPanel(new GridLayout(2, 1));
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Customer Name:"));
        inputPanel.add(customerNameField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(addGeneralBtn);
        buttonPanel.add(addVipBtn);
        buttonPanel.add(serveGeneralBtn);
        buttonPanel.add(serveVipBtn);
        buttonPanel.add(reportBtn);

        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);

        // Add components to frame
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add event handlers
        addGeneralBtn.addActionListener(e -> addGeneralCustomer());
        addVipBtn.addActionListener(e -> addVipCustomer());
        serveGeneralBtn.addActionListener(e -> serveGeneralCustomer());
        serveVipBtn.addActionListener(e -> serveVipCustomer());
        reportBtn.addActionListener(e -> displayReport());

        // Add some sample data
        addSampleData();
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return button;
    }

    private void addSampleData() {
        generalCustomer.add("Alex");
        generalCustomer.add("Brian");
        generalCustomer.add("Ron");
        vipCustomer.push("Roll");
        vipCustomer.push("Charlie");
        vipCustomer.push("Dan");
        appendOutput("Sample data loaded: 3 general and 3 VIP customers added.");
    }

    private void addGeneralCustomer() {
        String name = customerNameField.getText().trim();
        if (!name.isEmpty()) {
            generalCustomer.add(name);
            appendOutput("Added general customer: " + name);
            customerNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a customer name", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addVipCustomer() {
        String name = customerNameField.getText().trim();
        if (!name.isEmpty()) {
            vipCustomer.push(name);
            appendOutput("Added VIP customer: " + name);
            customerNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a customer name", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void serveGeneralCustomer() {
        if (!generalCustomer.isEmpty()) {
            String served = generalCustomer.poll();
            totalGeneralServed++;
            appendOutput("Served general customer: " + served);
        } else {
            appendOutput("No general customers to serve");
        }
    }

    private void serveVipCustomer() {
        if (!vipCustomer.isEmpty()) {
            String served = vipCustomer.pop();
            totalVIPServed++;
            appendOutput("Served VIP customer: " + served);
        } else {
            appendOutput("No VIP customers to serve");
        }
    }

    private void displayReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n--- Customer Service Report ---\n");
        report.append("VIP customers served: ").append(totalVIPServed).append("\n");
        report.append("General customers served: ").append(totalGeneralServed).append("\n");

        report.append("\nRemaining VIP customers:\n");
        if (vipCustomer.isEmpty()) {
            report.append("None\n");
        } else {
            for (String vip : vipCustomer) {
                report.append(vip).append("\n");
            }
        }

        report.append("\nRemaining General customers:\n");
        if (generalCustomer.isEmpty()) {
            report.append("None\n");
        } else {
            for (String general : generalCustomer) {
                report.append(general).append("\n");
            }
        }

        appendOutput(report.toString());
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PassesManagementSystemUI ui = new PassesManagementSystemUI();
            ui.setVisible(true);
        });
    }
}
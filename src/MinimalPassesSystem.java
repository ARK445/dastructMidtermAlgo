import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MinimalPassesSystem extends JFrame {
    private Queue<String> generalQueue = new LinkedList<>();
    private Stack<String> vipStack = new Stack<>();
    private int generalServed = 0;
    private int vipServed = 0;

    // UI Components
    private JTextArea logArea = new JTextArea(10, 30);
    private JTextField nameField = new JTextField(15);

    public MinimalPassesSystem() {
        configureWindow();
        createLayout();
        loadSampleData();
    }

    private void configureWindow() {
        setTitle("Queue Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    private void createLayout() {
        // Main container with subtle padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);

        // Button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.add(createButton("Add General", this::addGeneral));
        buttonPanel.add(createButton("Add VIP", this::addVip));
        buttonPanel.add(createButton("Serve General", this::serveGeneral));
        buttonPanel.add(createButton("Serve VIP", this::serveVip));

        // Report button below
        JPanel reportPanel = new JPanel();
        reportPanel.add(createButton("Generate Report", this::generateReport));

        // Log area with subtle styling
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Assemble components
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(reportPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.EAST);

        add(mainPanel);
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setMargin(new Insets(5, 10, 5, 10));
        button.addActionListener(e -> action.run());
        return button;
    }

    private void loadSampleData() {
        generalQueue.add("Alex");
        generalQueue.add("Brian");
        generalQueue.add("Ron");
        vipStack.push("Roll");
        vipStack.push("Charlie");
        vipStack.push("Dan");
        log("System ready with sample data loaded");
    }

    // Core operations
    private void addGeneral() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) return;

        generalQueue.add(name);
        log("Added general: " + name);
        nameField.setText("");
    }

    private void addVip() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) return;

        vipStack.push(name);
        log("Added VIP: " + name);
        nameField.setText("");
    }

    private void serveGeneral() {
        if (generalQueue.isEmpty()) {
            log("No general customers waiting");
            return;
        }
        generalServed++;
        log("Served general: " + generalQueue.poll());
    }

    private void serveVip() {
        if (vipStack.isEmpty()) {
            log("No VIP customers waiting");
            return;
        }
        vipServed++;
        log("Served VIP: " + vipStack.pop());
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n=== Queue Report ===\n");
        report.append("Served: ").append(vipServed).append(" VIP, ")
                .append(generalServed).append(" General\n\n");

        report.append("Waiting VIP:\n");
        if (vipStack.isEmpty()) report.append("None\n");
        else vipStack.forEach(v -> report.append("- ").append(v).append("\n"));

        report.append("\nWaiting General:\n");
        if (generalQueue.isEmpty()) report.append("None\n");
        else generalQueue.forEach(g -> report.append("- ").append(g).append("\n"));

        log(report.toString());
    }

    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MinimalPassesSystem ui = new MinimalPassesSystem();
            ui.setVisible(true);
        });
    }
}
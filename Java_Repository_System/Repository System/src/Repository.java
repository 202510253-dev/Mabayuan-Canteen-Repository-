import java.awt.*;
import javax.swing.*;

public class Repository {

    int boardWidth = 900;
    int boardHeight = 600;

    // Panel Section
    JFrame frame = new JFrame("Mabayuan Canteen Repository System");
    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel centerPanel = new JPanel(); // table

    // Top Section
    JTextField searchField = new JTextField();
    JButton searchButton = new JButton("Search");

    // Left Buttons
    JButton addnewButton = new JButton("Add New Product");
    JButton editselectedButton = new JButton("<html><center>Edit Selected<br>Product</center></html>");
    JButton removeselectedButton = new JButton("<html><center>Remove Selected<br>Product</center></html>");

    // Table Rows/Columns
    String[] columns = { "ID", "Name", "Price", "Image", "Quantity", "Category" };

    // Data Section - Will be connected to DataBase once the logic and funciton are
    // done.
    JTable table = new JTable(new Object[0][6], columns);
    JScrollPane scrollPane = new JScrollPane(table);

    Repository() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // top panel
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(58, 70, 90));

        searchField.setFont(new Font("Arial", Font.PLAIN, 16));

        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(Color.WHITE);
        searchButton.setFocusable(false);

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        // left panel
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        leftPanel.setBackground(new Color(50, 70, 90));
        leftPanel.setPreferredSize(new Dimension(220, 0));

        for (JButton btn : new JButton[] { addnewButton, editselectedButton, removeselectedButton }) {
            btn.setBackground(new Color(220, 80, 40));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusable(false);
            btn.setBorderPainted(true);
            btn.setMaximumSize(new Dimension(180, 100));
            leftPanel.add(btn);
        }

        leftPanel.add(addnewButton);
        leftPanel.add(editselectedButton);
        leftPanel.add(removeselectedButton);

        // center panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(58, 70, 90));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        table.getTableHeader().setBackground(new Color(220, 80, 40));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(60);
        table.setGridColor(Color.BLACK);
        table.setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Repository();
    }
}

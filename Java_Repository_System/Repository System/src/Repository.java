import java.awt.*;
import java.sql.Connection;
import javax.swing.*;

public class Repository {

    int boardWidth = 900;
    int boardHeight = 600;

    JFrame frame = new JFrame("Mabayuan Canteen Repository System");
    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel centerPanel = new JPanel();

    JTextField searchField = new JTextField();
    JButton searchButton = new JButton("Search");

    JButton addnewButton = new JButton("Add New Product");
    JButton editselectedButton = new JButton("<html><center>Edit Selected<br>Product</center></html>");
    JButton removeselectedButton = new JButton("<html><center>Remove Selected<br>Product</center></html>");

    String[] columns = { "ID", "Name", "Price", "Quantity", "Category" };

    JTable table = new JTable(new Object[0][5], columns);
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

        // ACTION LISTENERS
        addnewButton.addActionListener(e -> new AddWindow(this));

        editselectedButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a product to edit.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            String name = (String) table.getValueAt(selectedRow, 1);
            int price = (int) table.getValueAt(selectedRow, 2);
            int quantity = (int) table.getValueAt(selectedRow, 3);
            String category = (String) table.getValueAt(selectedRow, 4);
            new EditWindow(this, id, name, price, quantity, category);
        });

        removeselectedButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a product to remove.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            String name = (String) table.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(frame, "Remove \"" + name + "\"?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    java.sql.PreparedStatement ps = conn.prepareStatement("DELETE FROM products_table WHERE Item_id=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    loadTableData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "DB Error: " + ex.getMessage());
                }
            }
        });

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

        loadTableData();
        frame.setVisible(true);
    }

    void loadTableData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT Item_id, Item_name, Item_price, Item_quantity, Item_category FROM products_table";
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(query);

            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[] {
                        rs.getInt("Item_id"),
                        rs.getString("Item_name"),
                        rs.getInt("Item_price"),
                        rs.getInt("Item_quantity"),
                        rs.getString("Item_category") // <-- backtick removed
                });
            }

            table.setModel(new javax.swing.table.DefaultTableModel(
                    rows.toArray(new Object[0][]), columns));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "DB Error: S" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Repository();
    }
}

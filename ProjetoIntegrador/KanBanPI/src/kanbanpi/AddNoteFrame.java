package kanbanpi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddNoteFrame extends JFrame {
    private JTextField titleField;
    private JTextArea noteArea;
    private JComboBox<String> statusBox;
    private KanbanFrame kanbanFrame;

    public AddNoteFrame(KanbanFrame kanbanFrame) {
        this.kanbanFrame = kanbanFrame;

        setTitle("Add Note");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.PINK);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Título:");
        inputPanel.add(titleLabel, gbc);

        titleField = new JTextField(30);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleField, gbc);

        gbc.gridy++;
        JLabel noteLabel = new JLabel("Nota:");
        inputPanel.add(noteLabel, gbc);

        noteArea = new JTextArea(10, 40);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(noteScrollPane, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel statusLabel = new JLabel("Status:");
        inputPanel.add(statusLabel, gbc);

        statusBox = new JComboBox<>(new String[]{"TODO", "DOING", "DONE"});
        gbc.gridx = 1;
        gbc.weightx = 0;
        inputPanel.add(statusBox, gbc);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new AddButtonListener());

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);

        add(panel);
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String note = noteArea.getText();
            String status = (String) statusBox.getSelectedItem();

            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO notes (title, description, status) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, title);
                pstmt.setString(2, note);
                pstmt.setString(3, status);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Note added successfully");
                kanbanFrame.loadNotes(); // Atualiza as notas no KanbanFrame
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

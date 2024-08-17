package kanbanpi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPostItFrame extends JFrame {
    private PostItFrame PostItFrame; // Alterado de PostItFrame para KanbanFrame
    private JTextField titleField;
    private JTextArea descriptionArea;

    public AddPostItFrame(PostItFrame PostItFrame) { // Alterado de PostItFrame para KanbanFrame
        this.PostItFrame = PostItFrame;

        setTitle("Add Post-It Note");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.PINK);

        titleField = new JTextField();
        titleField.setBorder(BorderFactory.createTitledBorder("Title"));
        panel.add(titleField, BorderLayout.NORTH);

        descriptionArea = new JTextArea();
        descriptionArea.setBorder(BorderFactory.createTitledBorder("Description"));
        panel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPostIt();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addPostIt() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and Description cannot be empty.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO post_its (title, description) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Post-it added successfully.");
            PostItFrame.loadPostIts(); // Alterado de postItFrame.loadPostIts() para kanbanFrame.loadPostIts()
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

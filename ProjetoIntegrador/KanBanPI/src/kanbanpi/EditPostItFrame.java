package kanbanpi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditPostItFrame extends JFrame {
    private PostItFrame postItFrame;
    private PostIt postIt;
    private JTextField titleField;
    private JTextArea descriptionArea;

    public EditPostItFrame(PostItFrame postItFrame, PostIt postIt) {
    this.postItFrame = postItFrame;
    this.postIt = postIt;

    setTitle("Edit Post-It Note");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.PINK);

    titleField = new JTextField(postIt.getTitle());
    titleField.setBorder(BorderFactory.createTitledBorder("Title"));
    panel.add(titleField, BorderLayout.NORTH);

    descriptionArea = new JTextArea(postIt.getDescription());
    descriptionArea.setBorder(BorderFactory.createTitledBorder("Description"));
    panel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

    JButton saveButton = new JButton("Save");
    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            savePostIt();
        }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(saveButton);

    add(panel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
}


    private void savePostIt() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and Description cannot be empty.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE post_its SET title = ?, description = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, postIt.getId());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Post-it updated successfully.");
            postItFrame.loadPostIts();
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

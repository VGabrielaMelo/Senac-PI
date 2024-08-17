package kanbanpi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostItFrame extends JFrame {
    private DefaultListModel<PostIt> postItModel;
    private JList<PostIt> postItList;

    public PostItFrame() {
        setTitle("Post-It Notes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        postItModel = new DefaultListModel<>();
        postItList = new JList<>(postItModel);
        postItList.setCellRenderer(new PostItRenderer());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.PINK);

        JButton addButton = new JButton("Add Post-It");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPostItFrame addPostItFrame = new AddPostItFrame(PostItFrame.this);
                addPostItFrame.setVisible(true);
            }
        });

        JButton deleteButton = new JButton("Delete Post-It");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedPostIt();
            }
        });

        JButton editButton = new JButton("Edit Post-It");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedPostIt();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        panel.add(new JScrollPane(postItList), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        loadPostIts();
    }

    public void loadPostIts() {
        postItModel.clear();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, title, description FROM post_its";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");

                PostIt postIt = new PostIt(id, title, description);
                postItModel.addElement(postIt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteSelectedPostIt() {
        PostIt selectedPostIt = postItList.getSelectedValue();
        if (selectedPostIt == null) {
            JOptionPane.showMessageDialog(this, "Please select a post-it to delete.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM post_its WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, selectedPostIt.getId());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Post-it deleted successfully.");
            loadPostIts();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void editSelectedPostIt() {
        PostIt selectedPostIt = postItList.getSelectedValue();
        if (selectedPostIt == null) {
            JOptionPane.showMessageDialog(this, "Please select a post-it to edit.");
            return;
        }

        EditPostItFrame editPostItFrame = new EditPostItFrame(this, selectedPostIt);
        editPostItFrame.setVisible(true);
    }
}

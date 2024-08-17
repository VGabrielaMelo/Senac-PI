package kanbanpi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KanbanFrame extends JFrame {
    private DefaultListModel<Note> todoModel;
    private DefaultListModel<Note> doingModel;
    private DefaultListModel<Note> doneModel;
    private DefaultListModel<PostIt> postItModel;
    private JList<Note> todoList;
    private JList<Note> doingList;
    private JList<Note> doneList;
    private JList<PostIt> postItList;

    public KanbanFrame() {
        setTitle("Kanban Board");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.PINK);

        JPanel kanbanPanel = new JPanel(new GridLayout(1, 3));
        todoModel = new DefaultListModel<>();
        doingModel = new DefaultListModel<>();
        doneModel = new DefaultListModel<>();
        postItModel = new DefaultListModel<>();

        todoList = new JList<>(todoModel);
        doingList = new JList<>(doingModel);
        doneList = new JList<>(doneModel);
        postItList = new JList<>(postItModel);

        todoList.setCellRenderer(new NoteRenderer());
        doingList.setCellRenderer(new NoteRenderer());
        doneList.setCellRenderer(new NoteRenderer());
        postItList.setCellRenderer(new PostItRenderer());

        kanbanPanel.add(createKanbanColumn("TODO", todoList));
        kanbanPanel.add(createKanbanColumn("DOING", doingList));
        kanbanPanel.add(createKanbanColumn("DONE", doneList));

        JButton addButton = new JButton("Add Note");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNoteFrame addNoteFrame = new AddNoteFrame(KanbanFrame.this);
                addNoteFrame.setVisible(true);
            }
        });

        JButton postItButton = new JButton("Post-It Notes");
        postItButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostItFrame postItFrame = new PostItFrame();
                postItFrame.setVisible(true);
            }
        });

        JButton deleteButton = new JButton("Delete Note");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedNote();
            }
        });

        JButton moveButton = new JButton("Move Note");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedNote();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(postItButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(moveButton);

        panel.add(kanbanPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        loadNotes();
        loadPostIts();
    }

    private JPanel createKanbanColumn(String title, JList<Note> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }

    public void loadNotes() {
        todoModel.clear();
        doingModel.clear();
        doneModel.clear();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, title, description, status FROM notes";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String status = rs.getString("status");

                Note note = new Note(id, title, description);

                switch (status) {
                    case "TODO":
                        todoModel.addElement(note);
                        break;
                    case "DOING":
                        doingModel.addElement(note);
                        break;
                    case "DONE":
                        doneModel.addElement(note);
                        break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    private void deleteSelectedNote() {
        Note selectedNote = getSelectedNote();
        if (selectedNote == null) {
            JOptionPane.showMessageDialog(this, "Please select a note to delete.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM notes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, selectedNote.getId());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Note deleted successfully.");
            loadNotes();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void moveSelectedNote() {
        Note selectedNote = getSelectedNote();
        if (selectedNote == null) {
            JOptionPane.showMessageDialog(this, "Please select a note to move.");
            return;
        }

        String[] statuses = {"TODO", "DOING", "DONE"};
        String newStatus = (String) JOptionPane.showInputDialog(
                this,
                "Select new status:",
                "Move Note",
                JOptionPane.PLAIN_MESSAGE,
                null,
                statuses,
                "TODO"
        );

        if (newStatus != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE notes SET status = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newStatus);
                pstmt.setInt(2, selectedNote.getId());
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Note moved successfully.");
                loadNotes();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Note getSelectedNote() {
        if (todoList.getSelectedValue() != null) {
            return todoList.getSelectedValue();
        } else if (doingList.getSelectedValue() != null) {
            return doingList.getSelectedValue();
        } else if (doneList.getSelectedValue() != null) {
            return doneList.getSelectedValue();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KanbanFrame().setVisible(true);
            }
        });
    }
}

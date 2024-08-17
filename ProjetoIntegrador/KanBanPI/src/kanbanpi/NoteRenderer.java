
package kanbanpi;
import javax.swing.*;
import java.awt.*;

public class NoteRenderer extends JPanel implements ListCellRenderer<Note> {
    private JLabel titleLabel;
    private JLabel descriptionLabel;

    public NoteRenderer() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.PINK);

        titleLabel = new JLabel();
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 192, 203)); // Rosa mais forte
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        add(titleLabel, BorderLayout.NORTH);

        descriptionLabel = new JLabel();
        add(descriptionLabel, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Note> list, Note note, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        titleLabel.setText(note.getTitle());
        descriptionLabel.setText("<html>" + note.getDescription() + "</html>");

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            titleLabel.setBackground(new Color(255, 192, 203)); // Rosa mais forte quando selecionado
            titleLabel.setForeground(list.getSelectionForeground());
            descriptionLabel.setBackground(list.getSelectionBackground());
            descriptionLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            titleLabel.setBackground(new Color(255, 192, 203)); // Rosa mais forte quando não selecionado
            titleLabel.setForeground(list.getForeground());
            descriptionLabel.setBackground(list.getBackground());
            descriptionLabel.setForeground(list.getForeground());
        }

        return this;
    }
}

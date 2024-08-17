
package kanbanpi;
import javax.swing.*;
import java.awt.*;

public class PostItRenderer extends JPanel implements ListCellRenderer<PostIt> {
    private JLabel titleLabel;
    private JLabel descriptionLabel;

    public PostItRenderer() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.YELLOW); // Cor de fundo para os post-its

        titleLabel = new JLabel();
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 255, 153)); // Cor de fundo para o título
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        add(titleLabel, BorderLayout.NORTH);

        descriptionLabel = new JLabel();
        add(descriptionLabel, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends PostIt> list, PostIt postIt, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        titleLabel.setText(postIt.getTitle());
        descriptionLabel.setText("<html>" + postIt.getDescription() + "</html>");

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            titleLabel.setBackground(new Color(255, 255, 153)); // Cor de fundo do título quando selecionado
            titleLabel.setForeground(list.getSelectionForeground());
            descriptionLabel.setBackground(list.getSelectionBackground());
            descriptionLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            titleLabel.setBackground(new Color(255, 255, 153)); // Cor de fundo do título quando não selecionado
            titleLabel.setForeground(list.getForeground());
            descriptionLabel.setBackground(list.getBackground());
            descriptionLabel.setForeground(list.getForeground());
        }

        return this;
    }
}

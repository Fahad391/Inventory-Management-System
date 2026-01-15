import GUI.InventoryManagerPage;
import javax.swing.SwingUtilities;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManagerPage frame = new InventoryManagerPage();
            frame.setVisible(true);
        });
    }
}
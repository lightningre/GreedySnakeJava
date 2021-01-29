import java.awt.*;
import javax.swing.*;;

public class GreedySnake extends JFrame {
  private CorePanel corePanel = new CorePanel();

  public GreedySnake() {
    corePanel.setBackground(Color.BLACK);
    add(corePanel);
    corePanel.setFocusable(true);
    corePanel.requestFocus();
  }

  public static void main(String[] args) {
    GreedySnake frame = new GreedySnake();
    frame.setTitle("GreedySnake");
    frame.setSize(600, 800);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
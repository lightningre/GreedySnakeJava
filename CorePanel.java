import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class CorePanel extends JPanel {

  private Rectangle2D[][] squares;
  private int[][] sign;
  private Pair food;
  private int foodNum = 0;
  // TODO: private int level = 3; // level -> normal:3 middle:2 hard:1
  // TODO: private Timer timer;

  private final int WIDTH_SIZE = 30;
  private final int HEIGHT_SIZE = 40;
  private final int GreedySnake_TYPE = 2;
  private final int FOOD_TYPE = 1;
  private final int AREA_TYPE = 0;

  private final int KEY_UP = 1;
  private final int KEY_DOWN = 2;
  private final int KEY_LEFT = 3;
  private final int KEY_RIGHT = 4;

  private int status = 0;
  private int direction = 2; // init right
  private int delay = 100; // milliseconds
  private LinkedList<Pair> GreedySnake;
  private Pair oldNode, newNode;

  private ActionListener taskFoodGenerate = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      if (status == 1) {
        food = new Pair((int) (Math.random() * 28), (int) (Math.random() * 38));
        sign[food.x][food.y] = FOOD_TYPE;
        ++foodNum;
      }
    }
  };

  private ActionListener taskSelfUpdate = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      try {
        if (status == 1) {
          oldNode = GreedySnake.removeFirst();
          sign[oldNode.x][oldNode.y] = AREA_TYPE;
          switch (direction) {
            case KEY_UP: // up
              newNode = new Pair(GreedySnake.getLast().x, GreedySnake.getLast().y - 1);
              break;
            case KEY_DOWN: // down
              newNode = new Pair(GreedySnake.getLast().x, GreedySnake.getLast().y + 1);
              break;
            case KEY_LEFT: // left
              newNode = new Pair(GreedySnake.getLast().x - 1, GreedySnake.getLast().y);
              break;
            case KEY_RIGHT: // right
              newNode = new Pair(GreedySnake.getLast().x + 1, GreedySnake.getLast().y);
              break;
          }
          sign[newNode.x][newNode.y] = GreedySnake_TYPE;
          for (Pair node : GreedySnake) {
            if (node.equals(newNode)) {
              System.out.println("YOU BIT YOURSELF GAME OVER !!!");
              System.exit(0);
            }
          }
          GreedySnake.addLast(newNode);
          if (foodNum != 0 && newNode.equals(food)) {
            GreedySnake.addFirst(oldNode);
            --foodNum;
          }
          repaint();
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Game Over");
        System.exit(0);
      }
    }
  };

  public CorePanel() {
    sign = new int[WIDTH_SIZE][HEIGHT_SIZE];
    squares = new Rectangle2D[WIDTH_SIZE][HEIGHT_SIZE];
    int swidth = 600;
    int sheight = 800;
    double pwidth = 1.0 * swidth / WIDTH_SIZE;
    double pheight = 1.0 * sheight / HEIGHT_SIZE;
    for (int i = 0; i < WIDTH_SIZE; ++i) {
      for (int j = 0; j < HEIGHT_SIZE; ++j) {
        sign[i][j] = AREA_TYPE;
        squares[i][j] = new Rectangle2D.Double(i * pwidth, j * pheight, pwidth, pheight);
      }
    }
    GreedySnake = new LinkedList<Pair>();
    GreedySnake.add(new Pair(2, 2));
    GreedySnake.add(new Pair(3, 2));
    GreedySnake.add(new Pair(4, 2));
    GreedySnake.add(new Pair(5, 2));
    GreedySnake.add(new Pair(6, 2));
    for (Pair node : GreedySnake) {
      sign[node.x][node.y] = GreedySnake_TYPE;
    }
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_DOWN:
            if (direction != 1) {
              direction = 2;
            }
            break;
          case KeyEvent.VK_UP:
            if (direction != 2) {
              direction = 1;
            }
            break;
          case KeyEvent.VK_LEFT:
            if (direction != 4) {
              direction = 3;
            }
            break;
          case KeyEvent.VK_RIGHT:
            if (direction != 3) {
              direction = 4;
            }
            break;
          case KeyEvent.VK_SPACE:
            if (status == 0) {
              System.out.println("GAME START");
              status = 1;
            } else {
              System.out.println("GAME STOP");
              status = 0;
            }
            break;
        }
        repaint();
      }
    });

    new Timer(delay, taskSelfUpdate).start();

    new Timer(delay * 40, taskFoodGenerate).start();
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    for (int i = 0; i < squares.length; ++i) {
      for (int j = 0; j < squares[i].length; ++j) {
        if (sign[i][j] == FOOD_TYPE) {
          g2.setColor(Color.RED);
        } else if (sign[i][j] == GreedySnake_TYPE) {
          g2.setColor(Color.ORANGE);
        } else {
          g2.setColor(Color.BLACK);
        }
        g2.fill(squares[i][j]);
      }
    }
  }
}
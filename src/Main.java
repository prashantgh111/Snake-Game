import java.awt.Color;
import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
// create JFram
        JFrame frame = new JFrame("snake game");
        frame.setBounds(10,10,905,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // create object of GamePanel class and add into ur main frame
        GamePanel panel = new GamePanel();
        // SET BACKGRAOUND COLOR
        panel.setBackground(Color.DARK_GRAY);

        frame.add(panel);   // we only pass components and panel is not a component
        // so  go GamePanel class and make it as component as extend with JPanel; and import java swing

        // FRAME IS BY DEFAULT INVISIBLE SO WE MAKE IT AS VISIBLE
        frame.setVisible(true);

    }
}

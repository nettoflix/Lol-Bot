import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {
    JPanel mainPanel;
    public MyWindow()
    {
        super();
        //JFrame frame = new JFrame();
        this.setSize(200,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(400,70);
        this.setTitle("Example Frame");
        this.setAlwaysOnTop(true);
        FlowLayout flowLayout = new FlowLayout();
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        mainPanel = new JPanel();
        this.add(mainPanel, BorderLayout.CENTER);
        JLabel label = new JLabel("OII SOU LABEL1");
        label.setName("label1");
        JLabel label2 = new JLabel("Oii sou label2");
        label2.setName("infoLabel");
       // label.setText("OIII");

        mainPanel.add(label);
        mainPanel.add(label2);


        //  frame.setUndecorated(true);
        // frame.setBackground(new Color(100,200,200, 50));
    }
    public Component getComponent(JPanel father, String name)
    {
        if(father==null) return null;
        Component[] components = father.getComponents();
        for(int i=0; i<components.length; i++)
        {
            if(components[i].getName() != null && components[i].getName().equals(name))
            {
                return components[i];
            }
        }
        return null;
    }

}

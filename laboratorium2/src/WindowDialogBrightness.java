import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowDialogBrightness extends JDialog implements ActionListener
{
    JLabel text;
    JSpinner spinner;
    JButton zatwierdz, returned;
    int k = 0;
    boolean isOK;

    public WindowDialogBrightness(JFrame owner){
        super(owner, "Ustawianie Jasności Obrazu", true);
        setSize(200, 180);
        setLayout(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = getSize().width;
        int height = getSize().height;
        int lokwidth = (dim.width - width) / 2;
        int lokheight = (dim.height - height) / 2;
        setLocation(lokwidth, lokheight);

        text = new JLabel("Jasność", JLabel.CENTER);
        text.setBounds(10, 10, 160, 30);
        add(text);

        SpinnerModel model = new SpinnerNumberModel(0, -255, 255, 1);
        spinner = new JSpinner(model);
        spinner.setLocation(45, 50);
        spinner.setSize(100, 25);

        this.add(spinner);

        zatwierdz = new JButton("OK");
        zatwierdz.setBounds(10, 85, 75, 30);
        add(zatwierdz);
        zatwierdz.addActionListener(this);

        returned = new JButton("Powrót");
        returned.setBounds(95, 85, 75, 30);
        add(returned);
        returned.addActionListener(this);
    }

    public int getValue(){
        return (int) spinner.getValue();
    }

    public boolean getOK(){
        return isOK;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source== returned)
        {
            isOK = false;
            dispose();
        }
        else if(source==zatwierdz){
            isOK = true;
            dispose();
        }
    }
}

package ConversExcelToLDIF;

import javax.swing.*;

/**
 * синглетон, создаёт инстанс надписи в окне
 * формальные параметры -
 * x - координата расположения по оси х
 * y - координата расположения по оси у
 * h - ширина строки
 * w - высота строки
 * name - надпись
 */

public class CreateLabel extends JLabel {
    private static CreateLabel InstanceLabel;
    private CreateLabel(int x, int y, int h, int w, String textLine){
        new JButton();
        this.setBounds(x, y, h, w);
        this.setText(textLine);
    }
    public static CreateLabel getInstans(int x, int y, int h, int w, String textLine){
        if (InstanceLabel == null){
            InstanceLabel = new CreateLabel(x,y,h,w,textLine);
        } else{
            InstanceLabel.removeAll();
            InstanceLabel = new CreateLabel(x,y,h,w,textLine);
        }
        return InstanceLabel;
    }
}
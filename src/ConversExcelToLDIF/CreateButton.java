package ConversExcelToLDIF;

import javax.swing.*;

/**
 * синглетон, создаёт инстанс кнопки
 * формальные параметры -
 * x - координата расположения по оси х
 * y - координата расположения по оси у
 * h - ширина кнопки
 * w - высота кнопки
 * name - надпись на кнопке
 */

public class CreateButton extends JButton {
    private static CreateButton InstanceButton;
    private CreateButton(int x, int y, int h, int w, String name){
        new JButton();
        this.setBounds(x, y, h, w);
        this.setText(name);
    }
    public static CreateButton getInstans(int x, int y, int h, int w, String name){
        if (InstanceButton == null){
            InstanceButton = new CreateButton(x,y,h,w,name);
        } else{
            InstanceButton.removeAll();
            InstanceButton = new CreateButton(x,y,h,w,name);
        }
        return InstanceButton;
    }
}
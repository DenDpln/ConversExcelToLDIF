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

public class CreateTextField extends JTextField {
    private static CreateTextField InstanceTextField;
    private CreateTextField(int x, int y, int h, int w){
        new JTextField();
        this.setBounds(x, y, h, w);
    }
    public static CreateTextField getInstans(int x, int y, int h, int w){
        if (InstanceTextField == null){
            InstanceTextField = new CreateTextField(x,y,h,w);
        } else{
            InstanceTextField.removeAll();
            InstanceTextField = new CreateTextField(x,y,h,w);
        }
        return InstanceTextField;
    }
}
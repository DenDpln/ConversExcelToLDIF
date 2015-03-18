package ConversExcelToLDIF;

import javax.swing.*;

/**
 * синглетон, создаёт инстанс фрейм
 * формальный параметр - размер фрейма
 */
public class CreateFrame extends JFrame {
  private static CreateFrame InstanceFrame;
    private CreateFrame(int x, int y){
        new JFrame();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(x, y);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
    }
    public static CreateFrame getInstans(int x,int y){
      if (InstanceFrame == null){
          InstanceFrame = new CreateFrame(x,y);
      } else {InstanceFrame.dispose();
          InstanceFrame = new CreateFrame(x,y);
      }
      return InstanceFrame;
  }

}
package ConversExcelToLDIF;

import java.util.HashMap;
import java.util.Map;

/** запускается по нажатию кнопки "сформировать" получает карту
 *  MapGreatJCheckBox greatMap().
 *  Запускается лиснерами, галочка поставлена - вносится запись в карту  mapJCheckBox.put(ind,ch).
 *  галочка снята - запись удаляется mapJCheckBox.remove(ind);
 *  ind - индификатор(номер лиснера) лиснера и он же номер чекбокса
 */
public class Checker {
    private static boolean chk;

    /**
     * вызывает синглетон MapGreatJCheckBox
     * галочка поставлена - записываем в карту номер лиснера и еденицу
     * @param ind   индификатор лиснера и он же номер чекбокса
     * @param ch еденица
     */
    public static void chekerJCheckBoxPut(int ind, int ch){
        MapGreatJCheckBox mapJCheckBox =  MapGreatJCheckBox.greatMap();
        mapJCheckBox.put(ind,ch);
    }
    /**
     * вызывает синглетон MapGreatJCheckBox
     * галочка снята - удаляем из карты запись, ключ которой совпадает с номером лиснера(чекбокса) ind
     * @param ind   индификатор лиснера и он же номер чекбокса
     */
    public static void checkerJCheckBoxRemove (int ind){
        MapGreatJCheckBox mapJCheckBox =  MapGreatJCheckBox.greatMap();
        mapJCheckBox.remove(ind);
    }
    /**
     * вызывает синглетон MapGreatJComboBox
     * удаляет из карты запись с номером ind
     * @param ind   индификатор лиснера и он же номер комбобокса
     */
    public static void chekerJComboBoxRemove (int ind){
        MapGreatJComboBox1 chekerJComboBoxRemove =  MapGreatJComboBox1.greatMap();
        chekerJComboBoxRemove.remove(ind);
    }
    /**
     * вызывает синглетон MapGreatJComboBox
     * показывает запись с номером ind
     * @param   ind   индификатор лиснера и он же номер комбобокса
     */
    public static String chekerJComboBoxGet (int ind){
        return MapGreatJComboBox1.greatMap().get(ind);
    }
    /**
     * возвращает карту MapJCheckBox
     */
    public static  Map<Integer, Integer> getMapJCheckBox (){
        Map<Integer, Integer> m =  MapGreatJCheckBox.greatMap().getMap();
            return m;
    }
    /**
     * возвращает карту MapJComboBox
     */
    public static Map<Integer, String> getMapJComboBox1(){
        Map<Integer, String> m =  MapGreatJComboBox1.greatMap().getMap();
        return m;
    }
    public static boolean getContainsValue(String str){
      return MapGreatJComboBox1.greatMap().containsValue(str);
    }
    /**
     * вызывает синглетон MapGreatJComboBox
     * записывает в карту значения выбранного поля и номер лиснера(идентификатор)
     * @param ind   индификатор лиснера и он же номер комбобокса
     * @param ch значение выбранного поля
     */
    public static void chekerJComboBoxPut(int ind, String ch){
        MapGreatJComboBox1 chekerJComboBoxPut =  MapGreatJComboBox1.greatMap();
        chekerJComboBoxPut.put(ind,ch);
    }
    public static void chekerJComboBoxPut(String ch, int ind){
        MapGreatJComboBox2 chekerJComboBoxPut =  MapGreatJComboBox2.greatMap2();
        chekerJComboBoxPut.put(ch,ind);
    }
    /**
     * вызывает синглетон MapGreatJComboBox
     * удаляет из карты запись с номером ind
     * @param ch   индификатор лиснера и он же номер комбобокса
     */
    public static void chekerJComboBoxRemove2 (String ch){
        MapGreatJComboBox2 chekerJComboBoxRemove =  MapGreatJComboBox2.greatMap2();
        chekerJComboBoxRemove.remove(ch);
    }
    /**
     * вызывает синглетон MapGreatJComboBox
     * показывает запись с номером ind
     */
    public static Integer chekerJComboBoxGet2(String ch){
        return MapGreatJComboBox2.greatMap2().get(ch);
    }

    public static boolean getContainsKey(String str){
        return MapGreatJComboBox2.greatMap2().containsKey(str);
    }

    public static Map<String, Integer > getMapJComboBox2(){
        HashMap<String, Integer> m =  MapGreatJComboBox2.greatMap2().getMap2();
        return m;
    }
    public static boolean getMapCheck2(String str){
        HashMap<String, Integer> m =  MapGreatJComboBox2.greatMap2().getMap2();
       if (m.containsKey(str)) return true;
        return false;
    }
}

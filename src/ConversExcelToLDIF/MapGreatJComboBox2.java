package ConversExcelToLDIF;

import java.util.HashMap;

/**Синглетон, создаёт инстанс карты, для добавления и удаления события на комбобоксе
 *
 */

public class MapGreatJComboBox2 {

    private static MapGreatJComboBox2 instance;

    HashMap<String, Integer> map;

    private MapGreatJComboBox2() {
        map = new HashMap<String, Integer>();

    }

    public static MapGreatJComboBox2 greatMap2() {
        if (instance == null) {
            instance = new MapGreatJComboBox2();
        }
        return instance;
    }

    public HashMap<String, Integer> getMap2() {
        return map;
    }

    /**
     * кладем в карту
     *
     * @param value
     * @param key
     * @return
     */
    public Integer put(String value, Integer key) {
        return map.put(value, key);
    }

    /**
     *  получаем по ключу из карты
     *
     * @param value
     * @return
     */
    public Integer get(String value) {
        return map.get(value);
    }

    /**
     * Удаление из карты по ключу
     *
     * @param value
     * @return
     */
    public Integer remove(String value){
        return map.remove(value);
    }

    public int size() {
        return map.size();
    }
    public boolean containsValue(int key){
        return map.containsValue(key);
    }
    public boolean containsKey(String value){
        return map.containsValue(value);
    }
}
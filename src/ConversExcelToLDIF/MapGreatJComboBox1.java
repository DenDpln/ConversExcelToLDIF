package ConversExcelToLDIF;

import java.util.HashMap;
import java.util.Map;

/**Синглетон, создаёт инстанс карты, для добавления и удаления события на комбобоксе
 *
 */

public class MapGreatJComboBox1 {

    private static MapGreatJComboBox1 instance;

    Map<Integer, String> map;

    private MapGreatJComboBox1() {
        map = new HashMap<Integer, String>();

    }

    public static MapGreatJComboBox1 greatMap() {
        if (instance == null) {
            instance = new MapGreatJComboBox1();
        }
        //else instance.remove(instance.size());
        return instance;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    /**
     * кладем в карту
     * @param key
     * @param value
     * @return
     */
    public String put(Integer key, String value) {
        return map.put(key, value);
    }

    /**
     *  получаем по ключу из карты
     * @param key
     * @return
     */
    public String get(Integer key) {
        return map.get(key);
    }

    /**
     * Удаление из карты по ключу
     * @param key
     * @return
     */
    public String remove(Integer key){
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }
    public boolean containsValue(String value){
        return map.containsValue( value);
    }
    public boolean containsKey(int key){
        return map.containsValue(key);
    }
}
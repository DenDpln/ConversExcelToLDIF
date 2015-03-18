package ConversExcelToLDIF;
import java.util.HashMap;
import java.util.Map;

/**Синглетон, создаёт инстанс карты, для добавления и удаления события на чекбоксе
 *
 */

public class MapGreatJCheckBox {

    private static MapGreatJCheckBox instance;

    Map<Integer, Integer> map;

    private MapGreatJCheckBox() {
        map = new HashMap<Integer, Integer>();

    }

    public static MapGreatJCheckBox greatMap() {
        if (instance == null) {
            instance = new MapGreatJCheckBox();
        }
        //else instance.remove(instance.size());
        return instance;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    /**
     * кладем в карту
     * @param key
     * @param value
     * @return
     */
    public Integer put(Integer key, Integer value) {
        return map.put(key, value);
    }

    /**
     *  получаем по ключу из карты
     * @param key
     * @return
     */
    public Integer get(Integer key) {
        return map.get(key);
    }

    /**
     * Удаление из карты по ключу (удаление с возвратом значения)
     * @param key
     * @return
     */
    public Integer remove(Integer key){
        return map.remove(key);
    }

    /**
     * Размер карты
     * @return
     */
    public int size() {
        return map.size();
    }
}
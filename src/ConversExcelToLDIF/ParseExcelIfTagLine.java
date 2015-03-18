package ConversExcelToLDIF;

import org.apache.poi.ss.usermodel.Sheet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Показывает первые несколько строк, всех столбцов из экселя,
 * показывает чекбоксы в том же коллличестве что и коллличество столбцов в экселе,
 * вешает на каждый свой листнер,
 * показывает комбобоксы в том же колличестве, листенер на каждый
 *
 * objList String массив, хранит значения для списка комбобокса
 * width - расстояние по ширине в форме, между элементами(чекбоксы и комбобоксы)
 * celQ - колличество колонок в эксэле
 */
public class ParseExcelIfTagLine {
    private static int width = AllWindows.getWidth();
    private static int numberCellSurname = -1;
    private static int numberCellName = -1;
    private static int numberCellPatronymic = -1;
    private static int numberCellPosition = -1;
    private static int numberCellPhone = -1;
    private static int numberCellEmail = -1;
    private static int numberCellOrganization = -1;
    private static int numberCellPassword = -1;
    private static String surname;
    private static String name;
    private static String patronymic;
    private static String position;
    private static String phone;
    private static String email;
    private static String organization;
    private static String password;
    private static String recUserToLDIF;

    /**
     *
     * @param dataForm  карта, котороая содержит имена выбранных тегов. Например {Имя,1}
     */

    protected static void getExcelTagLine(Map dataForm){  //вызывается по кнопке "Сформировать"
        final Sheet sheet = ParseDocumentExcel.getDocExel(AllWindows.getFileAbsolutePath(),AllWindows.getNumberSheetValue()-1);
        final ArrayList<String> allRecUserToLDIF = new ArrayList<>();
        final int quantityRow = ParseDocumentExcel.largestNumberOfRow(sheet);       //колличество строк в Excel
        allRecUserToLDIF.clear();

            if (dataForm.get("Имя")!=null){numberCellName = (int) dataForm.get("Имя");}      //вытаскиваем номера колонок из массива в соответствии с тэгом
            if (dataForm.get("Фамилия")!=null){numberCellSurname = (int) dataForm.get("Фамилия");}
            if (dataForm.get("Отчество")!=null){numberCellPatronymic = (int) dataForm.get("Отчество");}
            if (dataForm.get("Должность")!=null){numberCellPosition = (int) dataForm.get("Должность");}
            if (dataForm.get("Телефон")!=null){numberCellPhone = (int) dataForm.get("Телефон");}
            if (dataForm.get("Организация")!=null){numberCellOrganization = (int) dataForm.get("Организация");}
            if (dataForm.get("e-mail")!=null){numberCellEmail = (int) dataForm.get("e-mail");}
            if (dataForm.get("Пароль")!=null){numberCellPassword = (int) dataForm.get("Пароль");}
            /**
             * цикл по колличеству строк, вызывает метод  ParseCell, в качестве параметров
             * j - номер строки(итерация)
             * f - номер столбца(по имени тега)
             * sheet - лист экселя
             */
            for (int j=0;j<quantityRow;j++) {
                if (numberCellSurname > -1)
                    surname = (" " + ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellSurname, sheet));
                if (numberCellName > -1)
                    name = (" " + ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellName, sheet));
                if (numberCellPatronymic > -1)
                    patronymic = (" " + ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellPatronymic, sheet));
                if (numberCellSurname > -1)
                    position = (", " + ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellPosition, sheet));
                if (numberCellPosition > -1)
                    phone = (", " + ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellPhone, sheet));
                if (numberCellEmail > -1)
                    email = ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellEmail, sheet);
                if (numberCellOrganization > -1)
                    organization = ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellOrganization, sheet);
                if (numberCellPassword > -1)
                    password = ConversExcelToLDIF.ParseDocumentExcel.parseCell(j, numberCellPassword, sheet);
                recUserToLDIF = (                              //формирую лдиф
                        "dn: uid=" + email + "," + AllWindows.getBaseDNTextFieldValue() + "\n" +
                                "changetype: add\n" +
                                "objectClass: inetOrgPerson\n" +
                                "objectClass: posixAccount\n" +
                                "objectClass: person\n" +
                                "objectClass: top\n" +
                                "uidNumber: 23714\n" +
                                "gidNumber: 513\n" +
                                "cn: " + email + "\n" +
                                "description: " + surname + name + patronymic + position + phone + "\n" +
                                "displayName: " + surname + " " + name + " " + patronymic + "\n" +
                                "givenName: " + surname + " " + name + " " + patronymic + "\n" +
                                "homeDirectory: /home/" + email + "\n" +
                                "mail: " + email + "\n" +
                                "o: " + organization + "\n" +
                                "sn: " + email + "\n" +
                                "st: rus" + "\n" +
                                "telephoneNumber:" + phone + "\n" +
                                "title:" + position + "\n" +
                                "uid: " + email + "\n" +
                                "userPassword: " + password + "\n" + "\n" + "\n");
                if (email.equals("") || email.contains(" "))     //проверка обязательно поля e-mail
                {
                      recUserToLDIF = ""; }
                //  notAdd.add(j+1);                              //номер пустой записи
                      LogerError.log.info("В строке " + (j+1)+" и столбце " + (numberCellEmail+1) + " не указан e-mail");
                    if (password.equals("") || password.contains(" "))      //проверка обязательно поля Пароль
                    {
                        recUserToLDIF = ""; }
                        // notAdd.add(j+1);                              //номер пустой записи
                        LogerError.log.info("В строке " + (j+1)+" и столбце " + (numberCellPassword+1) + " не указан пароль");
                        recUserToLDIF = recUserToLDIF.replace("title:,", "title:");                //корректируем лдиф записи
                        recUserToLDIF = recUserToLDIF.replace("description: ,", "description:");
                        recUserToLDIF = recUserToLDIF.replace("telephoneNumber:,", "telephoneNumber:");
                        recUserToLDIF = recUserToLDIF.replace(", null", "");
                        recUserToLDIF = recUserToLDIF.replace("null", "");
                        recUserToLDIF = recUserToLDIF.replace(" ,", ",");
                        allRecUserToLDIF.add(recUserToLDIF);
                    }
                            try {
                                CreateLDIFFileRecord.formationLdif(allRecUserToLDIF);
                            } catch (IOException e) {
                                e.printStackTrace();
                                ConversExcelToLDIF.MessageWindows.msbox("Ошибка на файловой системе! Файл не создан.");
                                ConversExcelToLDIF.AllWindows.tagLineWindow();
                                LogerError.log.info("Ошибка на файловой системе! Файл не создан.");
                            }
//            for (int x = 0; x < notAdd.size(); x++){    // удаление одинаковых номеров строк не внесённых записей
//                for (int z = 0; z < notAdd.size(); z++){
//                    int k = notAdd.get(x);
//                    int l = notAdd.get(z);
//                    if (k==l & x!=z){
//                        notAdd.remove(x);
//                    }
//                }
//            }

    }




    /**
     * листнер отслеживает событие чекбоксов, вызывает  класс Checker
     * который записывает номер листнера и еденицу в карту
     */
    static class ActionHandlerCheckBox implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JCheckBox chb = (JCheckBox) event.getSource();
            if (chb.isSelected()) {
                Checker.chekerJCheckBoxPut(Integer.parseInt(chb.getText()),1);
            } else {
                Checker.checkerJCheckBoxRemove(Integer.parseInt(chb.getText()));
            }
        }
    }


    /**
     * листнер отслеживает событие комбобоксов, вызывает  класс Checker
     * который записывает номер листнера и и значение стринг в одну карту  Map<Integer, String> chekerJComboBox1,
     * где ind номер комбобокса и лиснера, а str значение выбора;
     * и во вторую   Map<String, Integer> chekerJComboBox2 где в качестве ключа наоборот значение выбора, а в роли значения номер лиснера и комбобокса;
     * Сделано для того чтобы отслеживать возможный выбор одинаковых значений комбобоксов
     * номер лиснера - подумать как получить по другому, сейчас это
     * результат целочисленнго деления координаты х на 140 - ужас как криво...
     * отслеживает повторяющийся выбор, в этом случае выдаёт сообщение "повторите выбор", удаляет запись из этого лиснера из обоих карт
     * в случае если выбор изменён на фразу "Выбрать объект" - удаляет записи из обоих карт
     */
    static class ActionHandlerComboBox implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JComboBox  cmb = (JComboBox) event.getSource();
            int ind = cmb.getX()/width;
            String str = cmb.getSelectedItem().toString().trim();
            Map<Integer, String> chekerJComboBox1 = ConversExcelToLDIF.Checker.getMapJComboBox1();
            Map<String, Integer> chekerJComboBox2 = ConversExcelToLDIF.Checker.getMapJComboBox2();
            if (chekerJComboBox1.isEmpty()&chekerJComboBox2.isEmpty()){
                ConversExcelToLDIF.Checker.chekerJComboBoxPut(ind,str);
                ConversExcelToLDIF.Checker.chekerJComboBoxPut(str,ind);
            }else {ConversExcelToLDIF.Checker.chekerJComboBoxRemove2(ConversExcelToLDIF.Checker.chekerJComboBoxGet(ind));ConversExcelToLDIF.Checker.chekerJComboBoxRemove(ind);
                if (MapGreatJComboBox2.greatMap2().getMap2().containsKey(str)){
                    cmb.setSelectedItem("повторите выбор");
                    ConversExcelToLDIF.Checker.chekerJComboBoxRemove2(ConversExcelToLDIF.Checker.chekerJComboBoxGet(ind));
                    if (ConversExcelToLDIF.Checker.chekerJComboBoxGet2(str)==null || ConversExcelToLDIF.Checker.chekerJComboBoxGet2(str)==cmb.getX()/width){
                        cmb.setSelectedItem(str);ConversExcelToLDIF.Checker.chekerJComboBoxPut(str,ind);}
                    if (cmb.getSelectedItem().toString().toLowerCase().trim().equals("повторите выбор")){ConversExcelToLDIF.Checker.chekerJComboBoxRemove(ind);
                        ConversExcelToLDIF.Checker.chekerJComboBoxRemove2(ConversExcelToLDIF.Checker.chekerJComboBoxGet(ind));}
                    if (str.equals("Выбрать объект")){ConversExcelToLDIF.Checker.chekerJComboBoxRemove2(ConversExcelToLDIF.Checker.chekerJComboBoxGet(ind));ConversExcelToLDIF.Checker.chekerJComboBoxRemove(ind);}
                }else {
                    if (str.equals("Выбрать объект")){ConversExcelToLDIF.Checker.chekerJComboBoxRemove2(ConversExcelToLDIF.Checker.chekerJComboBoxGet(ind));ConversExcelToLDIF.Checker.chekerJComboBoxRemove(ind);}
                    else {
                        ConversExcelToLDIF.Checker.chekerJComboBoxPut(str,ind);
                        ConversExcelToLDIF.Checker.chekerJComboBoxPut(ind,str);
                    }
                }
            }
        }
    }



}




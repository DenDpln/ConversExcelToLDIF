package ConversExcelToLDIF;

import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by den on 06.08.2014.
 */
public class AllWindows {
    private static String fileName;
    private static String filePath;
    private static String fileAbsolutePath;
    private static String baseDNTextFieldValue;
    private static String baseDNValue = null;
    private static String  radioChoice;
    private static int numberSheetValue = -1;
    private static String numberLineValue;
    private static int width =140;
    private static int quantityCellTable = 10;
    /**
     * первое окно выбора, откуда генерировать LDIF, из шаблона с указанными полями,
     * или из файла  указывая соответствие столбцов тэгам
     */
    protected static void tagLineOrTamplate(){
        final CreateFrame jfrmСhoice = CreateFrame.getInstans(500,250);
        final CreateButton nextButton = CreateButton.getInstans(340,160,120,30,"Далее");
        final CreateButton exitButton = CreateButton.getInstans(30,160,120,30,"Выход");
        final CreateButton helpTamplete = CreateButton.getInstans(410,40,45,30,"?");
        final CreateButton helpTagLane = CreateButton.getInstans(410,80,45,30,"?");
        final JRadioButton radioTemplate = new JRadioButton("Сформировать LDIF из шаблона",true);
        final JRadioButton radioTagLine = new JRadioButton("Сформировать LDIF указав соответсвие тэгов и столбцов",false);
        final ButtonGroup radioGroup = new ButtonGroup();
        radioTagLine.setBounds(30,80,370,30);
        radioTemplate.setBounds(30,40,370,30);
        radioTagLine.setActionCommand("TagLine");
        radioTemplate.setActionCommand("Tamplate");
        radioGroup.add(radioTemplate);
        radioGroup.add(radioTagLine);
        jfrmСhoice.add(exitButton);
        jfrmСhoice.add(nextButton);
        jfrmСhoice.add(helpTagLane);
        jfrmСhoice.add(helpTamplete);
        jfrmСhoice.add(radioTemplate);
        jfrmСhoice.add(radioTagLine);
        jfrmСhoice.setVisible(true);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 radioChoice = radioGroup.getSelection().getActionCommand();
                tamplateOrTagLaneWindowInternals(radioChoice);
                jfrmСhoice.dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        helpTamplete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpTamplate();
                jfrmСhoice.dispose();
            }
        });
        helpTagLane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpTagLine();
                jfrmСhoice.dispose();
            }
        });
}

    /**
     * кнопка помощи, вызывает окно с описанием загрузки из шаблона,
     * есть возможность скачать шаблон-пример.
     */
    protected static void helpTamplate(){
        final CreateFrame jfrmHelpTamplate = CreateFrame.getInstans(600,250);
        final CreateLabel line_1 = CreateLabel.getInstans(10,10,590,20,"     В Вашем файле должны присутствовать следующие поля, в первой строке:");
        final CreateLabel line_2 = CreateLabel.getInstans(10,30,590,20,"\"Имя\", \"Фамилия\", \"Отчество\", \"Должность\", \"Организация\",\"Телефон\", \"Пароль\", \"e-mail\".");
        final CreateLabel line_3 = CreateLabel.getInstans(10,50,590,20,"Порядок полей в первой строке может быть произвольным.");
        final CreateLabel line_4 = CreateLabel.getInstans(10,70,210,20,"     В столбцах обозначенных как -");
        final CreateLabel line_4_5 = CreateLabel.getInstans(225,70,250,20,"\"Пароль\" и \"e-mail\"");
        final CreateLabel line_5 = CreateLabel.getInstans(10,90,590,20,"должны быть заполнены все поля, иначе вся строка с пустым полем будет пропущена и в");
        final CreateLabel line_6 = CreateLabel.getInstans(10,110,590,20,"LDIF файл не вставлена, о чём Вы получите сообщение, с указанием номеров пустых строк.");
        final CreateLabel line_7 = CreateLabel.getInstans(10,130,590,20,"     Вы можете скачать шаблон файла EXCEL, в качестве примера.");
        final CreateButton downloadButton = CreateButton.getInstans(420,160,140,30,"Скачать шаблон");
        final CreateButton backButton = CreateButton.getInstans(150,160,120,30,"Назад");
        final CreateButton exitButton = CreateButton.getInstans(20,160,120,30,"Выход");
        line_2.setForeground(Color.BLUE);
        line_4_5.setForeground(Color.RED);
        jfrmHelpTamplate.add(line_1);
        jfrmHelpTamplate.add(line_2);
        jfrmHelpTamplate.add(line_3);
        jfrmHelpTamplate.add(line_4);
        jfrmHelpTamplate.add(line_4_5);
        jfrmHelpTamplate.add(line_5);
        jfrmHelpTamplate.add(line_6);
        jfrmHelpTamplate.add(line_7);
        jfrmHelpTamplate.add(downloadButton);
        jfrmHelpTamplate.add(backButton);
        jfrmHelpTamplate.add(exitButton);
        jfrmHelpTamplate.setVisible(true);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tagLineOrTamplate();
                jfrmHelpTamplate.dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * вызывается кнопкой  вызова помощи, описывает возможность указать в форме соответствие столбцов из
     * экселя тегам для формирования LDIF, есть возможность скачать пример-картинку
     */
    protected static void helpTagLine(){
        final CreateFrame jfrmHelpTagLine = CreateFrame.getInstans(600,250);
        final CreateLabel line_1 = CreateLabel.getInstans(10,10,590,20,"В форму \"Соответствие\" будут загружены первые десять строк из файла.");
        final CreateLabel line_2 = CreateLabel.getInstans(10,30,590,20,"Из выпадающего меню, нужно выбрать ТЭГ над соответствующей ему колонкой.");
        final CreateLabel line_3 = CreateLabel.getInstans(10,50,590,20,"Чтобы связать выбранный ТЭГ с нужной колонкой поставьте, под меню \"галочку\".");
        final CreateLabel line_4 = CreateLabel.getInstans(10,70,590,20,"Порядок выбора ТЭГов произвольный.");
        final CreateLabel line_5 = CreateLabel.getInstans(10,90,590,20,"Поля \"e-mail\" и \"Пароль\"   - должны быть указаны ОБЯЗАТЕЛЬНО! ");
        final CreateButton exampleButton = CreateButton.getInstans(440,160,120,30,"Пример");
        final CreateButton backButton = CreateButton.getInstans(150,160,120,30,"Назад");
        final CreateButton exitButton = CreateButton.getInstans(20,160,120,30,"Выход");

        jfrmHelpTagLine.add(line_1);
        jfrmHelpTagLine.add(line_2);
        jfrmHelpTagLine.add(line_3);
        jfrmHelpTagLine.add(line_4);
        jfrmHelpTagLine.add(line_5);
        jfrmHelpTagLine.add(exampleButton);
        jfrmHelpTagLine.add(exitButton);
        jfrmHelpTagLine.add(backButton);
        jfrmHelpTagLine.setVisible(true);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tagLineOrTamplate();
                jfrmHelpTagLine.dispose();
            }
        });

    }

    /**
     *  вызывается кнопкой "Далее" в окне TagLineOrTamplate
     * @param radioChoice - параметр указывающий какая радиокнопка выбрана в окне TagLineOrTamplate
     *  в зависимости от значения radioChoice формирует окно в котором либо кнопка для скачивания шаблона,
     *  либо картинки с описанием тегов - столбцов.Остальные элементы одинаковы: поле для ввода BaseDN, номера
     *  листа в Excel и указанием номеров строк, не нужных в LDIFе - через запятую.
     */
    protected static void tamplateOrTagLaneWindowInternals(final String radioChoice){
        final CreateFrame jfrmWindowInternals = CreateFrame.getInstans(530,360);
        if (radioChoice.equals("Tamplate")){
            final CreateButton exampleButton = CreateButton.getInstans(235,280,130,30,"Скачать шаблон");
            jfrmWindowInternals.add(exampleButton);
        }
        if (radioChoice.equals("TagLine")){
            final CreateButton downloadButton = CreateButton.getInstans(235,280,130,30,"Открыть пример");
            jfrmWindowInternals.add(downloadButton);
        }
        final CreateLabel labelBDN = CreateLabel.getInstans(125,40,300,30,"Обязательно к заполнению!  Укажите BaseDN.");
        final CreateTextField textFieldBDN = CreateTextField.getInstans(20,70,470,30);
        final CreateTextField textFieldListNumber = CreateTextField.getInstans(20,130,30,30);
        final CreateLabel labelListNumber = CreateLabel.getInstans(70,130,300,30,"Обязательно к заполнению!  Укажите номер листа.");
        final CreateLabel labelExcludeLine = CreateLabel.getInstans(40,190,480,30,"Укажите через запятую, номера строк, которые не нужно включать в LDIF.");
        final CreateTextField textFieldExcludeLine = CreateTextField.getInstans(20,220,470,30);
        final CreateButton backButton = CreateButton.getInstans(125,280,100,30,"Назад");
        final CreateButton exitButton = CreateButton.getInstans(20,280,100,30,"Выход");
        final CreateButton choiceFile = CreateButton.getInstans(370,280,130,30,"Выбрать файл");
        textFieldExcludeLine.setText("");
        textFieldListNumber.setText("1");
        jfrmWindowInternals.add(labelBDN);
        jfrmWindowInternals.add(labelListNumber);
        jfrmWindowInternals.add(labelExcludeLine);

        jfrmWindowInternals.add(exitButton);
        jfrmWindowInternals.add(backButton);
        jfrmWindowInternals.add(choiceFile);


        textFieldExcludeLine.getDocument().addDocumentListener(new DocumentListener() {
            String numbLine;
            @Override
            public void insertUpdate(DocumentEvent e) {
                numbLine =  textFieldExcludeLine.getText();
                if (!checkNumberLineInsert(numbLine)){MessageWindows.msbox("Введите число! Или запятую!");
                      numbLine = numbLine.substring(0,numbLine.length()-1);
                    System.out.println("после удаления последнего символа  " + numbLine);
                    Document rr = textFieldExcludeLine.getDocument();
                    System.out.println(rr.getLength() + "   длинна нового документа  " + rr.toString());

                    try {
                        rr.remove(rr.getLength(),rr.getLength());
                        rr.insertString(rr.getLength(),numbLine,null);
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                  //  textFieldExcludeLine.setText(numbLine);
                }else {numberLineValue = numbLine;}
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                numbLine =  textFieldExcludeLine.getText();
//                if (!checkNumberLineInsert(numbLine)){MessageWindows.msbox("Введите число! Или запятую!");
//                }else {numberLineValue = numbLine;}
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                numbLine =  textFieldExcludeLine.getText();
                if (!checkNumberLineInsert(numbLine)){MessageWindows.msbox("Введите число! Или запятую!");
                }else {numberLineValue = numbLine;}
            }
        });
        jfrmWindowInternals.add(textFieldExcludeLine);

        /**
         * лиснер отлавливает изменения в поле textFieldListNumber и передаёт в переменную numberSheet
         */
        textFieldListNumber.getDocument().addDocumentListener(new DocumentListener() {
            String numb;
            @Override
            public void insertUpdate(DocumentEvent e) {
                numb =  textFieldListNumber.getText();
                if (checkNumberSheetInsert(numb)){numberSheetValue = Integer.parseInt(numb);
                }else {
                    MessageWindows.msbox("Введите число!");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                numb =  textFieldListNumber.getText();
                if (checkNumberSheetInsert(numb)){numberSheetValue = Integer.parseInt(numb);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                numb =  textFieldListNumber.getText();
                if (checkNumberSheetInsert(numb)){numberSheetValue = Integer.parseInt(numb);
                }
            }
        });
        /**
         * лиснер отлавливает изменения в поле textFieldBDN и передаёт в переменную baseDNValue
         */
        textFieldBDN.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                baseDNValue =  textFieldBDN.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                baseDNValue =  textFieldBDN.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) { baseDNValue =  textFieldBDN.getText(); }
        });
        if (baseDNValue != null){textFieldBDN.setText(baseDNValue);}
        jfrmWindowInternals.add(textFieldBDN);
        if (numberSheetValue > 0){textFieldListNumber.setText(String.valueOf(numberSheetValue));}
        jfrmWindowInternals.add(textFieldListNumber);
        jfrmWindowInternals.setVisible(true);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tagLineOrTamplate();
                jfrmWindowInternals.dispose();
            }
        });
        choiceFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                baseDNTextFieldValue = textFieldBDN.getText();
                numberSheetValue = Integer.parseInt(textFieldListNumber.getText());
                if (baseDNTextFieldValue.equals("")){MessageWindows.msbox("BaseDN не указан!");}
                else { if (numberSheetValue <=0 ){MessageWindows.msbox("Номер листа не указан.Значение по умолчанию - 1");
                numberSheetValue =  1;
                textFieldListNumber.setText("1");}else {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    fileName = fileopen.getSelectedFile().getName();
                    fileAbsolutePath = fileopen.getSelectedFile().getAbsolutePath();
                    filePath = fileopen.getSelectedFile().getParentFile().toString();

                }
                confirmsSelectionParam(radioChoice);
                jfrmWindowInternals.dispose();}}}

        });
    }

    protected static void confirmsSelectionParam(final String radioChoice){
        final CreateFrame jfrmConfirmsSelectionParam = CreateFrame.getInstans(500,300);
        final CreateLabel labelExcludeLine = CreateLabel.getInstans(20,140,460,30,"вставить номера строк");
        final CreateButton exitButton = CreateButton.getInstans(20,220,120,30,"Выход");
        final CreateButton backButton = CreateButton.getInstans(145,220,120,30,"Назад");
        final CreateButton readyButton = CreateButton.getInstans(350,220,120,30,"Далее");
        readyButton.setEnabled(false);
        String checkResultTamplate = "Файл не соответствует шаблону";
        final Sheet sheet = ParseDocumentExcel.getDocExel(fileAbsolutePath, numberSheetValue - 1);
        int passwordCellNumber = ParseDocumentExcel.searchCellByName(sheet, "пароль");
        int emailCellNumber = ParseDocumentExcel.searchCellByName(sheet, "e-mail");
        if (passwordCellNumber == -2 || emailCellNumber == -2){checkResultTamplate = "В шаблоне есть столбцы с одинаковым названием";}
        else {if (passwordCellNumber > -1 && emailCellNumber > -1){checkResultTamplate = "Файл соответствует шаблону";
                  readyButton.setEnabled(true);}
        }
        String absolutePathText = "Выбран файл:  " + fileAbsolutePath;
        String numberListText = "Указан номер листа в Excel :  " + numberSheetValue;
        String baseDNText = "Указан BaseDN :  " + baseDNTextFieldValue;
        final CreateLabel labelAbsolutePath = CreateLabel.getInstans(20,20,460,30,absolutePathText);
        if (radioChoice.equals("Tamplate")){
            final CreateLabel labelTamplateOrTagLineCheck = CreateLabel.getInstans(20,50,460,30,checkResultTamplate);
            final CreateLabel labelTamplateOrTagLine = CreateLabel.getInstans(20,80,460,30,"Вы выбрали - Сформировать LDIF из шаблона");
            jfrmConfirmsSelectionParam.add(labelTamplateOrTagLine);
            jfrmConfirmsSelectionParam.add(labelTamplateOrTagLineCheck);
        }
        if (radioChoice.equals("TagLine")){
            final CreateLabel labelTamplateOrTagLineCheck = CreateLabel.getInstans(20,50,460,30,checkResultTamplate);
            final CreateLabel labelTamplateOrTagLine = CreateLabel.getInstans(20,80,460,30,"Вы выбрали - Сформировать LDIF указав соответсвие тэгов и столбцов");
            jfrmConfirmsSelectionParam.add(labelTamplateOrTagLine);
            jfrmConfirmsSelectionParam.add(labelTamplateOrTagLineCheck);
        }
        final CreateLabel labelNumberList = CreateLabel.getInstans(20,110,460,30,numberListText);
        final CreateLabel labelBaseDN = CreateLabel.getInstans(20,170,460,30,baseDNText);
        jfrmConfirmsSelectionParam.add(labelBaseDN);
        jfrmConfirmsSelectionParam.add(labelExcludeLine);
        jfrmConfirmsSelectionParam.add(labelNumberList);
        jfrmConfirmsSelectionParam.add(labelAbsolutePath);
        jfrmConfirmsSelectionParam.add(exitButton);
        jfrmConfirmsSelectionParam.add(backButton);
        jfrmConfirmsSelectionParam.add(readyButton);
        jfrmConfirmsSelectionParam.setVisible(true);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tamplateOrTagLaneWindowInternals(radioChoice);
                jfrmConfirmsSelectionParam.dispose();
            }
        });
        readyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogProp.updateLog4jConfiguration(filePath + "\\" + fileName + "_ldif.log");
                if (radioChoice.equals("Tamplate")){
                try {
                    ParseExcelIfTamplate.getExcelTamplate("фамилия", "имя", "отчество", "должность", "телефон", "e-mail", "организация", "пароль");
                } catch (IOException e1) {
                    MessageWindows.msbox("Файл не найден!");
                }
                jfrmConfirmsSelectionParam.dispose();
            }else {
                    AllWindows.tagLineWindow();
                }
            }
        });
    }

    protected static void tagLineWindow() {
        ActionListener actionListenerCheckBox = new ParseExcelIfTagLine.ActionHandlerCheckBox();
        ActionListener actionListenerComboBox = new ParseExcelIfTagLine.ActionHandlerComboBox();
        String[] dropDownMenu = new String[]{"Выбрать объект", "Фамилия", "Имя", "Отчество", "e-mail", "Пароль", "Организация", "Телефон", "Должность"};
        final ArrayList<ArrayList<String>> allCellByRow = ParseDocumentExcel.getExcelAllRowAndCell();
        final ArrayList<JComboBox> JComBox = new ArrayList<JComboBox>();
        final ArrayList<JCheckBox> JChBox = new ArrayList<JCheckBox>();
        Sheet sheet = ParseDocumentExcel.getDocExel(AllWindows.getFileAbsolutePath(), AllWindows.getNumberSheetValue() -1);
        final int cellQuantity = ParseDocumentExcel.largestNumberOfCell(sheet);
        int x = 0;
        int z = 0;
        final CreateFrame jfrmTagLine = CreateFrame.getInstans(800, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        /**
         * JComboBox to create the number of columns in Excel and adding to the panel and list Arrey
         */
        for (int p = 0; p < cellQuantity; p++) {
            final DefaultComboBoxModel model = new DefaultComboBoxModel(dropDownMenu);
            JComBox.add(new JComboBox(model));
            JComBox.get(p).setBounds(20 + z, 10, 130, 20);
            z = z + width;
            JComBox.get(p).setEditable(true);
            JComBox.get(p).addActionListener(actionListenerComboBox);
            jPanel.add(JComBox.get(p));
        }
        jPanel.setPreferredSize(new Dimension(20 + z, 450));

/**
 * JCheckBox to create the number of columns in Excel and adding to the panel and list Arrey
 */

        for (int p = 0; p < cellQuantity; p++) {
            JChBox.add(new JCheckBox(String.valueOf(p)));
            JChBox.get(p).setBounds(75 + x, 50, 20, 20);
            JChBox.get(p).addActionListener(actionListenerCheckBox);
            x = x + width;
            jPanel.add(JChBox.get(p));
        }
        jPanel.setVisible(true);

        /**
         * *columns in the table is the same as in Excel
         */
        JTable visualTable = new JTable(quantityCellTable, cellQuantity);
        visualTable.setBounds(20, 100, cellQuantity * width, 160);
        visualTable.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        for (int k = 0; k < cellQuantity; k++) {
            visualTable.getColumnModel().getColumn(k).setMaxWidth(width);
            visualTable.getColumnModel().getColumn(k).setMinWidth(width);
        }
        visualTable.getColumnModel().setColumnSelectionAllowed(true);
        visualTable.setRowSelectionAllowed(false);
        if (ParseDocumentExcel.largestNumberOfRow(sheet)<quantityCellTable)quantityCellTable=ParseDocumentExcel.largestNumberOfRow(sheet);
        for (int f = 0; f < quantityCellTable; f++) {
            ArrayList<String> rowEx = allCellByRow.get(f);
            for (int w = 0; w  < cellQuantity ; w++) {
                visualTable.setValueAt(rowEx.get(w), f, w);
            }
        }
        jPanel.add(visualTable);


        /**
         * горизонтальный скрол
         */
        JScrollPane sc = new JScrollPane(jPanel);
        sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        sc.setSize(790, 300);


        /**
         * кнопка
         */
        final JButton backButton = new JButton("Назад");
        backButton.setBounds(10, 320, 80, 30);
        jfrmTagLine.add(backButton);


        /**
         * кнопка
         */
        final JButton generateButton = new JButton("Сформировать");
        generateButton.setBounds(100, 320, 150, 30);
        jfrmTagLine.add(generateButton);
        jfrmTagLine.add(sc);
        jfrmTagLine.setVisible(true);
        jfrmTagLine.setLocationRelativeTo(null);
        final JButton exitButton = new JButton("Выход");
        jfrmTagLine.add(exitButton);
        exitButton.setBounds(690, 320, 80, 30);


        /**
         * листнер - по нажатию кнопки "сформировать" вызывает Checker, и получает обе карты
         * MapJCheckBox и MapJComboBox
         */
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<Integer, Integer> chekerJCheckBox = ConversExcelToLDIF.Checker.getMapJCheckBox();
                Map<Integer, String> chekerJComboBox = ConversExcelToLDIF.Checker.getMapJComboBox1();
                Map<String, Integer> checkerResult = new HashMap<String, Integer>();
                int check;
                if (chekerJCheckBox.isEmpty() || chekerJComboBox.isEmpty()) {
                    ConversExcelToLDIF.MessageWindows.msbox("Объекты не выбраны");
                } else {
                    for (Map.Entry entry : chekerJCheckBox.entrySet()) {
                        check = (int) entry.getKey();
                        for (Map.Entry entry1 : chekerJComboBox.entrySet()) {
                            if (check == (int) entry1.getKey()) {
                                checkerResult.put(entry1.getValue().toString(), check);
                            }
                        }
                    }
                    if (!checkerResult.containsKey("e-mail")){MessageWindows.msbox("Не указано соответствие тэга  e-mail");}
                    if (!checkerResult.containsKey("Пароль")){MessageWindows.msbox("Не указано соответствие тэга Пароль");}
                    if (checkerResult.containsKey("e-mail")&&checkerResult.containsKey("Пароль")){
                    ConversExcelToLDIF.ParseExcelIfTagLine.getExcelTagLine(checkerResult);
                    checkerResult.clear();
                    jfrmTagLine.setVisible(false);
                    jfrmTagLine.removeAll();
                    jfrmTagLine.setEnabled(false);}
                   AllWindows.finishWindow();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConversExcelToLDIF.AllWindows.confirmsSelectionParam(AllWindows.getRadioChoice());
                jfrmTagLine.setEnabled(false);
                jfrmTagLine.removeAll();
                // ConversExcelToLDIF.GetExelAuto.notAdd.clear();

            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogProp.updateLog4jConfiguration(filePath + "\\" + fileName + "_ldif.log");
                LogerError.log.info("---------------- выход ----------------");
                System.exit(0);
            }
        });
    }

    protected static void finishWindow() {
        final CreateFrame jfrmFinishWindow = CreateFrame.getInstans(500,220);
        final CreateLabel parhSave = CreateLabel.getInstans(10,10,490,30,"Файл сохранён в :          " + filePath);
        final CreateLabel nameLDIF = CreateLabel.getInstans(10, 40, 490, 30,"Имя LDIF файла :           " +
                CreateLDIFFileRecord.getReadyLdifFile());
        final CreateLabel lineNotAdd = CreateLabel.getInstans(10,70,490,30,"Строки не внесены в LDIF : ");
        final CreateLabel nameLogFile = CreateLabel.getInstans(10,100,490,30,"Имя LOG файла :            " +
                filePath + "\\" + fileName + "_ldif.log");
        final CreateButton exitButton = CreateButton.getInstans(10,140,100,30,"Выход");
        final CreateButton beginningButton = CreateButton.getInstans(370,140,100,30,"В начало");
        jfrmFinishWindow.add(parhSave);
        jfrmFinishWindow.add(nameLDIF);
        jfrmFinishWindow.add(lineNotAdd);
        jfrmFinishWindow.add(nameLogFile);
        jfrmFinishWindow.add(exitButton);
        jfrmFinishWindow.add(beginningButton);
        jfrmFinishWindow.setVisible(true);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogerError.log.info("---------------- выход ----------------");
                System.exit(0);
            }
        });
        beginningButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogerError.log.info("---------------- в начало ----------------");
                AllWindows.tagLineOrTamplate();
            }
        });
    }


    protected static String getFileName(){return fileName;}
    protected static String getFilePath(){return filePath;}
    protected static String getFileAbsolutePath(){return fileAbsolutePath;}
    protected static String getBaseDNTextFieldValue(){return baseDNTextFieldValue;}
    protected static int getNumberSheetValue(){return numberSheetValue;}
    protected static String getRadioChoice(){return radioChoice;}
    protected static int getWidth(){return width;}
    /**
     * проверяет ввод в поле - номер листа, если приводится к типу int возвращает true
     * иначе false
     * @param text
     * @return
     */
    private  static boolean checkNumberSheetInsert(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private  static boolean checkNumberLineInsert(String text) {
        boolean ret = false;
        boolean ret1 = false;
        boolean ret2 = false;
        char c;
        char[] delemiters = {','};
       // for (int i = 0; i < text.length(); i++) {
            String r = String.valueOf(text.charAt(text.length()-1));
            System.out.println("До проверки  "+r + "  номер символа "  +  text.length() +  "   проверяем символ  " + text.charAt(text.length()-1));
            if (r.contains(",")|r.contains(" ")) {
                System.out.println(" условие выполняется есть запятая "+r);
               ret1 = true;
            }
            if (checkNumberSheetInsert(r)){
                System.out.println(" условие выполняется есть цифра "+r);
                ret2 = true;
            }
            if (ret1|ret2){
               ret = true;
            }
     //   }
        System.out.println(ret);
        System.out.println("--------------------");
        return ret;

    }





    public static void main(String[] args) {
       tagLineOrTamplate();
       // FinishWindow();

    }
}

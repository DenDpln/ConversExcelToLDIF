package ConversExcelToLDIF;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by den on 15.08.2014.
 */
public class ParseExcelIfTamplate {

    private static int numberCellSurname;
    private static int numberCellName;
    private static int numberCellPatronymic;
    private static int numberCellPosition;
    private static int numberCellPhone;
    private static int numberCellEmail;
    private static int numberCellOrganization;
    private static int numberCellPassword;
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
     * @param surnameValue  фамилия
     * @param nameValue     имя
     * @param patronymicValue  отчество
     * @param positionValue  должность
     * @param phoneValue  телефон
     * @param emailValue  e-mail
     * @param organizationValue  организация
     * @param passwordValue  пароль
     * @throws IOException
     */
       protected static void getExcelTamplate(String surnameValue, String nameValue, String patronymicValue, String positionValue, String phoneValue, String emailValue, String organizationValue, String passwordValue) throws IOException {
           final Sheet sheet = ParseDocumentExcel.getDocExel(AllWindows.getFileAbsolutePath(),AllWindows.getNumberSheetValue()-1);
           final ArrayList<String> allRecUserToLDIF = new ArrayList<>();
           numberCellSurname = ParseDocumentExcel.searchCellByName(sheet,surnameValue.toLowerCase());           //получаю номера столбцов с указаными значениями
           numberCellName = ParseDocumentExcel.searchCellByName(sheet,nameValue.toLowerCase());
           numberCellPatronymic = ParseDocumentExcel.searchCellByName(sheet,patronymicValue.toLowerCase());
           numberCellPosition = ParseDocumentExcel.searchCellByName(sheet,positionValue.toLowerCase());
           numberCellPhone = ParseDocumentExcel.searchCellByName(sheet,phoneValue.toLowerCase());
           numberCellEmail = ParseDocumentExcel.searchCellByName(sheet,emailValue.toLowerCase());
           numberCellOrganization = ParseDocumentExcel.searchCellByName(sheet,organizationValue.toLowerCase());
           numberCellPassword = ParseDocumentExcel.searchCellByName(sheet,passwordValue.toLowerCase());
          for (int i = 1;i < ParseDocumentExcel.largestNumberOfRow(sheet);i++){  //вытаскиваю значения ячеек
              surname = ParseDocumentExcel.parseCell(i,numberCellSurname,sheet);
              name = ParseDocumentExcel.parseCell(i, numberCellName, sheet);
              patronymic = ParseDocumentExcel.parseCell(i, numberCellPatronymic, sheet);
              position = ParseDocumentExcel.parseCell(i, numberCellPosition, sheet);
              phone = ParseDocumentExcel.parseCell(i, numberCellPhone, sheet);
              email = ParseDocumentExcel.parseCell(i, numberCellEmail, sheet);
              organization = ParseDocumentExcel.parseCell(i, numberCellOrganization, sheet);
              password = ParseDocumentExcel.parseCell(i, numberCellPassword, sheet);
              recUserToLDIF = (                              //формирую лдиф
                      "dn: uid=" + email + AllWindows.getBaseDNTextFieldValue() + "\n" +
                              "changetype: add\n" +
                              "objectClass: inetOrgPerson\n" +
                              "objectClass: posixAccount\n" +
                              "objectClass: person\n" +
                              "objectClass: top\n" +
                              "uidNumber: 23714\n" +
                              "gidNumber: 513\n" +
                              "cn: " + email + "\n" +
                              "description: " + surname + " " + name + " " + patronymic + ", " + position + ", " + phone + "\n" +
                              "displayName: " + surname + " " + name + " " + patronymic + "\n" +
                              "givenName: " + surname + " " + name + " " + patronymic + "\n" +
                              "homeDirectory: /home/" + email + "\n" +
                              "mail: " + email + "\n" +
                              "o: " + organization + "\n" +
                              "sn: " + email + "\n" +
                              "st: rus" + "\n" +
                              "telephoneNumber: " + phone + "\n" +
                              "title: " + position + "\n" +
                              "uid: " + email + "\n" +
                              "userPassword: " + password + "\n" + "\n" + "\n");
              if (email.contains(" ") || email.equals("")) //проверка обязательно поля e-mail
              {
                  recUserToLDIF = "";
                  LogerError.log.info("В строке " + (i+1)+" и столбце " + (numberCellEmail+1) + " не указан e-mail");
              }
              if (password.contains(" ") || password.equals(""))   // Проверка обязательно поля Пароль
              {
                  recUserToLDIF = "";
                  LogerError.log.info("В строке " + (i+1)+" и столбце " + (numberCellPassword+1) + " не указан пароль");
              }
              allRecUserToLDIF.add(recUserToLDIF);
          }
           try {
               CreateLDIFFileRecord.formationLdif(allRecUserToLDIF);
               AllWindows.finishWindow();
           } catch (IOException e) {
               e.printStackTrace();
               ConversExcelToLDIF.MessageWindows.msbox("Ошибка на файловой системе! Файл не создан.");
               LogerError.log.info("Ошибка на файловой системе! Файл не создан.");
           }

       }

}

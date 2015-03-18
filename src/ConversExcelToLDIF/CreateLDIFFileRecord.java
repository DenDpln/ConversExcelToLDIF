package ConversExcelToLDIF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

class CreateLDIFFileRecord {
    private static String newFileAbsolutePath;
        protected static String formationLdif(ArrayList<String> text) throws IOException {
            Calendar calendar = Calendar.getInstance();
            newFileAbsolutePath = (AllWindows.getFilePath()+ "\\" + "LDIF_" + calendar.get(Calendar.HOUR)+"-"+calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + ".ldif");
    File ldifFile = new File(newFileAbsolutePath);
    try{
        ldifFile.createNewFile();
        FileWriter wrt = new FileWriter(ldifFile);
            for (int r = 0; r<text.size();r++){
                wrt.append(text.get(r));
            } wrt.close();
     text.clear();
    }catch(IOException e){
       LogerError.log.info("Ошибка на файловой системе! LOG файл не создан.");
        e.printStackTrace();
    }        text.clear();
            return null;
        }
    protected static String getReadyLdifFile(){return newFileAbsolutePath;}
    }


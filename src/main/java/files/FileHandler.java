package files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class FileHandler {

    /*читает файл с бинарного файла/сокета -> записывет в бинарный файл/сокет*/
    public static void transferBinaryFile(BufferedOutputStream bos, BufferedInputStream bis) throws IOException {
        int in;
        int length = 4096;
        byte[] byteArray = new byte[length];
        while ((in = bis.read(byteArray)) != -1){
            if (in<length)
                break;
            else  bos.write(byteArray,0,in);
        }
        bos.write(byteArray);

    }
}

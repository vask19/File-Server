package files;

import data.Request;

public class test {
    public static void main(String[] args) {

        String filePath = "c\\r\\b\\re\\tt.trt";
        String[] a = (filePath.split("\\\\"));
        String b = a[a.length-1];
        System.out.println(b);
    }

}

package com.timeless;

public class HexTool {

    //字符串转十六进制
    public static String toHex(String s) {
        String ss = s;
        byte[] bt = new byte[0];

        try {
            bt = ss.getBytes("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        String s1 = "";
        for (int i = 0; i < bt.length; i++)
        {
            String tempStr = Integer.toHexString(bt[i]);
            if (tempStr.length() > 2)
                tempStr = tempStr.substring(tempStr.length() - 2);
            s1 = s1 + tempStr + "";
        }
        return s1.toUpperCase();
    }

    // 转化十六进制编码为字符串
    public static String toString(String s) throws Exception {
        s = s.substring(2, s.length());
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                    i * 2, i * 2 + 2), 16));

        }

        // UTF-16le:Not
        s = new String(baKeyword, "utf-8");

        return s;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(toHex("'name':'lisi'"));
//        System.out.println(toString("7B2773273A2773277D"));
        String ax = "0x7B2773273A2773277D";
        System.out.println(ax.substring(2, ax.length()));
    }
}
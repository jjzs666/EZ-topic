package work.jame.topic.util;

import java.text.DecimalFormat;

/**
 * @author : Jame
 * @date : 2022-05-19 19:48
 * @description :
 **/
public class StringUtil {


    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }




    /**
     *  <a href="https://blog.csdn.net/rchm8519/article/details/107225653">来源</a>
     * 第二种实现方式 (获取两串不匹配字符数)
     * @param str
     * @param target
     * @return
     *
     */
    private static int compare(String str, String target) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        // 遍历str
        for (i = 1; i <= n; i++) {
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 比较俩个字符串的相似度（方式一）
     * 步骤1：获取两个串中不相同的字符数
     * 步骤2：不同字符数 除以 较长串的长度
     * @param strA
     * @param strB
     * @return
     */
    public static double similarityRatio(String strA, String strB) {
        return 1 - (double) compare(strA, strB) / Math.max(strA.length(), strB.length());
    }

    public static int getAnswerType(String s){
        if(s.contains("单"))
            return 1;
        if(s.contains("多"))
            return 2;
        if(s.contains("判断"))
            return 3;
        return -1;
    }






}

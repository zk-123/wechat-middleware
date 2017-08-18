package cn.zkdcloud.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 接口认证
 * Created by zk on 2016/10/30.
 */
public class ConfirmUtil {

    /**微信认证
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return
     */
    public static String confirm(String signature,String timestamp,String nonce,String echostr){
        List<String> list = new ArrayList<String>();
        list.add(Const.TOKEN);
        list.add(timestamp);
        list.add(nonce);

        Collections.sort(list, new SpellComparator()); //排序
        String temp = list.get(0) +list.get(1) + list.get(2);//拼接字符串

        String digest = new SHA1().getDigestOfString(temp.getBytes());//sha1 校验并返回结果
        return digest.equalsIgnoreCase(signature) == true ? echostr : "";
    }

    /**
     * 字母默认排序
     */
    public static class SpellComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            try {
                // 取得比较对象的汉字编码，并将其转换成字符串
                String s1 = new String(o1.toString().getBytes("utf-8"), "utf-8");
                String s2 = new String(o2.toString().getBytes("utf-8"), "utf-8");
                // 运用String类的 compareTo（）方法对两对象进行比较
                return s1.compareTo(s2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    /**
     * SHA1 算法
     */
    public static class SHA1 {
        private final int[] abcde = {
                0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476, 0xc3d2e1f0
        };
        private int[] digestInt = new int[5];
        private int[] tmpData = new int[80];

        private int process_input_bytes(byte[] bytedata) {
            System.arraycopy(abcde, 0, digestInt, 0, abcde.length);
            byte[] newbyte = byteArrayFormatData(bytedata);
            int MCount = newbyte.length / 64;
            for (int pos = 0; pos < MCount; pos++) {
                for (int j = 0; j < 16; j++) {
                    tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));
                }
                encrypt();
            }
            return 20;
        }

        private byte[] byteArrayFormatData(byte[] bytedata) {
            int zeros = 0;
            int size = 0;
            int n = bytedata.length;
            int m = n % 64;
            if (m < 56) {
                zeros = 55 - m;
                size = n - m + 64;
            } else if (m == 56) {
                zeros = 63;
                size = n + 8 + 64;
            } else {
                zeros = 63 - m + 56;
                size = (n + 64) - m + 64;
            }
            byte[] newbyte = new byte[size];
            System.arraycopy(bytedata, 0, newbyte, 0, n);
            int l = n;
            newbyte[l++] = (byte) 0x80;
            for (int i = 0; i < zeros; i++) {
                newbyte[l++] = (byte) 0x00;
            }
            long N = (long) n * 8;
            byte h8 = (byte) (N & 0xFF);
            byte h7 = (byte) ((N >> 8) & 0xFF);
            byte h6 = (byte) ((N >> 16) & 0xFF);
            byte h5 = (byte) ((N >> 24) & 0xFF);
            byte h4 = (byte) ((N >> 32) & 0xFF);
            byte h3 = (byte) ((N >> 40) & 0xFF);
            byte h2 = (byte) ((N >> 48) & 0xFF);
            byte h1 = (byte) (N >> 56);
            newbyte[l++] = h1;
            newbyte[l++] = h2;
            newbyte[l++] = h3;
            newbyte[l++] = h4;
            newbyte[l++] = h5;
            newbyte[l++] = h6;
            newbyte[l++] = h7;
            newbyte[l++] = h8;
            return newbyte;
        }

        private int f1(int x, int y, int z) {
            return (x & y) | (~x & z);
        }

        private int f2(int x, int y, int z) {
            return x ^ y ^ z;
        }

        private int f3(int x, int y, int z) {
            return (x & y) | (x & z) | (y & z);
        }

        private int f4(int x, int y) {
            return (x << y) | x >>> (32 - y);
        }

        private void encrypt() {
            for (int i = 16; i <= 79; i++) {
                tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14] ^
                        tmpData[i - 16], 1);
            }
            int[] tmpabcde = new int[5];
            for (int i1 = 0; i1 < tmpabcde.length; i1++) {
                tmpabcde[i1] = digestInt[i1];
            }
            for (int j = 0; j <= 19; j++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[j] + 0x5a827999;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int k = 20; k <= 39; k++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[k] + 0x6ed9eba1;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int l = 40; l <= 59; l++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[l] + 0x8f1bbcdc;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int m = 60; m <= 79; m++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[m] + 0xca62c1d6;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int i2 = 0; i2 < tmpabcde.length; i2++) {
                digestInt[i2] = digestInt[i2] + tmpabcde[i2];
            }
            for (int n = 0; n < tmpData.length; n++) {
                tmpData[n] = 0;
            }
        }

        private int byteArrayToInt(byte[] bytedata, int i) {
            return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16) |
                    ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);
        }

        private void intToByteArray(int intValue, byte[] byteData, int i) {
            byteData[i] = (byte) (intValue >>> 24);
            byteData[i + 1] = (byte) (intValue >>> 16);
            byteData[i + 2] = (byte) (intValue >>> 8);
            byteData[i + 3] = (byte) intValue;
        }

        private String byteToHexString(byte ib) {
            char[] Digit = {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                    'D', 'E', 'F'
            };
            char[] ob = new char[2];
            ob[0] = Digit[(ib >>> 4) & 0X0F];
            ob[1] = Digit[ib & 0X0F];
            String s = new String(ob);
            return s;
        }

        private String byteArrayToHexString(byte[] bytearray) {
            String strDigest = "";
            for (int i = 0; i < bytearray.length; i++) {
                strDigest += byteToHexString(bytearray[i]);
            }
            return strDigest;
        }

        public byte[] getDigestOfBytes(byte[] byteData) {
            process_input_bytes(byteData);
            byte[] digest = new byte[20];
            for (int i = 0; i < digestInt.length; i++) {
                intToByteArray(digestInt[i], digest, i * 4);
            }
            return digest;
        }

        public String getDigestOfString(byte[] byteData) {
            return byteArrayToHexString(getDigestOfBytes(byteData));
        }

    }

}

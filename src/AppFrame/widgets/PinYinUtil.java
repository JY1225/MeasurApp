package AppFrame.widgets;


import java.util.HashSet;
import java.util.Set;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @author  : Robert robert@xiaobei668.com
 * @version : 1.00
 * Create Time : 2011-3-22-����07:04:30
 * Description : 
 *              �����ֺͶ�Ӧƴ��ת���Ĺ����� 
 * History��
 *  Editor       version      Time               Operation    Description*
 *  
 *
 */
public class PinYinUtil {
        
        /**
         * 
         * @param src
         * @return
         * author  : Robert
         * about version ��1.00
         * create time   : 2011-3-22-����07:04:27
         * Description ��
         *             ���뺺���ַ�����ƴ�ӳɶ�Ӧ��ƴ��,����ƴ���ļ���
         */
        public static Set<String> getPinYinSet(String src){
                Set<String> lstResult = new HashSet<String>();
                char[] t1 = null;  //�ַ���ת����char����
                t1 = src.toCharArray();
                
                //�ٵ�������
                for(char ch : t1){  
                        String s[] = getPinYin(ch);  
                        Set<String> lstNew = new HashSet<String>();
                        //�ڵ���ÿ�����ֵ�ƴ������
                        for(String str : s){
                                if(lstResult.size()==0){
                                        lstNew.add(str);
                                }else{
                                        for(String ss : lstResult){
                                                ss += str;
                                                lstNew.add(ss);
                                        }
                                }
                        }
                        lstResult.clear();
                        lstResult = lstNew;
                }

                return lstResult;
        }
        
        public static void main(String[] args) {
                Set<String> lst = PinYinUtil.getPinYinSet("����ÿ�����ֵ�ƴ������,�÷������Գ���Ա֮��");
                for (String string : lst) {
                        System.out.println(string);
                }
        }
        
        /**
         * 
         * @param src
         * @return
         * author  : Robert
         * about version ��1.00
         * create time   : 2011-3-22-����02:21:42
         * Description ��
         *             �������ĺ��֣�ת������Ӧƴ��
         *             ע������ͬ���֣�Ĭ��ѡ����ȫƴ�ĵ�һ�ֶ���
         */
        public static String getPinYin(String src) {
                char[] t1 = null;
                t1 = src.toCharArray();
                String[] t2 = new String[t1.length];

                // ���ú���ƴ������ĸ�ʽ
                HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
                t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
                t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                t3.setVCharType(HanyuPinyinVCharType.WITH_V);
                String t4 = "";
                int t0 = t1.length;
                try {
                        for (int i = 0; i < t0; i++) {
                                // �ж��ܷ�Ϊ�����ַ�
                                // System.out.println(t1[i]);
                                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                                        t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// �����ֵļ���ȫƴ���浽t2������
                                        t4 += t2[0];// ȡ���ú���ȫƴ�ĵ�һ�ֶ��������ӵ��ַ���t4��
                                } else {
                                        // ������Ǻ����ַ������ȡ���ַ������ӵ��ַ���t4��
                                        t4 += Character.toString(t1[i]);
                                }
                        }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                }
                return t4;
        }
        
        /**
         * @param src
         * @return
         * author  : Robert
         * about version ��1.00
         * create time   : 2011-3-22-����02:52:35
         * Description ��
         *             ����������ת���ɺ���ƴ�������ǵ�ͬ�������⣬�����ַ����������ʽ
         */
        public static String[] getPinYin(char src){
                char[] t1 = {src};
                String[] t2 = new String[t1.length];
                
                // ���ú���ƴ������ĸ�ʽ
                HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
                t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
                t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                t3.setVCharType(HanyuPinyinVCharType.WITH_V);
                
                // �ж��ܷ�Ϊ�����ַ�
                if (Character.toString(t1[0]).matches("[\\u4E00-\\u9FA5]+")) {
                        try {
                                // �����ֵļ���ȫƴ���浽t2������
                                t2 = PinyinHelper.toHanyuPinyinStringArray(t1[0], t3);
                        } catch (BadHanyuPinyinOutputFormatCombination e) {
                                e.printStackTrace();
                        }
                } else {
                        // ������Ǻ����ַ�������ַ�ֱ�ӷ���t2������
                        t2[0] = String.valueOf(src);
                }
                return t2;
        }
        
        /**
         * 
         * @param src
         * @return
         * author  : Robert
         * about version ��1.00
         * create time   : 2011-3-22-����03:03:02
         * Description ��
         *             ����û�ж����ֵ����ĺ��֣�ת������Ӧƴ��
         *             ע��������������������һͬ���ֶ��᷵���ַ�����Ϣ��false
         */
        public static String getNoPolyphone(String src){
                char[] t1 = null;
                t1 = src.toCharArray();
                String[] t2 = new String[t1.length];

                // ���ú���ƴ������ĸ�ʽ
                HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
                t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
                t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                t3.setVCharType(HanyuPinyinVCharType.WITH_V);
                String t4 = "";
                int t0 = t1.length;
                try {
                        for (int i = 0; i < t0; i++) {
                                // �ж��ܷ�Ϊ�����ַ�
                                // System.out.println(t1[i]);
                                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                                        t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// �����ֵļ���ȫƴ���浽t2������
                                        if(t2.length>1){
                                                return "false";
                                        }else{
                                                t4 += t2[0];// ȡ���ú���ȫƴ�ĵ�һ�ֶ��������ӵ��ַ���t4��
                                        }
                                } else {
                                        // ������Ǻ����ַ������ȡ���ַ������ӵ��ַ���t4��
                                        t4 += Character.toString(t1[i]);
                                }
                        }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                }
                return t4;
        }
        
        
}

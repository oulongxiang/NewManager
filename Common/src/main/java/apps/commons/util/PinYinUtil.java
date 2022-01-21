package apps.commons.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.springframework.util.StringUtils;

public class PinYinUtil {

    /**
     * 汉字转换为汉语拼音首字母,英文字符不变
     *
     * @param hanye 汉语拼音
     * @return String 转换为汉语拼音首字母
     */
    public static String convertToFirstSpell(String hanye) {
        if (StringUtils.isEmpty(hanye)) {
            return "";
        }
        StringBuffer hanyePinyin = new StringBuffer();
        char[] hanyeChar = hanye.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < hanyeChar.length; i++) {
            if (hanyeChar[i] > 128) {
                try {
                    hanyePinyin.append(PinyinHelper.toHanyuPinyinStringArray(hanyeChar[i], defaultFormat)[0].charAt(0));
                } catch (Exception e) {
                    hanyePinyin.append(hanyeChar[i]);
                    //throw new SystemException("汉字转换汉语拼音时报错,请转换为半角输入法后重新输入");
                }
            } else {
                hanyePinyin.append(hanyeChar[i]);
            }
        }
        return hanyePinyin.toString();
    }
}

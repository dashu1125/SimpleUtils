import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shushu
 * @Date 2021/9/3 15:31
 */
public class TestRegex {

    Pattern compile = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");

    /**
     * 获取字符串中的数字和小数点
     */
    @Test
    public void numTest(){
        String pepo = "120.25万人";
        Matcher matcher = compile.matcher(pepo);
        if (matcher.find()){
            String group = matcher.group();
            System.out.println(group);
        }
    }

}

package demo.core;

import com.epam.mjc.StringUtils;

public class Utils {
    public boolean isAllPositiveNumbers(String... strArray) {
        StringUtils stringUtils = new StringUtils();
        if ((strArray == null) || (strArray.length == 0)) {
            return false;
        }

        for (String stringElement : strArray) {
            if (!stringUtils.isPositiveNumber(stringElement)) {
                return false;
            }
        }
        return true;
    }
}

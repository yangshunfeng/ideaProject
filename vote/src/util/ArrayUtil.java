package util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
    public static List<Integer> StringToArray(String ids, String symbol) {
        if (ids == null || "".equals(ids) == true) return null;
        String s[] = ids.split(symbol);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0, length = s.length; i < length; i++) {
            Integer x = Integer.valueOf(s[i]);
            if (list.contains(x) == false) list.add(x);
        }
        return list;
    }

    public static String ListToString(List<Integer> ids, String symbol) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, len = ids.size(); i < len; i++) {
            if (i == 0) sb.append(ids.get(i));
            else {
                sb.append(symbol).append(ids.get(i));
            }
        }
        return sb.toString();
    }
}

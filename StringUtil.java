
public class StringUtil {
    public static String padRight(String text, int length, Character paddingChar) {
        StringBuilder sbTemp = new StringBuilder();
        if(text.length() >= length){
            return text;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(text);
        for (int i = text.length(); i < length; i++) {
            sb.append(paddingChar);
        }
        return sb.toString();
    }
}
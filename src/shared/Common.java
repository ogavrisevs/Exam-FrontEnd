package shared;


public class Common {

    public static String remQuotes(String str){
        if (str.contains("\"")){
            String str2 = str.replaceAll("\"", "\\\\\"");
            return str2;
        }
        return str;
    }
}

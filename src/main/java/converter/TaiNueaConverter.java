package converter;

import breaker.TaiNueaSyllableBreaker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 2.1
 */
public class TaiNueaConverter {

    public static String shn2tdd(String input) {

        String rule = "[" +
                "{  \"from\": \"(\\u1075\\u103b)\",  \"to\": \"\\u1953\" }," +
                "{  \"from\": \"\\u1075\",  \"to\": \"\\u1950\" }," +
                "{ \"from\": \"(\\u1076\\u103b)\", \"to\": \"\\u1960\" }, " +
                "{  \"from\": \"\\u1076\",  \"to\": \"\\u1951\" }," +

                "{  \"from\": \"\\u1004\",  \"to\": \"\\u1952\" }," +
                "{  \"from\": \"\\u1078\",  \"to\": \"\\u1953\" }," +
                "{ \"from\": \"(\\u101e\\u103b)\", \"to\": \"\\u1961\" }, " +
                "{ \"from\": \"(\\u101e\\u103c\\u1083\\u1087)\", \"to\": \"\\u1954\\u1963\\u1971\\u1958\\u1963\\u1971\" }, " +
                "{  \"from\": \"\\u101e\",  \"to\": \"\\u1954\" }," +
                "{ \"from\": \"\\u107a\", \"to\": \"\\u196d\" }, " +
                "{  \"from\": \"\\u1010\\u103c\\u1083\\u1038\",  \"to\": \"\\u1956\\u1963\\u1970\\u1958\\u1963\\u1970\" }," +
                "{  \"from\": \"\\u1010\",  \"to\": \"\\u1956\" }," +
                "{  \"from\": \"\\u1011\",  \"to\": \"\\u1957\" }," +
                "{  \"from\": \"\\u107c\",  \"to\": \"\\u1962\" }," +
                "{  \"from\": \"\\u1015\",  \"to\": \"\\u1959\" }," +
                "{  \"from\": \"\\u107d\\u103c\\u1083\\u1038\",  \"to\": \"\\u195a\\u1963\\u1970\\u1958\\u1963\\u1970\" }," +
                "{  \"from\": \"\\u107d\",  \"to\": \"\\u195a\" }," +
                "{  \"from\": \"\\u107e\",  \"to\": \"\\u195c\" }," +
                "{  \"from\": \"\\u1019\",  \"to\": \"\\u195b\" }," +
                "{  \"from\": \"\\u101a\",  \"to\": \"\\u1955\" }," +
//                "{  \"from\": \"\\u101b\",  \"to\": \"\\u1951\" }," +
                "{  \"from\": \"\\u101c\",  \"to\": \"\\u1958\" }," +
                "{  \"from\": \"\\u101d\",  \"to\": \"\\u195d\" }," +
                "{  \"from\": \"\\u1081\",  \"to\": \"\\u195e\" }," +
                "{  \"from\": \"\\u1022\",  \"to\": \"\\u195f\" }," +

                // End Tai Consonants
                "{ \"from\": \"(\\u1031\\u1083)\", \"to\": \"\\u1969\\u1974\" }, " +
                "{ \"from\": \"(\\u1031|\\u1035)\", \"to\": \"\\u1965\\u1974\" }, " +
                "{ \"from\": \"(\\u1084|\\u1085)\", \"to\": \"\\u1966\\u1974\" }, " +

                "{ \"from\": \"(\\u102d\\u102f)\", \"to\": \"\\u196a\" }, " +
                "{ \"from\": \"(\\u102d\\u1030)\", \"to\": \"\\u196b\" }, " +

                "{  \"from\": \"\\u102f\",  \"to\": \"\\u1967\" }," +
//                "{  \"from\": \"\\u103b\",  \"to\": \"\\u1966\" }," +
                "{  \"from\": \"\\u1030\",  \"to\": \"\\u1968\\u1974\" }," +
                "{  \"from\": \"\\u103d\",  \"to\": \"\\u1969\" }," +
                "{  \"from\": \"\\u103d\",  \"to\": \"\\u1969\" }," +
                // kai khin
                "{  \"from\": \"\\u1086\",  \"to\": \"\\u196d\\u1974\" }," +
                "{ \"from\": \"\\u102e\", \"to\": \"\\u1964\\u1974\" }, " +
                "{ \"from\": \"\\u102d\", \"to\": \"\\u1964\" }, " +
                "{ \"from\": \"(\\u1082\\u103a)\", \"to\": \"\\u196c\\u1974\" }, " +
                "{  \"from\": \"\\u103a\",  \"to\": \"\\u1974\" }," +
                "{  \"from\": \"\\u1087\",  \"to\": \"\\u1971\" }," +
                "{  \"from\": \"\\u1088\",  \"to\": \"\\u1972\" }," +
                "{  \"from\": \"\\u1038\",  \"to\": \"\\u1970\" }," +
                "{ \"from\": \"(\\u1089|\\u1037)\", \"to\": \"\\u1973\" }, " +
                "{ \"from\": \"\\u1082\", \"to\": \"\" }, " +
                // set temp for yak khunt
                "{ \"from\": \"\\u108a\", \"to\": \"\\u5000\" }, " +
                "{ \"from\": \"\\u1083\", \"to\": \"\\u1963\\u1974\" }, " +
                "{ \"from\": \"\\u1062\", \"to\": \"\\u1963\" }, " +
                "{ \"from\": \"(\\u1974)([\\u1950-\\u195f\\u196d\\u1962])(\\u1974)\", \"to\": \"$2$3\" }, " +
                "{ \"from\": \"(\\u1974)([\\u1971|\\u1972|\\u1970|\\u1973])\", \"to\": \"$2\" }, " +
                // Fixed 1
                "{\"from\": \"([\\u1950-\\u195f\\u196d\\u1962])(\\u1968)([\\u1971|\\u1972|\\u1970|\\u1973|\\u1974|\\u5000])\", \"to\": \"$1\\u1967$3\"}, " +
                "{\"from\": \"([\\u1950-\\u195f\\u196d\\u1962])(\\u196b|\\u196a)(\\u195d)\", \"to\": \"$1$2\"}, " +

                "{ \"from\": \"(\\u1974)(\\u5000)\", \"to\": \"\" }, " +
                "]";

        return replace_with_rule(rule, input);
    }

    private static String beforeConvert(String input) {
        String output = input;
        output = output.replaceAll("\\u0020", "\u1500");
        return output;
    }

    private static String afterConvert(String input) {
        String output = input;
        output = output.replaceAll("\\u0020", "");
        output = output.replaceAll("\\u1500", "\u0020");
        return output;
    }

    public static String tdd2shn(String input) {
        String output = input;
        // Convert spacing to another unused codes
        output = beforeConvert(output);
        // Word break Tai Nuea text
        output = TaiNueaSyllableBreaker.syllable_break(output);
        // Convert the word breaking Tai Nuea text
        output = tdd2shn_raw(output);
        // Convert the unused code to spacing back
        output = afterConvert(output);
        // Additional fix for Fra, Tra, Sra etc...
        output = additionalFix(output);
        return output;
    }

    private static String additionalFix(String input) {
        String output = input;
        // Phra, Tra, Sra
        output = output.replaceAll("(\\u107d\\u1083\\u1038)(\\u0020)?(\\u101c\\u1083\\u1038)", "\u107d\u103c\u1083\u1038");
        output = output.replaceAll("(\\u1010\\u1083\\u1038)(\\u0020)?(\\u101c\\u1083\\u1038)", "\u1010\u103c\u1083\u1038");
        output = output.replaceAll("(\\u101e\\u1083\\u1087)(\\u0020)?(\\u101c\\u1083\\u1087)", "\u101e\u103c\u1083\u1087");
        return output;
    }


    private static String tdd2shn_raw(String input) {
        String rule = "[ " +
                // Fix 1
                "{\"from\": \"([\\u1950-\\u195f\\u196d\\u1962])(\\u196b|\\u196a)([\\u1971|\\u1972|\\u1970|\\u1973|\\u1974|\\u0020])\", \"to\": \"$1$2\\u195d$3\"}, " +
                // For Yak Khint
                "{\"from\":\"([\\u1950-\\u195f\\u196d\\u1962])([\\u1969\\u1965\\u1966\\u1964\\u196a\\u196b\\u1963\\u1967\\u1968])?([\\u1950-\\u195f\\u196d\\u1962])(\\u0020|\\u000a|\\u104a|\\u104b)\", \"to\": \"$1$2$3\\u103a\\u108a$4\"}," +
                "{\"from\":\"([\\u1968\\u1967\\u196c\\u1963\\u1965\\u1966\\u1964\\u1969])(\\u0020|\\u000a|\\u104a|\\u104b)\", \"to\": \"$1\\u108a$2\"}," +
                // kai khin
                "{\"from\":\"([\\u1950-\\u195f\\u196d\\u1962])([\\u1963\\u1969])?(\\u196d)\", \"to\":\"$1$2\\u1086\"}," +
                "{\"from\":\"(\\u1086)(\\u103a)(\\u108a)\", \"to\":\"$1$3\"}," +
                // arr pot & arr yao
                "{\"from\":\"(\\u1963)([\\u1970-\\u1974\\u00a8\\u02c7\\u02cb\\u02d9\\u02ca\\u108a])\", \"to\":\"\\u1083$2\"}," +
                "{\"from\":\"\\u1963\", \"to\":\"\\u1062\"}," +
                // Hoi (or) asai aryao
                "{\"from\":\"(\\u1969)([\\u1970-\\u1974\\u00a8\\u02c7\\u02cb\\u02d9\\u02ca\\u108a])\", \"to\":\"\\u1031\\u1083$2\"}," +
                // asai
                "{\"from\":\"(\\u1965)([\\u1970-\\u1974\\u00a8\\u02c7\\u02cb\\u02d9\\u02ca\\u108a])\", \"to\":\"\\u1031$2\"}," +
                "{\"from\":\"\\u1965\", \"to\":\"\\u1035\"}," +
                // esai
                "{\"from\":\"(\\u1966)([\\u1970-\\u1974\\u00a8\\u02c7\\u02cb\\u02d9\\u02ca\\u108a])\", \"to\":\"\\u1084$2\"}," +
                "{\"from\":\"\\u1966\", \"to\":\"\\u1085\"}," +
                // tang & tang khet shen
                "{\"from\":\"(\\u1964)([\\u1970-\\u1974\\u00a8\\u02c7\\u02cb\\u02d9\\u02ca\\u108a])\", \"to\":\"\\u102e$2\"}," +
                "{\"from\":\"\\u1964\", \"to\":\"\\u102d\"}," +
                // toon sein 6 toon

                "{\"from\":\"([\\u1950-\\u195f\\u196d\\u1962])(\\u1971|\\u02c7)\", \"to\":\"$1\\u103a\\u1087\"}," +
                "{\"from\":\"([\\u1950-\\u195f\\u196d\\u1962])(\\u1972|\\u02cb)\", \"to\":\"$1\\u103a\\u1088\"}," +
                "{\"from\":\"([\\u1950-\\u195f\\u196d\\u1962])(\\u1970|\\u00a8)\", \"to\":\"$1\\u103a\\u1038\"}," +
                "{\"from\":\"([\\u1950-\\u195f\\u196d\\u1962])(\\u1973|\\u02d9)\", \"to\":\"$1\\u103a\\u1089\"}," +
                "{\"from\":\"([\\u1950-\\u195f\\u196d\\u1962])(\\u1974|\\u02ca)\", \"to\":\"$1\\u103a\\u5000\"}," +

//                "{\"from\":\"(?!([\\u1971\\u02c7\\u1972\\u02cb\\u1970\\u00a8\\u1973\\u02d9\\u1974\\u02ca]))(\\u0020)\", \"to\": \"\\u108a$2\"}," +
                "{\"from\":\"(\\u1971|\\u02c7)\", \"to\":\"\\u1087\"}," +
                "{\"from\":\"(\\u1972|\\u02cb)\", \"to\":\"\\u1088\"}," +
                "{\"from\":\"(\\u1970|\\u00a8)\", \"to\":\"\\u1038\"}," +
                "{\"from\":\"(\\u1973|\\u02d9)\", \"to\":\"\\u1089\"}," +
                "{\"from\":\"(\\u1974|\\u02ca)\", \"to\":\"\\u5000\"}," +
                // end toon sein
                // tai lone consonants
                "{\"from\":\"\\u1950\", \"to\":\"\\u1075\"}," +
                "{\"from\":\"\\u1951\", \"to\":\"\\u1076\"}," +
                "{\"from\":\"\\u1952\", \"to\":\"\\u1004\"}," +
                "{\"from\":\"\\u1953\", \"to\":\"\\u1078\"}," +
                "{\"from\":\"\\u1954\", \"to\":\"\\u101e\"}," +
                // Sha & Cha
                "{\"from\":\"\\u1961\", \"to\":\"\\u101e\\u103b\"}," +
                "{\"from\":\"\\u1960\", \"to\":\"\\u1076\\u103b\"}," +
                "{\"from\":\"\\u196d\", \"to\":\"\\u107a\"}," +
                "{\"from\":\"\\u1956\", \"to\":\"\\u1010\"}," +
                "{\"from\":\"\\u1957\", \"to\":\"\\u1011\"}," +
                "{\"from\":\"\\u1962\", \"to\":\"\\u107c\"}," +
                "{\"from\":\"\\u1959\", \"to\":\"\\u1015\"}," +
                "{\"from\":\"\\u195a\", \"to\":\"\\u107d\"}," +
                "{\"from\":\"\\u195c\", \"to\":\"\\u107e\"}," +
                "{\"from\":\"\\u195b\", \"to\":\"\\u1019\"}," +
                "{\"from\":\"\\u1955\", \"to\":\"\\u101a\"}," +
                "{\"from\":\"\\u1958\", \"to\":\"\\u101c\"}," +
                "{\"from\":\"\\u195d\", \"to\":\"\\u101d\"}," +
                "{\"from\":\"\\u195e\", \"to\":\"\\u1081\"}," +
                "{\"from\":\"\\u195f\", \"to\":\"\\u1022\"}," +

                // End Tai consonants
                "{\"from\":\"\\u1967\", \"to\":\"\\u102f\"}," +
                "{\"from\":\"\\u1968\", \"to\":\"\\u1030\"}," +
                "{\"from\":\"\\u1969\", \"to\":\"\\u103d\"}," +
                // Kang King
                "{\"from\":\"\\u196c\", \"to\":\"\\u1082\\u103a\"}," +
                "{\"from\":\"\\u196a\", \"to\":\"\\u102d\\u102f\"}," +
                "{\"from\":\"\\u196b\", \"to\": \"\\u102d\\u1030\"}," +
                // For yak khin
                "{\"from\":\"([\\u1087\\u1088\\u1038\\u1089\\u1037\\u108a\\u5000])(\\u108a)\", \"to\": \"$1\"}," +
//                "{\"from\":\"([\\u1075-\\u1081\\u1004\\u101e\\u1010\\u1011\\u1015\\u1019\\u101a\\u101b\\u101c\\u101d\\u1022])(\\u1035)(\\u108a)\", \"to\": \"$1\\u103a$3\"}," +
//                "{\"from\":\"(\\u1085)(\\u108a)\", \"to\": \"\\u1084$2\"}," +

                // Fix 1
                "{\"from\":\"([\\u1075-\\u1081\\u1004\\u101e\\u1010\\u1011\\u1015\\u1019\\u101a\\u101b\\u101c\\u101d\\u1022])(\\u102f)([\\u1087\\u1088\\u1038\\u1089\\u1037\\u108a\\u5000])\", \"to\":\"$1\\u1030$3\"}, " +
                "{\"from\":\"\\u5000\", \"to\": \"\"}," +
                "{\"from\":\"\\u200b\", \"to\": \"\"}," +
                "]";
        return replace_with_rule(rule, input);
    }

    public static String replace_with_rule(String rule, String output) {

        try {
            JSONArray rule_array = new JSONArray(rule);
            int max_loop = rule_array.length();

            //because of JDK 7 bugs in Android
            output = output.replace("null", "\uFFFF\uFFFF");

            for (int i = 0; i < max_loop; i++) {

                JSONObject obj = rule_array.getJSONObject(i);
                String from = obj.getString("from");
                String to = obj.getString("to");

                output = output.replaceAll(from, to);
                output = output.replace("null", "");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        output = output.replace("\uFFFF\uFFFF", "null");
        return output;

    }

    public static boolean isLeikTaiMao(String input) {
        int confirm = 0;
        for (int i = 0; i < input.length(); i++) {
            int character = input.charAt(i);
            if (character >= 6480 && character < 6520) {
                confirm++;
            }
            if (confirm > 5) return true;
        }
        return confirm > 0;
    }

}

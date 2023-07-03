package detector;

/**
 * Created by Mao on 7/29/2022.
 */


public class TaiNueaDetector {

    public static boolean isTaiNuea(String input) {

        char[] characters = input.toCharArray();
        /*
         Just don't want to loop through the whole string,
         what if the input string contains more than 1000 chars
         */

        int check_counts = Math.min(characters.length, 50);

        for (int i = 0; i < check_counts; i ++) {
            // Check if the character is Tai Nuea code
            // Which is between 1950 & 1974
            if ((int)characters[i] >= 6480 && (int) characters[i] <=6516) {
                return true;
            }
        }
        return false;

    }

}

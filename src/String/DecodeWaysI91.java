package String;

public class DecodeWaysI91 {

    public int numDecodings(String s) {
        //Use DP to solve it
        //corner case:
        if (s == null || s.length() == 0) return 0;
        if (s.charAt(0) == '0') return 0;
        //Initialize DP array
        int[] DP = new int[s.length()+1]; //DP[i] is decode ways for first i letters
        DP[0] = 1; //no char but still one solution as to serve DP[2]
        DP[1] = 1;
        for (int i=2; i<=s.length();i++){
            DP[i] = 0;
            //case 1: take 1 digit on the ith letter
            if (s.charAt(i-1) != '0'){
                DP[i]= DP[i-1];
            }
            //case 2: take 2 digits on i-1 th and ith letters
            if (s.charAt(i-2) != '0' && ( (s.charAt(i-2) == '2' && s.charAt(i-1) <='6') || s.charAt(i-2) == '1')){
                DP[i] = DP[i] + DP[i-2];
            }
        }
        return DP[s.length()];
    }
}

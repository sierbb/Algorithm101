package String;

public class WildCardMatching {

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) return false;

        //first remove adjacent * in p
        String p1 = removeAdjacentStar(p);

        //now use dp to process
        boolean[][] dp = new boolean[s.length()+1][p1.length()+1];
        dp[0][0] = true;
        for (int i = 1; i <= s.length(); i++){ //0 pattern can not match any s
            dp[i][0] = false;
        }
        for (int j = 1; j <= p1.length(); j++){
            if (p1.charAt(j-1) == '*'){
                dp[0][j] = dp[0][j-1]; //depends on if previous pattern matches
            }
        }
        for (int i = 1; i <= s.length(); i++){ //i and j are first i, j letters
            for (int j = 1; j <= p1.length(); j++){
                if (p1.charAt(j-1) == s.charAt(i-1) || p1.charAt(j-1) == '?'){
                    dp[i][j] = dp[i-1][j-1];
                }else if (p1.charAt(j-1) == '*'){
                    dp[i][j] = dp[i-1][j] || dp[i][j-1]; //either matches current i, or matching 0 letter
                }else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[s.length()][p1.length()];
    }

    private String removeAdjacentStar(String p){
        if (p.length() <=1){
            return p;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(p.charAt(0));
        int i = 1;
        while (i < p.length()){
            if (p.charAt(i) == '*'){
                while (i < p.length() && p.charAt(i) == p.charAt(i-1)){
                    i++; //remove repeated *
                }
            }
            if (i < p.length()){
                sb.append(p.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }


    public boolean isMatchOneStart(String s, String p){
        //assume one start exists in pattern, split by *
        int split = -1;
        for (int i=0; i<p.length(); i++){
            if (p.charAt(i) == '*'){
                split = i;
            }
        }
        if (split == -1){
            return s.equals(p);
        }

        //1. First check whether both part is longer than s
        if (p.length() -1 > s.length()){ //p-* > s
            return false;
        }
        //2. Then check whether s starts with p[0,split-1] and ends with p[split+1, p.length()-1]
        //2.1 check forward
        int startS = 0;
        while (startS < split){
            if (s.charAt(startS) != p.charAt(startS)){
                return false;
            }
            startS++;
        }
        //2.2 Check backward
        int endS = s.length()-1, endP = p.length()-1;
        while ( endP >= split+1 && endS >= 0){
            if (s.charAt(endS) != p.charAt(endP)){
                return false;
            }
            endP--;
            endS--;
        }
        return true;
    }

    public static void main(String[] args){
        WildCardMatching obj = new WildCardMatching();
        System.out.println(obj.isMatchOneStart("abbba", "a*bbba"));
    }
}

//M1: DFS classic + memorization
//use dp as hashSet to memorize Node(s, p), if (s, p) exists. return directly,
//base case is either s and p are both out of bound = false  || when p is last and p == '*' = true.

//if s = p || p == '?'; dp(s,p) = dfs( s+1, p+1 );
//else if p == *; dp(s, p) = dfs( s+1, p)(match as least one) || dfs(s, p+1);(match 0)
//else dp(s, p) = false;
//return dp(s, p);
//This is actually comparing two strings by moving pointers


//M2: DP
//Seems like the DFS can be reverse and we can start from the start.
//if still comparing s and p -> first s letters and p letters.
//Base case: 0 letter and 0 letters = M[0][0] = 0; no match
//if s == p || p == ?; dp(s, p) = dp(s-1, p-1);
//if p == *; dp(s, p) = dp(s, p-1)(match nothing) || dp(s-1, p)(match last s);
//else dp(s, p) = false

//return dp(s, p)

//TC:O(nm); SC:O(mn)
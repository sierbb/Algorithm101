package Array;

import java.util.*;

public class RankTeams {

    public String rankTeams(String[] votes) {
        if (votes == null || votes.length == 0){
            return "";
        }
        int votePosCount = votes[0].length();
        Map<Character, int[]> map = new HashMap<>();
        for (int i=0; i<votes.length; i++){
            for (int j=0; j < votePosCount; j++){
                char team = votes[i].charAt(j);
                if (!map.containsKey(team)){
                    map.put(team, new int[votePosCount]);
                }
                int[] ranks = map.get(team);
                ranks[j]++;
                map.put(team, ranks);
            }
        }

        List<Character> list = new ArrayList<>(map.keySet());
        Collections.sort(list, new Comparator<Character>(){
            @Override
            public int compare(Character c1, Character c2){
                //first check by position count from first to last rank
                for (int j=0; j< votePosCount; j++){
                    if (map.get(c1)[j] != map.get(c2)[j]){
                        return map.get(c2)[j] - map.get(c1)[j]; //the team with more higher rank wins
                    }
                }
                return c1 - c2; //by alphebet
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<list.size(); i++){
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        String[] input = new String[]{"FVSHJIEMNGYPTQOURLWCZKAX","AITFQORCEHPVJMXGKSLNZWUY","OTERVXFZUMHNIYSCQAWGPKJL","VMSERIJYLZNWCPQTOKFUHAXG","VNHOZWKQCEFYPSGLAMXJIUTR","ANPHQIJMXCWOSKTYGULFVERZ","RFYUXJEWCKQOMGATHZVILNSP","SCPYUMQJTVEXKRNLIOWGHAFZ","VIKTSJCEYQGLOMPZWAHFXURN","SVJICLXKHQZTFWNPYRGMEUAO","JRCTHYKIGSXPOZLUQAVNEWFM","NGMSWJITREHFZVQCUKXYAPOL","WUXJOQKGNSYLHEZAFIPMRCVT","PKYQIOLXFCRGHZNAMJVUTWES","FERSGNMJVZXWAYLIKCPUQHTO","HPLRIUQMTSGYJVAXWNOCZEKF","JUVWPTEGCOFYSKXNRMHQALIZ","MWPIAZCNSLEYRTHFKQXUOVGJ","EZXLUNFVCMORSIWKTYHJAQPG","HRQNLTKJFIEGMCSXAZPYOVUW","LOHXVYGWRIJMCPSQENUAKTZF","XKUTWPRGHOAQFLVYMJSNEIZC","WTCRQMVKPHOSLGAXZUEFYNJI"};
        RankTeams obj = new RankTeams();
        System.out.println(obj.rankTeams(input));

    }

}



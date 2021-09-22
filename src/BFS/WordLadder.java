package BFS;

import java.util.*;

/**
 * Find the shortest path from beginWord to endWord
 * Use BFS1 with level order.
 * Adjacencylist build is just an example here, it is not required for this question and does not contribute to anything.
 */
public class WordLadder {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //corner case:
        if (wordList.size() == 0 || beginWord == null || endWord == null) return -1;
        //if beginWord not exists
        if (wordList.indexOf(beginWord) == -1){
            wordList.add(beginWord);
        }
        //if endWord not exists
        if (wordList.indexOf(endWord) == -1){
            return 0;
        }

        //Step 1: Build adjacency list from wordList
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<wordList.size(); i++){
            map.put(wordList.get(i), i);
        }
        Adjacency adjacency = new Adjacency(wordList);

        //Step 2: start from beginWord, run BFS to fill in adjacency list and at the same time find the shortest path to endWord
        Queue<String> fifo = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        fifo.offer(beginWord);
        visited.add(beginWord);
        //Doing a level order BFS because we need to get the exact path cost
        int len = 1;
        while (!fifo.isEmpty()){
            int size = fifo.size();
            for (int i=0; i<size; i++){
                String cur = fifo.poll();
                if (cur.equals(endWord)){ //as long as we see endWord, get the shortest path
                    return len;
                }
                List<String> adj = adjacency.list.get(map.get(cur));
                for (String nei : getNeighbor(map, cur)){
                    //add neighbor words to adjacencyList, even if they are visited
                    //(adjacency list build should not related to visited or not)
                    adj.add(nei);
                    if (!visited.contains(nei)){
                        visited.add(nei);
                        fifo.offer(nei);
                    }
                }
            }
            len++;
        }
        return 0;
    }

    private List<String> getNeighbor(Map<String, Integer> map, String word){
        //get current word's neighbor that's exists in map
        StringBuilder sb = new StringBuilder(word);
        List<String> nei = new ArrayList<>();
        for (int k=0; k<word.length(); k++){
            char ch = word.charAt(k);
            for (char j='a'; j<='z'; j++){ //replace the position with other letter, and check whether it exists
                if ( j!= ch ){
                    sb.setCharAt(k, j);
                    if (map.containsKey(sb.toString())){
                        nei.add(sb.toString());
                    }
                }
                sb.setCharAt(k, ch);
            }
        }
        return nei;
    }

    class Adjacency{
        private List<List<String>> list;
        private List<String> wordList;

        public Adjacency(List<String> wordList){
            this.list = new ArrayList<>();
            this.wordList = wordList;
            for (int i=0; i<wordList.size(); i++){
                list.add(new ArrayList<>());
            }
        }
    }


    public static void main(String[] args){
        WordLadder obj = new WordLadder();
        List<String> list = new ArrayList<String>();
        list.add("hot");
        list.add("dot");
        list.add("lot");
        list.add("log");
        list.add("cog");
        System.out.println(obj.ladderLength("hit", "cog", list));
    }
}

//"hit"
//"cog"
//["hot","dot","dog","lot","log","cog"]

//Clarification: both begin and endWord are in wordlist
//beginWord != endWord
//wordList.length = n; word.length=m < 26

//Step1: create HashMap from wordList adjacency list - O(n)
//Step 2:for word in wordList:  O(n)
//  replace each position with 25 char, and check whether exists in wordList - O(m^2)
//  if exists, add the word as the neighbor of current word -> adjacency list

//Step 3:
//start from beginWord -> BFS once to find the shortest path to endWord, each level++ O(n)
//stop when a neighbor == endWord.
//should we dedupe and not use the same word again? Yes? Can use hashset to store on the go.

//TC:O(n)+O(mn*m)+O(nm)BFS = O(m^2 * n); m is for each sb.toString()
//SC:O(nm*m)adj +O(nm)visited + O(mn) = O(m^2 *n)
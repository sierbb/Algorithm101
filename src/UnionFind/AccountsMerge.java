package UnionFind;

import java.util.*;

public class AccountsMerge {

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> res = new ArrayList<>();
        if (accounts == null || accounts.size() == 0){
            return res;
        }
        //1. Build the adjacencyList from each email
        Map<String, List<String>> adjacency = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        for ( List<String> account : accounts){
            String name = "";
            for (int i=0; i < account.size(); i++){
                if (i == 0){
                    name = account.get(i);
                    continue;
                }
                if (i == 1){
                    if (!adjacency.containsKey(account.get(i))){
                        adjacency.put(account.get(i), new ArrayList<String>());
                    }
                }else{
                    //if for other emails, add them to first email's adjacency list, and add first email to each of them's ajdacneyclist
                    if (!adjacency.containsKey(account.get(i))){
                        adjacency.put(account.get(i), new ArrayList<String>());
                    }
                    adjacency.get(account.get(1)).add(account.get(i));
                    adjacency.get(account.get(i)).add(account.get(1));
                }
                emailToName.put(account.get(i), name);
            }
        }
        //2. Traverse all the email from adjacencylist, find each component that connected to each other (belong to same person), created new list to store them
        Set<String> visited = new HashSet<>(); //avoid duplicate visit
        for (String email : adjacency.keySet()){
            BFS(adjacency, email, visited, emailToName, res);
        }
        return res;
    }

    private void BFS(Map<String, List<String>> adjacency, String email, Set<String> visited, Map<String, String> emailToName, List<List<String>> res){
        if (visited.contains(email)){
            return;
        }
        //start a new component
        List<String> component = new ArrayList<>();
        Queue<String> fifo = new LinkedList<>();
        fifo.offer(email);
        visited.add(email);
        while (!fifo.isEmpty()){
            String cur = fifo.poll();
            component.add(cur);
            for (String nei : adjacency.get(cur)){
                if (!visited.contains(nei)){
                    fifo.offer(nei);
                    visited.add(nei);
                }
            }
        }
        //finish a component
        Collections.sort(component);
        //add the name to the front
        component.add(0, emailToName.get(email));
        res.add(component);
    }

    public static void main(String[] args){
        AccountsMerge obj = new AccountsMerge();
        List<List<String>> input = new ArrayList<>();
        List<String> a1 = new ArrayList<>();
        a1.add("John");
        a1.add("johnsmith@mail.com");
        a1.add("john_newyork@mail.com");
        List<String> a2 = new ArrayList<>();
        a2.add("John");
        a2.add("johnsmith@mail.com");
        a2.add("john00@mail.com");
        List<String> a3 = new ArrayList<>();
        a3.add("Mary");
        a3.add("mary@mail.com");
        List<String> a4 = new ArrayList<>();
        a4.add("John");
        a4.add("johnnybravo@mail.com");
        input.add(a1);
        input.add(a2);
        input.add(a3);
        input.add(a4);
        //
        System.out.println(obj.accountsMerge(input));
    }

}

//Find the name with duplicate emails to be merged together.
//accounts.length = n;
//accounts[i].length = m

//1.build graph: each node is a email, each edge is the emails connected if they are from the same person -> for each line, connect the emails together
//Keep track of Map<email, name> to know a email belongs to a name (name can be different if it is the same person)
//For each accocunt, only need to build account(1) -> listofOther, and each of other -> account(1) to be enough to connect them -> O(n) instead of O(n^2) for each account
//2.How generate neighbor? for each email in Map<email, List<email>> -> run BFS for a connected component
//Then once the connected graph is done, use Map<email, Name> to find the name to add to the conponent

//[["John","johnsmith@mail.com","john_newyork@mail.com"],
//["John","johnsmith@mail.com","john00@mail.com"],
//["Mary","mary@mail.com"],
//["John","johnnybravo@mail.com"]]

//Map: <email, List<email>>
//<johnsmith,  <john_newyork,john00>  -> they are connected
//<john_newyork, johnsmith>
//<john00, johnsmith>

//TC:O(nm)build graph + O(nm) traverse +O(logmn)sorting=  O(mnlogmn)
//SC:O(mn) graph + O(mn) emailToName = O(mn)
//}

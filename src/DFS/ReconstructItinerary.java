package DFS;

import java.util.*;
import java.util.Collections;

public class ReconstructItinerary {

    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        if (tickets == null || tickets.size() == 0){
            return res;
        }
        Map<String, List<String>> adjacency = new HashMap<>();
        for (int i=0; i<tickets.size(); i++){
            List<String> ticket = tickets.get(i);
            if (!adjacency.containsKey(ticket.get(0))){
                adjacency.put(ticket.get(0), new ArrayList<String>());
            }
            adjacency.get(ticket.get(0)).add(ticket.get(1));
            if (!adjacency.containsKey(ticket.get(1))){
                adjacency.put(ticket.get(1), new ArrayList<String>());
            }
        }
        //sort the adjaecncy list based on lexical order
        for (Map.Entry<String, List<String>> e: adjacency.entrySet()){
            Collections.sort(e.getValue());
        }

        //now try build the itinerary using DFS
        //start with "JFK"
        res.add("JFK");
        DFS(adjacency,"JFK", res, tickets.size()+1);
        return res;
    }

    private boolean DFS(Map<String, List<String>> adjacency, String last, List<String> res, int total){

        //base case: when all the cities has been cleared out or when my current city does not have any out going city left
        if (res.size() == total){
            return true;
        }
        // String last = res.get(res.size()-1);
        List<String> allNext = adjacency.get(last);
        for (int i=0; i < allNext.size(); i++){
            String next = allNext.get(i);
            allNext.remove(i);
            res.add(next);
            if (DFS(adjacency, next, res, total)){
                return true;
            }
            res.remove(res.size()-1);
            allNext.add(i, next);
        }
        return false;
    }

    public static void main(String[] args){
        ReconstructItinerary obj = new ReconstructItinerary();
        List<List<String>> input = new ArrayList<>();
        input.add(new ArrayList<>(Arrays.asList("MUC","LHR")));
        input.add(new ArrayList<>(Arrays.asList("JFK","MUC")));
        input.add(new ArrayList<>(Arrays.asList("SFO","SJC")));
        input.add(new ArrayList<>(Arrays.asList("LHR","SFO")));
        System.out.println(obj.findItinerary(input));

        //{"LHR":["SFO"],"MUC":[],"SFO":[],"SJC":[],"JFK":["MUC"]}
        //[["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
        //output: [JFK, MUC, LHR, SFO, SJC]
    }
}

//clarification:
//First build the adjacency list directed graph.
//[["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
//Out graph
//MUC: LHR
//JFK: MUC
//SFO: SJC
//LHR: SFO
//SJC: ?

//find the one with 0 in edge to start with
//JFK -> MUC -> LHR -> SFO -> SJC no longer go from this path
//All of the location in in graph has been cleared since they all have 0 in edge

//[["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
//Out graph
//JFK: [ ] ATL,SFO List that we can sort after we build the graph
//SFO:     [ATL]
//ATL:  [JFK, SFO]


//DFS and pick the lexical first
//JFK -> ATL -> JFK -> SFO -> ATL -> SFO = ans

//TC:O(V+E) for 
//SC:O(v+E) for adjacency list
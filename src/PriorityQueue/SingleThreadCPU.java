package PriorityQueue;

import java.util.*;

public class SingleThreadCPU {
    public int[] getOrder(int[][] tasks) {
        if (tasks == null || tasks.length == 0){
            return new int[0];
        }
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                //we only care about smaller processingTIme
                //as long as it is pushed into queue, means its time to execute
                if (n1.task[1] == n2.task[1]){
                    return n1.pos - n2.pos;
                }
                return n1.task[1] - n2.task[1];
            }
        });

        List<Node> list = new ArrayList<>();
        for (int i=0; i<tasks.length; i++){
            list.add(new Node(i, tasks[i]));
        }
        //Step 1: sort list of input task by start time
        //PQ to sort by smallest processing time, then the index
        Collections.sort(list, (a1, a2) -> a1.task[0]-a2.task[0]); //first sort by startTime

        //Step 2: if ready, offer task to pq
        //Poll the most fitted task from pq one task each time
        int[] out = new int[tasks.length];
        int outIdx = 0; //output index
        int index = 0; //task pointer
        int timeStamp = list.get(0).task[0]; //the first task's startTime
        while (index < list.size()){
            //if there are any task ready, offer to pq
            while (index < list.size() && list.get(index).task[0] <= timeStamp){
                pq.offer(list.get(index));
                index++;
            }
            if (pq.isEmpty()){
                timeStamp = list.get(index).task[0];
                continue;
            }
            //pop task if there's task in queue
            Node cur = pq.poll();
            out[outIdx] = cur.pos;
            outIdx++;
            //start working on this task, other task has to wait until now
            timeStamp += cur.task[1];
        }
        //now pq may still have tasks in it
        while (!pq.isEmpty()){
            out[outIdx] = pq.poll().pos;
            outIdx++;
        }
        return out;

    }

    class Node{
        private int pos;
        private int[] task;
        public Node(int pos, int[] task){
            this.pos = pos;
            this.task = task;
        }
    }
}

//clarification:
//is Enqueue time strictly increasing?

//[enqueueTime, processingTime]
//rule: processTime smallest > index smallest
//[[1,2], [2,4], [3,2],[4,1]]
//[1,2], [2, 4],
//1      2      3 offer[3,2] 4 offer[4,1]pick
//once offer(ready), or when it pass the startTime, we will only look at processingTime?


//[[7,10],[7,12],[7,5],[7,4],[7,2]]
//all available - pop the shorest processing time

//Seems like we just need a PQ to help us do it, we offer and poll out task
//At time t, if task a comes, offer it first to PQ then poll out?
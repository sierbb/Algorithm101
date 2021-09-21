package DynamicProgramming;

public class MinimumNumberOfRefuelingStops {

    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        if (stations == null){
            return -1;
        }
        if (startFuel >= target){
            return 0;
        }
        if (stations.length == 0){
            return startFuel >= target ? 0 : -1;
        }
        //DP[i][j] represents the furthest distance we can get from ith station by making j stops
        int[][] DP = new int[stations.length+1][stations.length+1];
        for (int i = 0; i < stations.length; i++){
            DP[i][0] = startFuel;
        }
        int globalMin = Integer.MAX_VALUE;
        for (int i=1; i<=stations.length; i++){
            for (int j=1; j<=i; j++){
                //what if a station can not be reached(from either previous state)? we do nothing if so
                //case 1:
                if (DP[i-1][j] >= stations[i-1][0]){
                    DP[i][j] = DP[i-1][j];
                }
                //case 2:
                if (DP[i-1][j-1] >= stations[i-1][0]){
                    DP[i][j] = Math.max(DP[i-1][j-1] + stations[i-1][1], DP[i][j]);
                }
                //even though we cost gas to arrive at ith, but we dont subtract it because it is not the furthest we can go?
                if (DP[i][j] >= target){ //keep track of min stops to get target
                    globalMin = Math.min(globalMin, j);
                }
            }
        }
        return globalMin == Integer.MAX_VALUE? -1 : globalMin;
    }
}

//Clarification:
//[positioni, fueli] position from starting point
//Car may not be able to reach destination
//Mininum number of stop need to made
//previous stop will affect latter result.
//DP[i] represents min stop to reach destination from 0 to stations[i] ?
//Something similar to jump game? Can jump stations[i] step from station to the next. But also with the existing gas.

//Linear scan and look back from every position? for what? -> for how far we can get by making j stops along the way when we reach i

//dp[i][j] = at the ith station, the farthest distance someone can get by making j stops. j must <=i.
//fill in the diagonal top right part of the DP matrix.
//dp[0][0] = startFuel; dp[i][0] = startFuel.
//for i = 1~n; for 0<j<=i
//dp[i][j] = max(dp[i-1][j](dont stop), dp[i-1][j-1]+stations[i](stop at ith));

//During process, there could be a point where dp[i][j] < targett ? Its ok, we just need to find the DP point that's larger than target. So in the process for each DP[i] -> get the min j that is larger than target and return immediately

//TC:O(n^2) for DP matrix
//SC:O(n^2) for DP matriix -> O(n)

package com.alan.monitor1.common;


public class Solution {

    static int sum=0;
    static int answer =0;
    static boolean[] visited;

    public int solution(int[] v) {

        visited = new boolean[v.length];
        int[] v2 = new int[v.length];
        dfs(v,visited,v2,0);

        return answer;

    }

    public void dfs (int[]v, boolean[] visited,int[] v2,int node){


        if(node == v2.length){
            for(int num: v2){
                System.out.print(num + " ");
            }
            System.out.println();

            for(int i=0;i<v2.length-1;i++){
                sum += Math.abs(v2[i]-v2[i+1]);
            }

            answer = sum > answer ? sum : answer;
            sum=0;

        } else {
            for(int i =0; i<v.length;i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    v2[node] = v[i];
                    dfs(v, visited, v2, node + 1);
                    visited[i] = false;
                }
            }
        }
    }


    public static void main(String[] args) {

        Solution solution = new Solution();
        int[] v = {20,8,10,1,4,15};

        //1,4,8,10,15,20
        //10-4  = 6
        //4-20  = 16
        //20-1  = 19
        //1-15  = 14
        //15-8  =  7
        // ==> 62 최대값

        System.out.println(solution.solution(v));
        System.out.println(sum);

    }

}
package com.alan.monitor1.common;


public class Cote {

    static int count = 0;
    static boolean[] visited;

    public int solution(int k, int[][] dungeons) {

        visited = new boolean[dungeons.length];
        int[][] v2 = new int[dungeons.length][2];


        dfs(k, dungeons, visited, 0);


        return count;
    }

    public void dfs(int k, int[][] arr, boolean[] visited, int node){

        for(int i=0; i<arr.length;i++){
            if(!visited[i]&& arr[i][0] <=k) {
                visited[i] = true;
                dfs(k - arr[i][1], arr, visited, node + 1);
                visited[i]=false;
            }
        }

        System.out.println(count);

        count = Math.max(node,count);

    }


    public static void main(String[] args) {

        Cote cote = new Cote();

        int k = 80;
        int[][] arr = {{80,20},{50,40},{30,10}};

        System.out.println(cote.solution(k,arr));


    }
}

package com.alan.monitor1.test;

public class Test {

    public int rank(int num){

        int rank=6;
        switch(num) {
            case 6 : rank = 1;
            break;
            case 5 : rank = 2;
                break;
            case 4 : rank = 3;
                break;
            case 3 : rank = 4;
                break;
            case 2 : rank = 5;
                break;
            default:
                break;
        }

        return rank;

    }


    public int[] solution (int[] lottos, int[] win_nums) {
        int answer[] = new int[2];

        int zero = 0;
        int win =0;

        for(int i : lottos){
            if(i == 0)
                zero++;
            for(int j: win_nums){
                if(i==j)
                    win++;
            }
        }

        answer[0] = rank(zero+win);
        answer[1] = rank(win);

        return answer;
    }


    public static void main(String[] args) {
        Test test = new Test();
        int lottos[] = {44, 1, 0, 0, 31, 25};
        int win_nums[] = {31, 10, 45, 1, 6, 19};
        int [] result =  test.solution(lottos,win_nums);

        for (int i : result)
            System.out.println(i);


    }
}

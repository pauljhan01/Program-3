public class Program3 {
    int maxFood = 0;
    int[][] OPT;
    
    public int maxFoodCount (int[] plots) {
        // Implement your dynamic programming algorithm here
        // You may use helper functions as needed

        // OPT table, my algorithm is bottom up
        OPT = new int[plots.length][plots.length];

        boolean finished = false;
        int i = 0;
        int j = 1;
        int length = 2;
        int plot_sum = 0;
        while(!finished){
            switch(length){
                //this should never occur
                case 1:
                    OPT[i][j] = 0;
                    i++;
                    j++;
                    break;
                //this should be the first case
                case 2:
                    plot_sum = plots[i] < plots[j] ? plots[i]:plots[j];
                    OPT[i][j] = plot_sum;
                    i++;
                    j++;
                    break;
                //call helper function to find the sum of the plots not eaten by crows
                //+ the future OPT values
                default:
                    OPT[i][j] = findMaxOPT(i, j, length, plots);
                    i++;
                    j++;
                    break;
            }
            //this should allow us to loop back and calculate the OPT values for larger
            //length subarrays
            if(j == plots.length){
                i = 0;
                j = length;
                length++;
            }
            //this should break us out of the while loop
            if(length > plots.length){
                finished = true;
                break;
            }
        }
        return OPT[0][plots.length-1];
    }
    public int findMaxOPT(int i, int j, int length, int[] plots){
        int scarecrow = i+(length%2==0 ? length/2: length/2+1);
        int scarecrow_2 = scarecrow - 1;

        boolean finished = false;

        int current_max_OPT = 0;

        int scarecrow_1_min = 0;
        int scarecrow_2_min = 0;

        int scarecrow_1_left_half = 0;
        int scarecrow_1_right_half = 0;
        int scarecrow_2_left_half = 0;
        int scarecrow_2_right_half = 0;

        boolean increase = false;

        while(!finished){
            for(int x = i; x < scarecrow; x++){
                scarecrow_1_left_half += plots[x];
            }
            scarecrow_1_left_half += OPT[i][scarecrow-1];
            for(int x = scarecrow; x < i+length; x++){
                scarecrow_1_right_half += plots[x];
            }
            scarecrow_1_right_half += OPT[scarecrow][j];
            scarecrow_1_min = (scarecrow_1_left_half > scarecrow_1_right_half ? scarecrow_1_right_half:scarecrow_1_left_half);
            for(int x = i; x < scarecrow_2; x++){
                scarecrow_2_left_half += plots[x];
            }
            scarecrow_2_left_half += OPT[i][scarecrow_2];
            for(int x = scarecrow_2; x < j; x++){
                scarecrow_2_right_half += plots[x];
            }
            scarecrow_2_right_half += OPT[scarecrow_2][j];
            scarecrow_2_min = scarecrow_2_left_half > scarecrow_2_right_half ? scarecrow_2_right_half:scarecrow_2_left_half;
    
            if(scarecrow_1_min > scarecrow_2_min){
                increase = true;
                scarecrow_2 = scarecrow+1;
                scarecrow++;
            }else{
                if(increase){
                    current_max_OPT = scarecrow_2_min;
                    finished = true;
                    break;
                 }
                scarecrow = scarecrow_2;
                scarecrow_2 -= 1;
            }
        }
        return current_max_OPT;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


class Item{
   public int value;
   public int weight;

    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}


class DynamicProgramming_and_GENOM_alligment {

    public static void print(int [][] arr){
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static String readFasta(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder sequence = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith(">")) {
                continue; // Başlık satırı, atla
            }
            sequence.append(line.trim().replaceAll("[^ACGT]", ""));
        }

        reader.close();
        return sequence.toString();

    }
    public static void main(String args[]) throws IOException {

        //String str1 = "ACTCTTCTGGTCCCCACAGACTCAGAAAGAACCCACCATGGTGCTGTCTCCTGCCGACAAGACCAACGTCAAGGCCGCCTGGGGTAAGGTCGGCGCGCACGCTGGCGAGTATGGTGCGGAGGCCCTGGAGAGGATGTTCCTGTCCTTCCCCACCACCAAGACCTACTTCCCGCACTTCGACCTGAGCCACGGCTCTGCCCAGGTTAAGGGCCACGGCAAGAAGGTGGCCGACGCGCTGACCAACGCCGTGGCGCACGTGGACGACATGCCCAACGCGCTGTCCGCCCTGAGCGACCTGCACGCGCACAAGCTTCGGGTGGACCCGGTCAACTTCAAGCTCCTAAGCCACTGCCTGCTGGTGACCCTGGCCGCCCACCTCCCCGCCGAGTTCACCCCTGCGGTGCACGCCTCCCTGGACAAGTTCCTGGCTTCTGTGAGCACCGTGCTGACCTCCAAATACCGTTAAGCTGGGGACTCGGTGGCCGTTCCTCCTGCCCGCTGGGCCTCCCAACGGGCCCTCCTCCCCTCCTTGCACCGGCCCTTCCTGGTCTTTGAATAAAAGTCTGAGTGGGCGGC";
       // String str2 = "ACTCTTCTGGTCCCCACAGACTCAGAGAGAACCCACCATGGTGCTGTCTCCTGCCGACAAGACCAACGTCAAGGCCGCCTGGGGTAAGGTCGGCGCGCACGCTGGCGAGTATGGTGCGGAGGCCCTGGAGAGGATGTTCCTGTCCTTCCCCACCACCAAGACCTACTTCCCGCACTTCGACCTGAGCCACGGCTCTGCCCAGGTTAAGGGCCACGGCAAGAAGGTGGCCGACGCGCTGACCAACGCCGTGGCGCACGTGGACGACATGCCCAACGCGCTGTCCGCCCTGAGCGACCTGCACGCGCACAAGCTTCGGGTGGACCCGGTCAACTTCAAGCTCCTAAGCCACTGCCTGCTGGTGACCCTGGCCGCCCACCTCCCCGCCGAGTTCACCCCTGCGGTGCACGCCTCCCTGGACAAGTTCCTGGCTTCTGTGAGCACCGTGCTGACCTCCAAATACCGTTAAGCTGGAGCCTCGGTAGCCGTTCCTCCTGCCCGCTGGGCCTCCCAACGGGCCCTCCTCCCCTCCTTGCACCGGCCCTTCCTGGTCTTTGAATAAAAGTCTGAGTGGGCAGCA";
        //String str1=readFasta("insan.fa");
        //String str2=readFasta("chr1.fa");
        String str1 = "ATGGAGAGGACTCCGTGCTCAGCTCCTGCTCAGCCTCTGAGCCCAGGCTGGTCTCAAACTCCTGACCTCAGGTGATCCACCCGCCTCGGCCTCCCAAAGTGCTGGGATTACAGGCGCCCACCACCACGCCTGGCTAATTTTTGTATTTTTAGTAGAGATGGGGTCTCACCATGTTGCCCAGGCTGGTCTCAAACTCCTGACCTCAGGTGATCCACCCGCCTCGGCCTCCCAAAGTGCTGGGATTACAGGCGCCCACCACCACGCCTGGCTAAAGTGAGGTTTCCTTCCACCTTGCCCCCAGCTGGTGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCTGAGGCAGGAGAATGGCGTGAACCCAGGAGGCAGAGGTTGCAGTGAGCTGAGATTGCACCACTGCACTCCAGCCTGGGTAACAGAGCAAGACTCTGTCTCAAAAAAAAAAAAAAAAAAAAAAAA";
        String str2 = "ATGGAGAGGACTCCGTGCTCAGCTCCTGCTCAGCCTCTGAGCCCAGGCTGGTCTCAAACTCCTGACCTCAGGTGATCCACCCGCCTCGGCCTCCCAAAGTGCTGGGATTACAGGCGCCCACCACCACGCCTGGCTAATTTTTGTATTTTTAGTAGAGATGGGGTCTCACCATGTTGCCCAGGCTGGTCTCAAACTCCTGACCTCAGGTGATCCACCCGCCTCGGCCTCCCAAAGTGCTGGGATTACAGGCGCCCACCACCACGCCTGGCTAAAGTGAGGTTTCCTTCCACCTTGCCCCCAGCTGGTGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCTGAGGCAGGAGAATGGCGTGAACCCAGGAGGCAGAGGTTGCAGTGAGCTGAGATTGCACCACTGCACTCCAGCCTGGGTAACAGAGCAAGACTCTGTCTCAAAAGTGA";        System.out.println(str2);
        System.out.println(str1);
        int m = str1.length();
        int n = str2.length();

        int match = -1;
        int mismatch = 2;
        int gap = 2;

        int[][] dp = new int[m + 1][n + 1];

        dp[0][0] = 0;

        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + gap;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + gap;
        }


        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int minGap = Math.min(dp[i - 1][j] + gap, dp[i][j - 1] + gap);
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = Math.min(minGap, dp[i - 1][j - 1] + match);
                } else {
                    dp[i][j] = Math.min(minGap, dp[i - 1][j - 1] + mismatch);
                }
            }
        }


        int i = m, j = n;
        int matchCount = 0;
        int gapCount = 0;
        int mismatchCount = 0;

        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                matchCount++;
                i--;
                j--;
            } else {
                int current = dp[i][j];
                if (current == dp[i - 1][j - 1] + mismatch) {
                    mismatchCount++;
                    i--;
                    j--;
                } else if (current == dp[i - 1][j] + gap) {
                    gapCount++;
                    i--;
                } else {
                    gapCount++;
                    j--;
                }
            }
        }


        while (i > 0) {
            gapCount++;
            i--;
        }
        while (j > 0) {
            gapCount++;
            j--;
        }

        System.out.println("Toplam ceza: " + dp[m][n]);
        System.out.println("Eşleşen harf sayısı: " + matchCount);
        System.out.println("Mismatch (uyuşmayan) harf sayısı: " + mismatchCount);
        System.out.println("Gap (boşluk) sayısı: " + gapCount);

        int alignmentLength = matchCount + mismatchCount + gapCount;
        double similarity = (matchCount * 100.0) / alignmentLength;
        System.out.printf("Benzerlik Oranı: %.2f%%\n", similarity);




        //COİNS PROBLEM
        /*

    int[] coins={ 1, 10, 21, 34, 70, 100, 350, 1295, 1500 };

    int val=20;

    int[] dp=new int[val+1];
    Arrays.fill(dp,val+1);
    dp[0]=0;

    for (int i=1;i<=val;i++){
        for (int coin: coins){
            if(i-coin>=0){
                dp[i]=Integer.min(dp[i],1+dp[i-coin]);
            }

        }
        System.out.print(dp[i]+" ");
    }
        System.out.println();

        System.out.println(dp[val]);


         */


        //Knapsack Problem
        /*
        Item item1=new Item(1,1);
        Item item2=new Item(6,2);
        Item item3=new Item(18,5);
        Item item4=new Item(22,6);
        Item item5=new Item(28,7);

        Item [] arr=new Item[5];
        arr[0]=item1;
        arr[1]=item2;
        arr[2]=item3;
        arr[3]=item4;
        arr[4]=item5;

        int W=11;

        int [][] dp=new int[6][12];

        for (int i=1;i<=5;i++){
            for (int w=0;w<=11;w++){
                if(i==0 || w==0){
                    dp[i][w]=0;
                    continue;
                }
                if(w>=arr[i-1].weight){
                    dp[i][w]=Integer.max(dp[i-1][w-arr[i-1].weight]+arr[i-1].value,dp[i-1][w]);
                }
                else{
                    dp[i][w]=dp[i-1][w];
                }

            }
        }
        print(dp);

         */



    }


}
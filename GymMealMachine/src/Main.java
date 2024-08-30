public class Main {
    public static void main(String[] args) {
        String path1=args[0];
        String path2=args[1];
        String path3=args[2];

        GMM gmm=new GMM();
        gmm.fill(path1,path3);
        gmm.ShowSlots(path3);
        gmm.Purchase(path2,path3);
        gmm.ShowSlots(path3);



    }
}


public class GMM {
    private Slot[][] slots;


    // Constructor initializes the slots with empty Slot objects.
    public GMM() {
        this.slots = new Slot[6][4];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                this.slots[i][j] = new Slot();
            }
        }
    }

    // Checks if all slots are full (each slot can hold up to 10 products).
    public Boolean isFull(){
        for(int i=0;i<6;i++){
            for(int j=0;j<4;j++){
                if(slots[i][j].HowMany()!=10){
                    return false;
                }
            }
        }
        return true;
    }

    // Fills the machine slots with products read from a file.
    public int fill(String path1,String path2) {

        String[] File = FileInput.readFile(path1,true,true);
        String fileContents = "";
        for (String i : File){
            fileContents += i +"\n";}

        String[] lines = fileContents.split("\\n");

        //This is a counter to accurately print the machine's occupancy
        int n=0;

        for (String line : lines) {
            String[] parts = line.split("\\t");
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);
            String[] nutritionalValues = parts[2].split(" ");
            double protein = Double.parseDouble(nutritionalValues[0]);
            double carb = Double.parseDouble(nutritionalValues[1]);
            double fat = Double.parseDouble(nutritionalValues[2]);

            Product product = new Product(name, price, protein, carb, fat);
            boolean placed = false;

            for (int i = 0; i < slots.length && !placed; i++) {
                for (int j = 0; j < slots[i].length && !placed; j++) {
                    // Implementation of reading product details from a file and adding them to the slots.
                    placed = slots[i][j].addProduct(product);


                }
            }
            // If  there's no available place for a new product, it logs info to a file .
             if(placed==false){
                String name1= product.getName();
                FileOutput.writeToFile(path2,"INFO: There is no available place to put "+name1,true,true);


            }
            // Returns -1 if the machine is full, otherwise returns 1.
            if(isFull()) {

                if (n == 1) {
                    FileOutput.writeToFile(path2, "INFO: The machine is full!", true, true);
                    return -1;
                }
                n++;
            }



        }
        return 1;

    }
    // Displays the current state of slots, including product names, calorie values, and counts.
    public void ShowSlots(String path2) {
        FileOutput.writeToFile(path2, "-----Gym Meal Machine-----", true, true);

        for (int i = 0; i < 6; i++) {
            FileOutput.writeToFile(path2, "", true, false);

            for (int j = 0; j < 4; j++) {
                if (slots[i][j].ShowProducts() != null) {
                    String productName = slots[i][j].ShowProducts().getName();
                    double calorieValue = slots[i][j].ShowProducts().CalculateCalorie();
                    int productCount = slots[i][j].HowMany();

                    int roundedCalories = (int) Math.round(calorieValue);

                    if (j > 0) {
                        FileOutput.writeToFile(path2, "___", true, false);

                    }

                    FileOutput.writeToFile(path2, productName + "(" + roundedCalories + ", " + productCount + ")", true, false);

                }
                else {
                    if(slots[i][j].getIsfinish()){
                        FileOutput.writeToFile(path2, "___(0, 0)", true, false);
                    }
                    else {
                        if (j == 0) {
                            FileOutput.writeToFile(path2, "___(0, 0)", true, false);
                        } else {
                            FileOutput.writeToFile(path2, "______(0, 0)", true, false);

                        }
                    }
                }
            }


            FileOutput.writeToFile(path2, "___", true, true);


        }

        FileOutput.writeToFile(path2, "----------", true, true);
    }





    public boolean CashControl(Double money){
    int[] cashes = {1, 5, 10, 20, 50,100,200};
        for(double cash : cashes){
            if(cash==money){
                return true;
            }

        }
        return false;
}

    // Handles the purchase of products based on selection.
  public Boolean f(Slot slot,Product product,Double amount,Double Choice2,int TotalMoney,String path2) {

      if (product != null) {
          if (Choice2 - 5 <= amount && amount <= Choice2 + 5) {
              if (product.getPrice() <= TotalMoney) {
                  if (slot.Buying(product)) {
                      String name= product.getName();
                      int returning=(int)(TotalMoney - product.getPrice());
                      FileOutput.writeToFile(path2,"PURCHASE: You have bought one " + name,true,true);
                      FileOutput.writeToFile(path2,"RETURN: Returning your change: " + returning + " TL",true,true);
                      return true;

                  }
              } else {
                  FileOutput.writeToFile(path2,"INFO: " + "Insufficient money, try again with more money.",true,true);
                  FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);
                  return true;
              }
          }

      }
        return false;

  }

    // Processes purchase requests read from a file.
    public void Purchase(String path1,String path2) {
        //This part provides us to take input file and to parse as we want
        String[] file_contents = FileInput.readFile(path1, false, false);
        String PurchaseContents = "";
        for (String i : file_contents) {
            PurchaseContents += i + "\n";
        }
        String[] lines = PurchaseContents.split("\\n");
        for (String line : lines) {
            FileOutput.writeToFile(path2,"INPUT: " + line,true,true);

            String parts[] = line.split("\\t");

            //This section receives the coins and checks whether the coins are available or not
            String moneys[] = parts[1].split(" ");

            int TotalMoney = 0;



            for (int i = 0; i < moneys.length; i++) {
                if (CashControl(Double.parseDouble(moneys[i]))) {
                    TotalMoney += Integer.parseInt(moneys[i]);
                } else {

                    continue;
                }
            }


            String Choice = parts[2];
            //If NUMBER is selected, Choice 2 shows which slot it is. If something else is selected, it tells the amount.
            Double Choice2 = Double.parseDouble(parts[3]);

            boolean Buyed = false;


            if (Choice.equals("NUMBER")) {
                if (Choice2 <= 24) {
                    int a = (int) (Choice2 / 4);
                    int b = (int) (Choice2 % 4);


                    Product product = slots[a][b].ShowProducts();
                    if (product != null) {
                        if (product.getPrice() <= TotalMoney) {
                            if (slots[a][b].Buying(product)) {
                                String name=product.getName();
                                int returning=(int)(TotalMoney - product.getPrice());
                                FileOutput.writeToFile(path2,"PURCHASE: You have bought one " + name,true,true);
                                FileOutput.writeToFile(path2,"RETURN: Returning your change: " + returning + " TL",true,true);

                            }
                        }
                        else {
                            FileOutput.writeToFile(path2,"INFO: " + "Insufficient money, try again with more money.",true,true);
                            FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);

                             }


                    } else {
                        FileOutput.writeToFile(path2,"INFO: This slot is empty, your money will be returned.",true,true);
                        FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);

                         }
                } else {
                    FileOutput.writeToFile(path2,"INFO: Number cannot be accepted. Please try again with another number.",true,true);
                    FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);

                     }
            } else if (Choice.equals("PROTEIN")) {

                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (Buyed == false) {
                            Product product = slots[i][j].ShowProducts();
                            Slot slot = slots[i][j];
                            if (product != null) {
                                Double amount = product.getProtein();
                                Buyed=f(slot, product, amount,  Choice2, TotalMoney,path2);
                            }
                        }
                    }
                }
                if (Buyed == false) {
                    FileOutput.writeToFile(path2,"INFO: Product not found, your money will be returned.",true,true);
                    FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);

                    Buyed=true;

            } }else if (Choice.equals("CARB")) {

                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (Buyed == false) {
                            Slot slot = slots[i][j];
                            Product product = slots[i][j].ShowProducts();
                            if (product != null) {
                                Double amount = product.getCarb();
                                Buyed=f(slot, product, amount, Choice2, TotalMoney,path2);
                            }
                        }
                    }
                }
                if (Buyed == false) {
                    FileOutput.writeToFile(path2,"INFO: Product not found, your money will be returned.",true,true);
                    FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);

                    Buyed=true;

            } }else if (Choice.equals("FAT")) {

                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (Buyed == false) {
                                Product product = slots[i][j].ShowProducts();
                                Slot slot = slots[i][j];
                                if (product != null) {
                                    Double amount = product.getFat();
                                    Buyed = f(slot, product, amount,  Choice2, TotalMoney,path2);
                                }
                            }
                        }
                    }
                    if (Buyed == false) {
                        FileOutput.writeToFile(path2,"INFO: Product not found, your money will be returned.",true,true);
                        FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);
                        Buyed = true;

                    } }else if (Choice.equals("CALORIE")) {

                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 4; j++) {
                                if (Buyed == false) {
                                    Product product = slots[i][j].ShowProducts();
                                    Slot slot = slots[i][j];
                                    if (product != null) {
                                        Double amount = product.CalculateCalorie();
                                        Buyed = f(slot, product, amount, Choice2, TotalMoney,path2);
                                    }
                                }
                            }
                        }
                        if (Buyed == false) {
                            FileOutput.writeToFile(path2,"INFO: Product not found, your money will be returned.",true,true);
                            FileOutput.writeToFile(path2,"RETURN: Returning your change: " + TotalMoney + " TL",true,true);

                            Buyed = true;
                        }
                    }
                }
            }
        }


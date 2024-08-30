public class Product {
    private String name;
    private double price;
    private double protein;
    private double carb;
    private double fat;
    private double calorie;

    public Product(String name, double price, double protein, double carb, double fat) {
        this.setPrice(price);
        this.setName(name);
        this.setProtein(protein);
        this.setCarb(carb);
        this.setFat(fat);

    }


    public double CalculateCalorie() {
        this.setCalorie((4 * this.getProtein() + 4 * this.getCarb() + 9 * this.getFat()));
        return getCalorie();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }
}



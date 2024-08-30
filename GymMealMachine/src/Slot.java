public class Slot {
     // A fixed array to hold products, with a maximum capacity of 10.
     private Product[] products = new Product[10];
     // Counter to keep track of the number of products currently in the slot.
     private int count = 0;

     private Boolean isfinish = false;

     // Adds a product to the slot if it matches the first product type or if the slot is empty.
     public Boolean addProduct(Product product) {
          // Checks if the first product is null (meaning slot is empty) or matches the new product.
          if (getProducts()[0] == null || getProducts()[0].getName().equals(product.getName())) {
               // Ensures there's room for the new product.
               if (getCount() < getProducts().length) {
                    getProducts()[getCount()] = product; // Adds the product at the current count index.
                    setCount(getCount() + 1); // Increments the product count.
                    return true; // Indicates success.
               } else {
                    return false; // Indicates the slot is full.
               }
          } else {
               return false; // Indicates the product does not match the slot's product type.
          }
     }

     // Shows the first product in the slot, which represents the type of products in this slot.
     public Product ShowProducts() {
          return getProducts()[0];
     }

     // Attempts to buy a product from the slot, removing the first product if it matches.
     public boolean Buying(Product product) {
          if (product.equals(getProducts()[0])) { // Checks if the product matches the first product.
               getProducts()[0] = null; // Removes the product.
               compress(); // Shifts all products one slot forward.
               if (HowMany() == 0) { // Checks if the slot is now empty.
                    setIsfinish(true); // Marks the slot as finished.
               }
               return true; // Indicates successful purchase.
          } else {
               return false; // Indicates failure to match the product.
          }
     }

     // Shifts products forward in the array to fill null gaps after a purchase.
     private void compress() {
          for (int i = 0; i < getProducts().length - 1; i++) {
               if (getProducts()[i] == null) {
                    getProducts()[i] = getProducts()[i + 1];
                    getProducts()[i + 1] = null;
               }
          }
     }

     // Counts how many non-null products are in the slot.
     public int HowMany() {
          int count = 0;
          for (Product product : getProducts()) {
               if (product != null) {
                    count++;
               }
          }
          return count;
     }


     public Product[] getProducts() {
          return products;
     }

     public void setProducts(Product[] products) {
          this.products = products;
     }

     public int getCount() {
          return count;
     }

     public void setCount(int count) {
          this.count = count;
     }

     public Boolean getIsfinish() {
          return isfinish;
     }

     public void setIsfinish(Boolean isfinish) {
          this.isfinish = isfinish;
     }
}

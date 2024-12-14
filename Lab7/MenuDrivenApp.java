import java.util.*;
// Customer Class
class Customer{
    private int id;
    private String name;
    private int loyaltyPnts;
    private HashSet<Product> products;
    public Customer(int id, String name, int loyaltyPnts){
        this.id = id;
        this.name = name;
        this.loyaltyPnts = loyaltyPnts;
        this.products = new HashSet<>();
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getloyaltyPnts() {
        return loyaltyPnts;
    }
    public void addProd(Product product) {
        products.add(product);
    }
    public HashSet<Product> getProd() {
        return products;
    }
    @Override
    public String toString() {
        return "Customer{" + "id=" + id +", name='" + name + '\'' +", loyaltyPnts=" + loyaltyPnts +'}';
    }
}
// Product Class
class Product {
    private int id;
    private String name;
    private double price;
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "Product{" + "id=" + id +", name='" + name + '\'' +", price=" + price +'}';
    }
}
// Order Class
class Order {
    private int orderId;
    private Customer customer;
    private Product product;
    private Date deliveryDate;
    public Order(int orderId, Customer customer, Product product, Date deliveryDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.product = product;
        this.deliveryDate = deliveryDate;
    }
    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }
    public Customer getCust() {
        return customer;
    }
    public Product getProd() {
        return product;
    }
    public Date getDelDate() {
        return deliveryDate;
    }
    @Override
    public String toString() {
        return "Order{" +"orderId=" + orderId +", customer=" + customer.getName() +", product=" + product.getName() +", deliveryDate=" + deliveryDate +'}';
    }
}
//comparator-sorting products by price
class priceComp implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return Double.compare(p1.getPrice(), p2.getPrice());
    }
}
//comparator-sorting customers by loyalty points
class loyaltyComp implements Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        return Integer.compare(c2.getloyaltyPnts(), c1.getloyaltyPnts());
        // Descending order
    }
}
public class MenuDrivenApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Data Structures
        ArrayList<Customer> custList = new ArrayList<>();
        HashMap<Integer, Product> productMap = new HashMap<>();
        ArrayList<Order> orderList = new ArrayList<>();
        // Infinite loop for menu-driven application
        while (true) {
            System.out.println("Menu");
            System.out.println("1.Add customer");
            System.out.println("2.Add product");
            System.out.println("3.Create order");
            System.out.println("4.View sorted products by price");
            System.out.println("5.View sorted customers by loyalty points");
            System.out.println("6.exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    // Adding a new customer
                    System.out.print("Enter customer ID: ");
                    int customerId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter customer name: ");
                    String customerName = sc.nextLine();
                    System.out.print("Enter loyalty points: ");
                    int loyaltyPnts = sc.nextInt();
                    sc.nextLine();
                    // Validation: Ensure positive ID and loyalty points
                    if (customerId <= 0 || loyaltyPnts < 0) {
                        System.out.println("Invalid input, customerID and loyalty points shld be +ve.");
                    } else if (customerName.trim().isEmpty()) {
                        System.out.println("Invalid input, customer name cannot be empty.");
                    } else {
                        custList.add(new Customer(customerId, customerName, loyaltyPnts));
                        System.out.println("Added successfully.");
                    }
                    break;
                case 2:
                    // Adding a new product
                    System.out.print("Enter product ID: ");
                    int productId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter product Name: ");
                    String productName = sc.nextLine();
                    System.out.print("Enter product Price: ");
                    double productPrice = sc.nextDouble();
                    sc.nextLine();
                    //validation:positive ID and price
                    if (productId <= 0 || productPrice <= 0) {
                        System.out.println("Invalid input, product ID and price shld be +ve.");
                    } else if (productName.trim().isEmpty()) {
                        System.out.println("Invalid input, product name cannot be empty.");
                    } else {
                        productMap.put(productId, new Product(productId, productName, productPrice));
                        System.out.println("Product added successfully.");
                    }
                    break;
                case 3:
                    // Creating a new order
                    System.out.print("Enter order ID: ");
                    int orderId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter customer ID for the order: ");
                    int orderCustId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter product ID for the order: ");
                    int orderProdId = sc.nextInt();
                    sc.nextLine();
                    // Finding the customer and product
                    Customer orderCust = null;
                    Product orderProd = null;
                    for (Customer c:custList) {
                        if (c.getId()==orderCustId) {
                            orderCust=c;
                            break;
                        }
                    }
                    orderProd = productMap.get(orderProdId);
                    //validation:ensure valid IDs and no duplicate order ID
                    if (orderCust==null || orderProd==null) {
                        System.out.println("Invalid Customer ID or Product ID.");
                    } else if (orderList.stream().anyMatch(order -> order.getOrderId() == orderId)) {
                        System.out.println("Order ID already exists.");
                    } else {
                        orderList.add(new Order(orderId, orderCust, orderProd, new Date()));
                        System.out.println("Order created successfully.");
                    }
                    break;
                case 4:
                    //display sorted products by price
                    TreeSet<Product> sortedProd = new TreeSet<>(new priceComp());
                    sortedProd.addAll(productMap.values());
                    System.out.println("\nSorted products by price:");
                    for (Product product : sortedProd) {
                        System.out.println(product);
                    }
                    break;
                case 5:
                    //display sorted customers by loyalty points
                    TreeSet<Customer> sortedCust = new TreeSet<>(new loyaltyComp());
                    sortedCust.addAll(custList);
                    System.out.println("\nSorted customers by loyalty points:");
                    for (Customer customer : sortedCust) {
                        System.out.println(customer);
                    }
                    break;
                case 6:
                    //exit the application
                    System.out.println("exiting the application.");
                    sc.close();
                    return;
                default:
                    //invalid menu option
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
}

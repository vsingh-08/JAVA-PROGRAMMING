import java.util.*;

class CounterEmptyException extends Exception {
    public CounterEmptyException(String message) {
        super(message);
    }
}

class CoffeeShop {
    private final Queue<String> counter = new LinkedList<>();
    private final int MAX_CAPACITY = 3;

    //baristas
    public synchronized void produce(String coffee, String baristaName) {
        while (counter.size() == MAX_CAPACITY) {
            try {
                System.out.println(baristaName + " is waiting. Counter is full.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        counter.add(coffee);
        System.out.println(baristaName + " prepared coffee. Counter: " + counter.size());
        notifyAll();
    }

    //customers
    public synchronized String consume(String customerName) throws CounterEmptyException {
        while (counter.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (counter.isEmpty()) {
            throw new CounterEmptyException(customerName + " tried to pick up coffee, but the counter is empty!");
        }
        String coffee = counter.poll();
        System.out.println(customerName + " picked up coffee. Counter: " + counter.size());
        notifyAll();
        return coffee;
    }

    //reviewer
    public synchronized String review(String reviewerName) throws CounterEmptyException {
        while (counter.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (counter.isEmpty()) {
            throw new CounterEmptyException(reviewerName + " tried to review coffee, but the counter is empty!");
        }
        String coffee = counter.poll();
        System.out.println(reviewerName + " sampled coffee. Counter: " + counter.size());
        notifyAll();
        return coffee;
    }
}

public class CoffeeShopSimulation {
    public static void main(String[] args) {
        CoffeeShop coffeeShop = new CoffeeShop();
        Scanner sc = new Scanner(System.in);

        //inputs-baristas
        System.out.print("Enter the number of baristas: ");
        int numBaristas = sc.nextInt();
        Map<Integer, Integer> baristaTasks = new HashMap<>();
        for (int i = 1; i <= numBaristas; i++) {
            System.out.print("Enter the number of coffees Barista " + i + " will prepare: ");
            baristaTasks.put(i, sc.nextInt());
        }

        //inputs-customers
        System.out.print("Enter the number of customers: ");
        int numCustomers = sc.nextInt();
        Map<Integer, Integer> customerTasks = new HashMap<>();
        for (int i = 1; i <= numCustomers; i++) {
            System.out.print("Enter the number of coffees Customer " + i + " will pick up: ");
            customerTasks.put(i, sc.nextInt());
        }

        //inputs-reviewers
        System.out.print("Enter the number of reviewers: ");
        int numReviewers = sc.nextInt();
        Map<Integer, Integer> reviewerTasks = new HashMap<>();
        for (int i = 1; i <= numReviewers; i++) {
            System.out.print("Enter the number of coffees Reviewer " + i + " will sample: ");
            reviewerTasks.put(i, sc.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        //barista threads
        baristaTasks.forEach((id, taskCount) -> {
            threads.add(new Thread(() -> {
                for (int i = 0; i < taskCount; i++) {
                    coffeeShop.produce("Coffee from Barista " + id, "Barista " + id);
                }
            }, "Barista " + id));
        });

        //customer threads
        customerTasks.forEach((id, taskCount) -> {
            threads.add(new Thread(() -> {
                for (int i = 0; i < taskCount; i++) {
                    try {
                        coffeeShop.consume("Customer " + id);
                    } catch (CounterEmptyException e) {
                    }
                }
            }, "Customer " + id));
        });

        //reviewer threads
        reviewerTasks.forEach((id, taskCount) -> {
            threads.add(new Thread(() -> {
                for (int i = 0; i < taskCount; i++) {
                    try {
                        coffeeShop.review("Reviewer " + id);
                    } catch (CounterEmptyException e) {
                        System.out.println(e.getMessage()); 
                    }
                }
            }, "Reviewer " + id));
        });

        // Start baristas
        threads.stream().filter(t -> t.getName().contains("Barista")).forEach(Thread::start);

        try {
            Thread.sleep(100);  // barista 1 to finish before barista 2
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // customer threads after baristas
        threads.stream().filter(t -> t.getName().contains("Customer")).forEach(Thread::start);

        // reviewer threads last
        threads.stream().filter(t -> t.getName().contains("Reviewer")).forEach(Thread::start);

        // all threads finish
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        sc.close();
    }
}

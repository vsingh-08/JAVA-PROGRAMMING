import java.util.Scanner;

public class Creditcard {
    private long credno;

    public Creditcard(long credno){
        this.credno=credno;
        validateCard();
    }

    public void validateCard(){
        String len = Long.toString(credno);
        int n = len.length();

        if (n==8 || n==9){
            System.out.println("Credit card no is:" + credno);

            //a-Remove last digit
            long lastDigit = credno % 10;
            long credcut = credno / 10;
            System.out.println("Credit card no after removing the last digit: " + credcut);

            //b-Reverse
            String credno1= Long.toString(credcut);
            String rev_credno1= new StringBuilder(credno1).reverse().toString();
            System.out.println("Reversed credit card number: " + rev_credno1);

            //c,d-doubling and adding
            int sum = calSum(rev_credno1);
            System.out.println("Sum of the processed digits: " + sum);

            //e- for 10 - lastdigit of sum = y, finding y.
            int checkDigit = (10 - (sum %10) % 10);

            //f- comparing
            if (checkDigit == lastDigit){
                System.out.println("Credit card is valid");
            }
            else{
                System.out.println("Credit card is invalid");
            }
        }
        else{
            System.out.println("Invalid number, must be 8/9 digits long.");
        }
        }

        public int calSum(String rev_credno1){
            int sum=0;
            for (int i = 0; i<rev_credno1.length(); i++){
                int currentDigit = Character.getNumericValue(rev_credno1.charAt(i));
                if (i % 2 == 0){
                    int doubled = currentDigit * 2;
                    if (doubled > 9){
                        doubled = doubled / 10 + doubled % 10;
                    }
                    
                    sum += doubled / 10 + doubled % 10;  // Sum the digits of doubled

                }
                else{
                    sum += currentDigit;
                }

            }
            return sum;
        }

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the credit card number- ");

            if(sc.hasNextLong()){
                long credno = sc.nextLong();
                new Creditcard(credno);
            }
            else{
                System.out.println("Invalid data type. Enter a valid numeric credit card number.");
            }
            sc.close();
        }
    }



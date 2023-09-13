import java.util.*;

public class task1
{
    public static void main(String args[])
    {
        Random rand = new Random();
        int randomNumber = rand.nextInt(100) + 1;

        Scanner sc = new Scanner(System.in);
        int userNumber,count=0; 

        //System.out.println(randomNumber);

        do
        {
            userNumber = sc.nextInt();
            count++;

            if(count==10)
            {
                System.out.println("Sorry your attempts completed . Try Again");
                return;
            }

            if (Math.abs(userNumber - randomNumber) < 10) 
            {
                System.out.println("So Close ..");
            } 
            else if ((Math.abs(userNumber - randomNumber) > 10) && (Math.abs(userNumber - randomNumber) < 40)) 
            {
                System.out.println("Good key on guessing ..");
            } 
            else if (Math.abs(userNumber - randomNumber) > 40) 
            {
                System.out.println("So far ..");
            }
        }
        while(randomNumber != userNumber);

        System.out.println("You have guessed  "+ (count-1) + "  times to get actual number");
    }
}
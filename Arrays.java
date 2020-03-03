import java.lang.Math;

public class Arrays {
    public static void main(String[] args) {
        int[] arr = getRandomArray(10);
        System.out.println("Get Random Array:");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        System.out.println();

        int[] sortedArr = getSortedArray(10);
        System.out.println("Get Sorted Array:");
        for (int i = 0; i < sortedArr.length; i++)
            System.out.print(sortedArr[i] + " ");
        System.out.println();
    }

    public static int[] getRandomArray(int n)
    {
        // For n numbers, get a random number and add it to the array.
        int[] randArray = new int[n];
        for (int i = 0; i < n;)  // for loop to fill all indices of the array
        {
            boolean match = false;
            int temp = (int)(Math.random() * (n+1));  // element

            for (int j = 0; j < n; j++)
            {
                if (randArray[j] == temp)   // check the array, if any of the elements match the new number, choose again
                    match = true;
            }
            if (match == false) {  // otherwise add it to the array
                randArray[i] = temp;
                i++;
            }
        }
        return randArray;
    }

    public static int[] getSortedArray(int n)
    {
        int[] arr = new int[n];  // Make a new int array
        int size = n;
        for (int i = 0; i < size; i++)  // Add the elements from n to 1 decreasing
        {
            arr[i] = n;
            n--;
        }
        return arr;
    }
}

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainProgram
{
    // k: ATTRIBUTES ---------------------------------------------------------------------------------------------------
    private static Random mRandom = new Random();
    // k: "GETTERS" ----------------------------------------------------------------------------------------------------
    private static Random getRandom() { return mRandom; }
    // k: "SETTERS" ----------------------------------------------------------------------------------------------------
    private static void setRandom(long seedValue) { mRandom.setSeed(seedValue); }

    // k: MAIN PROGRAM -------------------------------------------------------------------------------------------------
    public static void main(String [] args)
    {
        // k: INSERTION SORT: Run the sorting algorithm 5 times.
        int[] testCaseIS = generateRandomArray(10);
        for (int i = 0; i < 5; i ++) {
            insertionSort(testCaseIS, 0, testCaseIS.length-1);
        }

        // k: QUICK SORT: Run the sorting algorithm 5 times.
        int[] testCaseQS = generateRandomArray(10);
        for (int i = 0; i < 5; i++) { quickSort(testCaseQS); }

        // TODO: Prompt user for inputSize.
        Scanner scan = new Scanner(System.in);
        System.out.print("Input Size: ");
        int inputSize = scan.nextInt();
        System.out.println();

        // TODO: Display Menu Options.
        int menuOption = displayMenu();

        // for options 1 thru 3:
        while (menuOption > 0 && menuOption < 4)
        {
            // TODO: Generate an array of ["inputSize"] # of integers based on selection ("original"). Create two duplicates of original ("duplicate" & "duplicate2").
            int[][] mArrays = {generateSortedArray(inputSize), generateRandomArray(inputSize), generateReverseArray(generateSortedArray(inputSize))};
            String[] mArrayNames = {"SORTED", "RANDOM", "REVERSE"};
            int[] original = mArrays[menuOption-1]; // ie. (1, 2, 3) --> (0, 1, 2)
            int[] duplicate = new int[original.length];
            System.arraycopy(original, 0, duplicate, 0, original.length);
            int[] duplicate2 = new int[original.length];
            System.arraycopy(original, 0, duplicate2, 0, original.length);

            // k: INSERTION SORT ---------------------------------------------------------------------------------------

            System.out.print("BEFORE insertionSort(): ");
            for (int i  = 0; i < 10; i++) {
                System.out.print(duplicate[i] + ", "); // print array before sorting
            }
            System.out.println();
            // TODO: 2. Sort "duplicate" array using Insertion Sort. Validate that the array is actually sorted (via isOrdered()).
            long runtimeISNS = insertionSort(duplicate, 0, duplicate.length-1);
            double runtimeISMS = runtimeISNS / 1E6;
            if (isOrdered(duplicate))
            {
                System.out.println("INSERTION SORT correctly sorted the " + mArrayNames[menuOption-1] + " array."); // ie. (1, 2, 3) --> (0, 1, 2)
                //System.out.println(String.format("INSERTION SORT took %.2g ms", runtimeISMS));
                System.out.println("INSERTION SORT took " + runtimeISMS + " ms");
                //System.out.println("INSERTION SORT took " + runtimeISNS + " ns");
                System.out.print("AFTER insertionSort(): ");
                for (int i  = 0; i < 10; i++) {
                    System.out.print(duplicate[i] + ", "); // print array after sorting
                }
                System.out.println();
            }
            else { System.out.println("ERROR: INSERTION SORT failed to sort the " + mArrayNames[menuOption-1] + " array."); }
            System.out.println();

            // k: QUICK SORT -------------------------------------------------------------------------------------------

            System.out.print("BEFORE quickSort(): ");
            for (int i  = 0; i < 10; i++) {
                System.out.print(duplicate2[i] + ", "); // print array before sorting
            }
            System.out.println();
            // TODO: 3. Sort "duplicate2" array using Insertion Sort. Validate that the array is actually sorted (via isOrdered()).
            long runtimeQSNS = quickSort(duplicate2);
            double runtimeQSMS = runtimeQSNS / 1E6;
            if (isOrdered(duplicate2))
            {
                System.out.println("QUICK SORT correctly sorted the " + mArrayNames[menuOption-1] + " array."); // ie. (1, 2, 3) --> (0, 1, 2)
                //System.out.println(String.format("QUICK SORT took %.2g ms", runtimeQSMS));
                System.out.println("QUICK SORT took " + runtimeQSMS + " ms");
                //System.out.println("QUICK SORT took " + runtimeQSNS + " ns");
                System.out.print("AFTER quickSort(): ");
                for (int i  = 0; i < 10; i++) {
                    System.out.print(duplicate2[i] + ", "); // print array after sorting
                }
                System.out.println();
            }
            else { System.out.println("ERROR: QUICK SORT failed to sort the " + mArrayNames[menuOption-1] + " array."); }
            System.out.println();

            // TODO: 4. Return to the menu.
            menuOption = displayMenu();
        }   // <--- (END of WHILE-LOOP)
    }   // <--- (END of MAIN)

    // k: CLASS METHODS ------------------------------------------------------------------------------------------------
    public static int displayMenu()
    {
        // TODO: 1. Ask the user to select 1 of 3 arrays to sort:
        Scanner scan = new Scanner(System.in);
        System.out.println( "1) Sorted\n" + // index: 0
                            "2) Random\n" + // index: 1
                            "3) Reverse\n" + // index: 2
                            "4) Quit\n"); // index: 3
        System.out.print("Select 1 of 3 arrays to sort: ");
        return scan.nextInt();
    } // <--- (END of displayMenu())

    // TODO: 2c. Write a function to create & return an array of ["inputSize"] # of random integers from -100 to +100, inclusive.
    public static int[] generateSortedArray(int inputSize)
    {
        // TODO: Create an array of ["inputSize"] # integers.
        int[] sortedArray = new int[inputSize];
        for (int i = 1; i <= inputSize; i++)
        {
            // TODO: Add "i" to array ("randInts").
            sortedArray[i-1] = i;
        }
        return sortedArray;
    } // <--- (END of getSortedArray())

    public static int[] generateRandomArray(int inputSize)
    {
        // TODO: Create an array of ["inputSize"] # integers.
        int[] randomArray = generateSortedArray(inputSize);
        for (int i = 0; i < randomArray.length; i++) {
            int randomIndexToSwap = getRandom().nextInt(randomArray.length);
            int temp = randomArray[randomIndexToSwap];
            randomArray[randomIndexToSwap] = randomArray[i];
            randomArray[i] = temp;
        }
        return randomArray;
    } // <--- (END of getRandomArray())

    public static int[] generateReverseArray(int[] sortedArray)
    {
        // TODO: Create an array of ["inputSize"] # integers.
        int[] reverseArray = new int[sortedArray.length];
        int rit = sortedArray.length; // reverse iterate thru reverseArray
        for (int i : sortedArray) { // iterate thru sortedArray
            reverseArray[rit - 1] = i;
            rit -= 1;
        }
        return reverseArray;
    } // <--- (END of putinReverseTArray())

    private static long insertionSort(int[] duplicate, int left, int right) {
        long startTime = System.nanoTime(); // k: START of Alg. --------------------------------------------------------
        // NOTE: i represents the # of values in the correct spot ("sorted")
        for (int i = left + 1; i <= right; i++) { // NOTE: i depends on right
            int j, temp = duplicate[i]; // make a copy of a[i] // NOTE: does not increase with length of the array, since these values get thrown away after every iteration ---> doesn't need any memory (O(n)) or "in place"
            // NOTE: takes value & shifts it to the left until it is in the right spot.
            for (j = i - 1; j >= 0; j--) { // start "moving left" // NOTE: j depends on i, and i depnds on right
                if (duplicate[j] > temp) {
                    duplicate[j + 1] = duplicate[j]; // inversion detected; move a[j] to the right
                }
                else {
                    break;
                } // index j is one spot to the left of where temp belongs
            }
            duplicate[j+1] = temp;
        }
        long endTime = System.nanoTime(); // k: END of Alg. ------------------------------------------------------------
        return (endTime - startTime); // returns runtime
    }

    private static long quickSort(int[] duplicate)
    {
        long startTime = System.nanoTime(); // k: START of Alg. --------------------------------------------------------
        recursiveQuickSort(duplicate, 0, duplicate.length-1); // [left, right]
        long endTime = System.nanoTime(); // k: END of Alg. ------------------------------------------------------------
        return (endTime - startTime); // returns runtime
    }

    private static void recursiveQuickSort(int[] duplicate, int left, int right) {
        // TODO: 1. BASE CASE: Use Insertion Sort to sort any subsequence of 10 elements or less.
        int numOfElements = (right - left) + 1;
        if (numOfElements <= 10) {
            insertionSort(duplicate, left, right); // DONT INSERTION SORT ENTIRE ARRAY LENGTH
            return;
        }

        // TODO: 2. Select pivotIndex (via Median-of-3).
        int midPoint = (left + right) / 2;
        int medianOf3INDEX = medianOf3(duplicate, left, right, midPoint);

        // TODO: 3. Partitioning Step (via Partitioning Algorithm).
        //  (so that values <= m are to its left & values > m are to its right)
        int pivotIndex = partition(duplicate, left, right, medianOf3INDEX);

        // TODO: 4. LAST: Sort partitions recursively (via recursiveQuickSort).
        recursiveQuickSort(duplicate, left, pivotIndex-1); // left partition (values <= m)
        recursiveQuickSort(duplicate, pivotIndex+1, right); // right partition (values > m)
    }

    // helper method's for sorting algorithms
    // returns index of medianOf3 value
    private static int medianOf3(int[] duplicate, int left, int right, int midPoint)
    {
        // Checking for b
        if ((duplicate[left] < duplicate[right] && duplicate[right] < duplicate[midPoint]) || (duplicate[midPoint] < duplicate[right] && duplicate[right] < duplicate[left]))
        { return right; }
        // Checking for a
        else if ((duplicate[right] < duplicate[left] && duplicate[left] < duplicate[midPoint]) || (duplicate[midPoint] < duplicate[left] && duplicate[left] < duplicate[right]))
        { return left; }
        else { return midPoint; }
    }

    private static int partition(int[] duplicate, int left, int right, int pivotIndex) {
        int pivotValue = duplicate[pivotIndex];
        swap(duplicate, pivotIndex, right); // move pivotValue to end of array ("duplicate")

        int store = left; // store marks beginning of "new" array
        for (int i = left; i < right; i++) { // iterate thru array (left -> right)
            if (duplicate[i] <= pivotValue) { // overwrite value @store w/ any value than pivotValue
                swap(duplicate, store, i);
                store++;
            }
        }
        swap(duplicate, right, store); // swap pivotValue to its final position
        return store;
    }

    private static void swap(int[] duplicate, int a, int b) { // Q: a & b might be switched around
        int temp = duplicate[a];
        duplicate[a] = duplicate[b];
        duplicate[b] = temp;
    }

    public static boolean isOrdered(int[] duplicate) {
        for (int i = 0; i < (duplicate.length - 1); i++) {
            if (duplicate[i] >= duplicate[i + 1]) { return false; }
        }
        return true;
    }
}
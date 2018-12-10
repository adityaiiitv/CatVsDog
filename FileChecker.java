import java.util.ArrayList;
import java.util.*;
import java.io.InputStreamReader;
import java.io.*;

public class FileChecker
{
    private static ArrayList<String> testExamples = null;
    public static int epoch;
	// Get Hash value
    public static int getHashValue(String str) 
    {
        int sum = 0;
        byte[] data = str.getBytes();
        int len = data.length;
        for (int i = 0; i < len; i++) 
        {
            sum ^= data[len];
        }
        return sum;
    }

	// Get unique and duplicate files
    private static void dealWithCases(ArrayList<String> testCaseList) 
    {
        int noOfHashCollision = 0;
		HashSet<String> copyStringSet = new HashSet<String>();
        String[] testElements = testCaseList.toArray(new String[testCaseList.size()]);
        int uniqueFiles = epoch;
        int len1 = testElements.length;
        for (int i = 0; i < len1-1; i++) 
        {
            if (copyStringSet.contains(testElements[i])) 
            {
                uniqueFiles = uniqueFiles - 1;
            }
            else
            {
                copyStringSet.add(testElements[i]);
            }
			int len2 = testElements.length;
            for (int j = (i + 1); j < len2; j++)
            {
                int hashOne = getHashValue(testElements[i]);
                int hashTwo = getHashValue(testElements[j]);
                if ((hashOne == hashTwo) && !(testElements[i].equals(testElements[j]))) 
                {
                    noOfHashCollision = noOfHashCollision + 1;
                }
            }
        }

        if (copyStringSet.contains(testElements[testElements.length - 1])) 
        {
            uniqueFiles = uniqueFiles - 1;
        }

        System.out.println(uniqueFiles + " " + noOfHashCollision);
    }
    
	// Main Function
    public static void main(String[] args) throws IOException 
    {
		Scanner sc = new Scanner(System.in);
		epoch = sc.nextInt();
		while(epoch!=0)
		{
			testExamples = new ArrayList<String>();
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
			String line;
			while ((line = buffRead.readLine()) != null)
			{
				testExamples.add(line);
			}
			
			// List on test cases
			while (!testExamples.isEmpty()) 
			{
				dealWithCases(new ArrayList<String>(testExamples.subList(0, epoch + 1)));
				testExamples.subList(0, epoch + 1).clear();
			}
			epoch = sc.nextInt();
		}
    }
}

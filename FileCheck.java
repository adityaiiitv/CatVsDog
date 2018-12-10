import java.util.ArrayList;
import java.util.*;
import java.io.InputStreamReader;
import java.io.*;

public class FileCheck
{
    private static ArrayList<String> testCasesList = null;
    public static void main(String[] args) throws IOException 
    {
        // TODO Auto-generated method stub
        final long startTime = System.currentTimeMillis();
        testCasesList = new ArrayList<String>();
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = bi.readLine()) != null)
        {
            testCasesList.add(line);
        }




        /* remove 0 from the end */

        int positionOf0 = -1;
        for (int k = 0; k < testCasesList.size(); k++) {
            if (testCasesList.get(k).startsWith("0") && Integer.parseInt(testCasesList.get(k)) == 0) {
                positionOf0 = k;
                // System.out.println("positionOf0" + positionOf0);
                break;
            }
        }
        testCasesList.subList(positionOf0, testCasesList.size()).clear();
        testCasesList.remove(testCasesList.size() - 1);
        /* partition list based on individual test case */

        while (!testCasesList.isEmpty()) 
        {
            int valueAtFirstPos = Integer.parseInt(testCasesList.get(0));
            processEachTestCase(new ArrayList<String>(testCasesList.subList(0, valueAtFirstPos + 1)));
            testCasesList.subList(0, valueAtFirstPos + 1).clear();
        }

         final long duration = System.currentTimeMillis() - startTime;
         System.err.println(" Program took milli seconds time to run - " +
         duration);
    }

    /*
     * below method contains actual logic to find out unique and duplicate files
     */

    private static void processEachTestCase(ArrayList<String> singleTestCaseList) 
    {
        // TODO Auto-generated method stub
        int noOfHashCollision = 0;
        HashSet<String> duplicateStringSet = new HashSet<String>();

        String[] elementsUnderTest = singleTestCaseList.toArray(new String[singleTestCaseList.size()]);
        int valueAtFirstPos = Integer.parseInt(elementsUnderTest[0]);

        int noOfUniqueFiles = valueAtFirstPos;

        for (int i = 1; i < (elementsUnderTest.length - 1); i++) {

            if (duplicateStringSet.contains(elementsUnderTest[i])) 
            {
                noOfUniqueFiles = noOfUniqueFiles - 1;
            }
            else
            {
                duplicateStringSet.add(elementsUnderTest[i]);
            }

            for (int j = (i + 1); j < (elementsUnderTest.length); j++)
            {
                int hashStr1 = getHashValue(elementsUnderTest[i]);
                int hashStr2 = getHashValue(elementsUnderTest[j]);
                if ((hashStr1 == hashStr2) && !(elementsUnderTest[i].equals(elementsUnderTest[j]))) 
                {
                    noOfHashCollision = noOfHashCollision + 1;
                }
            }
        }

        if (duplicateStringSet.contains(elementsUnderTest[elementsUnderTest.length - 1])) 
        {
            noOfUniqueFiles = noOfUniqueFiles - 1;
        }
        System.out.println(noOfUniqueFiles + " " + noOfHashCollision);
    }

    /*
     * below method contains actual logic to calculate hash code for each file
     */

    static int getHashValue(String str) 
    {
        int sum = 0;
        byte[] data = str.getBytes();

        for (int i = 0; i < data.length; ++i) 
        {
            sum ^= data[i];
        }
        // System.out.println(sum);
        return sum;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reductmax;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author nikola
 */
public class ReductMax {

    public static int kvadratni(ArrayList<Integer> array) {

        int max = array.get(0);

        for (int mainCount = 0; mainCount < array.size(); mainCount++) {
            int componentSum = 0;

            for (int innerCount = mainCount; innerCount < array.size(); innerCount++) {
                componentSum += array.get(innerCount);
                if (componentSum > max) {
                    max = componentSum;
                }
            }
        }

        return max;
    }

    public static int mojAlg(ArrayList<Integer> inputArray) {
        
        int previous = inputArray.get(0);
        //replacing sequencies of same sign numbers with sum of these numbers
        //[5,2,-1,-3,-4,1,-2] => [7,-7,1,-2] 
        for (int i = 1; i < inputArray.size(); i++) {
            int current = inputArray.get(i);
            if (previous * current >= 0) {
                inputArray.set(i, current + previous);
                inputArray.remove(i - 1);
                i--;
            }
            previous = inputArray.get(i);
        }
        //removing negative numbers from begin or end of array
        if (!inputArray.isEmpty() && inputArray.get(inputArray.size() - 1).compareTo(0) < 0) {
            inputArray.remove(inputArray.size() - 1);
        }
        if (!inputArray.isEmpty() && inputArray.get(0).compareTo(0) < 0) {
            inputArray.remove(0);
        }
        //if only one element remained in array
        if (inputArray.size() == 1) {
            //and if negative return 0
            if (inputArray.get(0) < 0) {
                return 0;
            } else {//otherwise return that element
                return inputArray.get(0);
            }
        }
        if (inputArray.isEmpty()) {
            return 0;
        }
        //fun starts here
        //now, array shoud be like this: [+,-,+,-,+,...,+,-,+]
        //take first element - this element holds whole sequence in one int
        int firstElementOfSequence = inputArray.get(0);
        //memorize highest maximum of sequncies seen so far
        int tempMax = firstElementOfSequence;
        for (int i = 2; i < inputArray.size(); i += 2) {
            //take sum of next pair (should be in form: -,+)
            int nextPairSum = inputArray.get(i) + inputArray.get(i - 1);
            //if this sum is greater then positive element of pair
            if (nextPairSum >= 0 && firstElementOfSequence + nextPairSum > inputArray.get(i)) {
                //exceed sequence by including that pair in firstElement [+,-,+] => [+]
                firstElementOfSequence += nextPairSum;
            } else {//otherwise, nextPair is negative, possible break of sequence
                //first, check if this sequence is greater then max
                if (tempMax < firstElementOfSequence) {
                    tempMax = firstElementOfSequence;
                }
                //second, check if sequence should be broken
                //by cheking if sequence "+" nextPairSum is greater then positive number in pair
                //quotes are cause this isn't add opperation, it's substraction
                if (firstElementOfSequence + nextPairSum > inputArray.get(i)) {
                    //this is case when it's better to exceed with sequence
                    firstElementOfSequence = firstElementOfSequence + nextPairSum;
                } else {
                    //break sequence by setting start of new one
                    firstElementOfSequence = inputArray.get(i);
                }
            }
            //idea of this algorithm is scanning reduced array from left to right
            //forming triples [+L,-M,+R] and reducing theese triples to one number
            //1: [+newL = (+L)+(-M)+(+R)] if +R < sumOfTriple
            //2: [+R] +R > sumOfTriple
            //theese triples when reduced to newL form next iteration's triple [+newL,-M,+R] etc.
            //tmpMax holds greatest value of all broken sequnces (of triples)
        }
        //finally, check if last element of array is greater then current sequence
        if (firstElementOfSequence < inputArray.get(inputArray.size() - 1)) {
            firstElementOfSequence = inputArray.get(inputArray.size() - 1);
        }
        //comparing tempMax and last sequence
        if (tempMax < firstElementOfSequence) {
            tempMax = firstElementOfSequence;
        }
        return tempMax;
    }

    public static ArrayList<Integer> createInput(int size, int amplitude) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int temp = (int) Math.ceil(Math.random() * amplitude) - amplitude / 2;
            list.add(temp);
        }
        return list;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int count = 0;
        int numberOfTests = 1;
        for (int i = 0; i < numberOfTests; i++) {
            ArrayList<Integer> inputArray = createInput(100000, 20);
            
            long startTime1 = System.currentTimeMillis();
            int rezKvadr = kvadratni(inputArray);
            long estimatedTime1 = System.currentTimeMillis() - startTime1;
            long startTime2 = System.currentTimeMillis();
            int rezMoj = mojAlg(inputArray);
            long estimatedTime2 = System.currentTimeMillis() - startTime2;
            
            System.out.println(estimatedTime1 + "-----------" + estimatedTime2);
            if (rezKvadr == rezMoj) {
                count++;
            } else {
                System.out.println(rezKvadr + " - " + rezMoj);
            }
        }
        if (count == numberOfTests) {
            System.out.println("JUPI");
        } else {
            System.out.println("SCHEISSE");
        }

    }

}

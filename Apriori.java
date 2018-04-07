import java.util.*;

import java.io.*;

class Apriori {

    //ArrayList<ArrayList<Integer>> transactionlist = new ArrayList<ArrayList<Integer>>();

    public static void main(String[] args) {
        
        Apriori apriori = new Apriori();

        ArrayList<ArrayList<Integer>> transactionlist = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> T1 = new ArrayList<Integer>();
        T1.add(1);
        T1.add(3);
        T1.add(4);
        transactionlist.add(T1);

        ArrayList<Integer> T2 = new ArrayList<Integer>();
        T2.add(2);
        T2.add(3);
        T2.add(5);
        transactionlist.add(T2);

        ArrayList<Integer> T3 = new ArrayList<Integer>();
        T3.add(1);
        T3.add(2);
        T3.add(3);
        T3.add(5);
        transactionlist.add(T3);

        ArrayList<Integer> T4 = new ArrayList<Integer>();
        T4.add(2);
        T4.add(5);
        transactionlist.add(T4);


        int ITEM_COUNT = 0;
       
        int transactionCount;

        transactionlist.clear();
        Scanner sc = new Scanner(System.in);
     
        System.out.print("Enter number of transactions: ");
        transactionCount = sc.nextInt();
        System.out.println("Enter minimum support value: ");
        int support = sc.nextInt();
        System.out.println("Enter confidence percent: ");
        int confidence = sc.nextInt();

        for (int i = 1; i <= transactionCount; i += 1) {

            System.out.print("Enter transaction "+(i)+"(Comma Separated): ");
            String transaction = sc.next();
            String [] transactionItems = transaction.split("\\,");

            ArrayList<Integer> nthTransactionList = new ArrayList<Integer>(); 

            for (String item: transactionItems ) {
                int itemValue = Integer.parseInt(item);
                if (itemValue > ITEM_COUNT) {
                    ITEM_COUNT = itemValue;
                }
                nthTransactionList.add(itemValue);

            }
            transactionlist.add(nthTransactionList);
        }

        ArrayList<ArrayList<Integer>> first_set = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> associationRules = new ArrayList<String>();
        ArrayList<Integer> supports = new ArrayList<Integer>();
        ArrayList<Double> confidences = new ArrayList<Double>();

       
        for (int item = 1; item <= ITEM_COUNT; item += 1) {

            ArrayList<Integer> candidate = new ArrayList<>();
            candidate.add(item);

            if ( apriori.countOccourance(candidate, transactionlist) >= support ) {
                first_set.add(candidate);
            }
        }

        System.out.println("First List: "+ first_set);

         do {

           first_set = apriori.createCandidates(first_set);
           
           ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();

           for (ArrayList<Integer> candidate: first_set) {

                if (apriori.countOccourance(candidate, transactionlist) >= support) {

                    temp.add(candidate);

                }
           }
           
           first_set = temp;

           System.out.println("Updated List: "+first_set);

        }   while ( first_set.size() > 1 && first_set.get(0).size() <= 2 );


        System.out.println("\nAssociation rules are\n");

        for (ArrayList<Integer> frequentSet: first_set) {

            apriori.generateRule(frequentSet, transactionlist, associationRules, supports, confidences);

        }

        ArrayList<ArrayList<String>> finalRules = new ArrayList<ArrayList<String>>();

        System.out.println("  Rule    Support   Confidence(%)");     
        for (int i = 0; i < associationRules.size(); i += 1) {
            System.out.println(associationRules.get(i) + "      " + supports.get(i) + "         " + confidences.get(i) );

            if (confidences.get(i) > confidence) {
                ArrayList<String> rule = new ArrayList<String>();
                rule.add( associationRules.get(i));
                rule.add(""+supports.get(i));
                rule.add(""+confidences.get(i));

                finalRules.add(rule);
            }
        }
       System.out.println("Final rules are:");
        for (ArrayList<String> rule: finalRules) {
            System.out.println(rule);
        }
    }

    int countOccourance(ArrayList<Integer> candidate, ArrayList<ArrayList<Integer>> transactions) {
        
        int count = 0;

        for (ArrayList<Integer> transaction : transactions ) {
            int matches = 0;

            for (int item : candidate) {


                if (transaction.contains(item)) {
                    matches += 1;
                    
                }
            }

            if (matches == candidate.size()) {
                count += 1;
            }
        }

        return count;
    }

    ArrayList<ArrayList<Integer>> createCandidates(ArrayList<ArrayList<Integer>> candidates) {

        ArrayList<ArrayList<Integer>> newCandidates = new ArrayList<ArrayList<Integer>>();

        int newCandidateSize = candidates.get(0).size() + 1;

        for (int i = 0; i < candidates.size(); i++) {
            for (int j = 1 + i; j < candidates.size(); j++ ) {

            
                ArrayList<Integer> firstCandidate = candidates.get(i); 
                ArrayList<Integer> secondCandidate = candidates.get(j);
                                
                Set<Integer> set = new HashSet<Integer>();
                set.addAll(firstCandidate);
                set.addAll(secondCandidate);
                ArrayList<Integer> combined = new ArrayList<Integer>(set);

                if (!newCandidates.contains(combined) && combined.size() == newCandidateSize) {
                    newCandidates.add(combined);
                }
            }

        }

        System.out.println("New Candidates: "+ newCandidates);
        
        return newCandidates;
        
    }

    void generateRule(ArrayList<Integer> frequentSet,ArrayList<ArrayList<Integer>> transactions, ArrayList<String> associationRules,ArrayList<Integer> supports, ArrayList<Double> confidences ) {
        
        Apriori apriori = new Apriori();

        if (frequentSet.size() == 2) {

            for (int i = 0; i < 1; i++) {
                
                int second = 0;

                if (i == 0 ) {
                    second = 1;

                    String rule = frequentSet.get(i) + "=>" + frequentSet.get(second);
                    int support = apriori.countOccourance(frequentSet, transactions);

                    ArrayList<Integer> subset = new ArrayList<Integer>();
                    subset.add(frequentSet.get(i));
                    Double confidence = support / (double)apriori.countOccourance(subset, transactions);

                    associationRules.add(rule);
                    supports.add(support);
                    confidences.add(confidence);
                }
            }

        } else {

            int frequentSetSupport = apriori.countOccourance(frequentSet, transactions);

            for (int i = 0; i < 3; i += 1) {

                ArrayList<Integer> set1 = new ArrayList<Integer>();
                ArrayList<Integer> set2 = new ArrayList<Integer>();

                for (int j = 0; j < frequentSet.size(); j += 1 ) {
                    if (j == i) {
                        set2.add(frequentSet.get(j));
                        
                    } else {
                        set1.add(frequentSet.get(j));
                    }
                }
                
                int set1Support = apriori.countOccourance(set1, transactions);
                int set2Support = apriori.countOccourance(set2, transactions);

                String rule2 = set2.get(0) +"=>"+ apriori.join(set1);
                Double confidence2 = frequentSetSupport / (double)set2Support;
                confidence2 = (double)Math.round( confidence2 * 10000 )/100;

                associationRules.add(rule2);
                supports.add(frequentSetSupport);
                confidences.add(confidence2);

                String rule1 = apriori.join(set1) + "=>" + set2.get(0);
                Double confidence1 = frequentSetSupport / (double)set1Support;
                confidence1 = (double)Math.round( confidence1 * 10000 )/100;

                associationRules.add(rule1);
                supports.add(frequentSetSupport);
                confidences.add(confidence1);

            }
        }


    }

    String join(ArrayList<Integer> items) {

        String joined = ""+items.get(0);

        for (int i = 1; i < items.size(); i += 1) {

            joined += "^"+ items.get(i);
        }
        
        return joined;
    }
}
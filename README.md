# Apriori (Association Mining Java)
This is a JAVA implementation of Apriori Association Mining. You will be able to see different association rules too.

#### Number of iterations stops after associations of 3 elemnts are formed

>You  can change this by ediditng the do ... while condition
```
while ( first_set.size() > 1 && first_set.get(0).size() <= 2 );
// first_set.get(0).size() <= SIZE OF DESIRED ASSOCIATION
```

#### Minimum support is provided as number of occourance and not percent 
#### Confidence is provided as percent

![Full Output](https://drive.google.com/open?id=1A1jF81lOYWvvwkg52877YtFuhr6NjEiB)

![Candidates](https://drive.google.com/open?id=1lgsuN2NLAG4dljomZWbOC29QBQ7ugh1b)

![Rules](https://github.com/parthv21/Apriori-Association-Mining-Java-/blob/master/Outputs/Rules.png)

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

[Full Output](https://imgur.com/ndzpIJP)

[Candidates](https://imgur.com/5yyZvNy)

[Rules](https://imgur.com/PGpFko8)

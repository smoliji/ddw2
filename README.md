#DDW Homework 2

Java application for calculating PageRank, optimized for handlig large graphs using sparse vectors.

*What are these files?* It is a Netbeans directory structure.  Source codes can be found in [src](https://github.com/smoliji/ddw2/tree/master/src).

  * test1|2|3.txt - small testing webgraphs
  * v2-hostgraph_weighted.graph-txt - large graph (cca 200k webpages)
  * full_hostids.txt - list of hostnames
  * pageRanks.txt - page ranks result
  * times.txt - measured times of each step of the program    

## HowTo

Run program with `java -jar ddw-hw2 graphFile iterations dampingFactor`. Don't forget to provide file `full_hostids.txt` with hostnames (one per line).

As a result will be `pageRanks.txt` with resulting PageRanks, `times.txt` with measured times and `log.txt`, where the program puts L1 norm of differences between current and previous PR-vector.
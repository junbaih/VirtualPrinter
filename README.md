# miniOS
A mini OS demonstrating IO concurrency

OS reads from USERs input, writes the input to the disk and prints data to PRINTERs concurrently. 

*.save A* means that user A issues a write commmand, and the following lines are the data to write, until reach *.end*. *. print A* will print A's data to an available printer.

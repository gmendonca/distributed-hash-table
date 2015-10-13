#!/bin/bash

build()
{
	ant clean && ant compile && ant jar
}

run_openbench()
{
	java -jar build/OpenBench.jar $1 $2
}

run_client()
{
	java -jar build/Client.jar $1 $2 $3
}

run_bench()
{
	java -jar build/Benchmarking.jar $1 $2
}

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

help()
{
	echo "        "
	echo "        "
	echo "        run_openbench - Run Benchmarking leaving the socket open"
	echo "        run_client - Run a Client and Server with an user interface"
	echo "        run_bench - Run Benchmarking closing and opening the socket"
	echo "        "
	echo "        "
}

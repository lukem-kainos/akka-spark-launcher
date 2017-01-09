## Synopsis

A quick POC for the CDC. Specifically to test the use of an AKKA actor system in manipulating files in HDFS and running spark jobs from an external scala/akka program.

## Installation


I'm running the application outside the cloudera quickstartVM. To do so I needed to create a host-only adapter that allowed me to access the ports inside the virtual machine. To might need to do something similar. Neil Richardson showed me how. 

## Warnings

* the core-site.xml and hdfs-site.xml files have been copied and pasted into this project. They already reside in the cloudera vm.

I've changed the line 
from:
 <value>hdfs://quickstart.cloudera:8020</value>
 to:
 <value>hdfs://localhost:8020</value>
 so that I can run the application from outside the VM

 I also found and replaced quickstart.cloudera with localhost in hdfs-site.xml but I don't think that for this small purpose I needed to.
 
 <--- For the problem outtlined below I first added a new user, but this was not needed.
 Instead I used the line:
 * System.setProperty("HADOOP_USER_NAME", "cloudera") // I'm just testing this out TODO <- remove it

 
 I'm trying to run this on the cloudera vm:
 and I'm getting:
 org.apache.hadoop.ipc.RemoteException(org.apache.hadoop.security.AccessControlException): Permission denied: user=lukem, access=WRITE, inode="/user":hdfs:supergroup:drwxr-xr-x

 sudo -u hdfs hadoop fs -mkdir /user/lukem
 sudo -u hdfs hadoop fs -chown lukem /user/lukem


    <property>
        <name>dfs.client.use.datanode.hostname</name>
        <value>true</value> <---- changed false to true here
    </property>

<!---

## Code Example

Show what the library does as concisely as possible, developers should be able to figure out **how** your project solves their problem by looking at the code example. Make sure the API you are showing off is obvious, and that your code is short and concise.

## Motivation

A short description of the motivation behind the creation and maintenance of the project. This should explain **why** the project exists.

## Tests

You should be so lucky

## Contributors

Luke Matthews

## License

All rights reserved

---> 

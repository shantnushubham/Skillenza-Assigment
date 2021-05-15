
### Getting Started with Spring Backend

Dependencies:

*Java (SE) 8*, _Maven_ and _MongoDB_

Download Maven (Binary) from:
https://downloads.apache.org/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.zip

Unzip the .zip file and add to your path `%maven-unzipped-folder-path%/bin`
For example: `C:\Program Files\Maven\bin`

Add a user variable called `JAVA_HOME` and set it to your Java 8 installed folder. Example: `C:\Program Files\Java\jdk1.8.0_291`

Download and install MongoDB from:
https://www.mongodb.com/try/download/community

## Starting Project
Clone the project and go the project path on your terminal and execute:

    mvn clean install

Now go to `%project-path%/target`on your terminal and execute:

    java -jar spring-boot-skillenza-0.0.1-SNAPSHOT.jar

For example: `D:\A\Self\Skillenza-Assigment\sping-backend\target>java -jar spring-boot-skillenza-0.0.1-SNAPSHOT.jar
`
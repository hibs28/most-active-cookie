# Most Active Cookie Checker (MACC)
Installation and usage manual for the MACC console application.

# Table of Contents
1. [Installation](#installation)
2. [Usage](#usage)
3. [Development Notes](#development-notes)
   1. [Assumptions](#assumptions)
   2. [Improvements](#improvements)

## Installation
MACC is a jvm console app which requires your computer to have a configured JRE (java runtime environment). This tutorial assumes you are running on a Mac machine.

#### Installing the JRE
Running the application has two prerequisites: installing the JRE and configuring Windows environmental variables to know the path to the JRE.

Step 1. Obtain a JDK (java development kit, which includes the JRE) from the official oracle jdks  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html (at time of writing the newest one is jdk-16.0.1_windows-x64_bin )
Step 2. Check in browser downloads or finder to find the .dmg file and double click it.
Step 3. This will display the installion screen. 
Step 4. Continue following the steps on the screen and click Install.
Step 5. This may ask for admin access, please enter the Administration username and password. A confirmation window will appear once installed
    
To confirm if it has been successfully installed you can check two ways:

1. The JDK is installed in /Library/Java/JavaVirtualMachine
         Example : /Library/Java/JavaVirtualMachines/jdk-17.jdk/

2. Verify that the path is correct by opening a terminal and typing java -version. 
A response such as this would indicate that the environment is configured correctly.

java 17.0.10 2024-01-16 LTS
Java(TM) SE Runtime Environment (build 17.0.10+11-LTS-240)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.10+11-LTS-240, mixed mode, sharing)

For more information on how to install a JDK: https://docs.oracle.com/en/java/javase/21/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE

## Usage

### Terminal

Navigate to your terminal. Set the directory to the location of the .jar file

Example if it's located on the desktop 
`cd ~/desktop/dist`

Then you can run the following command to check the most active cookie

`java -jar target/most-active-cookie-1.0-SNAPSHOT-jar-with-dependencies.jar -f "logFiles/cookie_log.csv" -d 2018-12-09`

Example output:
````
Most active cookies for 2018-12-09:
AtY0laUfhglK3lC7
````

You can bring this manual up for a reminder while by executing the command without any of the arguments

`java -jar target/most-active-cookie-1.0-SNAPSHOT-jar-with-dependencies.jar`

```
usage: Command Option:
 -d,--dateString <d>   Date format YYYY-MM-DD
 -f,--filename <f>     Path to CSV log file which contains:
                       <cookies,timestamp>
                       Example:
                       -f /path/to/file
                       -f "/path/to/file"
                 
```

You can bring this manual up for a reminder while by executing the command without any of the arguments
`java -jar target/most-active-cookie-1.0-SNAPSHOT-jar-with-dependencies.jar`

Useful macOS commands:
1. clear -> clear terminal of logs
2. ls -> list the current contents of the directory you are in (files and subdirectories)
3. cd -> navigate to a directory. For full usage, please refer to the official documentation
4. typing the start of a command\directory and pressing [TAB] attempts to autocomplete your statement


## Development Notes
### Assumptions
1. Date format only to be YYYY-MM-DD.
2. Only accepts CSV files.
3. Small file logs.
4. The cookies are sorted in DESC order by date.
5. CLI validation on date and file format.

### Improvements
1. The code currently is only suitable for smaller log files, so if it had a log file with 100+ cookies. It would either crash or be slow. Instead of reading every line it can stop after it reaches its desired date (if assumption 4 is still valid). 
2. Add more assertions to the unhappy path test. Issue while development was to check the error messages as it would perform `System.exit`.
3. To the readme add how to run from different OS like Windows.
4. Allow different date formats in the command line.
5. Instead of using the jar file, create an exe file instead. (Fix the pom.xml file)
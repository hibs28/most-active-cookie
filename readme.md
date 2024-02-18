# Most Active Cookie Checker (MACC)
Installation and usage manual for the MACC console application.

## Installation
EDM is a jvm console app which requires your computer to have a configured JRE (java runtime environment). This tutorial assumes you are running on a Windows machine.

#### Installing the JRE
Running the application has two prerequisites: installing the JRE and configuring Windows environmental variables to know the path to the JRE.

You can:
1. (Method A) use a compressed zip which we extract (no install required)
2. (Method B) use the exe which installs and attempts to set the path automatically. 


##### Method A

Step 1. Obtain a JDK (java development kit, which includes the JRE) from the official oracle jdks (Windows x64 Installer zip) https://www.oracle.com/java/technologies/javase-jdk16-downloads.html (at time of writing the newest one is jdk-16.0.1_windows-x64_bin )
Step 2. Extract it somewhere on your PC. Preferably on your system drive (usually the C:\\ drive)
Step 3. (requires admin access) Go to Start Menu > Edit the System Environment Variables
Step 4. From the new window, select Environment Variables
Step 5. (personal installation) under the section "User variables for [user name]" find the variable Path and select Edit
Step 6. In the new window that has opened, create an entry by selecting New and writing the full path to bin folder of the jre. 
    
        Examples:

        if you've used the default install directory for the exe jre, it should be similar to C:\Program Files\Java\jdk-16.0.1\bin
        if you use the .zip jdk, provide the full path to the extracted jdk's bin folder. If you've extracted it in the Documents folder, it should be similar to C:\Users\[user name]\Documents\jdk-16.0.1\bin

Step 7: Verify that the path is correct by opening a terminal (CMD or PowerShell from the start menu) and typing java -version. 
A response such as this would indicate that the environment is configured correctly.

java 16.0.1 2021-04-20
Java(TM) SE Runtime Environment (build 16.0.1+9-24)
Java HotSpot(TM) 64-Bit Server VM (build 16.0.1+9-24, mixed mode, sharing)

If that is not successful, start from Method A Step 5, but instead of configuring the User variables, find the Path variable in the section "System Variables".


##### Method B

Step 1. Obtain jdk from the official oracle jdks (Windows x64 exe)
Step 2. Install the exe (the default directory C:\Program Files\Java\jdk-16.0.1)
Step 3. Restart your PC
Step 4. Validate that the installation has successfully added the Path variable by doing the same thing as Method A Step 7. There is a chance automatically adding the path variable will be unsuccessful due to company policy restrictions on your machine. If that is the case, follow the manual process of adding the path variable from Method A Step 3.


## Usage

Navigate to your terminal. If on windows, search for CMD or PowerShell in the start menu search.
Once in the terminal window, navigate to 

```
Usage: 
    --filename <f>   Attempt to scan a page to assess if there is an image before parsing it.
                               Note: this speeds up the process but some diagrams might be missed.
                               Default is off
    --input <i>                Path to directory containing PDF documents to process.
                               Examples:
                               --input \path\to\input
                               --input \path\to\input
                               --input "\input with spaces in path\folder"
                               --input .\relative\path
    --output <o>               Path to directory where the results will be saved. Note: Each document parsed will create its own subfolder.
                               Example (path syntax is identical to --input):
                               --output= \path\to\output
                               Default is same as input path
    --multithreaded            (experimental) Enable processing pdfs in
                               parallel. Might result in a speedup, but
                               has limitations on Windows machines (might
                               result in documents not being saved).
    --extract-sections         Extract all sections from the pdf and export them as separate .txt files
```

You can bring this manual up for a reminder while by executing .\edm without any of the arguments

Useful commands:
1. csl -> clear terminal of logs
2. dir -> list the current contents of the directory you are in (files and subdirectories)
3. cd -> navigate to a directory. For full usage, please refer to the official documentation https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/cd
4. typing the start of a command\directory and pressing [TAB] attempts to autocomplete your statement
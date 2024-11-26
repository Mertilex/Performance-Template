# Introduction
This project provides a comprehensive template solution for performance, load, and stress testing, utilizing Gatling and Java to deliver scalable and efficient test execution. Designed for developers and QA engineers, the template simplifies the process of simulating real-world user interactions with high concurrency and ensures system reliability under varying load conditions. With customizable test scenarios, reusable components, and detailed reporting, it serves as a robust starting point for testing the performance of web applications and APIs.

## How to start
1. Clone repository
2. Install Java JDK
3. Open Repository in any IDE. Strongly recommend using VSCode as it support plugins compatible with JAVA and Gatling.
4. Edit file: /gatling-charts-highcharts-bundle-3.9.5/user-files/simulations/Configs/GlobalConfig.java
5. Set values to each variable that meet your environment.
6. Open Terminal
7. To Compile and execute tests use this command: `cd /home/fp/Desktop/Perf-Template/gatling-charts-highcharts-bundle-3.9.5/bin` then `sh gatling.sh`
8. Follow instructions displayed in terminal.

![image](https://github.com/user-attachments/assets/11baea46-cc8f-49a9-b64c-65e723890419)



# Key features
## Multiple environment support
The solution was designed in a way that unlimited number of environments are supported. Everything that is needd to work with the solution is located in this repository. 
Including `Lib` files that were added intentionally due too Maven issue.

## Test recorder
The solution includes a built-in Gatling Test Recorder that simplifies the creation of basic test scenarios. By capturing real user interactions, the recorder generates a skeleton test script tailored to your application's workflows. Once the recording session is closed, the recorder automatically generates properly formatted Scala code, which can be further customized to fit advanced test requirements. This feature enables quick and easy scenario creation, even for users new to performance testing.

This command will run it: `sh recorder.sh`
![image](https://github.com/user-attachments/assets/ae85bbcb-4479-4beb-8780-20a6ef05cb46)


# Logs Configuration

The solution supports multiple logging levels, offering flexibility in the amount of detail captured during test execution. Starting from DEBUG, which provides essential diagnostic information, to TRACE, which delivers the most granular details, each level offers progressively more insights into the system's behavior. This configuration ensures that you can tailor logging to suit your needs—whether for troubleshooting specific issues or conducting in-depth analysis—without overwhelming your logs with unnecessary data.

It can be set in `gatling.conf` file
![image](https://github.com/user-attachments/assets/f5213514-c5f2-489f-807c-58ed2aa91b85)



# Project structure
Files in the projects are divided into two groups. User files and Gatling Core files.
Only user files should be edited for purpose of performance testing. All of them are located in directory `user-files`

### Pages
All files related to simulation steps should be placed in directory that is corelated with application business flow. For example: If you add steps that are located in the application here: Main page → Clients → Payments then the file containing steps related to Payments should be located here: 
\gatling-charts-highcharts-bundle-3.9.5\user-files\simulations\Pages\MainPage\Clients\Payments\PaymentSteps.java

### Feeders
All classes that fetch data from the Database should be located in this directory:
\gatling-charts-highcharts-bundle-3.9.5\user-files\simulations\Feeders

The directory might contain subfolders if needed. Try to keep Feeders as small as possible and divide them into multiple classes if necessary.

### FrameworkCore
This directory should contain files that are directly related to the framework, such as: HTTPDefaults that configures API calls made by Gatling.

### Configs
This folder should contain all config files that are created in the Framework, especially Global config and configs containing configuration for specific  environments

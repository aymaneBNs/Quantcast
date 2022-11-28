# Getting Started

### Executing The Program through the Console 

 1- Run the Main class `MostActiveCookies`
 
 2- Tape the following command in the console 

 ```
-f cookie_log.csv -d 2018-12-09
```
 - -f : is the file name  (make sure that the file exists in the project directory)
 
 - -d : is the date  (make sure that the date format is correct `yyyy-MM-dd`)
 
 
 ### Exceptions
 the following exceptions could be thrown while executing the programm 
 
`ConsoleReadingException` : Exception Related to Console Input, the fileName -f and/or date -d are not correct .
`CookieFileReadingExceptionè` : Exception Related Reading the Cookies Log File, the file doesn't exist or the content is corrupted.


 ### Build The Project 
to build the project and deploy locally (generate a jar file in target) 

 ```
mvn clean install 
```

please make sure that maven is install in you machine 

 ### Logging Level

By default the Log Level is set to Error . The configuration file can be found in `src/main/resources/log4j2.xml` (just a simple configuration file )








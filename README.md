# holiday-information
Holiday Information Service

The task was, to return next holiday after the given date that will happen on the same day in both countries. 
But free version of holidayapi.com offers only searching backward, and searching forward ("upcoming" parameter) is not available in free edition, regardless of whether the date is past or future.
Taking this into account, I created an application with property search.direction. 
When it is set to "previous" you can look for a common holiday back from the given date. By default this property is set to "previous". 
A forward search (using a commercial version of api) is enabled when search.direction property is set to "upcoming".
In application.properties you can also enter api key in api.key property. Default there is entered free key so you can use only backward searching.

Example request to my application should look like this:
POST holidays/find Content-Type: application/json body: {"countryCode1":"PL","countryCode2":"US","date":"2019-02-23"}
And the response will be:
HTTP 200 OK {"date":"2019-01-06","name1":"Święto Trzech Króli","name2":"Epiphany"} 
or HTTP 500 when there will be some error in the application or in the holidayapi.com
If I have more time I could create messages for the user that would inform about the type of error.

How to run:
-You need maven and Java 11 in your JAVA_HOME environment variable.
-In cmd console enter command: mvn clean package. It will generate jar in target directory.
-Go to target directory and run jar file by command: java -jar holiday-information-1.0.jar
-Now you can use application. Send POST request with correct body for example from SoapUI to http://localhost:8080/holidays/find .

--- Purpose of Application ---

Appointment Scheduling App

--- Scenario ---
You are working for a software company that has been contracted to develop a GUI-based scheduling desktop application.
The contract is with a global consulting organization that conducts business in multiple languages and has main offices
in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England. The consulting organization has
provided a MySQL database that the application must pull data from. The database is used for other systems, so its
structure cannot be modified.

The organization outlined specific business requirements that must be met as part of the application. From these
requirements, a system analyst at your company created solution statements for you to implement in developing the
application.

Your company acquires Country and First-Level-Division data from a third party that is updated once per year.
These tables are prepopulated with read-only data. Please use the attachment “Locale Codes for Region and Language” to
review division data. Your company also supplies a list of contacts, which are prepopulated in the Contacts table;
however, administrative functions such as adding users are beyond the scope of the application and done by your
company’s IT support staff. Your application should be organized logically using one or more design patterns and
generously commented using Javadoc so your code can be read and maintained by other programmers.


Author: Andrew Vigil
Contact: andrewvigil@live.com
Student Application Version: 1.0
Date: 6/10/23

--- IDE and Java Module Information ---
IntelliJ IDEA Community Edition 2021.1.3 x64
JDK-17.0.1
JavaFX-SDK-17.0.1

MySQL-connector: mysql-connector-java-8.0.27

--- Additional Report ---
Per requirement A3f, added an additional report to display total number of customers in scheduler.

--- Directions ---
As the program starts, a login screen is presented. The user will be required to have a valid username and password
that matches information in the mySQL database. From there the user will be brought to a main screen whee they can
navigate to the pages of Appointments, Customers, or Reports.
# testautomationtool
This is the senior project for Ozyegin University Computer Science department made by Ekin Insel and Ugur Temir.
This projet will be about test generation tool.
The first step that we did is parsing the code by using JavaParser and printing the names of the methods and parameters of those parameters and also the start line and finish line of those methods.
After than parsing the methods we created a class object from the class that will be tested. With this object we called all functions of the class which returns any object. And we had a list for storing those results for comparing in tests. These results would be expecteds for asserts.
We generated a test class with java poet and added asserts to that class with expected results and given input numbers and strings.
At the end of the project, the tool generate a test class with given inputs and when run the test all tests passes.

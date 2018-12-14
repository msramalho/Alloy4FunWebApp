# Alloy 4 Fun
Web application for Alloy. [Original Thesis](https://repositorium.sdum.uminho.pt/bitstream/1822/47719/1/Jos%C3%A9%20Manuel%20Costa%20Pereira.pdf)

## About
Alloy is a language for describing structures and a tool for exploring them.

Alloy4Fun is a Web platform that supports edditing and interpreting Alloy models through your browser in real time.

The latest version is available at [aws](http://ec2-52-36-177-8.us-west-2.compute.amazonaws.com/) or [uminho](http://alloy4fun.di.uminho.pt/). 

Alloy4Fun is being developed using the Meteor framework which is a full-stack JavaScript platform for developing

modern web and mobile applications.

## Prerequisites

1. Install **meteor**. You cant get it here: https://www.meteor.com/install
2. ~~Install **Apache Tomcat**: https://tomcat.apache.org~~
3. Install **Eclipse IDE for Java EE Developers** (or any other IDE capable of creating a Dynamic web Service, although in this study case Eclipse is the one being used): http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen2
4. Add **axis2-1.7.7** Runtime location to Ecplise:
    - Download [Axis2 Binary Distribution](https://axis.apache.org/axis2/java/core/download.html) and save it to a convenient folder
    - In eclipse: Window > Preferences > WebServices > Axis2 Preferences > [add axis2 folder location]



## Running web application
Installing **meteor.js** is pretty straight forward, just follow the steps in [their website](https://www.meteor.com/install).

To run the web app, go to the [Alloy4FunMeteor](Alloy4FunMeteor) folder and run:
```bash
meteor run
```
If you encounter problems like:
 * Failing to start MongoDB
 * Non-compatible package versions
 * New meteor versions available

Be sure to run `meteor npm i`, `meteor update` and keep an eye for additional recommended flags until you can simply do `meteor run` and get a message for the app running on [localhost:3000](http://localhost:3000/). 

## Running the web service:

  - Create a new Dynamic Web Project in Eclipse
  	- File > New > Dynamic Web Project
	- Project name: Alloy4Fun
	- Setup TargetRuntime:
		 - New Runtime > Apache TomCat v7.0
		 - Next
		 - Download and Install (wait and setup folder OR [download manually](https://tomcat.apache.org/download-70.cgi) to convenient folder and Hit "Browse" instead)
		 - Finish
	- Dynamic Web Module : 2.5
	- Configuration > modify > select Axis2 Web Services
	- Finish

- Eclipse Project Explorer > Alloy4Fun > JavaResources:
	- Create a new Package named **services** (Right Click > New > Package)
	- Drag here the two java files in the [Alloy4FunWebService/src/services](Alloy4FunWebService/Alloy4FunWebService/src/services) folder

- Download these jar files and add them to `Alloy4Fun/WebContent/WEB-INF/lib`:
	- [alloy4.2.jar](http://alloy.lcs.mit.edu/alloy/download.html)
	- [axis2-jaxws-1.7.7.jar](https://mvnrepository.com/artifact/org.apache.axis2/axis2-jaxws/1.7.7)
	- [javax.jws-3.1.2.jar](http://www.java2s.com/Code/Jar/j/Downloadjavaxjws312jar.htm)
	- [javax.ws.rs-api-2.0.jar](http://www.java2s.com/Code/Jar/j/Downloadjavaxwsrsapi20jar.htm)
	- [jaxws-rt.jar](http://www.java2s.com/Code/Jar/j/Downloadjaxwsrtjar.htm)
	- [jaxws-tools.jar](http://www.java2s.com/Code/Jar/j/Downloadjaxwstoolsjar.htm)
	- [jstl-1.2.jar](https://mvnrepository.com/artifact/javax.servlet/jstl/1.2)
	- [xmlschema-core-2.2.1.jar](https://mvnrepository.com/artifact/org.apache.ws.xmlschema/xmlschema-core/2.2.1)
	- [javax.json-1.0.jar](http://www.java2s.com/Code/Jar/j/Downloadjavaxjson10jar.htm)

- Create server:
	- Window > showView > servers 
		- new server > Tomcat V7.0 Server

- Right click at Alloy4Fun > new > other > Web Service :
	- Service Implementation: `services.AlloyService`
	- WebService runtime:  Apache axis2
	- finish

- Open `Alloy4Fun/WebContent/WEB-INF/services/AlloyService/META-INF/services.xml` in eclipse (text view)
	- Replace its content with the ones in this [services.xml](Alloy4FunWebService/Alloy4FunWebService/services.xml).
	
- Right click at Alloy4Fun > new > other > Web Service :
	- Service Implementation : `services.AlloyService`
	- WebService runtime :  Apache axis2
	- next > "use existing services.xml" > select the above in `Alloy4Fun/WebContent/WEB-INF/services/AlloyService/META-INF/services.xml`
	- next > Start Server > finish

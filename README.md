#Appium Support#
A set of tools to help in the process of creating automated mobile tests using Appium. Currently only Windows and Mac operating systems are supported.

[Dowbload the jar from Maven](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.genium-framework%22%20AND%20a%3A%22Appium-Support%22) or add the following to your pom.xml:

```
<dependency>
  <groupId>com.github.genium-framework</groupId>
  <artifactId>Appium-Support</artifactId>
  <version>1.0.3</version>
</dependency>
```

Current Supported Appium Server version: 1.4.0

##Changelog##
*1.0.4*
- You can define a custom timeout value for the server while starting it instead of the default 30 seconds.
- Changed the way to check if the server is running or not by sending an HTTP request and waiting for a response.
- Minor code changes and bug fixes.

*1.0.3*
- Fixed a bug with server running logic that caused the startServer method to go into an infinite loop.
- Minor code changes.

*1.0.2*
- Added support for Mac OS.

*1.0.1*
- Minor code changes.
- Adding unit tests to the project.

*1.0.0*
- Start a server instance with a set of arguments.
- Stop an already running server instance.
- Check if a server instance is running or not.

######Tested on environments######
- Windows 7 x86
- Windows 7 x64
- Mac OSX 10.9

Supported Java 1.5 and above

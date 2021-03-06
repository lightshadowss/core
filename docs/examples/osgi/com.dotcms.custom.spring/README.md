# README

This bundle plugin is an example of how to add Spring support to a bundle plugin, creates and registers a simple Spring Controller using a different Spring version that the one shipped with dotCMS in order to extend the reach of this example.

### How to build this example

To install all you need to do is build the JAR. to do this run 
```
./gradlew jar
```
This will build a jar in the build/libs directory

### To install this bundle:

Upload the bundle jar file using the dotCMS UI (*CMS Admin->Dynamic Plugins->Upload Plugin*).
	
### To uninstall this bundle:

Undeploy the bundle using the dotCMS UI (*CMS Admin->Dynamic Plugins->Undeploy*).


### How to create a bundle plugin with Spring support

In order to create an OSGI plugin, you must create a *META-INF/MANIFEST* to be included in the OSGI jar.
This file is being created for you by Gradle. If you need you can alter our config for this but in general our out of the box config should work.
The Gradle plugin uses BND to generate the Manifest. The main reason you need to alter the config is when you need to exclude a package you are including on your Bundle-ClassPath

In this *MANIFEST* you must specify (see the included plugin as an example):

* *Bundle-Name*: The name of your bundle
* *Bundle-SymbolicName*: A short an unique name for the bundle
* *Bundle-Activator*: Package and name of your Activator class (example: *com.dotmarketing.osgi.custom.spring.Activator*)
* *Bundle-ClassPath*: The Bundle-ClassPath specifies where to load classes and jars from from the bundle.
This is a comma separated list of elements to load (such as current folder,lib.jar,other.jar). (Example: ., lib/com.springsource.org.aopalliance-1.0.0.jar)
* *Import-Package*: This is a comma separated list of the names of packages to import. In this list there must be the packages that you are using inside your osgi bundle plugin and are exported and exposed by the dotCMS runtime.


### Beware (!)

In order to work inside the Apache Felix OSGI runtime, the import and export directive must be bidirectional.

As of dotcms 2.5.2 if you do not start with a **dotCMS/WEB-INF/felix/osgi-extra.conf** ALL packages will be exported for you. So there is nothing for you to do

The DotCMS must declare the set of packages that will be available to the OSGI plugins by changing the file: *dotCMS/WEB-INF/felix/osgi-extra.conf*.
This is possible also using the dotCMS UI (*CMS Admin->Dynamic Plugins->Exported Packages*).

Only after that exported packages are defined in this list, a plugin can Import the packages to use them inside the OSGI blundle.


## Components

### com.dotmarketing.osgi.custom.spring.ExampleController

Simple annotated Spring Controller

### com.dotmarketing.osgi.custom.spring.CustomViewResolver

Custom implementation of an Spring ViewResolver.

### com.dotmarketing.osgi.custom.spring.CustomView

Custom implementation of an Spring View.

### example-servlet.xml

Inside the *com.dotcms.custom.spring/src/main/resources/spring* folder is an Standard Spring configuration file where basically we enabled the support for anntotation-driven controllers and the Spring component-scan functionality.

### Activator

This bundle activator extends from *com.dotmarketing.osgi.GenericBundleActivator* and implements `BundleActivator.start()`.
Will manually register making use of the class *DispatcherServlet* our spring configuration file *spring/example-servlet.xml*.

* PLEASE note the `publishBundleServices( context )` call, this call is MANDATORY (!) as it will allow us to share resources between the bundle, the host container (dotCMS) and the Spring context.

________________________________________________________________________________________

## Testing

The Spring controller is registered under the url pattern **"/spring"** can be test it running and assuming your dotCMS url is *localhost:8080*:

* [http://localhost:8080/app/spring/examplecontroller/](http://localhost:8080/app/spring/examplecontroller/)
* [http://localhost:8080/app/spring/examplecontroller/Testing](http://localhost:8080/app/spring/examplecontroller/Testing)

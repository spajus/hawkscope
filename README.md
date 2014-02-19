# Hawkscope

Cross-platform pluggable menu based launcher

## Plugins

You can find plugins here: [spajus/hawkscope-plugins](https://github.com/spajus/hawkscope-plugins)

## Building Hawkscope

### Prequisites
To build Hawkscope executable jar, you need:
 * JDK 5+
 * Maven 2.0.6+

### Adding SWT native library to local Maven repository

Hawkscope uses Eclipse SWT native libraries for each os / arch. Library
dependency is automatically selected via Maven profiles in pom.xml.
Current profiles are: win32, win64, linux32, linux64.

To install SWT native library to local Maven repository, find out your
system architecture (i.e. if you're on Windows XP, it is most probably 32 bit,
so your architecture would be win32), then install the appropriate jar from
Hawkscope project's /lib folder. Refer to lib/install.txt to find the
mvn install:install-file line that is suitable for you.

If you want to build Hawkscope and there is no profile for your operating system
and CPU architecture, you can create new Maven profile in pom.xml, download your
SWT implementation from http://www.eclipse.org/swt/ and install it. Please
feel free to contribute new profiles.

### Packaging Hawkscope + dependencies into executable JAR

To build an executable jar, simply run:

```console
$ mvn clean package
```

The jar will be in target/ folder, named: `hawkscope-(version)-(os)-(arch)-jar-with-dependencies.jar`

## Running Hawkscope

### Running Hawkscope from source folder
Running directly from source is good for debugging purposes. In console, execute:

```console
$ mvn clean compile exec:java -Dexec.mainClass=com.varaneckas.hawkscope.Launcher -e
```

### Running Hawkscope from executable JAR

Doubleclick the executable jar, or execute in console:

```console
$ java -jar hawkscope-<version>.jar
```

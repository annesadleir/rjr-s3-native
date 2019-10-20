# rjr-s3-native

Trying to get an AWS Lambda running that a) has been compiled with GraalVM native-image and
b) can access an S3 bucket using the AWS Java SDK (v2).

## compiling using GraalVM native-image
This project requires [rjr-aws-lambda-base](https://github.com/annesadleir/rjr-aws-lambda-base) 
to be available to provide the Lambda runtime, so check it out and run `mvn clean install` on it.

Now compile this repo normally using maven, i.e.  
`mvn clean package`  

Then use GraalVM's `native-image` command to create the native executable:
native-image --enable-http --enable-https --enable-url-protocols=http,https --enable-all-security-services -H:+JNI -cp ./target/rjr-s3-native-1.0.jar -Djava.net.preferIPv4Stack=true -H:Name=rjr-s3-native -H:Class=uk.co.littlestickyleaves.lambda.S3LambdaMain -H:+ReportUnsupportedElementsAtRuntime --allow-incomplete-classpath -H:+TraceClassInitialization --no-fallback --initialize-at-build-time

NB The config in the `META-INF/native-image` folder of this repo was created using the [Tracing Agent](https://medium.com/graalvm/introducing-the-tracing-agent-simplifying-graalvm-native-image-configuration-c3b56c486271) 
against the `TracingAgentPath` class.

That `native-image` command is working for me but with a warning about a NoClassDefFoundError:  
`Warning: class initialization of class io.netty.util.internal.logging.Log4JLogger failed with exception java.lang.NoClassDefFoundError: org/apache/log4j/Priority. This class will be initialized at run time because option --allow-incomplete-classpath is used for image building. Use the option --initialize-at-run-time=io.netty.util.internal.logging.Log4JLogger to explicitly request delayed initialization of this class.`

Ignoring that error, I can then create a zip to deploy.  It requires three? things
* the `bootstrap` file from the root of this repo.  This is called by AWS to start the custom runtime.
It needs to be made executable e.g. `chmod 777 bootstrap`
* the native executable made by the native image tool, also made executable using chmod
* `libsunec.so`, taken from GraalVM at `/jre/lib/amd64` (see [PR on libsunec.so](https://github.com/oracle/graal/pull/1604/files))

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

## deploying to AWS
Ignoring that error, I can then create a zip to deploy.  It requires three? things
* the `bootstrap` file from the root of this repo.  This is called by AWS to start the custom runtime.
It needs to be made executable e.g. `chmod 777 bootstrap`
* the native executable made by the native image tool, also made executable using chmod
* `libsunec.so`, taken from GraalVM at `/jre/lib/amd64` -- see [PR on libsunec.so](https://github.com/oracle/graal/pull/1604/files) 
* `cacerts` -- according to [PR on libsunec.so](https://github.com/oracle/graal/pull/1604/files)
I shouldn't need this if I've taken the `libsunec.so` from GraalVM, but I was still getting the empty truststore error.

`zip rjr-s3-native.zip bootstrap rjr-s3-native libsunec.so`

You need the AWS CLI available, and to have set it up using the `aws configure` command. 
See [Installing the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html) 
and [Quickly configuring the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html#cli-quick-configuration)  
You also need an AWS IAM role available which has lambda permissions and S3 permissions: see [Lambda Execution Roles](https://docs.aws.amazon.com/lambda/latest/dg/lambda-intro-execution-role.html). 
(I just attached the AWS-managed policies called `AmazonS3FullAccess` and `AWSLambdaBasicExecutionRole`.) 
You'll need the `arn` for when you create the function.  

This command creates the function:     
`aws lambda create-function --function-name rjr-s3-native --zip-file fileb://~/linuxWorkarea/rjr-s3-native/rjr-s3-native.zip --handler function.handler --runtime provided --role arn:aws:iam::ROLE_ARN_HERE`  
If it fails because the function already exists, you need to do  
`aws lambda delete-function --function-name rjr-s3-native`

At this point you could do with an S3 bucket with a file on with several lines of text. 
Then you can invoke it like this:
`aws lambda invoke --function-name rjr-s3-native --payload '{"bucket":"bucketName","fileKey":"fileName","lines":2}' response.txt`  
It will return json that says what happened, and put any result into `result.txt`. 
To see full details you'll need to look at the CloudWatch logs, which is most easily done by logging
on to the AWS web console, looking at this specific lambda, and choosing the Monitoring tab.


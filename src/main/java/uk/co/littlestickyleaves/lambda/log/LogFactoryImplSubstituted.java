package uk.co.littlestickyleaves.lambda.log;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.commons.logging.impl.SimpleLog;

// https://github.com/DmPanov/graal-vs-apache-httpclient/blob/master/src/main/java/Main.java
// see https://github.com/oracle/graal/issues/715
@SuppressWarnings("unused")
@TargetClass(LogFactoryImpl.class)
final class LogFactoryImplSubstituted {
    @Substitute
    private Log discoverLogImplementation(String logCategory) {
        return new SimpleLog(logCategory);
    }
}
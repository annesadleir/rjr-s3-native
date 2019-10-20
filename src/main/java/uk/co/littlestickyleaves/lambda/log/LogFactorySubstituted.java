package uk.co.littlestickyleaves.lambda.log;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;

// https://github.com/DmPanov/graal-vs-apache-httpclient/blob/master/src/main/java/Main.java
// see https://github.com/oracle/graal/issues/715
@SuppressWarnings("unused")
@TargetClass(LogFactory.class)
final class LogFactorySubstituted {
    @Substitute
    protected static LogFactory newFactory(final String factoryClass,
                          final ClassLoader classLoader,
                          final ClassLoader contextClassLoader) {
        return new LogFactoryImpl();
    }
}

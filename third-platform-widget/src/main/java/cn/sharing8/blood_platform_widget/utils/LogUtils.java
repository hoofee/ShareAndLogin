package cn.sharing8.blood_platform_widget.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import cn.sharing8.blood_platform_widget.PlatformInitConfig;

/**
 * Log工具，类似android.util.Log。 tag自动产生，格式:
 * customTagPrefix:className.methodName(Line:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(Line:lineNumber)。
 * http://blog.csdn.net/finddreams
 */
public class LogUtils {

    public static        String  customTagPrefix = "hufei"; // 自定义Tag的前缀，可以是作者名
    private static final boolean isSaveLog       = false; // 是否把保存日志到SD卡中
    public static final  String  ROOT            = Environment.getExternalStorageDirectory()
            .getPath() + "/blood/"; // SD卡中的根目录
    private static final String  PATH_LOG_INFO   = ROOT + "info/";

    private LogUtils() {
    }

    //是否打印Log的总开关,true为打印，false为不打印
    public static boolean isPrintLog = PlatformInitConfig.IsShowLog;

    // 容许打印日志的类型，默认是true，设置为false则不打印
    public static boolean allowD   = isPrintLog;
    public static boolean allowE   = isPrintLog;
    public static boolean allowI   = isPrintLog;
    public static boolean allowV   = isPrintLog;
    public static boolean allowW   = isPrintLog;
    public static boolean allowWtf = isPrintLog;

    public static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = caller.getClassName(); // 获取到类名
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(),
                caller.getLineNumber()); // 替换
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":"
                + tag;
        return tag;
    }

    /**
     * 自定义的logger
     */
    public static CustomLogger customLogger;

    public interface CustomLogger {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }

    public static void d(Object content) {
        if (!allowD)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.d(tag, content.toString());
        } else {
            Log.d(tag, content.toString());
        }
    }

    public static void d(Object content, Throwable tr) {
        if (!allowD)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.d(tag, content.toString(), tr);
        } else {
            Log.d(tag, content.toString(), tr);
        }
    }

    public static void e(Object content) {
        if (!allowE)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "null";
        }
        if (customLogger != null) {
            customLogger.e(tag, content.toString());
        } else {
            Log.e(tag, content.toString());
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, content.toString());
        }
    }

    public static void e(Object content, Throwable tr) {
        if (!allowE)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.e(tag, content.toString(), tr);
        } else {
            Log.e(tag, content.toString(), tr);
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, tr.getMessage());
        }
    }

    public static void i(Object content) {
        if (!allowI)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "null";
        }
        if (customLogger != null) {
            customLogger.i(tag, content.toString());
        } else {
            Log.i(tag, content.toString());
        }

    }

    public static void i(Object content, Throwable tr) {
        if (!allowI)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.i(tag, content.toString(), tr);
        } else {
            Log.i(tag, content.toString(), tr);
        }

    }

    public static void v(Object content) {
        if (!allowV)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.v(tag, content.toString());
        } else {
            Log.v(tag, content.toString());
        }
    }

    public static void v(Object content, Throwable tr) {
        if (!allowV)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.v(tag, content.toString(), tr);
        } else {
            Log.v(tag, content.toString(), tr);
        }
    }

    public static void w(Object content) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.w(tag, content.toString());
        } else {
            Log.w(tag, content.toString());
        }
    }

    public static void w(Object content, Throwable tr) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.w(tag, content.toString(), tr);
        } else {
            Log.w(tag, content.toString(), tr);
        }
    }

    public static void w(Throwable tr) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, tr);
        } else {
            Log.w(tag, tr);
        }
    }

    public static void wtf(Object content) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.wtf(tag, content.toString());
        } else {
            Log.wtf(tag, content.toString());
        }
    }

    public static void wtf(Object content, Throwable tr) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (content == null) {
            content = "";
        }
        if (customLogger != null) {
            customLogger.wtf(tag, content.toString(), tr);
        } else {
            Log.wtf(tag, content.toString(), tr);
        }
    }

    public static void wtf(Throwable tr) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, tr);
        } else {
            Log.wtf(tag, tr);
        }
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void point(String path, String tag, String msg) {
        if (isSDAva()) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("",
                    Locale.SIMPLIFIED_CHINESE);
            dateFormat.applyPattern("yyyy");
            path = path + dateFormat.format(date) + "/";
            dateFormat.applyPattern("MM");
            path += dateFormat.format(date) + "/";
            dateFormat.applyPattern("dd");
            path += dateFormat.format(date) + ".log";
            dateFormat.applyPattern("[yyyy-MM-dd HH:mm:ss]");
            String time = dateFormat.format(date);
            File file = new File(path);
            if (!file.exists())
                createDipPath(path);
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file, true)));
                out.write(time + " " + tag + " " + msg + "\r\n");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据文件路径 递归创建文件
     *
     * @param file
     */
    public static void createDipPath(String file) {
        String parentFile = file.substring(0, file.lastIndexOf("/"));
        File file1 = new File(file);
        File parent = new File(parentFile);
        if (!file1.exists()) {
            parent.mkdirs();
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A little trick to reuse a formatter in the same thread
     */
    private static class ReusableFormatter {

        private Formatter     formatter;
        private StringBuilder builder;

        public ReusableFormatter() {
            builder = new StringBuilder();
            formatter = new Formatter(builder);
        }

        public String format(String msg, Object... args) {
            formatter.format(msg, args);
            String s = builder.toString();
            builder.setLength(0);
            return s;
        }
    }

    private static final ThreadLocal<ReusableFormatter> thread_local_formatter = new ThreadLocal<ReusableFormatter>() {
        protected ReusableFormatter initialValue() {
            return new ReusableFormatter();
        }
    };

    public static String format(String msg, Object... args) {
        ReusableFormatter formatter = thread_local_formatter.get();
        return formatter.format(msg, args);
    }

    public static boolean isSDAva() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                || Environment.getExternalStorageDirectory().exists()) {
            return true;
        } else {
            return false;
        }
    }
}
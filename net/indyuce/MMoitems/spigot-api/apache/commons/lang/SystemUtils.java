// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang;

import java.io.File;

public class SystemUtils
{
    private static final int JAVA_VERSION_TRIM_SIZE = 3;
    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
    private static final String USER_HOME_KEY = "user.home";
    private static final String USER_DIR_KEY = "user.dir";
    private static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";
    private static final String JAVA_HOME_KEY = "java.home";
    public static final String AWT_TOOLKIT;
    public static final String FILE_ENCODING;
    public static final String FILE_SEPARATOR;
    public static final String JAVA_AWT_FONTS;
    public static final String JAVA_AWT_GRAPHICSENV;
    public static final String JAVA_AWT_HEADLESS;
    public static final String JAVA_AWT_PRINTERJOB;
    public static final String JAVA_CLASS_PATH;
    public static final String JAVA_CLASS_VERSION;
    public static final String JAVA_COMPILER;
    public static final String JAVA_ENDORSED_DIRS;
    public static final String JAVA_EXT_DIRS;
    public static final String JAVA_HOME;
    public static final String JAVA_IO_TMPDIR;
    public static final String JAVA_LIBRARY_PATH;
    public static final String JAVA_RUNTIME_NAME;
    public static final String JAVA_RUNTIME_VERSION;
    public static final String JAVA_SPECIFICATION_NAME;
    public static final String JAVA_SPECIFICATION_VENDOR;
    public static final String JAVA_SPECIFICATION_VERSION;
    public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY;
    public static final String JAVA_VENDOR;
    public static final String JAVA_VENDOR_URL;
    public static final String JAVA_VERSION;
    public static final String JAVA_VM_INFO;
    public static final String JAVA_VM_NAME;
    public static final String JAVA_VM_SPECIFICATION_NAME;
    public static final String JAVA_VM_SPECIFICATION_VENDOR;
    public static final String JAVA_VM_SPECIFICATION_VERSION;
    public static final String JAVA_VM_VENDOR;
    public static final String JAVA_VM_VERSION;
    public static final String LINE_SEPARATOR;
    public static final String OS_ARCH;
    public static final String OS_NAME;
    public static final String OS_VERSION;
    public static final String PATH_SEPARATOR;
    public static final String USER_COUNTRY;
    public static final String USER_DIR;
    public static final String USER_HOME;
    public static final String USER_LANGUAGE;
    public static final String USER_NAME;
    public static final String USER_TIMEZONE;
    public static final String JAVA_VERSION_TRIMMED;
    public static final float JAVA_VERSION_FLOAT;
    public static final int JAVA_VERSION_INT;
    public static final boolean IS_JAVA_1_1;
    public static final boolean IS_JAVA_1_2;
    public static final boolean IS_JAVA_1_3;
    public static final boolean IS_JAVA_1_4;
    public static final boolean IS_JAVA_1_5;
    public static final boolean IS_JAVA_1_6;
    public static final boolean IS_JAVA_1_7;
    public static final boolean IS_OS_AIX;
    public static final boolean IS_OS_HP_UX;
    public static final boolean IS_OS_IRIX;
    public static final boolean IS_OS_LINUX;
    public static final boolean IS_OS_MAC;
    public static final boolean IS_OS_MAC_OSX;
    public static final boolean IS_OS_OS2;
    public static final boolean IS_OS_SOLARIS;
    public static final boolean IS_OS_SUN_OS;
    public static final boolean IS_OS_UNIX;
    public static final boolean IS_OS_WINDOWS;
    public static final boolean IS_OS_WINDOWS_2000;
    public static final boolean IS_OS_WINDOWS_95;
    public static final boolean IS_OS_WINDOWS_98;
    public static final boolean IS_OS_WINDOWS_ME;
    public static final boolean IS_OS_WINDOWS_NT;
    public static final boolean IS_OS_WINDOWS_XP;
    public static final boolean IS_OS_WINDOWS_VISTA;
    public static final boolean IS_OS_WINDOWS_7;
    
    public static File getJavaHome() {
        return new File(System.getProperty("java.home"));
    }
    
    public static File getJavaIoTmpDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }
    
    public static float getJavaVersion() {
        return SystemUtils.JAVA_VERSION_FLOAT;
    }
    
    private static float getJavaVersionAsFloat() {
        return toVersionFloat(toJavaVersionIntArray(SystemUtils.JAVA_VERSION, 3));
    }
    
    private static int getJavaVersionAsInt() {
        return toVersionInt(toJavaVersionIntArray(SystemUtils.JAVA_VERSION, 3));
    }
    
    private static boolean getJavaVersionMatches(final String versionPrefix) {
        return isJavaVersionMatch(SystemUtils.JAVA_VERSION_TRIMMED, versionPrefix);
    }
    
    private static String getJavaVersionTrimmed() {
        if (SystemUtils.JAVA_VERSION != null) {
            for (int i = 0; i < SystemUtils.JAVA_VERSION.length(); ++i) {
                final char ch = SystemUtils.JAVA_VERSION.charAt(i);
                if (ch >= '0' && ch <= '9') {
                    return SystemUtils.JAVA_VERSION.substring(i);
                }
            }
        }
        return null;
    }
    
    private static boolean getOSMatches(final String osNamePrefix, final String osVersionPrefix) {
        return isOSMatch(SystemUtils.OS_NAME, SystemUtils.OS_VERSION, osNamePrefix, osVersionPrefix);
    }
    
    private static boolean getOSMatchesName(final String osNamePrefix) {
        return isOSNameMatch(SystemUtils.OS_NAME, osNamePrefix);
    }
    
    private static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        }
        catch (SecurityException ex) {
            System.err.println("Caught a SecurityException reading the system property '" + property + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }
    
    public static File getUserDir() {
        return new File(System.getProperty("user.dir"));
    }
    
    public static File getUserHome() {
        return new File(System.getProperty("user.home"));
    }
    
    public static boolean isJavaAwtHeadless() {
        return SystemUtils.JAVA_AWT_HEADLESS != null && SystemUtils.JAVA_AWT_HEADLESS.equals(Boolean.TRUE.toString());
    }
    
    public static boolean isJavaVersionAtLeast(final float requiredVersion) {
        return SystemUtils.JAVA_VERSION_FLOAT >= requiredVersion;
    }
    
    public static boolean isJavaVersionAtLeast(final int requiredVersion) {
        return SystemUtils.JAVA_VERSION_INT >= requiredVersion;
    }
    
    static boolean isJavaVersionMatch(final String version, final String versionPrefix) {
        return version != null && version.startsWith(versionPrefix);
    }
    
    static boolean isOSMatch(final String osName, final String osVersion, final String osNamePrefix, final String osVersionPrefix) {
        return osName != null && osVersion != null && osName.startsWith(osNamePrefix) && osVersion.startsWith(osVersionPrefix);
    }
    
    static boolean isOSNameMatch(final String osName, final String osNamePrefix) {
        return osName != null && osName.startsWith(osNamePrefix);
    }
    
    static float toJavaVersionFloat(final String version) {
        return toVersionFloat(toJavaVersionIntArray(version, 3));
    }
    
    static int toJavaVersionInt(final String version) {
        return toVersionInt(toJavaVersionIntArray(version, 3));
    }
    
    static int[] toJavaVersionIntArray(final String version) {
        return toJavaVersionIntArray(version, Integer.MAX_VALUE);
    }
    
    private static int[] toJavaVersionIntArray(final String version, final int limit) {
        if (version == null) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final String[] strings = StringUtils.split(version, "._- ");
        int[] ints = new int[Math.min(limit, strings.length)];
        int j = 0;
        for (int i = 0; i < strings.length && j < limit; ++i) {
            final String s = strings[i];
            if (s.length() > 0) {
                try {
                    ints[j] = Integer.parseInt(s);
                    ++j;
                }
                catch (Exception ex) {}
            }
        }
        if (ints.length > j) {
            final int[] newInts = new int[j];
            System.arraycopy(ints, 0, newInts, 0, j);
            ints = newInts;
        }
        return ints;
    }
    
    private static float toVersionFloat(final int[] javaVersions) {
        if (javaVersions == null || javaVersions.length == 0) {
            return 0.0f;
        }
        if (javaVersions.length == 1) {
            return (float)javaVersions[0];
        }
        final StringBuffer builder = new StringBuffer();
        builder.append(javaVersions[0]);
        builder.append('.');
        for (int i = 1; i < javaVersions.length; ++i) {
            builder.append(javaVersions[i]);
        }
        try {
            return Float.parseFloat(builder.toString());
        }
        catch (Exception ex) {
            return 0.0f;
        }
    }
    
    private static int toVersionInt(final int[] javaVersions) {
        if (javaVersions == null) {
            return 0;
        }
        int intVersion = 0;
        final int len = javaVersions.length;
        if (len >= 1) {
            intVersion = javaVersions[0] * 100;
        }
        if (len >= 2) {
            intVersion += javaVersions[1] * 10;
        }
        if (len >= 3) {
            intVersion += javaVersions[2];
        }
        return intVersion;
    }
    
    static {
        AWT_TOOLKIT = getSystemProperty("awt.toolkit");
        FILE_ENCODING = getSystemProperty("file.encoding");
        FILE_SEPARATOR = getSystemProperty("file.separator");
        JAVA_AWT_FONTS = getSystemProperty("java.awt.fonts");
        JAVA_AWT_GRAPHICSENV = getSystemProperty("java.awt.graphicsenv");
        JAVA_AWT_HEADLESS = getSystemProperty("java.awt.headless");
        JAVA_AWT_PRINTERJOB = getSystemProperty("java.awt.printerjob");
        JAVA_CLASS_PATH = getSystemProperty("java.class.path");
        JAVA_CLASS_VERSION = getSystemProperty("java.class.version");
        JAVA_COMPILER = getSystemProperty("java.compiler");
        JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs");
        JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs");
        JAVA_HOME = getSystemProperty("java.home");
        JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir");
        JAVA_LIBRARY_PATH = getSystemProperty("java.library.path");
        JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name");
        JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version");
        JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name");
        JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor");
        JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");
        JAVA_UTIL_PREFS_PREFERENCES_FACTORY = getSystemProperty("java.util.prefs.PreferencesFactory");
        JAVA_VENDOR = getSystemProperty("java.vendor");
        JAVA_VENDOR_URL = getSystemProperty("java.vendor.url");
        JAVA_VERSION = getSystemProperty("java.version");
        JAVA_VM_INFO = getSystemProperty("java.vm.info");
        JAVA_VM_NAME = getSystemProperty("java.vm.name");
        JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name");
        JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor");
        JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version");
        JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor");
        JAVA_VM_VERSION = getSystemProperty("java.vm.version");
        LINE_SEPARATOR = getSystemProperty("line.separator");
        OS_ARCH = getSystemProperty("os.arch");
        OS_NAME = getSystemProperty("os.name");
        OS_VERSION = getSystemProperty("os.version");
        PATH_SEPARATOR = getSystemProperty("path.separator");
        USER_COUNTRY = ((getSystemProperty("user.country") == null) ? getSystemProperty("user.region") : getSystemProperty("user.country"));
        USER_DIR = getSystemProperty("user.dir");
        USER_HOME = getSystemProperty("user.home");
        USER_LANGUAGE = getSystemProperty("user.language");
        USER_NAME = getSystemProperty("user.name");
        USER_TIMEZONE = getSystemProperty("user.timezone");
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        JAVA_VERSION_FLOAT = getJavaVersionAsFloat();
        JAVA_VERSION_INT = getJavaVersionAsInt();
        IS_JAVA_1_1 = getJavaVersionMatches("1.1");
        IS_JAVA_1_2 = getJavaVersionMatches("1.2");
        IS_JAVA_1_3 = getJavaVersionMatches("1.3");
        IS_JAVA_1_4 = getJavaVersionMatches("1.4");
        IS_JAVA_1_5 = getJavaVersionMatches("1.5");
        IS_JAVA_1_6 = getJavaVersionMatches("1.6");
        IS_JAVA_1_7 = getJavaVersionMatches("1.7");
        IS_OS_AIX = getOSMatchesName("AIX");
        IS_OS_HP_UX = getOSMatchesName("HP-UX");
        IS_OS_IRIX = getOSMatchesName("Irix");
        IS_OS_LINUX = (getOSMatchesName("Linux") || getOSMatchesName("LINUX"));
        IS_OS_MAC = getOSMatchesName("Mac");
        IS_OS_MAC_OSX = getOSMatchesName("Mac OS X");
        IS_OS_OS2 = getOSMatchesName("OS/2");
        IS_OS_SOLARIS = getOSMatchesName("Solaris");
        IS_OS_SUN_OS = getOSMatchesName("SunOS");
        IS_OS_UNIX = (SystemUtils.IS_OS_AIX || SystemUtils.IS_OS_HP_UX || SystemUtils.IS_OS_IRIX || SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_SOLARIS || SystemUtils.IS_OS_SUN_OS);
        IS_OS_WINDOWS = getOSMatchesName("Windows");
        IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");
        IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");
        IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");
        IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");
        IS_OS_WINDOWS_NT = getOSMatchesName("Windows NT");
        IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");
        IS_OS_WINDOWS_VISTA = getOSMatches("Windows", "6.0");
        IS_OS_WINDOWS_7 = getOSMatches("Windows", "6.1");
    }
}

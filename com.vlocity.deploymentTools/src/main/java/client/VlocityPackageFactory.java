package client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Derek on 16/06/2016.
 */
public class VlocityPackageFactory {

    private static final Set<String> supportedPackages = new HashSet<>(Arrays.asList("vlocity_cmt"));

    public static VlocityPackage getPackage(VlocityClient client, String packageName, String packageVersion) throws PackageNotSupportedException, VersionNotSupportedException {
        if (!isSupported(packageName)) {
            throw new PackageNotSupportedException(packageName);
        }

        if (!isSupported(packageName, packageVersion)) {
            throw new VersionNotSupportedException(packageName, packageVersion);
        }

        if ("vlocity_cmt".equals(packageName) && "11".equals(getMajorVersion(packageVersion))) {
            client.cmt11x.VlocityPackage vpack = new client.cmt11x.VlocityPackage(client);

            vpack.setPackageVersion(packageVersion);

            return vpack;
        }

        throw new VersionNotSupportedException(packageName, packageVersion);

    }

    public static Boolean isSupported(String packageName) {
        if (supportedPackages.contains(packageName)) {
            return true;
        }

        return false;
    }

    public static Boolean isSupported(String packageName, String packageVersion) {
        if ("vlocity_cmt".equals(packageName) && "11".equals(getMajorVersion(packageVersion))) {
            return true;
        }

        return false;
    }

    public class PackageNotFoundException extends Exception {}

    public static String getMajorVersion(String packageVersion) {
        if (!packageVersion.contains(".")) return packageVersion;

        return packageVersion.split("\\.")[0];
    }

    public static String getMinorVersion(String packageVersion) {
        if (!packageVersion.contains(".")) return null;

        return packageVersion.split("\\.")[1];
    }
}

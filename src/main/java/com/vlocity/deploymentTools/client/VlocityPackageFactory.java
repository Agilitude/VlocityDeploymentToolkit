package com.vlocity.deploymentTools.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Derek on 16/06/2016.
 */
public class VlocityPackageFactory {

    private static final Set<String> supportedPackages = new HashSet<>(Arrays.asList("vlocity_cmt","vlocity_ins"));

    public static VlocityPackage getPackage(VlocityClient client, String packageName, String packageVersion) throws PackageNotSupportedException, VersionNotSupportedException {
        if (!isSupported(packageName)) {
            throw new PackageNotSupportedException(packageName);
        }

        if (!isSupported(packageName, packageVersion)) {
            throw new VersionNotSupportedException(packageName, packageVersion);
        }

        String majorVersion = getMajorVersion(packageVersion);

        if ("vlocity_cmt".equals(packageName) && "11".equals(majorVersion)) {
	        com.vlocity.deploymentTools.client.cmt11x.VlocityPackage  vpack = new com.vlocity.deploymentTools.client.cmt11x.VlocityPackage (client);

            vpack.setPackageVersion(packageVersion);

            return vpack;
        }

        if ("vlocity_ins".equals(packageName) && ("12".equals(majorVersion) || "888".equals(majorVersion))) {
	        com.vlocity.deploymentTools.client.ins12x.VlocityPackage  vpack = new com.vlocity.deploymentTools.client.ins12x.VlocityPackage (client);

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
        String majorVersion = getMajorVersion(packageVersion);

        if ("vlocity_cmt".equals(packageName) && "11".equals(majorVersion)) {
            return true;
        }
        if ("vlocity_ins".equals(packageName) && ("12".equals(majorVersion) || "888".equals(majorVersion))) {
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

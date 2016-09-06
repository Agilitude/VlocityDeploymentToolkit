package com.vlocity.deploymentTools.client;

/**
 * Created by Derek on 23/06/2016.
 */
public class VersionNotSupportedException extends Exception {
    private static String formatExceptionMessage(String packageName, String packageVersion) {
        String majorVersion = VlocityPackageFactory.getMajorVersion(packageVersion);
        String minorVerison = VlocityPackageFactory.getMinorVersion(packageVersion);

        return String.format("Package %1s v%2s.%3s (%4s) is not supported", packageName, majorVersion, minorVerison, packageVersion);
    }

    public VersionNotSupportedException(String packageName, String packageVersion) {
        super(formatExceptionMessage(packageName, packageVersion));
    }

}

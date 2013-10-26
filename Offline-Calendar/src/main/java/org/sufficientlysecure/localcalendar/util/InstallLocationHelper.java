/**
 *  Copyright (C) 2013  Dominik Schürmann <dominik@dominikschuermann.de>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sufficientlysecure.localcalendar.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class InstallLocationHelper {

    /**
     * Checks if the application is installed on the SD card. See
     * http://stackoverflow.com/questions/
     * 5814474/how-can-i-find-out-if-my-app-is-installed-on-sd-card
     *
     * @return true if the application is installed on the sd cards
     */
    @SuppressLint("SdCardPath")
    public static boolean isInstalledOnSdCard(Context context) {
        // check for API level 8 and higher
        if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                ApplicationInfo ai = pi.applicationInfo;
                return (ai.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE;
            } catch (PackageManager.NameNotFoundException e) {
                // ignore
            }
        }

        // check for API level 7 (rooted devices) - check files dir
        try {
            String filesDir = context.getFilesDir().getAbsolutePath();
            if (filesDir.startsWith("/data/")) {
                return false;
            } else if (filesDir.contains("/mnt/") || filesDir.contains("/sdcard/")) {
                return true;
            }
        } catch (Throwable e) {
            // ignore
        }

        return false;
    }
}

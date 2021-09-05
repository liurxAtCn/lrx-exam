package net.exam.assess.lrxexam.constants;

import net.exam.assess.lrxexam.domain.Version;

import java.util.Calendar;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * static default version
 */
public final class DefaultVersion {

    private static Version version;

    public static Version getDefaultVersion(){
        if( null == version){
            synchronized ( DefaultVersion.class){
                if( null == version){
                    version = generateDefaultVersion();
                }
            }
        }
        return version;
    }

    private static Version generateDefaultVersion(){
        Version defaultVersion = new Version();
        defaultVersion.setVersion( "x.y.x");
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.YEAR, 2020);
        calendar.set( Calendar.MONTH, 0);
        calendar.set( Calendar.DAY_OF_MONTH, 1);
        calendar.set( Calendar.HOUR_OF_DAY, 10);
        calendar.set( Calendar.MINUTE, 12);
        calendar.set( Calendar.SECOND, 12);
        calendar.set( Calendar.ZONE_OFFSET, 123);
        defaultVersion.setReleaseAt( calendar.getTime());
        return defaultVersion;
    }

}

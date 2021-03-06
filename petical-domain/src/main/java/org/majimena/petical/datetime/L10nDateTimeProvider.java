package org.majimena.petical.datetime;

import org.majimena.petical.datatype.TimeZone;
import org.majimena.petical.security.SecurityUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ローカル日時プロバイダ.
 */
public class L10nDateTimeProvider {

    /**
     * 現在日時を取得する（ローカライゼーションできるようにUTCベースの日時を返す）.
     *
     * @return UTCの現在日時
     */
    public static ZonedDateTime now() {
        return ZonedDateTime.now(TimeZone.UTC.getZoneId());
    }

    /**
     * ゾーン情報を持つ現在日を取得する.
     * FIXME 間違ってる？？
     *
     * @return ゾーン情報を持つ現在日時
     */
    public static ZonedDateTime today() {
        TimeZone zone = SecurityUtils.getCurrentTimeZone();
        ZonedDateTime now = ZonedDateTime.now(zone.getZoneId());
        return now.withNano(0).withSecond(0);
    }

    public static LocalDateTime from(String datetime, DateTimeFormatter formatter) {
        return LocalDateTime.from(formatter.parse(datetime));
    }

    /**
     * 指定日をローカライズしてUTCに変換した日時オブジェクトを取得する.
     *
     * @param year   年
     * @param month  月
     * @param date   日
     * @param hour   時
     * @param minute 分
     * @param second 秒
     * @return ローカル日時
     */
    public static LocalDateTime of(int year, int month, int date, int hour, int minute, int second) {
        TimeZone timeZone = SecurityUtils.getCurrentTimeZone();
        ZonedDateTime dateTime = ZonedDateTime.of(year, month, date, hour, minute, second, 0, timeZone.getZoneId());
        return LocalDateTime.ofInstant(dateTime.toInstant(), TimeZone.UTC.getZoneId());
    }

    /**
     * 指定日をローカライズしてUTCに変換した日時オブジェクトを取得する.
     *
     * @param year  年
     * @param month 月
     * @param date  日
     * @param hour  時
     * @return ローカル日時
     */
    public static LocalDateTime of(int year, int month, int date, int hour) {
        TimeZone timeZone = SecurityUtils.getCurrentTimeZone();
        ZonedDateTime dateTime = ZonedDateTime.of(year, month, date, hour, 0, 0, 0, timeZone.getZoneId());
        return LocalDateTime.ofInstant(dateTime.toInstant(), TimeZone.UTC.getZoneId());
    }

    /**
     * 指定日をローカライズしてUTCに変換した日時オブジェクトを取得する.
     *
     * @param year  年
     * @param month 月
     * @param date  日
     * @return ローカル日時
     */
    public static LocalDateTime of(int year, int month, int date) {
        TimeZone timeZone = SecurityUtils.getCurrentTimeZone();
        ZonedDateTime dateTime = ZonedDateTime.of(year, month, date, 0, 0, 0, 0, timeZone.getZoneId());
        return LocalDateTime.ofInstant(dateTime.toInstant(), TimeZone.UTC.getZoneId());
    }

    /**
     * 指定月初日をローカライズしてUTCに変換した日時オブジェクトを取得する.
     *
     * @param year  年
     * @param month 月
     * @return ローカル日時
     */
    public static LocalDateTime of(int year, int month) {
        TimeZone timeZone = SecurityUtils.getCurrentTimeZone();
        ZonedDateTime dateTime = ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, timeZone.getZoneId());
        return LocalDateTime.ofInstant(dateTime.toInstant(), TimeZone.UTC.getZoneId());
    }
}

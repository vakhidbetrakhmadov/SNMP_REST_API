package com.restermans.model;

import com.restermans.exceptions.UnexpectedErrorException;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@XmlRootElement
public class Duration implements Cloneable {

    // Private instance properties ...
    private long days;
    private LocalTime time;

    // Constructors ...
    public Duration() { this(0, LocalTime.of(0,0,0)); }

    public Duration(long days, LocalTime time) {
        this.days = days;
        this.time = time ;
    }

    // Getters ...
    final public long getDays() {
        return days;
    }

    final public LocalTime getTime() {
        return time;
    }

    // Setters ...
    final public void setDays(long days) {
        this.days = days;
    }

    final public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return days + " days, " + time;
    }

    @Override
    public Duration clone() {
        try {
            return (Duration) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

    // Public class methods ...
    public static Duration from(String upTime) {

        Pattern pattern = Pattern.compile("^(\\d+ days, \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,2}|\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,2})$");
        Matcher matcher = pattern.matcher(upTime);
        if(!matcher.matches())
            throw new UnexpectedErrorException("UpTime string representation couldn't be parsed. UpTime = " + "'" + upTime + "'");

        Duration duration = null;

        // 7 days, 13:39:26.00
        if (upTime.contains(",")) {

            String[] tokens = upTime.split(",");
            StringTokenizer tokenizer = new StringTokenizer(tokens[0].trim());
            long days = Long.parseLong(tokenizer.nextToken());
            tokenizer = new StringTokenizer(tokens[1].trim());
            int hours = Integer.parseInt(tokenizer.nextToken(":"));
            int minutes = Integer.parseInt(tokenizer.nextToken(":"));
            tokenizer = new StringTokenizer(tokenizer.nextToken(":"));
            int seconds = Integer.parseInt(tokenizer.nextToken("."));
            duration = new Duration(days, LocalTime.of(hours,minutes,seconds));

            // 13:39:26.00
        } else {

            StringTokenizer tokenizer = new StringTokenizer(upTime.trim());
            int hours = Integer.parseInt(tokenizer.nextToken(":"));
            int minutes = Integer.parseInt(tokenizer.nextToken(":"));
            tokenizer = new StringTokenizer(tokenizer.nextToken(":"));
            int seconds = Integer.parseInt(tokenizer.nextToken("."));
            duration = new Duration(0, LocalTime.of(hours,minutes,seconds));
        }

        return duration;
    }
}

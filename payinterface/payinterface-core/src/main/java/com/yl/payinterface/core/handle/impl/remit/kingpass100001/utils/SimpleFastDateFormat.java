package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.*;
import java.util.*;

/**
 * Created by Administrator on 2017/6/28.
 */
public class SimpleFastDateFormat extends Format{
    private static final long serialVersionUID = 1L;
    private static String cDefaultPattern;
    private static final Map cInstanceCache = new HashMap(7);
    private static final Map cDateInstanceCache = new HashMap(7);
    private static final Map cTimeInstanceCache = new HashMap(7);
    private static final Map cDateTimeInstanceCache = new HashMap(7);
    private static final Map cTimeZoneDisplayCache = new HashMap(7);
    private final String mPattern;
    private final TimeZone mTimeZone;
    private final boolean mTimeZoneForced;
    private final Locale mLocale;
    private final boolean mLocaleForced;
    private transient Rule[] mRules;
    private transient int mMaxLengthEstimate;

    public static SimpleFastDateFormat getInstance() {
        return getInstance(getDefaultPattern(), (TimeZone)null, (Locale)null);
    }

    public static SimpleFastDateFormat getInstance(String pattern) {
        return getInstance(pattern, (TimeZone)null, (Locale)null);
    }

    public static SimpleFastDateFormat getInstance(String pattern, TimeZone timeZone) {
        return getInstance(pattern, timeZone, (Locale)null);
    }

    public static SimpleFastDateFormat getInstance(String pattern, Locale locale) {
        return getInstance(pattern, (TimeZone)null, locale);
    }

    public static synchronized SimpleFastDateFormat getInstance(String pattern, TimeZone timeZone, Locale locale) {
        SimpleFastDateFormat emptyFormat = new SimpleFastDateFormat(pattern, timeZone, locale);
        SimpleFastDateFormat format = (SimpleFastDateFormat)cInstanceCache.get(emptyFormat);
        if(format == null) {
            format = emptyFormat;
            emptyFormat.init();
            cInstanceCache.put(emptyFormat, emptyFormat);
        }

        return format;
    }

    public static SimpleFastDateFormat getDateInstance(int style) {
        return getDateInstance(style, (TimeZone)null, (Locale)null);
    }

    public static SimpleFastDateFormat getDateInstance(int style, Locale locale) {
        return getDateInstance(style, (TimeZone)null, locale);
    }

    public static SimpleFastDateFormat getDateInstance(int style, TimeZone timeZone) {
        return getDateInstance(style, timeZone, (Locale)null);
    }

    public static synchronized SimpleFastDateFormat getDateInstance(int style, TimeZone timeZone, Locale locale) {
        Object key = new Integer(style);
        if(timeZone != null) {
            key = new Pair(key, timeZone);
        }

        if(locale == null) {
            locale = Locale.getDefault();
        }

        Pair key1 = new Pair(key, locale);
        SimpleFastDateFormat format = (SimpleFastDateFormat)cDateInstanceCache.get(key1);
        if(format == null) {
            try {
                SimpleDateFormat ex = (SimpleDateFormat) DateFormat.getDateInstance(style, locale);
                String pattern = ex.toPattern();
                format = getInstance(pattern, timeZone, locale);
                cDateInstanceCache.put(key1, format);
            } catch (ClassCastException var8) {
                throw new IllegalArgumentException("No date pattern for locale: " + locale);
            }
        }

        return format;
    }

    public static SimpleFastDateFormat getTimeInstance(int style) {
        return getTimeInstance(style, (TimeZone)null, (Locale)null);
    }

    public static SimpleFastDateFormat getTimeInstance(int style, Locale locale) {
        return getTimeInstance(style, (TimeZone)null, locale);
    }

    public static SimpleFastDateFormat getTimeInstance(int style, TimeZone timeZone) {
        return getTimeInstance(style, timeZone, (Locale)null);
    }

    public static synchronized SimpleFastDateFormat getTimeInstance(int style, TimeZone timeZone, Locale locale) {
        Object key = new Integer(style);
        if(timeZone != null) {
            key = new Pair(key, timeZone);
        }

        if(locale != null) {
            key = new Pair(key, locale);
        }

        SimpleFastDateFormat format = (SimpleFastDateFormat)cTimeInstanceCache.get(key);
        if(format == null) {
            if(locale == null) {
                locale = Locale.getDefault();
            }

            try {
                SimpleDateFormat ex = (SimpleDateFormat)DateFormat.getTimeInstance(style, locale);
                String pattern = ex.toPattern();
                format = getInstance(pattern, timeZone, locale);
                cTimeInstanceCache.put(key, format);
            } catch (ClassCastException var7) {
                throw new IllegalArgumentException("No date pattern for locale: " + locale);
            }
        }

        return format;
    }

    public static SimpleFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
        return getDateTimeInstance(dateStyle, timeStyle, (TimeZone)null, (Locale)null);
    }

    public static SimpleFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale) {
        return getDateTimeInstance(dateStyle, timeStyle, (TimeZone)null, locale);
    }

    public static SimpleFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone) {
        return getDateTimeInstance(dateStyle, timeStyle, timeZone, (Locale)null);
    }

    public static synchronized SimpleFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone, Locale locale) {
        Pair key = new Pair(new Integer(dateStyle), new Integer(timeStyle));
        if(timeZone != null) {
            key = new Pair(key, timeZone);
        }

        if(locale == null) {
            locale = Locale.getDefault();
        }

        key = new Pair(key, locale);
        SimpleFastDateFormat format = (SimpleFastDateFormat)cDateTimeInstanceCache.get(key);
        if(format == null) {
            try {
                SimpleDateFormat ex = (SimpleDateFormat)DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
                String pattern = ex.toPattern();
                format = getInstance(pattern, timeZone, locale);
                cDateTimeInstanceCache.put(key, format);
            } catch (ClassCastException var8) {
                throw new IllegalArgumentException("No date time pattern for locale: " + locale);
            }
        }

        return format;
    }

    static synchronized String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale) {
        TimeZoneDisplayKey key = new TimeZoneDisplayKey(tz, daylight, style, locale);
        String value = (String)cTimeZoneDisplayCache.get(key);
        if(value == null) {
            value = tz.getDisplayName(daylight, style, locale);
            cTimeZoneDisplayCache.put(key, value);
        }

        return value;
    }

    private static synchronized String getDefaultPattern() {
        if(cDefaultPattern == null) {
            cDefaultPattern = (new SimpleDateFormat()).toPattern();
        }

        return cDefaultPattern;
    }

    protected SimpleFastDateFormat(String pattern, TimeZone timeZone, Locale locale) {
        if(pattern == null) {
            throw new IllegalArgumentException("The pattern must not be null");
        } else {
            this.mPattern = pattern;
            this.mTimeZoneForced = timeZone != null;
            if(timeZone == null) {
                timeZone = TimeZone.getDefault();
            }

            this.mTimeZone = timeZone;
            this.mLocaleForced = locale != null;
            if(locale == null) {
                locale = Locale.getDefault();
            }

            this.mLocale = locale;
        }
    }

    protected void init() {
        List rulesList = this.parsePattern();
        this.mRules = (Rule[])((Rule[])((Rule[])rulesList.toArray(new Rule[rulesList.size()])));
        int len = 0;
        int i = this.mRules.length;

        while(true) {
            --i;
            if(i < 0) {
                this.mMaxLengthEstimate = len;
                return;
            }

            len += this.mRules[i].estimateLength();
        }
    }

    protected List parsePattern() {
        DateFormatSymbols symbols = new DateFormatSymbols(this.mLocale);
        ArrayList rules = new ArrayList();
        String[] ERAs = symbols.getEras();
        String[] months = symbols.getMonths();
        String[] shortMonths = symbols.getShortMonths();
        String[] weekdays = symbols.getWeekdays();
        String[] shortWeekdays = symbols.getShortWeekdays();
        String[] AmPmStrings = symbols.getAmPmStrings();
        int length = this.mPattern.length();
        int[] indexRef = new int[1];

        for(int i = 0; i < length; ++i) {
            indexRef[0] = i;
            String token = this.parseToken(this.mPattern, indexRef);
            i = indexRef[0];
            int tokenLen = token.length();
            if(tokenLen == 0) {
                break;
            }

            char c = token.charAt(0);
            Object rule;
            switch(c) {
                case '\'':
                    String sub = token.substring(1);
                    if(sub.length() == 1) {
                        rule = new CharacterLiteral(sub.charAt(0));
                    } else {
                        rule = new StringLiteral(sub);
                    }
                    break;
                case '(':
                case ')':
                case '*':
                case '+':
                case ',':
                case '-':
                case '.':
                case '/':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case ':':
                case ';':
                case '<':
                case '=':
                case '>':
                case '?':
                case '@':
                case 'A':
                case 'B':
                case 'C':
                case 'I':
                case 'J':
                case 'L':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'T':
                case 'U':
                case 'V':
                case 'X':
                case 'Y':
                case '[':
                case '\\':
                case ']':
                case '^':
                case '_':
                case '`':
                case 'b':
                case 'c':
                case 'e':
                case 'f':
                case 'g':
                case 'i':
                case 'j':
                case 'l':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 't':
                case 'u':
                case 'v':
                case 'x':
                default:
                    throw new IllegalArgumentException("Illegal pattern component: " + token);
                case 'D':
                    rule = this.selectNumberRule(6, tokenLen);
                    break;
                case 'E':
                    rule = new TextField(7, tokenLen < 4?shortWeekdays:weekdays);
                    break;
                case 'F':
                    rule = this.selectNumberRule(8, tokenLen);
                    break;
                case 'G':
                    rule = new TextField(0, ERAs);
                    break;
                case 'H':
                    rule = this.selectNumberRule(11, tokenLen);
                    break;
                case 'K':
                    rule = this.selectNumberRule(10, tokenLen);
                    break;
                case 'M':
                    if(tokenLen >= 4) {
                        rule = new TextField(2, months);
                    } else if(tokenLen == 3) {
                        rule = new TextField(2, shortMonths);
                    } else if(tokenLen == 2) {
                        rule = TwoDigitMonthField.INSTANCE;
                    } else {
                        rule = UnpaddedMonthField.INSTANCE;
                    }
                    break;
                case 'S':
                    rule = this.selectNumberRule(14, tokenLen);
                    break;
                case 'W':
                    rule = this.selectNumberRule(4, tokenLen);
                    break;
                case 'Z':
                    if(tokenLen == 1) {
                        rule = TimeZoneNumberRule.INSTANCE_NO_COLON;
                    } else {
                        rule = TimeZoneNumberRule.INSTANCE_COLON;
                    }
                    break;
                case 'a':
                    rule = new TextField(9, AmPmStrings);
                    break;
                case 'd':
                    rule = this.selectNumberRule(5, tokenLen);
                    break;
                case 'h':
                    rule = new TwelveHourField(this.selectNumberRule(10, tokenLen));
                    break;
                case 'k':
                    rule = new TwentyFourHourField(this.selectNumberRule(11, tokenLen));
                    break;
                case 'm':
                    rule = this.selectNumberRule(12, tokenLen);
                    break;
                case 's':
                    rule = this.selectNumberRule(13, tokenLen);
                    break;
                case 'w':
                    rule = this.selectNumberRule(3, tokenLen);
                    break;
                case 'y':
                    if(tokenLen >= 4) {
                        rule = this.selectNumberRule(1, tokenLen);
                    } else {
                        rule = TwoDigitYearField.INSTANCE;
                    }
                    break;
                case 'z':
                    if(tokenLen >= 4) {
                        rule = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 1);
                    } else {
                        rule = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 0);
                    }
            }

            rules.add(rule);
        }

        return rules;
    }

    protected String parseToken(String pattern, int[] indexRef) {
        StringBuffer buf = new StringBuffer();
        int i = indexRef[0];
        int length = pattern.length();
        char c = pattern.charAt(i);
        if(c >= 65 && c <= 90 || c >= 97 && c <= 122) {
            buf.append(c);

            while(i + 1 < length) {
                char var8 = pattern.charAt(i + 1);
                if(var8 != c) {
                    break;
                }

                buf.append(c);
                ++i;
            }
        } else {
            buf.append('\'');

            for(boolean inLiteral = false; i < length; ++i) {
                c = pattern.charAt(i);
                if(c == 39) {
                    if(i + 1 < length && pattern.charAt(i + 1) == 39) {
                        ++i;
                        buf.append(c);
                    } else {
                        inLiteral = !inLiteral;
                    }
                } else {
                    if(!inLiteral && (c >= 65 && c <= 90 || c >= 97 && c <= 122)) {
                        --i;
                        break;
                    }

                    buf.append(c);
                }
            }
        }

        indexRef[0] = i;
        return buf.toString();
    }

    protected NumberRule selectNumberRule(int field, int padding) {
        switch(padding) {
            case 1:
                return new UnpaddedNumberField(field);
            case 2:
                return new TwoDigitNumberField(field);
            default:
                return new PaddedNumberField(field, padding);
        }
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if(obj instanceof Date) {
            return this.format((Date)obj, toAppendTo);
        } else if(obj instanceof Calendar) {
            return this.format((Calendar)obj, toAppendTo);
        } else if(obj instanceof Long) {
            return this.format(((Long)obj).longValue(), toAppendTo);
        } else {
            throw new IllegalArgumentException("Unknown class: " + (obj == null?"<null>":obj.getClass().getName()));
        }
    }

    public String format(long millis) {
        return this.format(new Date(millis));
    }

    public String format(Date date) {
        GregorianCalendar c = new GregorianCalendar(this.mTimeZone);
        c.setTime(date);
        return this.applyRules(c, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public String format(Calendar calendar) {
        return this.format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public StringBuffer format(long millis, StringBuffer buf) {
        return this.format(new Date(millis), buf);
    }

    public StringBuffer format(Date date, StringBuffer buf) {
        GregorianCalendar c = new GregorianCalendar(this.mTimeZone);
        c.setTime(date);
        return this.applyRules(c, buf);
    }

    public StringBuffer format(Calendar calendar, StringBuffer buf) {
        if(this.mTimeZoneForced) {
            calendar.getTime();
            calendar = (Calendar)calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }

        return this.applyRules(calendar, buf);
    }

    protected StringBuffer applyRules(Calendar calendar, StringBuffer buf) {
        Rule[] rules = this.mRules;
        int len = this.mRules.length;

        for(int i = 0; i < len; ++i) {
            rules[i].appendTo(buf, calendar);
        }

        return buf;
    }

    public Object parseObject(String source, ParsePosition pos) {
        pos.setIndex(0);
        pos.setErrorIndex(0);
        return null;
    }

    public String getPattern() {
        return this.mPattern;
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public boolean getTimeZoneOverridesCalendar() {
        return this.mTimeZoneForced;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof SimpleFastDateFormat)) {
            return false;
        } else {
            SimpleFastDateFormat other = (SimpleFastDateFormat)obj;
            return (this.mPattern == other.mPattern || this.mPattern.equals(other.mPattern)) && (this.mTimeZone == other.mTimeZone || this.mTimeZone.equals(other.mTimeZone)) && (this.mLocale == other.mLocale || this.mLocale.equals(other.mLocale)) && this.mTimeZoneForced == other.mTimeZoneForced && this.mLocaleForced == other.mLocaleForced;
        }
    }

    public int hashCode() {
        byte total = 0;
        int total1 = total + this.mPattern.hashCode();
        total1 += this.mTimeZone.hashCode();
        total1 += this.mTimeZoneForced?1:0;
        total1 += this.mLocale.hashCode();
        total1 += this.mLocaleForced?1:0;
        return total1;
    }

    public String toString() {
        return "SimpleFastDateFormat[" + this.mPattern + "]";
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.init();
    }

    private interface Rule {
        int estimateLength();

        void appendTo(StringBuffer var1, Calendar var2);
    }

    private interface NumberRule extends Rule {
        void appendTo(StringBuffer var1, int var2);
    }

    private static class CharacterLiteral implements Rule {
        private final char mValue;

        CharacterLiteral(char value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return 1;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValue);
        }
    }

    private static class StringLiteral implements Rule {
        private final String mValue;

        StringLiteral(String value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return this.mValue.length();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValue);
        }
    }

    private static class TextField implements Rule {
        private final int mField;
        private final String[] mValues;

        TextField(int field, String[] values) {
            this.mField = field;
            this.mValues = values;
        }

        public int estimateLength() {
            int max = 0;
            int i = this.mValues.length;

            while(true) {
                --i;
                if(i < 0) {
                    return max;
                }

                int len = this.mValues[i].length();
                if(len > max) {
                    max = len;
                }
            }
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValues[calendar.get(this.mField)]);
        }
    }

    private static class UnpaddedNumberField implements NumberRule {
        private final int mField;

        UnpaddedNumberField(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            this.appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if(value < 10) {
                buffer.append((char)(value + 48));
            } else if(value < 100) {
                buffer.append((char)(value / 10 + 48));
                buffer.append((char)(value % 10 + 48));
            } else {
                buffer.append(Integer.toString(value));
            }

        }
    }

    private static class UnpaddedMonthField implements NumberRule {
        static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();

        UnpaddedMonthField() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            this.appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if(value < 10) {
                buffer.append((char)(value + 48));
            } else {
                buffer.append((char)(value / 10 + 48));
                buffer.append((char)(value % 10 + 48));
            }

        }
    }

    private static class PaddedNumberField implements NumberRule {
        private final int mField;
        private final int mSize;

        PaddedNumberField(int field, int size) {
            if(size < 3) {
                throw new IllegalArgumentException();
            } else {
                this.mField = field;
                this.mSize = size;
            }
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            this.appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            int digits;
            if(value < 100) {
                digits = this.mSize;

                while(true) {
                    --digits;
                    if(digits < 2) {
                        buffer.append((char)(value / 10 + 48));
                        buffer.append((char)(value % 10 + 48));
                        break;
                    }

                    buffer.append('0');
                }
            } else {
                if(value < 1000) {
                    digits = 3;
                } else {
                    if(value <= -1) {
                        throw new IllegalArgumentException("Negative values should not be possible" + value);
                    }

                    digits = Integer.toString(value).length();
                }

                int i = this.mSize;

                while(true) {
                    --i;
                    if(i < digits) {
                        buffer.append(Integer.toString(value));
                        break;
                    }

                    buffer.append('0');
                }
            }

        }
    }

    private static class TwoDigitNumberField implements NumberRule {
        private final int mField;

        TwoDigitNumberField(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            this.appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if(value < 100) {
                buffer.append((char)(value / 10 + 48));
                buffer.append((char)(value % 10 + 48));
            } else {
                buffer.append(Integer.toString(value));
            }

        }
    }

    private static class TwoDigitYearField implements NumberRule {
        static final TwoDigitYearField INSTANCE = new TwoDigitYearField();

        TwoDigitYearField() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            this.appendTo(buffer, calendar.get(1) % 100);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            buffer.append((char)(value / 10 + 48));
            buffer.append((char)(value % 10 + 48));
        }
    }

    private static class TwoDigitMonthField implements NumberRule {
        static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();

        TwoDigitMonthField() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            this.appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            buffer.append((char)(value / 10 + 48));
            buffer.append((char)(value % 10 + 48));
        }
    }

    private static class TwelveHourField implements NumberRule {
        private final NumberRule mRule;

        TwelveHourField(NumberRule rule) {
            this.mRule = rule;
        }

        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int value = calendar.get(10);
            if(value == 0) {
                value = calendar.getLeastMaximum(10) + 1;
            }

            this.mRule.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value) {
            this.mRule.appendTo(buffer, value);
        }
    }

    private static class TwentyFourHourField implements NumberRule {
        private final NumberRule mRule;

        TwentyFourHourField(NumberRule rule) {
            this.mRule = rule;
        }

        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int value = calendar.get(11);
            if(value == 0) {
                value = calendar.getMaximum(11) + 1;
            }

            this.mRule.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value) {
            this.mRule.appendTo(buffer, value);
        }
    }

    private static class TimeZoneNameRule implements Rule {
        private final TimeZone mTimeZone;
        private final boolean mTimeZoneForced;
        private final Locale mLocale;
        private final int mStyle;
        private final String mStandard;
        private final String mDaylight;

        TimeZoneNameRule(TimeZone timeZone, boolean timeZoneForced, Locale locale, int style) {
            this.mTimeZone = timeZone;
            this.mTimeZoneForced = timeZoneForced;
            this.mLocale = locale;
            this.mStyle = style;
            if(timeZoneForced) {
                this.mStandard = SimpleFastDateFormat.getTimeZoneDisplay(timeZone, false, style, locale);
                this.mDaylight = SimpleFastDateFormat.getTimeZoneDisplay(timeZone, true, style, locale);
            } else {
                this.mStandard = null;
                this.mDaylight = null;
            }

        }

        public int estimateLength() {
            return this.mTimeZoneForced?Math.max(this.mStandard.length(), this.mDaylight.length()):(this.mStyle == 0?4:40);
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            if(this.mTimeZoneForced) {
                if(this.mTimeZone.useDaylightTime() && calendar.get(16) != 0) {
                    buffer.append(this.mDaylight);
                } else {
                    buffer.append(this.mStandard);
                }
            } else {
                TimeZone timeZone = calendar.getTimeZone();
                if(timeZone.useDaylightTime() && calendar.get(16) != 0) {
                    buffer.append(SimpleFastDateFormat.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
                } else {
                    buffer.append(SimpleFastDateFormat.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
                }
            }

        }
    }

    private static class TimeZoneNumberRule implements Rule {
        static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
        static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        final boolean mColon;

        TimeZoneNumberRule(boolean colon) {
            this.mColon = colon;
        }

        public int estimateLength() {
            return 5;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int offset = calendar.get(15) + calendar.get(16);
            if(offset < 0) {
                buffer.append('-');
                offset = -offset;
            } else {
                buffer.append('+');
            }

            int hours = offset / 3600000;
            buffer.append((char)(hours / 10 + 48));
            buffer.append((char)(hours % 10 + 48));
            if(this.mColon) {
                buffer.append(':');
            }

            int minutes = offset / '\uea60' - 60 * hours;
            buffer.append((char)(minutes / 10 + 48));
            buffer.append((char)(minutes % 10 + 48));
        }
    }

    private static class TimeZoneDisplayKey {
        private final TimeZone mTimeZone;
        private final int mStyle;
        private final Locale mLocale;

        TimeZoneDisplayKey(TimeZone timeZone, boolean daylight, int style, Locale locale) {
            this.mTimeZone = timeZone;
            if(daylight) {
                style |= -2147483648;
            }

            this.mStyle = style;
            this.mLocale = locale;
        }

        public int hashCode() {
            return this.mStyle * 31 + this.mLocale.hashCode();
        }

        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            } else if(!(obj instanceof TimeZoneDisplayKey)) {
                return false;
            } else {
                TimeZoneDisplayKey other = (TimeZoneDisplayKey)obj;
                return this.mTimeZone.equals(other.mTimeZone) && this.mStyle == other.mStyle && this.mLocale.equals(other.mLocale);
            }
        }
    }

    private static class Pair {
        private final Object mObj1;
        private final Object mObj2;

        public Pair(Object obj1, Object obj2) {
            this.mObj1 = obj1;
            this.mObj2 = obj2;
        }

        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            } else if(!(obj instanceof Pair)) {
                return false;
            } else {
                Pair key = (Pair)obj;
                if(this.mObj1 == null) {
                    if(key.mObj1 != null) {
                        return false;
                    }
                } else if(!this.mObj1.equals(key.mObj1)) {
                    return false;
                }

                if(this.mObj2 == null) {
                    if(key.mObj2 == null) {
                        return true;
                    }
                } else if(this.mObj2.equals(key.mObj2)) {
                    return true;
                }

                return false;
            }
        }

        public int hashCode() {
            return (this.mObj1 == null?0:this.mObj1.hashCode()) + (this.mObj2 == null?0:this.mObj2.hashCode());
        }

        public String toString() {
            return "[" + this.mObj1 + ':' + this.mObj2 + ']';
        }
    }
}

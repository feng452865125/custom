package com.chunhe.custom.framework.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseValidator {

    public static final String IDENTITY_PAGINATION = "pagination";
    public static final String IDENTITY_LIST = "list";
    public static final String IDENTITY_CREATE = "create";
    public static final String IDENTITY_PATCH = "update";
    public static final String IDENTITY_DELETE = "delete";
    public static final String IDENTITY_CHECK = "check";
    public static final String IDENTITY_CANCEL = "cancel";

    protected static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

    @Getter
    @AllArgsConstructor
    public static class Tip {
        private final String key;
        private final Object value;
        private final String message;
    }

    public abstract Tip validate(Map<String, Object> map, String identity);

    /**
     * Validate Required. Allow space characters.
     */
    protected boolean validateRequired(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return true;
        }
        return false;
    }

    /**
     * Validate Required. Not null
     */
    protected  boolean validateNotNullRequired(Map<String, Object> map, String key){
        if (!validateRequired(map, key)) {
            return false;
        }

        if(null == map.get(key)){
            return false;
        }
        return true;
    }

    /**
     * Validate integer.
     */
    protected boolean validateInteger(Map<String, Object> map, String key, int min, int max) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (value instanceof Integer) {
            return true;
        }
        try {
            int temp = Integer.parseInt(String.valueOf(value).trim());
            if (temp < min || temp > max) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate long.
     */
    protected boolean validateLong(Map<String, Object> map, String key, long min, long max) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (value instanceof Long) {
            return true;
        }
        try {
            long temp = Long.parseLong(String.valueOf(value).trim());
            if (temp < min || temp > max) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate double.
     */
    protected boolean validateDouble(Map<String, Object> map, String key, double min, double max) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (value instanceof Double) {
            return true;
        }
        try {
            double temp = Double.parseDouble(String.valueOf(value).trim());
            if (temp < min || temp > max) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate date. Date formate: yyyy-MM-dd
     */
    protected boolean validateDate(Map<String, Object> map, String key, String pattern) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (value instanceof Date) {
            return true;
        }
        String temp = String.valueOf(value).trim();
        if (StringUtils.isBlank(temp)) {
            return false;
        }
        try {
            new SimpleDateFormat(pattern).parse(temp);	// Date temp = Date.valueOf(value); 为了兼容 64位 JDK
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate date.
     */
    protected boolean validateDate(Map<String, Object> map, String key, Date min, Date max, String pattern) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (value instanceof Date) {
            return true;
        }
        String temp = String.valueOf(value).trim();
        if (StringUtils.isBlank(temp)) {
            return false;
        }
        try {
            Date date = new SimpleDateFormat(pattern).parse(temp);	// Date temp = Date.valueOf(value); 为了兼容 64位 JDK
            if (date.after(max) || date.before(min)) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate equal field. Usually validate password and password again
     */
    protected boolean validateEqualField(Map<String, Object> map, String key1, String key2, String errorKey, String errorMessage) {
        Object value1 = map.get(key1);
        Object value2 = map.get(key2);
        if (value1 == null || value2 == null || (! value1.equals(value2))) {
            return false;
        }
        return true;
    }

    /**
     * Validate email.
     */
    protected boolean validateEmail(Map<String, Object> map, String key) {
        return validateRegex(map, key, emailAddressPattern, false);
    }

    /**
     * Validate URL.
     */
    protected boolean validateUrl(Map<String, Object> map, String key) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (!(value instanceof String)) {
            return false;
        }
        String email = String.valueOf(value).trim();
        try {
            if (email.startsWith("https://")) {
                value = "http://" + email.substring(8); // URL doesn't understand the https protocol, hack it
            }
            new URL(email);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * Validate regular expression.
     */
    protected boolean validateRegex(Map<String, Object> map, String key, String regExpression, boolean isCaseSensitive) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        String regex = String.valueOf(value).trim();
        if (StringUtils.isBlank(regex)) {
            return false;
        }
        Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(regex);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Validate regular expression and case sensitive.
     */
    protected boolean validateRegex(Map<String, Object> map, String key, String regExpression) {
        return validateRegex(map, key, regExpression, true);
    }

    /**
     * Validate string.
     */
    protected boolean validateString(Map<String, Object> map, String key, int minLen, int maxLen) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (!(value instanceof String)) {
            return false;
        }
        String str = String.valueOf(value);
        if (str.length() < minLen || str.length() > maxLen) {
            return false;
        }
        return true;
    }

    /**
     * validate boolean.
     */
    protected boolean validateBoolean(Map<String, Object> map, String key) {
        if (!validateRequired(map, key)) {
            return false;
        }
        Object value = map.get(key);
        if (value instanceof Boolean) {
            return true;
        }
        String bool = String.valueOf(value).trim().toLowerCase();
        if ("1".equals(bool) || "true".equals(bool)) {
            return true;
        } else if ("0".equals(bool) || "false".equals(bool)) {
            return true;
        }
        return false;
    }

}

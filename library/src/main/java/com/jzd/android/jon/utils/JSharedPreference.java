package com.jzd.android.jon.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 数据存储
 * 存储对象的时候 可以考虑Gson转换成string存储
 */

public class JSharedPreference {
    private SharedPreferences mPreferences;

    private JSharedPreference() {
    }

    private JSharedPreference(Context context, String name) {
        this.mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void remove(String key) {
        mPreferences.edit()
                .remove(key).apply();
    }

    public static class Builder {
        public Builder() {
        }

        public JSharedPreference build(Context context, String name) {
            return new JSharedPreference(context, name);
        }
    }

    public boolean save(Object object) throws IllegalAccessException {
        if(mPreferences == null || object == null) {
            return false;
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        Field[] fields = object.getClass()
                .getDeclaredFields();
        for(Field field : fields) {
            // 过滤不必要的字段,有些是Class中的字段
            if(field.getModifiers() == Modifier.STATIC || field.getModifiers() == Modifier.FINAL) {
                continue;
            }

            Shared shared = field.getAnnotation(Shared.class);
            // 没有注解的   value=false的不注入
            if(shared == null || !shared.value()) {
                continue;
            }

            field.setAccessible(true);
            Object obj = field.get(object);
            // 没有注解使用变量名称存入，有注解使用注解存入
            String name = shared.name()
                    .isEmpty() ? field.getName() : shared.name();
            if(obj instanceof String || obj == null) {
                if(obj != null) {
                    editor.putString(name, (String) obj);
                } else {
                    editor.putString(name, null);
                }
            } else if(obj instanceof Integer) {
                editor.putInt(name, (Integer) obj);
            } else if(obj instanceof Long) {
                editor.putLong(name, (Long) obj);
            } else if(obj instanceof Float) {
                editor.putFloat(name, (Float) obj);
            } else if(obj instanceof Boolean) {
                editor.putBoolean(name, (Boolean) obj);
            }
        }
        editor.apply();
        return true;
    }

    public void get(Object object) throws IllegalAccessException {
        if(object == null || mPreferences == null) {
            return;
        }
        Field[] fields = object.getClass()
                .getDeclaredFields();
        for(Field field : fields) {
            // 过滤不必要的字段,有些是Class中的字段
            if(field.getModifiers() == Modifier.STATIC || field.getModifiers() == Modifier.FINAL) {
                continue;
            }
            Shared shared = field.getAnnotation(Shared.class);
            // value=false的注解 过滤
            if(shared == null || !shared.value()) {
                continue;
            }
            String name = shared.name()
                    .isEmpty() ? field.getName() : shared.name();
            field.setAccessible(true);
            if(String.class.isAssignableFrom(field.getType())) {
                field.set(object, getString(name, ""));
            } else if(int.class.isAssignableFrom(field.getType())) {
                field.set(object, getInt(name, 0));
            } else if(float.class.isAssignableFrom(field.getType())) {
                field.set(object, getFloat(name, 0));
            } else if(boolean.class.isAssignableFrom(field.getType())) {
                field.set(object, getBoolean(name, false));
            } else if(long.class.isAssignableFrom(field.getType())) {
                field.set(object, getLong(name, 0));
            }
        }
    }

    public void clear() {
        if(mPreferences == null) {
            return;
        }
        mPreferences.edit()
                .clear()
                .apply();
    }


    public JSharedPreference putInt(String key, int value) {
        mPreferences.edit()
                .putInt(key, value)
                .apply();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public JSharedPreference putString(String key, String value) {
        mPreferences.edit()
                .putString(key, value)
                .apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public JSharedPreference putBoolean(String key, boolean value) {
        mPreferences.edit()
                .putBoolean(key, value)
                .apply();
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public JSharedPreference putFloat(String key, float value) {
        mPreferences.edit()
                .putFloat(key, value)
                .apply();
        return this;
    }

    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public JSharedPreference putLong(String key, long value) {
        mPreferences.edit()
                .putLong(key, value)
                .apply();
        return this;
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    @Target(value = ElementType.FIELD) @Retention(value = RetentionPolicy.RUNTIME) public @interface Shared {
        boolean value() default true;

        String name() default "";
    }
}

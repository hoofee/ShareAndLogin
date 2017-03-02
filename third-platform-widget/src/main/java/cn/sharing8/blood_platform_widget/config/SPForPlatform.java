package cn.sharing8.blood_platform_widget.config;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Method;

/**
 * Created by hufei on 2017/2/6.
 * 有关第三方登录分享等数据的SP缓存
 */

public class SPForPlatform {

    public static String FILE_NAME = "sp_blood_platform";

    //************************key**************************begin
    //wechat
    public static final String WECHAT_ACCESS_TOKEN_MODEL_JSON_STRING = "wechat_access_token_model_json_string";

    //sina
    public static final String SINA_AUTH_MODEL_JSON_STRING = "sina_auth_model_json_string";
    //************************value*************************end

    public SharedPreferences spInstance;

    public SPForPlatform(Context context) {
        spInstance = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public void put(String key, Object object) {
        SharedPreferences.Editor editor = spInstance.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SPCommitCompat.apply(editor);
    }

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return spInstance.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return spInstance.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return spInstance.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return spInstance.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return spInstance.getLong(key, (Long) defaultObject);
        }

        return defaultObject;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = spInstance.edit();
        editor.remove(key);
        SPCommitCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public boolean contains(String key) {
        return spInstance.contains(key);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences.Editor editor = spInstance.edit();
        editor.clear();
        SPCommitCompat.apply(editor);
    }

    private static class SPCommitCompat {

        private static final Method spEditorApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (spEditorApplyMethod == null) {
                    editor.commit();
                } else {
                    spEditorApplyMethod.invoke(editor);
                }
            } catch (Exception e) {
                editor.commit();
            }
        }
    }
}

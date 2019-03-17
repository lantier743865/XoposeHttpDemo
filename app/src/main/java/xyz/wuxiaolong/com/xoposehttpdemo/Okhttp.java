package xyz.wuxiaolong.com.xoposehttpdemo;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by wuxiaolong on 2019/3/17.
 */

public class Okhttp implements IXposedHookLoadPackage {
    private static final String TAG = "Okhttp";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        String packageName = lpparam.packageName;
        if (packageName.equals("xyz.wuxiaolong.com.requesthookdemo")) {
            XC_MethodHook xc_methodHook = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Object arg = param.args[0];
                    Class<?> aClass = arg.getClass();
                    Method request = aClass.getDeclaredMethod("request");
                    request.setAccessible(true);
                    Object requestObj = request.invoke(arg, new  Object[]{});
                    Class<?> requestClass = requestObj.getClass();
                    Field urlField = requestClass.getDeclaredField("url");
                    urlField.setAccessible(true);
                    Object url = urlField.get(requestObj);
                    Log.e(TAG, "---->>url? "+url);
                }
            };
            XposedHelpers.findAndHookMethod("okhttp3.internal.cache.CacheInterceptor", lpparam.classLoader,"intercept","okhttp3.Interceptor$Chain",xc_methodHook);

        }
    }
}

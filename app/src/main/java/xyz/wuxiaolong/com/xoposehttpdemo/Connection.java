package xyz.wuxiaolong.com.xoposehttpdemo;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by wuxiaolong on 2019/3/17.
 */


public class Connection implements IXposedHookLoadPackage {
    private static final String TAG = "Connection";
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
                    Object[] args = param.args;
                    for (int i = 0; i < args.length; i++) {
                        Log.e(TAG, "---->>url? "+args[i] );
                    }
                }
            };
            XposedHelpers.findAndHookConstructor("java.net.HttpURLConnection",lpparam.classLoader,"java.net.URL",xc_methodHook);
        }
    }
}


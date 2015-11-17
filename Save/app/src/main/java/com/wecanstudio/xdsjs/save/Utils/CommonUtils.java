package com.wecanstudio.xdsjs.save.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;


import com.xdsjs.save.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 通用的工具类
 */
public class CommonUtils {

    public static void bytesToFile(final File file, byte[] bytes)
            throws FileNotFoundException, IOException {
        FileOutputStream output = new FileOutputStream(file);
        output.write(bytes);
        output.close();
    }

    /**
     * 判断是否有sd卡
     * @return
     */
    public static boolean hasSDcard() {
        // TODO Auto-generated method stub
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static String format(int curPos) {
        int sec1 = curPos / 1000;
        int min = sec1 / 60;
        int sec = sec1 % 60;
        int tm = min / 10, mm = min % 10;
        int ts = sec / 10, ms = sec % 10;
        String str = String.format("%d%d:%d%d", tm, mm, ts, ms);
        return str;
    }

    /**
     * 删除文件夹
     * @param dir
     */
    public static void clearDir(String dir) {
        File file = new File(dir);
        File[] fs = file.listFiles();
        for (File f : fs) {
            f.delete();
        }
    }

    public static String prettyFormat(Date date) {
        String dateStr;
        String am_pm = "am";
        SimpleDateFormat format = new SimpleDateFormat("M-d h");
        SimpleDateFormat format1 = new SimpleDateFormat("aa", Locale.ENGLISH);
        if (format1.format(date).equals("PM")) {
            am_pm = "pm";
        }
        dateStr = format.format(date) + am_pm;
        return dateStr;
    }

    public static void alertDialog(Activity activity, String s) {
        new AlertDialog.Builder(activity).setMessage(s).show();
    }

    public static void alertDialog(Activity activity, int msgId) {
        new AlertDialog.Builder(activity).
                setMessage(activity.getString(msgId)).show();
    }

    public static int getWindowWidth(Activity cxt) {
        int width;
        DisplayMetrics metrics = cxt.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        return width;
    }

    public static long getLongByTimeStr(String begin) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
        String origin = "00:00:00.00";
        Date parse = format.parse(begin);
        return parse.getTime() - format.parse(origin).getTime();
    }

    public static long getPassTime(long st) {
        return System.currentTimeMillis() - st;
    }

    public static String getEquation(int finalNum, int delta) {
        String equation;
        int abs = Math.abs(delta);
        if (delta >= 0) {
            equation = String.format("%d+%d=%d", finalNum - delta, abs, finalNum);
        } else {
            equation = String.format("%d-%d=%d", finalNum - delta, abs, finalNum);
        }
        return equation;
    }

    public static Uri getCacheUri(String path, String url) {
        Uri uri = Uri.parse(url);
        uri = Uri.parse("cache:" + path + ":" + uri.toString());
        return uri;
    }

    @SuppressLint("NewApi")
    public static void notify(Context context, String msg, String title, Class<?> toClz, int notifyId) {
        PendingIntent pend = PendingIntent.getActivity(context, 0,
                new Intent(context, toClz), 0);
        Notification.Builder builder = new Notification.Builder(context);
        int icon = context.getApplicationInfo().icon;
        builder.setContentIntent(pend)
                .setSmallIcon(icon)
                .setWhen(System.currentTimeMillis())
                .setTicker(msg)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManager man = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        man.notify(notifyId, builder.getNotification());
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    public static String getStringByFile(File f) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        br.close();
        return builder.toString();
    }

    public static String getShortUrl(String longUrl) throws IOException, JSONException {
        if (longUrl.startsWith("http") == false) {
            throw new IllegalArgumentException("longUrl must startSearchAround with http");
        }
        String url = "https://api.weibo.com/2/short_url/shorten.json";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("access_token", "2.00_hkjqBR1dbuCc632289355qerfeD"));
        params.add(new BasicNameValuePair("url_long", longUrl));
        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse res = new DefaultHttpClient().execute(post);
        if (res.getStatusLine().getStatusCode() == 200) {
            String str = EntityUtils.toString(res.getEntity());
            JSONObject json = new JSONObject(str);
            JSONArray arr = json.getJSONArray("urls");
            JSONObject urls = arr.getJSONObject(0);
            if (urls.getBoolean("result")) {
                return urls.getString("url_short");
            } else {
                return null;
            }
        }
        return null;
    }

    public static String getGb2312Encode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "gb2312");
    }

    public static void installApk(Context context, String path) {
        Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_VIEW);
        File file = new File(path);
        Log.i("lzw", file.getAbsolutePath());
        intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }


    public static Activity modifyDialogContext(Activity cxt) {
        Activity parent = cxt.getParent();
        if (parent != null) {
            return parent;
        } else {
            return cxt;
        }
    }

}

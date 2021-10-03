package io.github.isning.imaband.misc;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class Util {
    public static Bitmap Drawable2Bitmap(Drawable drawable){
        return ((BitmapDrawable)drawable).getBitmap();
    }
    public static byte[] Bitmap2Bytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public static byte[] Drawable2Bytes(Drawable drawable){
        return Bitmap2Bytes(Drawable2Bitmap(drawable));
    }
    public static Bitmap Bytes2Bitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static Drawable Bytes2DrawAble(Resources res, byte[] bytes){
        return new BitmapDrawable(res, Bytes2Bitmap(bytes));
    }

    public static boolean checkPer(Context ctx, String permission) {
        return ActivityCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED;
    }
}

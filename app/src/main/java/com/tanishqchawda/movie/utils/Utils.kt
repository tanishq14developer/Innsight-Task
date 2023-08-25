package com.tanishqchawda.movie.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider.getUriForFile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

object Utils {
    fun shareOnWhatsapp(
        appCompatActivity: AppCompatActivity, textBody: String?, textDes: String?, image: String
    ) {
        Glide.with(appCompatActivity)
            .asBitmap()
            .load(image)
            .into(
                object : CustomTarget<Bitmap?>() {
                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                    override fun onResourceReady(
                        bitmap: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?
                    ) {
                        val cachePath = File(appCompatActivity.externalCacheDir, "my_images/")
                        cachePath.mkdirs()
                        val file = File(cachePath, Date().time.toString() + ".png")
                        val fileOutputStream: FileOutputStream
                        try {
                            fileOutputStream = FileOutputStream(file)
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                            fileOutputStream.flush()
                            fileOutputStream.close()
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        val myImageFileUri: Uri =
                            getUriForFile(
                                appCompatActivity,
                                appCompatActivity.packageName + ".provider",
                                file
                            )
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.setPackage("com.whatsapp")
                        intent.putExtra(
                            Intent.EXTRA_TEXT,
                            if (!TextUtils.isEmpty(textBody) && !TextUtils.isEmpty(textDes)) "$textBody\n $textDes" else ""
                        )

                        if (myImageFileUri != null) {
                            intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri)
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            intent.type = "image/*"
                        }
                        try {
                            appCompatActivity.startActivity(intent)
                        } catch (ex: ActivityNotFoundException) {
                            ex.printStackTrace()
                            Toast.makeText(
                                appCompatActivity, "Whatsapp not installed", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
    }
}

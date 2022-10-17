package com.learn.vocabulary.util.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.Spannable
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

fun Fragment.showDialog(camera: () -> Unit, gallery: () -> Unit) {
    val pictureDialog = AlertDialog.Builder(requireActivity())
    pictureDialog.setTitle("SelectAction")
    val pictureDialogItem =
        arrayOf("Select photo from Gallery", "Capture photo from Camera")
    pictureDialog.setItems(pictureDialogItem) { dialog, which ->

        when (which) {
            0 -> gallery.invoke()
            1 -> camera.invoke()
        }
    }
    pictureDialog.show()
}

fun TextView.setBoldSubstring(substring: String){


    try {
        val spannable = android.text.SpannableString(text)
        val start = text.indexOf(substring)
        spannable.setSpan(
            StyleSpan(BOLD),
            start,
            start+substring.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

        )
        text = spannable
    } catch (e: Exception) {
        Log.d(
            "ViewExtensions",
            "exception in setColorOfSubstring, text=$text, substring=$substring",
            e
        )
    }

}

fun Fragment.cameraCheckPermission() {
    Dexter.withContext(requireActivity())
        .withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        ).withListener(

            object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            camera()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    showRorationalDialogForPermission()
                }

            }
        ).onSameThread().check()
}

fun Fragment.camera() {


    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, CAMERA_REQUEST_CODE)

}

fun Fragment.gallery() {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    startActivityForResult(intent, GALLERY_REQUEST_CODE)
}
fun Fragment.galleryCheckPermission() {
    Dexter.withContext(requireActivity()).withPermission(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ).withListener(object : PermissionListener {
        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
            gallery()
        }

        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
            Toast.makeText(
                requireActivity(), "You have denied the storage permission to selecet image",
                Toast.LENGTH_SHORT
            ).show()
            showRorationalDialogForPermission()
        }

        override fun onPermissionRationaleShouldBeShown(
            p0: PermissionRequest?,
            p1: PermissionToken?
        ) {
            showRorationalDialogForPermission()
        }
    }).onSameThread().check()
}

fun Fragment.showRorationalDialogForPermission() {

    AlertDialog.Builder(requireActivity()).setMessage(
        "It looks like you have turned off permissions"
                + "required for this feature. It can be enable under App Settings!!"
    )
        .setPositiveButton("GO TO SETTINGS") { _, _ ->
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)


            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.dismiss()
        }.show()
}


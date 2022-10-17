package com.learn.vocabulary.ui.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.room.Room
import coil.load
import com.learn.vocabulary.R
import com.learn.vocabulary.data.local.AppDatabase
import com.learn.vocabulary.data.local.UserDao
import com.learn.vocabulary.data.local.UserEntity
import com.learn.vocabulary.databinding.FragmentProfileBinding
import com.learn.vocabulary.util.Constant.USER_DATABASE
import com.learn.vocabulary.util.extensions.*
import java.io.ByteArrayOutputStream


@Suppress("SENSELESS_COMPARISON")
class ProfileFragment : Fragment() {
    private var isEditEnable = false
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var appSettingPrefs: SharedPreferences
    lateinit var sharedPrefsEdit: SharedPreferences.Editor
    var photo: ByteArray? = null
    private lateinit var userEntity: UserEntity
    private lateinit var user: UserEntity

    private val userDB: AppDatabase by lazy {
        Room.databaseBuilder(requireActivity(), AppDatabase::class.java, USER_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = userDB.userDao().getUser(1)
        if (user != null) {
            val decodedImage = user?.userPhoto.let {
                BitmapFactory.decodeByteArray(
                    it,
                    0,
                    it?.size ?: 0
                )
            }
            binding.userPhoto.setImageBitmap(decodedImage)
            binding.userName.setText(user.userName)
        }

        binding.userPhoto.setOnClickListener {
            showDialog(camera = {
                cameraCheckPermission()
            }, gallery = {
                galleryCheckPermission()
            })
        }

        binding.madenByMyself.setBoldSubstring("Hasan GÜRGÜR")


        binding.edit.setOnClickListener {
            isEditEnable = !isEditEnable
            if (isEditEnable) {
                binding.userName.isEnabled = true
                binding.userName.requestFocus(binding.userName.length())
                binding.edit.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.ic_done)
            } else {

                if (binding.userName.text.isNullOrEmpty()) {
                    Toast.makeText(requireActivity(), "İsim boş olamaz", Toast.LENGTH_SHORT).show()
                } else {
                    binding.userName.isEnabled = false
                    binding.userName.clearFocus()
                    binding.edit.background =
                        ContextCompat.getDrawable(requireActivity(), R.drawable.ic_edit)

                    userEntity = UserEntity(1, binding.userName.text.toString(), user.userPhoto)
                    userDB.userDao().updateTask(userEntity)
                }


            }
        }
        binding.toolbar.title.text = "Profile"
        binding.toolbar.leftIcon.visibility = View.GONE

        binding.darkBtn.setOnClickListener {
            darkModeOnOff()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {


                CAMERA_REQUEST_CODE -> {
                    var bitmap = data?.extras?.get("data") as Bitmap
                    binding.userPhoto.load(bitmap)

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val image = stream.toByteArray()
                    photo = image
                    userEntity = UserEntity(1, user.userName, photo)
                    userDB.userDao().insertPhoto(userEntity)

                    val user = userDB.userDao().getUser(1)
                    if (user != null) {
                        //update et
                        userDB.userDao().updateTask(userEntity)
                    }

                }

                GALLERY_REQUEST_CODE -> {
                    val bitmap = requireActivity().getBitmap(data?.data!!)

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val image = stream.toByteArray()
                    photo = image
                    userEntity = UserEntity(1, user.userName, photo)
                    userDB.userDao().insertPhoto(userEntity)
                    binding.userPhoto.load(data?.data)

                    val user = userDB.userDao().getUser(1)
                    if (user != null) {
                        //update et
                        userDB.userDao().updateTask(userEntity)
                    }

                }
            }
        }
    }

    fun Context.getBitmap(uri: Uri): Bitmap =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                this.contentResolver,
                uri
            )
        )
        else MediaStore.Images.Media.getBitmap(this.contentResolver, uri)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        appSettingPrefs = requireActivity().getSharedPreferences("AppSettingPrefs", 0)
        sharedPrefsEdit = appSettingPrefs.edit()


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun darkModeOnOff() {
        if (appSettingPrefs.getBoolean("NightMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPrefsEdit.putBoolean("NightMode", false)
            sharedPrefsEdit.apply()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPrefsEdit.putBoolean("NightMode", true)
            sharedPrefsEdit.apply()
        }
        if (appSettingPrefs.getBoolean("NightMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}
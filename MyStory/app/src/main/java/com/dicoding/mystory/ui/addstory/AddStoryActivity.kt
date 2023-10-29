package com.dicoding.mystory.ui.addStory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.dicoding.mystory.R
import com.dicoding.mystory.databinding.ActivityAddStoryBinding
import com.dicoding.mystory.ui.camera.CameraActivity
import com.dicoding.mystory.ui.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.dicoding.mystory.ui.camera.CameraActivity.Companion.EXTRA_CAMERAX_IMAGE
import com.dicoding.mystory.ui.main.MainActivity
import com.dicoding.mystory.utils.StoryViewModelFactory
import com.dicoding.mystory.utils.reduceFileImage
import com.dicoding.mystory.utils.showLoading
import com.dicoding.mystory.utils.showToast
import com.dicoding.mystory.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private val addStoryViewModel: AddStoryViewModel by viewModels { StoryViewModelFactory.getInstance(this) }

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.add_story)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.apply {
            btnGallery.setOnClickListener{ startGallery() }
            btnCamera.setOnClickListener{ startCameraX() }
            btnUpload.setOnClickListener{ uploadImage() }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast(this, getString(R.string.permission_request_granted))
            } else {
                showToast(this, getString(R.string.permission_request_denied))
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION,
        ) == PackageManager.PERMISSION_GRANTED

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image Uri", "showImage: $it")
            binding.imgAdd.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            val description = binding.edtDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            addStoryViewModel.uploadFile(description, multipartBody)
            addStoryViewModel.isLoading.observe(this){
                binding.progressBar.showLoading(it)
            }
            addStoryViewModel.uploadResponse.observe(this){ response ->
                if(!response.error){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
            addStoryViewModel.responseMessage.observe(this){
                it.getContentIfNotHandled()?.let { message ->
                    showToast(this@AddStoryActivity, message)
                }
            }
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        const val MAXIMAL_SIZE = 1 * 1024 * 1024
    }
}
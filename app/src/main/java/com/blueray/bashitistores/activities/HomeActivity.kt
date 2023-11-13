package com.blueray.bashitistores.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.blueray.bashitistores.R
import com.blueray.bashitistores.databinding.ActivityHomeBinding
import com.blueray.bashitistores.helpers.HelpersUtils
import com.blueray.bashitistores.helpers.ViewUtils.hide
import com.blueray.bashitistores.helpers.ViewUtils.show


class HomeActivity : AppCompatActivity() {


        private var phoneNumber: String? = null
        private var facebookUrl: String? = null
        private lateinit var binding: ActivityHomeBinding
        private var uploadCallback: ValueCallback<Array<Uri>>? = null
        private var mFileChooserParams: WebChromeClient.FileChooserParams? = null
        private var firstLogin =true

        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE
//            )
            supportActionBar?.hide()

            val selectedLang = intent.getStringExtra("lang") as String




            val settings = binding.webView.settings
            settings.javaScriptEnabled = true // Enable JavaScript
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            binding.webView.webViewClient = WebViewCustom()
            binding.webView.webChromeClient = MyCustomChromeClient()

            // enable pinch zoom
            settings.setBuiltInZoomControls(true)
            settings.setDisplayZoomControls(false)

//            var uid = HelpersUtils.getUID(this)
//            var token  = HelpersUtils.getToken(this)
            if (savedInstanceState == null)
            {
                // check for lang
                if(selectedLang == "en"){
                    binding.webView.loadUrl("https://bashitistores.com/")
                }else{
                    binding.webView.loadUrl("https://bashitistores.com/ar")
                }
            }
        }


        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            binding.webView.saveState(outState)
        }

        override fun onRestoreInstanceState(savedInstanceState: Bundle) {
            super.onRestoreInstanceState(savedInstanceState)
            binding.webView.restoreState(savedInstanceState)
        }

        //make call for number click
        private fun phoneCall(phone: String) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(phone)
            startActivity(intent)
        }

        //open url in app
        @Throws(PackageManager.NameNotFoundException::class)
        private fun openApp(url: String, mPackage: String) {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage(mPackage)
            val applicationInfo: ApplicationInfo =
                packageManager.getApplicationInfo(mPackage, 0)
            if (applicationInfo.enabled) {
                when {
                    intent.resolveActivity(packageManager) != null -> startActivity(intent)
                    url.contains("facebook") -> openBrowser(facebookUrl!!)
                    else -> openBrowser(url)
                }
            }
        }

        //open external browser
        private fun openBrowser(url: String) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (browserIntent.resolveActivity(packageManager) != null) startActivity(
                browserIntent
            ) else Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
        }

        private fun sendEmail(email: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:$email"))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                if (e.message != null) e.printStackTrace()
                Toast.makeText(this, getString(R.string.no_email), Toast.LENGTH_SHORT).show()
            }
        }



        inner class WebViewCustom : WebViewClient() {

//            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
////                return when {
////                    request.url.toString().contains("login") -> {
////                        binding.webView.loadUrl(HelperUtil.getAndroidIdLogin(this@MainActivity))
////                        false
////                    }
////                    request.url.toString().contains(HelperUtil.MY_COURSE) -> {
////                        view.loadUrl(HelperUtil.COURSES_URL)
////                        true
////                    }
////                    request.url.toString().contains(HelperUtil.videosUrl) -> {
////                        openVideoActivity(request.url.toString())
////                        true
////                    }
////                    request.url.host != HelperUtil.HOST_URL -> checkUrl(request.url.toString())
////
////                    else -> false
////                }
//
//                return  false
//            }
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                // Handle SSL errors here
                // For example, you can choose to ignore SSL errors:
                handler?.proceed()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                // Load URLs inside the WebView
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                binding.progressBar.show()
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                binding.progressBar.hide()
//                if(firstLogin){
//                    binding.webView.loadUrl("https://bashitistores.com/")
//                    firstLogin = false
//                }
                super.onPageFinished(view, url)

            }

            //handle external links
            private fun checkUrl(url: String): Boolean {
                return when {
                    url.contains("tel") -> {
                        if (ActivityCompat.checkSelfPermission(
                                this@HomeActivity,
                                Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            phoneNumber = url
                            requestPhoneCallPermission.launch(Manifest.permission.CALL_PHONE)
                        } else
                            phoneCall(url)
                        true
                    }
                    url.contains("whatsapp") -> {
                        try {
                            openApp(url, "com.whatsapp")
                        } catch (e: PackageManager.NameNotFoundException) {
                            Toast.makeText(this@HomeActivity, "لا يوجد تطبيق واتساب", Toast.LENGTH_LONG)
                                .show()
                        }
                        true
                    }
                    url.contains("mailto") -> {
                        sendEmail(url)
                        true
                    }
                    url.contains("facebook") || url.contains("Roots-Online") -> {
                        facebookUrl = url
                        try {
                            openApp("fb://facewebmodal/f?href=${url}", "com.facebook.katana")
                        } catch (e: PackageManager.NameNotFoundException) {
                            openBrowser(url)
                        }
                        true
                    }
                    else -> false
                }
            }
        }

        private inner class MyCustomChromeClient : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                uploadCallback = null
                mFileChooserParams = null
                uploadCallback = filePathCallback
                mFileChooserParams = fileChooserParams
                if (ActivityCompat.checkSelfPermission(
                        this@HomeActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                } else
                    importImageGallery()
                return true
            }
        }

        private fun importImageGallery() {
            val intent = mFileChooserParams?.createIntent()
            openGalleryActivity.launch(intent)
        }

        private val openGalleryActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    uploadCallback?.onReceiveValue(
                        WebChromeClient.FileChooserParams.parseResult(
                            result.resultCode,
                            result.data,
                        )
                    )
                } else
                    uploadCallback?.onReceiveValue(emptyArray())
            }

        private val requestPhoneCallPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    Toast.makeText(this, getString(R.string.granted), Toast.LENGTH_LONG).show()
                    if (!phoneNumber.isNullOrEmpty())
                        phoneCall(phoneNumber!!)
                } else
                    Toast.makeText(
                        this,
                        getString(R.string.denied),
                        Toast.LENGTH_LONG
                    ).show()
            }

        private val requestStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    Toast.makeText(this, getString(R.string.granted), Toast.LENGTH_LONG).show()
                    importImageGallery()
                } else
                    Toast.makeText(
                        this,
                        getString(R.string.denied),
                        Toast.LENGTH_LONG
                    ).show()
            }

        override fun onBackPressed() {
            if (binding.webView.canGoBack())
                binding.webView.goBack()
            else
                super.onBackPressed()
        }
    }
package com.galaxy_techno.buyer.presentation.single_activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.ActivityMainBinding
import com.galaxy_techno.buyer.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    fun shouldShowBottomNavigation(isShown: Boolean) {
        if (isShown) {
            binding.bottomNav.visibility = View.VISIBLE
        } else {
            binding.bottomNav.visibility = View.GONE
        }
    }
    fun shouldShowToolbar(isShown: Boolean) {
        if (isShown) {
            binding.authToolbar.visibility = View.VISIBLE
        } else {
            binding.authToolbar.visibility = View.GONE
        }
    }


    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initialize() {
        super.initialize()
        if (isDarkTheme(this))
            this.setStatusBarColor(ContextCompat.getColor(this, R.color.black))
        else
            this.setStatusBarColor(ContextCompat.getColor(this, R.color.white))
        setUpNavigation()
    }

    private val viewModel: MainViewModel by viewModels()

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    fun Activity.isColorDark(color: Int): Boolean {
        val darkness =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }

    override fun observe() {
        super.observe()
        viewModel.authState.observe(this) {
            setUpNavigation()
        }
    }

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
    }
    private val navController: NavController by lazy {
        navHostFragment.navController
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("ResourceType")
    private fun setUpNavigation() {
        setSupportActionBar(binding.authToolbar)

        binding.bottomNav.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.top_home_navigation,
                R.id.dest_top_favourite,
                R.id.dest_top_cart,
                R.id.dest_top_message,
                R.id.profile_navigation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.dest_top_home) {
            showCustomDialog()
        } else
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun showLoadingDialog(text: String) {
        loadingDialog.apply {
            setMessage(text)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            show()
        }
    }

    fun hideLoadingDialog() {
        loadingDialog.hide()
    }

    private fun Activity.setStatusBarColor(color: Int) {
        var flags = window?.decorView?.systemUiVisibility // get current flag
        if (flags != null) {
            if (isColorDark(color)) {
                flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                window?.decorView?.systemUiVisibility = flags
            } else {
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window?.decorView?.systemUiVisibility = flags
            }
        }
        window?.statusBarColor = color
    }


    private fun showCustomDialog() {
        val exitBtn: Button
        val cancelBtn: Button
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_exit_confirmation)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /** animate dialog with window animation */
        dialog.window!!.setWindowAnimations(R.style.AnimationForDialog)
        dialog.setCanceledOnTouchOutside(false)
        exitBtn = dialog.findViewById(R.id.btnExit) as Button
        cancelBtn = dialog.findViewById(R.id.btnCancel) as Button

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        exitBtn.setOnClickListener {
            //todo delete dataStore userAccessToken key and LoginState
            finish()
        }
        dialog.show()
    }
}
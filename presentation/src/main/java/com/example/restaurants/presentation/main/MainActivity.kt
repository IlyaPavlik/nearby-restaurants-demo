package com.example.restaurants.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.restaurants.domain.common.ext.logger
import com.example.restaurants.presentation.R
import com.example.restaurants.presentation.common.navigation.GlobalNavigator
import com.example.restaurants.presentation.common.navigation.NavigationManager
import com.example.restaurants.presentation.databinding.ActivityMainBinding
import com.example.restaurants.presentation.main.navigation.MainScreen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private val log by logger("MainActivity")

    private lateinit var binding: ActivityMainBinding

    private val navigator = AppNavigator(this, R.id.content)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableMyLocation()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        log.debug("onResumeFragments")
        navigationManager.getGlobalNavigationHolder().setNavigator(GlobalNavigator(this))
        navigationManager.getNavigationHolder(MainScreen).setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        log.debug("onPause")
        navigationManager.getNavigationHolder(MainScreen).removeNavigator()
        navigationManager.getGlobalNavigationHolder().removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        log.debug("onDestroy, finishing: $isFinishing")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                val fineLocationGranted = isPermissionGranted(
                    permissions,
                    grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if (fineLocationGranted) {
                    viewModel.openMap()
                } else {
                    log.warn("Permissions are not granted")
                    finish()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private fun enableMyLocation() {
        val fineLocationGranted = isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
        if (fineLocationGranted) {
            viewModel.openMap()
        } else {
            requestPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     * Requests the fine location permission. If a rationale with an additional explanation should
     * be shown to the user, displays a dialog that triggers the request.
     */
    private fun requestPermission(activity: AppCompatActivity, vararg permissions: String) {
        val shouldShowRationale = permissions.all { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }

        if (shouldShowRationale) {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.permission_title))
                .setMessage(getString(R.string.location_permission_denied))
                .setPositiveButton(getString(R.string.permission_settings)) { _, _ -> viewModel.openAppSettings() }
                .setNegativeButton(getString(R.string.permission_exit)) { _, _ -> finish() }
                .setCancelable(false)
                .show()
        } else {
            // Location permission has not been granted yet, request it.
            ActivityCompat.requestPermissions(
                activity,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    /**
     * Checks if the result contains a [PackageManager.PERMISSION_GRANTED] result for a
     * permission from a runtime permissions request.
     *
     * @see androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
     */
    private fun isPermissionGranted(
        grantPermissions: Array<String>,
        grantResults: IntArray,
        permission: String
    ): Boolean {
        for (i in grantPermissions.indices) {
            if (permission == grantPermissions[i]) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}
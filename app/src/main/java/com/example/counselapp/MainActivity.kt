package com.example.counselapp

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.*

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.counselapp.base.BaseActivity
import java.util.jar.Manifest
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.DialogInterface
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AlertDialog


class MainActivity : BaseActivity() {
    override fun initPresenter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    val TAG = "MainActivity"

    val PERMISSIONS_REQUEST_CODE = 1000;
    val PERMISSIONS  = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    fun hasPermissions(permissions: Array<String>): Boolean{
        var result: Int
        for (i in permissions){
            result = ContextCompat.checkSelfPermission(this, i)
            if(result==PackageManager.PERMISSION_DENIED){
                return false
            }
        }
        return true;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, (VERSION.SDK_INT >=VERSION_CODES.M).toString())

        // 버전 정보 확인 -> 카메라 접근 등 permission
        if(VERSION.SDK_INT >=VERSION_CODES.M){
            if(!hasPermissions(PERMISSIONS)){ //퍼미션 확인
                // 허가 안되어있으면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            }else{
                // 허가 다 되어있으면 인텐트로 넘어가기
                val nextIntent = Intent(this, LogInActivity::class.java)
                startActivity(nextIntent)
                finish()
            }
        }
    }

    // permission허가 결과 전달되었을 때 호출
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            // request성공 시
            PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0) { // 
                    val cameraPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED //카메라
                    val diskPermissionAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED // 외부저장소 쓰기

                    if (!cameraPermissionAccepted || !diskPermissionAccepted) {
                        showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.")
                    } else {
                        val nextIntent = Intent(this, LogInActivity::class.java)
                        startActivity(nextIntent)
                        finish()
                    }
                }
            }
        }
    }

    @TargetApi(VERSION_CODES.M)
    fun showDialogForPermission(msg: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("알림")
        builder.setMessage(msg)
        builder.setCancelable(false)
        builder.setPositiveButton("예") { dialog, id -> requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE) }
        builder.setNegativeButton("아니오") { arg0, arg1 -> finish() }
        builder.create().show()
    }
    

}

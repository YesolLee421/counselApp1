package com.example.counselapp.expInfo

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.counselapp.LogInActivity
import com.example.counselapp.R
import com.example.counselapp.base.BaseActivity_noMVP
import com.example.counselapp.model.Expert
import com.example.counselapp.myPage.MyPageExpActivity
import com.example.counselapp.retrofit.CounselAppService
import com.example.counselapp.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_write_expert_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class WriteExpertProfileActivity : BaseActivity_noMVP() {

    var TAG = "WriteExpertProfileActivity"
    var portrait_uri: Uri? = null

    // retrofitClient, service 객체 생성
    val retrofitClient: Retrofit = RetrofitClient.instance
    lateinit var service: CounselAppService
    lateinit var expert: Expert
    lateinit var expertId: String
    var isFirst: Boolean = false

    val PERMISSIONS_REQUEST_CODE = 1000;
    val IMAGE_CAPTURE_CODE = 2001;
    val GALLERY_CODE = 2000;
    val PERMISSIONS  = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    // 퍼미션 확인하는 함수
    fun hasPermissions(permissions: Array<String>): Boolean{
        var result: Int
        for (i in permissions){
            result = ContextCompat.checkSelfPermission(this, i)
            if(result== PackageManager.PERMISSION_DENIED){
                return false
            }
        }
        return true;
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
                        showDialogForPermission("앱을 실행하려면 권한을 허가하셔야합니다.")
                    } else {
                        val nextIntent = Intent(this, LogInActivity::class.java)
                        startActivity(nextIntent)
                        finish()
                    }
                }
            }
        }
    }

    // 허가알림 다이얼로그
    @TargetApi(Build.VERSION_CODES.M)
    fun showDialogForPermission(msg: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("알림")
        builder.setMessage(msg)
        builder.setCancelable(false)
        builder.setPositiveButton("예") { dialog, id -> requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE) }
        builder.setNegativeButton("아니오") { arg0, arg1 -> finish() }
        builder.create().show()
    }

    // 사진 선택 다이얼로그
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("갤러리에서 사진 가져오기", "카메라로 사진 촬영")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun choosePhotoFromGallary() {
        // ACTION_PICK 다음에 외부저장소 명시 안 해주니 자꾸 구글 드라이브에서 사진 다운로드만 한다..
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_CODE)
    }

    private fun takePhotoFromCamera() {
        val portrait = ContentValues() // 찍은 사진의 uri 저장
        portrait_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, portrait)
        
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, portrait_uri)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // (카메라, 앨범) 액티비티 성공
        if(resultCode==Activity.RESULT_OK){
            when(requestCode){
                IMAGE_CAPTURE_CODE->img_writeExpert_portrait.setImageURI(portrait_uri) // 사진도 data로 바꿔봤는데 안 됨
                GALLERY_CODE->img_writeExpert_portrait.setImageURI(data!!.data) // 갤러리 선택한 것은 data로 uri바로 가져올 수 있음
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_expert_profile)

        expertId= intent.getStringExtra("expertId")
        isFirst = intent.getBooleanExtra("isFirst",false)

        // 서비스 시작
        service = retrofitClient.create(CounselAppService::class.java)

        // 사진 선택 버튼 클릭
        btn_writeExpert_selectImg.setOnClickListener {
            // 버전 정보 확인 -> 카메라 접근 등 permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(!hasPermissions(PERMISSIONS)){ //퍼미션 확인
                    // 허가 안되어있으면 사용자에게 요청
                    requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE)
                }else{
                    // 허가되어있으면 사진 선택 다이얼로그 호출
                    showPictureDialog()
                }
            }
        }

        // 저장 버튼 클릭
        btn_writeExpert_save.setOnClickListener {

            val name_formal = et_writeExpert_name_formal.text.toString()
            val about = et_writeExpert_about.text.toString()
            val belongTo = et_writeExpert_belongTo.text.toString()
            val career = et_writeExpert_career.text.toString()
            val certificate = et_writeExpert_certificate.text.toString()
            val education = et_writeExpert_education.text.toString()
            val major = et_writeExpert_major.text.toString()
            // 이 전에 사진 클릭해서 portrait 변수에 파일명 등 넣어야 함
            val portrait: String? = null
            val call = service.updateExpert(name_formal, about,belongTo,education,career,certificate, major, portrait)

            if(name_formal!=""){
                call.enqueue(object : Callback<String>{
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        getLog(TAG,"onFailure: ${t.message}")
                        showToast(t.message.toString(), this@WriteExpertProfileActivity)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.code()==200){
                            getLog(TAG,"onResponse: 성공")
                            showToast(response.body().toString(), this@WriteExpertProfileActivity)
                            // 여기서 갈라야 함
                            // 회원가입하면서 첫 생성 -> 로그인 이동
                            if(isFirst){
                                val intent = Intent(this@WriteExpertProfileActivity, LogInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this@WriteExpertProfileActivity, MyPageExpActivity::class.java)
                                intent.putExtra("expertId", expertId)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                })
            }else{
                showToast("실명은 반드시 입력해야 합니다.", this)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val call = service.getExpert(expertId)
        call.enqueue(object : Callback<Expert>{
            override fun onFailure(call: Call<Expert>, t: Throwable) {
                getLog(TAG,"onFailure: ${t.message}")
                showToast(t.message.toString(), this@WriteExpertProfileActivity)
            }

            override fun onResponse(call: Call<Expert>, response: Response<Expert>) {
                if(response.code()==200){
                    expert = response.body()!!
                    et_writeExpert_name_formal.setText(expert.name_formal)
                    et_writeExpert_about.setText(expert.about)
                    et_writeExpert_belongTo.setText(expert.belongTo)
                    et_writeExpert_career.setText(expert.career)
                    et_writeExpert_certificate.setText(expert.certificate)
                    et_writeExpert_education.setText(expert.education)
                    et_writeExpert_major.setText(expert.major)
                    // 프로필 사진 넣어주기
                    if(expert.portrait!=null){
                        Picasso.get().load(expert.portrait).into(img_writeExpert_portrait)
                    }
                }
            }
        })
    }
}

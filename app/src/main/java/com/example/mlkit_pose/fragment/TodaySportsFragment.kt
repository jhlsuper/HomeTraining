package com.example.mlkit_pose.fragment


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mlkit_pose.JSP
import com.example.mlkit_pose.PageActivity
import com.example.mlkit_pose.R
import kotlinx.android.synthetic.main.fragment_guide_sports.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class TodaySportsFragment : Fragment() {

    private var param2: String? = null

    var number: Int = 0

    var bitmap: Bitmap? = null
    var eturl: String? = null
    var result2: String? = null
    var exname: String? = null
    var exname_k: String? = null
    var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString("id")
            exname = it.getString("exname")
            exname_k = it.getString("kename")
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guide_sports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        sport_detail_name.text = exname
        result2 = exname

        val url = JSP.getSportsDetail(result2.toString())
        val queue = Volley.newRequestQueue(context)
//        val url = "Guide.jsp?guidename=브릿지"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                response.trim { it <= ' ' }
                val arr2 = response.split("$").toTypedArray()
                if (response == "error") {
                    Log.d("error", "error")
                } else {

                    sport_detail_text.text = arr2[0]


                    eturl = arr2[1]
                    // Setting Eng name
                    sport_detail_ename.text = arr2[2]
                    exname_k = sport_detail_name.text as String?
                    val testTEXT = arr2[2]
                    exname = arr2[2]
                }
            },
            { })
        queue.add(stringRequest)

        val uThread: Thread = object : Thread() {
            override fun run() {
                try {
                    val url = URL(JSP.getSportsPic(result2.toString()))
//                    Log.d("geturl",url.toString())

                    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    conn.doInput = true //Server 통신에서 입력 가능한 상태로 만듦
                    conn.connect() //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
                    val `is`: InputStream = conn.inputStream //inputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(`is`) // Bitmap으로 반환
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        uThread.start() // 작업 Thread 실행

        try {
            uThread.join()
            pengsu.setImageBitmap(bitmap)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        sports_link_check.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eturl))
            startActivity(intent)
        }


        btn_start_exercise.setOnClickListener {

            var flag = false


            val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.CAMERA
            )
            if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.RECORD_AUDIO,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), 1001
                )
                Toast.makeText(requireContext(), "거부할 시 카메라 사용에 문제가 있을 수 있습니다.", Toast.LENGTH_LONG)
                    .show()
            } else {
                flag = true
                (activity as PageActivity).showTimeSettingPopup(exname, exname_k, requireContext())
            }
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED && !flag
            ) {
                (activity as PageActivity).showTimeSettingPopup(exname, exname_k, requireContext())
            }

        }
        btn_add_routine.setOnClickListener {
            MyRoutinePopup()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun MyRoutinePopup() {

        val list: MutableList<Model> by lazy {
            mutableListOf<Model>()
        }
        Log.d("userId", "$id")
        val dialog = AlertDialog.Builder(context).create()
        val edialog: LayoutInflater = LayoutInflater.from(context)
        val mView: View = edialog.inflate(R.layout.popup_add_myroutine, null) //팝업창을 띄우는 코드
        val insert_button: Button = mView.findViewById<Button>(R.id.add_to_routine)
//        val checkbox:CheckBox =mView.findViewById<CheckBox>(R.id.checkBox)
        val cancel_button: Button = mView.findViewById<Button>(R.id.add_to_routine_cancel)
        val recyclerView: RecyclerView = mView.findViewById(R.id.recyclerView)
        val adapter = Adapter(list, R.layout.item_model, requireContext())
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )

        // 루틴 이름들 셋팅
        val queue = Volley.newRequestQueue(context)
        val url: String = JSP.getRoutineCheck(id!!)
        val stringRequest1 = StringRequest(Request.Method.GET, url, { response ->
            response.trim { it <= ' ' }
            val routineList = response.split(",")
            for ((i, item) in routineList.withIndex()) {
                if (i == routineList.size - 1) {
                    break
                }
                Log.d("GuideClick", "${item}, ${i}, ${routineList.size}")
                list.add(Model(item, i + 1))
            }
            adapter.notifyDataSetChanged()
        }, { Log.d("GuideClick", "Volley Error") })
        queue.add(stringRequest1)

        // 루틴에 추가 버튼 누를 때 --> Volley 처리
        insert_button.setOnClickListener {
            Log.d(
                "GuideClick",
                "Insert to ${adapter.selectedItem}, item : ${exname}, ${sport_detail_name.text}"
            )
            val queue = Volley.newRequestQueue(context)
            val urls: ArrayList<StringRequest> = ArrayList<StringRequest>()
            for (item: String in adapter.selectedItem) {
                val url_item: String = JSP.setRoutineInsOne(id!!, item, exname_k!!, exname!!)
                val stringRequests = StringRequest(Request.Method.GET, url_item, { response ->
                    response.trim { it <= ' ' }
                    Log.d("GuideClick_Insert", response)
                }, {
                    Log.d("GuideClick", "Volley Error")
                })
                urls.add(stringRequests)
            }
            for (requests: StringRequest in urls) {
                queue.add(requests)
            }
            dialog.dismiss()
        }
        // 취소 버튼 누를 때
        cancel_button.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setView(mView)
        dialog.create()
        dialog.show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1001 ->
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    (activity as PageActivity).showTimeSettingPopup(
                        exname,
                        exname_k,
                        requireContext()
                    )
                }
            //Alert 만들 것
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuideSportsFragment().apply {
                arguments = Bundle().apply {
                    putString("id", param1)
                }
            }
    }
}





package com.example.mlkit_pose.fragment.expre

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mlkit_pose.JSP
import com.example.mlkit_pose.R
import kotlinx.android.synthetic.main.edit_box.*
import kotlinx.android.synthetic.main.fragment_my_routine.*
import kotlinx.android.synthetic.main.popup_routine_add.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RoutineFragment : Fragment() {

    private var id: String? = null
    private var param2: String? = null
    lateinit var adapter: ItemAdapter

        //체크된 내용 기억
    val checkedItemList = ArrayList<String>()  // 선택된 항목을 담는 리스트
    val RoutineList = ArrayList<String>()  // 선택된 항목을 담는 리스트
    val RoutinesportsList = ArrayList<String>()  // 선택된 항목을 담는 리스트


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            id = it.getString("id")
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_routine, container, false)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: CustomRecyclerView =
            view.findViewById(R.id.recylcerview) as CustomRecyclerView
        adapter = ItemAdapter()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context))
        recyclerView.layoutManager = ItemLayoutManager(context)

        add_routineButton.setOnClickListener {
            var newRoutineName:String = ""
            val itemArray = arrayOf<String>(
                "푸쉬업", "풀 플랭크", "백 리프트", "슈퍼맨 운동", "덤벨 숄더 프레스", "덤벨 사이드 레터럴 레이즈",
                "업다운 플랭크", "덤벨 교차 운동", "레그레이즈", "시티드 니업", "버드독", "데드 버그",
                "V 싯업", "포워드 밴드", "한 발로 땅 짚기", "와이드 스쿼트", "사이드 레그레이즈", "밴드 사이드 스텝", "런지", "브릿지"
            )
            val itemEnArray = arrayOf<String>(
                "PushUp","FullPlank","BackLift","Superman","ShoulderPressDB","SideLateralRaiseDB",
                "UpDownPlank","CrossoverExerciseDB","LegRaise","SeatedKneeup","BirdDog","DeadBug","SitUp",
                "ElbowPlank","SideBandDB","ForwardBand","WideSquat","SideLegRaise","Lunge","Bridge","BandSideStep"
            )
            val checkedItems = booleanArrayOf(
                false, false, false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false
            )

            /* Exercise Check Part */
            val setExercise:AlertDialog.Builder = AlertDialog.Builder(context)
                .setTitle("CheckList Test")
            setExercise.setMultiChoiceItems(itemArray,checkedItems) { dialog, which, isChekced ->
                checkedItems[which] = isChekced
            }
            setExercise.setPositiveButton("확인",DialogInterface.OnClickListener(){ dialog,which->

                var texts:String = ""
                var engtexts:String =""
                for (i in 0 until itemArray.size){
                    val checked = checkedItems[i]
                    if (checked) {
                        texts += "${itemArray[i]},"
                        engtexts += "${itemEnArray[i]},"
                    }
                }
                setRoutine(id.toString(),newRoutineName,texts,engtexts)

                // write down volley code here

                Log.d("ROUTINE_LIST",texts+engtexts)
             })
            .setNegativeButton("취소",DialogInterface.OnClickListener(){ dialog, which ->
                dialog.dismiss()
            })
            setExercise.create()

            /* Input Routine Name Part */
            val inputName = layoutInflater.inflate(R.layout.popup_routine_add,null)
            val setRoutineName: AlertDialog.Builder = AlertDialog.Builder(context)
                .setView(inputName)
                .setPositiveButton("확인",DialogInterface.OnClickListener(){ dialog,which->
                    newRoutineName = inputName.findViewById<EditText>(R.id.editRoutineName).text.toString()
                    // write down volley code here


                    Log.d("ROUTINE_SET","inputName : $newRoutineName")
                    setExercise.show()
                })
                .setNegativeButton("취소",DialogInterface.OnClickListener(){
                        dialog, which -> dialog.dismiss()
                })
            setRoutineName.create().show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    private fun setRoutine(id:String,routineName:String,exerciseName:String,exerciseEnName:String){
        val queue = Volley.newRequestQueue(context)
        val url_setRoutine = JSP.setRoutineIns(id,routineName,exerciseName,exerciseEnName)
        val StringRequest = StringRequest(Request.Method.GET,url_setRoutine, { response ->
            response.trim { it <= ' ' }
            Toast.makeText(context,"$response, 보내졌습니다.",Toast.LENGTH_SHORT).show()

        }, {
            Toast.makeText(context,"sever error",Toast.LENGTH_SHORT).show()
        })
        queue.add(StringRequest)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RoutineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }


}
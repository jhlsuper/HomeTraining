package com.example.mlkit_pose


import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
//import com.example.mlkit_pose.databinding.ActivityMainBinding
import com.example.mlkit_pose.fragment.*
import com.example.mlkit_pose.kotlin.CameraXLivePreviewActivity
import com.example.mlkit_pose.kotlin.SettingLivePreviewActivity
import com.google.android.material.navigation.NavigationView
import com.google.mlkit.common.MlKit
import kotlinx.android.synthetic.main.fragment_main_page_part.*
import kotlinx.android.synthetic.main.fragment_ranking_main.*
import kotlinx.android.synthetic.main.fragment_tool_bar.*
import kotlinx.android.synthetic.main.main_drawer_header.*


class PageActivity : AppCompatActivity() ,View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var logoutButton: Button
    lateinit var userRankAdapter: UserRkAdapter
    val datas = mutableListOf<User>()



    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = sharedManager.getCurrentUser()
//        binding = ActivityMainBinding.inflate(layoutInflater)
        val transaction = supportFragmentManager.beginTransaction()
        setContentView(R.layout.fragment_main_page_part)
        transaction.add(R.id.frameLayout, HomeFragment())
        transaction.commit()

        setSupportActionBar(main_layout_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //드로어를 꺼낼 홈버튼 활상화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_menu)
        supportActionBar?.setDisplayShowTitleEnabled(false)  //타이틀 안보이게
        btn_home.setOnClickListener(this)
        btn_guide.setOnClickListener(this)
        btn_mypage.setOnClickListener(this)
        btn_routine.setOnClickListener(this)
        btn_ranking.setOnClickListener(this)
        btn_drawer.setOnClickListener(this)

        main_navigationView.setNavigationItemSelectedListener(this)
//        drawer_logoutButton.setOnClickListener(this)
//        setUserRank()
//        setRankData()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //툴바 버튼 처리
        when (item.itemId) {
            android.R.id.home -> {
                main_drawer_layout.openDrawer(GravityCompat.START)

            }

        }
        return super.onOptionsItemSelected(item)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_drawer_logout -> logOut()
        }
        return false
    }

    override fun onBackPressed() {
        //뒤로가기 버튼 처리
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawers()

        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(v: View) {
//        val datas = mutableListOf<User>()
        val currentUser = sharedManager.getCurrentUser()

        val transaction = supportFragmentManager.beginTransaction()
        when (v.id) {
            R.id.btn_home -> {
                setDataAtFragment(HomeFragment(), TAG_HOME_FRAGMENT)
            }
            R.id.btn_guide -> {
                setDataAtFragment(GuideMainFragment(), TAG_GUIDE_FRAGMENT)
            }
            R.id.btn_mypage -> {
                setDataAtFragment(MyPageFragment(), TAG_MYPAGE_FRAGMENT)
            }
            R.id.btn_ranking -> {
//                setRankData()
                setDataAtFragment(RankingMainFragment(), TAG_RANK_FRAGMENT)

            }
            R.id.btn_routine -> {
                setDataAtFragment(MyRoutineFragment(), TAG_ROUTINE_FRAGMENT)
            }
            R.id.btn_drawer -> {
                info_user_id.setText("${currentUser.id}")
                info_user_belong.setText("${currentUser.belong}")

                main_drawer_layout.openDrawer(GravityCompat.START)

            }



        }
    }



    fun logOut() {
        val currentUser = sharedManager.getCurrentUser()
        sharedManager.DeleteCurrentUser(currentUser)
        startActivity(Intent(this, MainActivity::class.java))
        finish()


    }
    private fun setDataAtFragment(fragment: Fragment, tag: String){
        val currentUser = sharedManager.getCurrentUser()
        val bundle = Bundle()
        bundle.putString("id",currentUser.id)
        bundle.putString("name",currentUser.name)
        bundle.putString("gender",currentUser.gender)
        bundle.putString("belong",currentUser.belong)
        bundle.putString("points",currentUser.points)
        fragment.arguments =bundle
        setFragment(fragment,tag)
    }
    private fun setFragment(fragment:Fragment, tag:String){

        val transaction = supportFragmentManager.beginTransaction()
        val manager = supportFragmentManager

        if (manager.findFragmentByTag(tag)==null){
            transaction.add(R.id.frameLayout,fragment,tag)
            transaction.show(fragment)
        }
        val home = manager.findFragmentByTag(TAG_HOME_FRAGMENT)
        val rank = manager.findFragmentByTag(TAG_RANK_FRAGMENT)
        val routine = manager.findFragmentByTag(TAG_ROUTINE_FRAGMENT)
        val guide = manager.findFragmentByTag(TAG_GUIDE_FRAGMENT)
        val mypage = manager.findFragmentByTag(TAG_MYPAGE_FRAGMENT)
        val guide_click = manager.findFragmentByTag(TAG_GUIDE_CLICK_FRAGMENT)
        val guide_sport = manager.findFragmentByTag(TAG_GUIDE_SPORT_FRAGMENT)

        if (home!= null){
//            transaction.remove(home)
                transaction.hide(home)
            Log.d("fragment","home hide")
        }
        if (rank!= null){
            transaction.hide(rank)
//            transaction.detach(rank)
        }
        if (routine!= null){
            transaction.hide(routine)
//            transaction.remove(routine)
        }
        if (guide!= null){
            transaction.hide(guide)
//            transaction.remove(guide)
        }
        if (mypage!= null){

            transaction.hide(mypage)
        }
        if(guide_click !=null){
            transaction.remove(guide_click)
            Log.d("fragment","gudie click removed")
        }
        if(guide_sport !=null){
            transaction.remove(guide_sport)
            Log.d("fragment","guide sport removed")
        }

        when (tag){
            TAG_HOME_FRAGMENT->{

                if(home!=null){
                    transaction.show(home)
                }
            }
            TAG_RANK_FRAGMENT->{

                if(rank!=null){
                    transaction.show(rank)
                }
            }
            TAG_ROUTINE_FRAGMENT->{

                if(routine!=null){
                    transaction.show(routine)
                }
            }
            TAG_GUIDE_FRAGMENT->{

                if(guide!=null){
                    if (guide_click != null) {
                        transaction.remove(guide_click)
                    }
                    if (guide_sport != null) {
                        transaction.remove(guide_sport)
                    }
                    transaction.show(guide)
                }
            }

            TAG_MYPAGE_FRAGMENT ->{
                if(mypage!=null){
                   transaction.show(mypage)
                }
            }
        }
//        manager.popBackStack(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        transaction.detach(fragment).attach(fragment).addToBackStack(fragName).commit()
//        transaction.replace(R.id.frameLayout,fragment)
//        transaction.addToBackStack(null)
//        transaction.addToBackStack(tag)

    //        transaction.replace(R.id.frameLayout,fragment,tag)

    //        transaction.commitAllowingStateLoss()
        transaction.commit()
    }

    fun initRecycler(){
        userRankAdapter = UserRkAdapter(this)
        rankingRecyclerView?.adapter = userRankAdapter
        userRankAdapter.datas =datas
        userRankAdapter.notifyDataSetChanged()
    }
    fun emptyRecycler(){
        userRankAdapter = UserRkAdapter(this)
        rankingRecyclerView?.adapter = null


    }


    private fun setUserRank(){
        val queue = Volley.newRequestQueue(this)
        var currentUser = sharedManager.getCurrentUser()

        val url_getUserRank = JSP.getUserRank(currentUser.id.toString())

        val StringRequest2 = StringRequest(
                Request.Method.GET, url_getUserRank, { response ->
            response.trim { it <= ' ' }

            val details2 = (response.trim().split(",")).toTypedArray()
            val userPoint :Int
//            Toast.makeText(this, "유저의 운동 points:${details2[2]}", Toast.LENGTH_SHORT).show()
            if (details2[2]==null){

                sharedManager.setUserPoint(currentUser,0)
            }
            else{
                sharedManager.setUserPoint(currentUser,details2[2].toInt())
            }
                                                     }, {
            Toast.makeText(this, "server error", Toast.LENGTH_SHORT).show()
        })
        queue.add(StringRequest2)

    }
     fun setRankData(){
        val queue = Volley.newRequestQueue(this)
        var currentUser = sharedManager.getCurrentUser()
        val url_getEveryRank = JSP.getEveryRank(currentUser.belong.toString())

        val StringRequest3 = StringRequest(
                Request.Method.GET, url_getEveryRank, { response ->
            response.trim { it <= ' ' }

            val details3 = (response.trim().split(",")).toTypedArray()
            datas.clear()
            for (i in 0 until (details3.size)-3 step 3){
//                Log.d("ranklist",details3.size.toString())
//                Log.d("ranklist",details3[i])
                datas.apply{
                    add(User(img = R.drawable.penguin, id = details3[i+1],points = details3[i+2]))

                }
                Log.d("ranklist","${details3[i+1]} ${details3[i+2]}")
            }
            initRecycler()

        }, {
            Toast.makeText(this, "server error", Toast.LENGTH_SHORT).show()
        })
        queue.add(StringRequest3)
    }
    fun startExcercise(exname:String?){
        // 점수 계산을 위한 운동 시간 설정

        // 안내
//        Handler().postDelayed({
//            val intent = Intent(this,SettingLivePreviewActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            startActivity(intent)
//        },5000)
        // 카메라 사용 시작
        val intent =Intent(this,SettingLivePreviewActivity::class.java)
        intent.putExtra("ExcerciseName",exname);
        startActivity(intent)
        // 카메라 사용 끝

        // 결과 화면

    }




    companion object {
        private const val TAG_HOME_FRAGMENT = "home"
        private const val TAG_RANK_FRAGMENT = "rank"
        private const val TAG_ROUTINE_FRAGMENT="routine"
        private const val TAG_GUIDE_FRAGMENT="guide"
        private const val TAG_MYPAGE_FRAGMENT="mypage"
        private const val TAG_GUIDE_CLICK_FRAGMENT="guide_click"
        private const val TAG_GUIDE_SPORT_FRAGMENT="guide_sport"
    }



}





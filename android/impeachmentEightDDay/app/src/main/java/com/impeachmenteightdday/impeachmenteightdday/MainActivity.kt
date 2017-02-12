package com.impeachmenteightdday.impeachmenteightdday


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.impeachmenteightdday.impeachmenteightdday.server.NetDefine
import com.impeachmenteightdday.impeachmenteightdday.server.ServerQuery
import com.impeachmenteightdday.impeachmenteightdday.server.response.CommonResults
import com.impeachmenteightdday.impeachmenteightdday.server.response.GetImpeachmentAndMemberCountResponse
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    var currentScreenNumber = 1
    var dDayTime: Long = 0
    var memberNum: Long = 1
    var nickName: String = ""
    lateinit var timer: Timer
    lateinit var adapter: ChateAdapter
    var socket: Socket? = null
    var isInAnimation: Boolean = false
    val randomImage: Int =
            when (Random().nextInt(3)) {
                else -> R.drawable.image1
            }
    var isAutoScrollLast = true
    val animTransRightIn by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                            relativeLayout_secondScreen_1?.visibility = View.VISIBLE
                        }
                    })
                }
    }
    val animTransRightOut by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_right)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            relativeLayout_secondScreen_2?.visibility = View.GONE
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                        }
                    })
                }
    }
    val animTransLeftIn by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                            relativeLayout_secondScreen_2?.visibility = View.VISIBLE
                        }
                    })
                }
    }
    val animTransLeftOut by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_left)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            relativeLayout_secondScreen_1?.visibility = View.GONE
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                        }
                    })
                }
    }

    val animTransTopIn: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_top)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                            relativeLayout_secondScreen?.visibility = View.VISIBLE
                        }
                    })
                }
    }
    val animTransTopOut: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_top)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            relativeLayout_firstScreen?.visibility = View.GONE
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                        }
                    })
                }
    }
    val animTransBottomIn by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_bottom)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                            relativeLayout_firstScreen?.visibility = View.VISIBLE
                        }
                    })
                }
    }
    val animTransBottomOut by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_bottom)
                .apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            relativeLayout_secondScreen?.visibility = View.GONE
                            isInAnimation = false
                        }

                        override fun onAnimationStart(p0: Animation?) {
                        }
                    })
                }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView_dday.typeface = Typeface.createFromAsset(this.assets, "nanumbrush.otf")
        textView_titleDday.typeface = Typeface.createFromAsset(this.assets, "bmdohyeon.otf")
        textView_expectTitleDday.typeface = Typeface.createFromAsset(this.assets, "bmdohyeon.otf")


        dDayTime = SharedPreferenceUtil.getdDayTime(this)
        Glide.with(this).load(randomImage).centerCrop().into(imageView)
        root.setBackgroundColor(Color.parseColor("#BF000000"))
        timerInit()
        recyclerViewInit()
        socketInit()
        setListener()
        getDdayTime()
    }

    private fun timerInit() {

        timer = Timer()
        timer.schedule(object : TimerTask() {

            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    updateTime()
                }
            }
        }, 0, 1000)
    }

    private fun updateTime() {
        val dDayTime = dDayTime - 60 * 60 * 9
        val text = getDateBeforeTimeText(dDayTime - System.currentTimeMillis() / 1000)
        textView_dday?.text = "탄핵일까지\n$text\n남았습니다."
        textView_dday_screenSecond?.text = "탄핵일까지 $text 남았습니다.\n$memberNum 명이서 대화중."
        val dateFormat = SimpleDateFormat("1차 예정일 : MM월 dd일")
        val titleText = dateFormat.format(Date(dDayTime * 1000))
        textView_expectTitleDday?.text = titleText
    }

    private fun recyclerViewInit() {
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = ChateAdapter()
        recyclerView?.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isAutoScrollLast = (recyclerView?.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: 0 == adapter.itemCount - 1
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    private fun socketInit() {

        socket = IO.socket(NetDefine.BASIC_PATH + "/chat")
        socket?.on(Socket.EVENT_CONNECT) {
            args ->
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(applicationContext, "채팅에 연결 되었습니다", Toast.LENGTH_SHORT).show()
            }
        }?.on("listen/new_comment") {
            args ->
            Handler(Looper.getMainLooper()).post {
                (args.firstOrNull() as? JSONObject)?.let {
                    (it.get("results") as? JSONObject)?.let {
                        val senderName = it.get("sender_name") as? String ?: ""
                        val content = it.get("content") as? String ?: ""
                        adapter.addComment(Comment(senderName, content))
                        if (isAutoScrollLast)
                            recyclerView.scrollToPosition(adapter.itemCount - 1)
                    }
                }
            }
        }?.on("listen/update_member_num") {
            args ->
            Handler(Looper.getMainLooper()).post {
                (args.firstOrNull() as? JSONObject)?.let {
                    (it.get("results") as? JSONObject)?.let {
                        memberNum = it.get("member_num") as? Long ?: memberNum
                    }
                }
            }
        }?.on(Socket.EVENT_DISCONNECT) {
            args ->
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(applicationContext, "채팅에 연결해제 되었습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setListener() {

        button_startChat.setOnClickListener {

            if (!isEnableInternet()) {
                Toast.makeText(applicationContext, "인터넷 연결을 확인하여 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isInAnimation) return@setOnClickListener
            isInAnimation = true
            relativeLayout_firstScreen.startAnimation(animTransTopOut)
            relativeLayout_secondScreen.visibility = View.VISIBLE
            relativeLayout_secondScreen.startAnimation(animTransTopIn)
            currentScreenNumber++
        }
        button_nickNameConfirm.setOnClickListener {
            val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            imm?.hideSoftInputFromWindow(editText_nickName.windowToken, 0)
            val length = editText_nickName?.length() ?: 0
            if (length < 1 || length > 15) {
                Toast.makeText(applicationContext, "닉네임은 1자리 이상 15자리 이하여야 합니다 ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEnableInternet()) {
                Toast.makeText(applicationContext, "인터넷 연결을 확인하여 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            nickName = editText_nickName.text.toString()
            if (isInAnimation) return@setOnClickListener
            isInAnimation = true
            relativeLayout_secondScreen_1.startAnimation(animTransLeftOut)
            relativeLayout_secondScreen_2.visibility = View.VISIBLE
            relativeLayout_secondScreen_2.startAnimation(animTransLeftIn)
            currentScreenNumber++
            socket?.connect()
            adapter.clear()
        }
        button_postComment.setOnClickListener {
            if (editText_comment.text.isEmpty()) {
                Toast.makeText(applicationContext, "입력된 글자가 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            postComment(editText_comment.text.toString())
        }
    }

    private fun getDdayTime() {
        ServerQuery.getImpeachmentAndMemberCount(object : Callback<GetImpeachmentAndMemberCountResponse> {
            override fun onResponse(call: Call<GetImpeachmentAndMemberCountResponse>?, response: Response<GetImpeachmentAndMemberCountResponse>?) {
                response?.let {
                    if (it.isSuccessful) {
                        dDayTime = it.body().impeachmentDatetime ?: dDayTime
                        memberNum = it.body().memberNum ?: memberNum
                        SharedPreferenceUtil.setdDayTime(applicationContext, dDayTime)
                    } else
                        Toast.makeText(applicationContext, "알수없는 오류 입니다", Toast.LENGTH_SHORT).show()
                } ?: Toast.makeText(applicationContext, "알수없는 오류 입니다", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<GetImpeachmentAndMemberCountResponse>?, t: Throwable?) {
                if (t is UnknownHostException)
                    Toast.makeText(applicationContext, "인터넷에 연결되어있지 않습니다", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(applicationContext, "알수없는 오류 입니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun postComment(content: String) {

        ServerQuery.postComment(nickName, content, object : Callback<CommonResults> {

            override fun onResponse(call: Call<CommonResults>?, response: Response<CommonResults>?) {
                response?.let {
                    if (!it.isSuccessful)
                        if (it.code() == 403)
                            Toast.makeText(applicationContext, "도배 방지를 위하여 1초가 지나야 채팅을 보낼 수 있습니다.", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(applicationContext, "알수없는 오류 입니다", Toast.LENGTH_SHORT).show()
                    else {
                        editText_comment.setText("")
                        recyclerView.scrollToPosition(Math.max(adapter.itemCount - 1, 0))
                    }
                } ?: Toast.makeText(applicationContext, "알수없는 오류 입니다", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<CommonResults>?, t: Throwable?) {
                if (t is UnknownHostException)
                    Toast.makeText(applicationContext, "인터넷에 연결되어있지 않습니다", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(applicationContext, "알수없는 오류 입니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun isEnableInternet(): Boolean {
        val manager = (this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
        val mobile = manager?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val wifi = manager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)


        return (wifi?.isConnected ?: false || mobile?.isConnected ?: false)
    }

    override fun onBackPressed() {
        when (currentScreenNumber) {
            1 -> finish()
            2 -> {
                if (isInAnimation) return
                isInAnimation = true
                relativeLayout_firstScreen.startAnimation(animTransBottomIn)
                relativeLayout_secondScreen.startAnimation(animTransBottomOut)
                currentScreenNumber--
            }
            3 -> {
                if (isInAnimation) return
                isInAnimation = true
                relativeLayout_secondScreen_1.startAnimation(animTransRightIn)
                relativeLayout_secondScreen_2.startAnimation(animTransRightOut)
                currentScreenNumber--
                socket?.disconnect()
                adapter.clear()
            }
            else -> finish()
        }
    }

    fun getDateBeforeTimeText(timeOfStudying: Long): String {

        return TimeUnit.SECONDS.toDays(timeOfStudying).toString() + "일 " + TimeUnit.SECONDS.toHours(timeOfStudying) % 24 + "시간 " + TimeUnit.SECONDS.toMinutes(timeOfStudying) % 60 + "분 " + TimeUnit.SECONDS.toSeconds(timeOfStudying) % 60 + "초"
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        socket?.disconnect()
    }
}


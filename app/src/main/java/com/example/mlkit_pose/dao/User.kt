package com.example.mlkit_pose.dao

import android.graphics.Bitmap

data class User (
    var name:String? = null,
    var id:String? = null,
    var password :String? = null,
    var belong: String? = null,
    var age: Int? = 0,
    var gender :String? = null,
    var points :String? = null,
    var height :String? =null,
    var weight :String? =null,
    var img: String?= null,
    var recentDay: String? = null,
    var countDays: Int? =0
)
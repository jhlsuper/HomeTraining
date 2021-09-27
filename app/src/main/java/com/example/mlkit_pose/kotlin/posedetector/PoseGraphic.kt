/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mlkit_pose.kotlin.posedetector

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mlkit_pose.GraphicOverlay
import com.google.common.primitives.Ints
import com.example.mlkit_pose.GraphicOverlay.Graphic
import com.example.mlkit_pose.R
import com.example.mlkit_pose.kotlin.CameraXLivePreviewActivity
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import java.lang.Math.max
import java.lang.Math.min
import java.util.*
import kotlin.math.atan2
import kotlin.concurrent.timer

/** Draw the detected pose in preview.  */
class PoseGraphic internal constructor(
    overlay: GraphicOverlay,
    private val pose: Pose,
    private val showInFrameLikelihood: Boolean,
    private val visualizeZ: Boolean,
    private val rescaleZForVisualization: Boolean,
    private val poseClassification: List<String>,
    private val exName: String?,
    private val isSetting: Boolean) : GraphicOverlay.Graphic(overlay) {
        private var zMin = java.lang.Float.MAX_VALUE
        private var zMax = java.lang.Float.MIN_VALUE
        private val classificationTextPaint: Paint
        private val whitePaint: Paint
        private val wrongPaint: Paint
        private val progPaint: Paint
        private val correctPaint: Paint
        private val poseSearchList : PoseSearcher
        private val nowPose:ExercisePose?
        public var correctArray = Array(8){i-> true}
//        val mediaPlayer2 = MediaPlayer.create(applicationContext,R.raw.beeps)
//        private val cameraXLivePreviewActivity = CameraXLivePreviewActivity.getInstance()
        init {
            classificationTextPaint = Paint()
            classificationTextPaint.color = Color.WHITE
            classificationTextPaint.textSize = POSE_CLASSIFICATION_TEXT_SIZE
            classificationTextPaint.setShadowLayer(5.0f, 0f, 0f, Color.BLACK)
            whitePaint = Paint()
            whitePaint.strokeWidth = STROKE_WIDTH
            whitePaint.color = Color.WHITE
            whitePaint.textSize = IN_FRAME_LIKELIHOOD_TEXT_SIZE
            wrongPaint = Paint()
            wrongPaint.strokeWidth = STROKE_WIDTH
            wrongPaint.color = Color.RED
            progPaint = Paint()
            progPaint.strokeWidth = STROKE_WIDTH
            progPaint.color = Color.YELLOW
            correctPaint = Paint()
            correctPaint.strokeWidth = STROKE_WIDTH
            correctPaint.color = Color.GREEN
            poseSearchList = PoseSearcher()
            nowPose = poseSearchList.searchExByName(exName)
            checkCorrect()
        }
    fun getAngle(firstPoint: PoseLandmark, midPoint: PoseLandmark, lastPoint: PoseLandmark): Double {
        var result = Math.toDegrees(
            (atan2(lastPoint.getPosition().y - midPoint.getPosition().y,
                lastPoint.getPosition().x - midPoint.getPosition().x)
                    - atan2(firstPoint.getPosition().y - midPoint.getPosition().y,
                firstPoint.getPosition().x - midPoint.getPosition().x)).toDouble()
        )
        result = Math.abs(result) // Angle should never be negative
        if (result > 180) {
            result = 360.0 - result // Always get the acute representation of the angle
        }
        return result
    }
    override fun draw(canvas: Canvas) {

//        if(!isSetting) {
//            mediaPlayer2.start() // Music Start
//        }
        val landmarks = pose.allPoseLandmarks
        if (landmarks.isEmpty()) {
            return
        }

        // Draw pose classification text.
        val classificationX = POSE_CLASSIFICATION_TEXT_SIZE * 0.5f
        for (i in poseClassification.indices) {
            val classificationY = canvas.height - (
                    POSE_CLASSIFICATION_TEXT_SIZE * 1.5f * (poseClassification.size - i).toFloat()
                    )
            canvas.drawText(
                poseClassification[i],
                classificationX,
                classificationY,
                classificationTextPaint
            )
        }

        // Draw all the points
        for (landmark in landmarks) {
            drawPoint(canvas, landmark, whitePaint)
            if (visualizeZ && rescaleZForVisualization) {
                zMin = min(zMin, landmark.position3D.z)
                zMax = max(zMax, landmark.position3D.z)
            }
        }
        Log.d("ExerciseName","PoseGraphic IN ENAME $exName")
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

        val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
        val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
        val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
        val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

        /* Get Angle */

        // Hip angle
        val rightHipAngle = getAngle(rightShoulder!!,rightHip!!,rightKnee!!)
        val leftHipAngle = getAngle(leftShoulder!!,leftHip!!,leftKnee!!)
        // Shoulder angle
        val rightShoulderAngle = getAngle(rightHip,rightShoulder,rightElbow!!)
        val leftShoulderAngle = getAngle(leftHip,leftShoulder,leftElbow!!)
        // Elbow Angle
        val rightElbowAngle = getAngle(rightShoulder,rightElbow,rightWrist!!)
        val leftElbowAngle = getAngle(leftShoulder,leftElbow,leftWrist!!)
        // Knee Angle
        val rightKneeAngle = getAngle(rightHip,rightKnee,rightAnkle!!)
        val leftKneeAngle = getAngle(leftHip,leftKnee,leftAnkle!!)

        Log.d("ANGLE","=================\n"+"Right_HipAngle : "+rightHipAngle.toString()+"\nLeft_HipAngle : "+leftHipAngle.toString()+
            "\nRight_ShoulderAngle : "+rightShoulderAngle.toString()+"\nLeft_ShoulderAngle : "+leftShoulderAngle.toString()+"\nRight_ElbowAngle : "+rightElbowAngle.toString()
            +"\nLeft_ElbowAngle : "+leftElbowAngle.toString()+"\nRight_KneeAngle : "+rightKneeAngle.toString()+"\nLeft_KneeAngle : "+leftKneeAngle.toString()
        )


        // Left body
        drawLine(canvas, leftShoulder, rightShoulder, correctPaint)
        drawLine(canvas, leftHip, rightHip, correctPaint)
        drawLine(canvas, leftShoulder, leftElbow, correctPaint)
        drawLine(canvas, leftElbow, leftWrist, correctPaint)
        drawLine(canvas, leftShoulder, leftHip, correctPaint)
        drawLine(canvas, leftHip, leftKnee, correctPaint)
        drawLine(canvas, leftKnee, leftAnkle, correctPaint)
        drawLine(canvas, leftWrist, leftThumb, correctPaint)
        drawLine(canvas, leftWrist, leftPinky, correctPaint)
        drawLine(canvas, leftWrist, leftIndex, correctPaint)
        drawLine(canvas, leftIndex, leftPinky, correctPaint)
        drawLine(canvas, leftAnkle, leftHeel, correctPaint)
        drawLine(canvas, leftHeel, leftFootIndex, correctPaint)

        // Right body
        drawLine(canvas, rightShoulder, rightElbow, correctPaint)
        drawLine(canvas, rightElbow, rightWrist, correctPaint)
        drawLine(canvas, rightShoulder, rightHip, correctPaint)
        drawLine(canvas, rightHip, rightKnee, correctPaint)
        drawLine(canvas, rightKnee, rightAnkle, correctPaint)
        drawLine(canvas, rightWrist, rightThumb, correctPaint)
        drawLine(canvas, rightWrist, rightPinky, correctPaint)
        drawLine(canvas, rightWrist, rightIndex, correctPaint)
        drawLine(canvas, rightIndex, rightPinky, correctPaint)
        drawLine(canvas, rightAnkle, rightHeel, correctPaint)
        drawLine(canvas, rightHeel, rightFootIndex, correctPaint)

        /*
        val rightHipAngle = getAngle(rightShoulder,rightHip,rightKnee)
        val leftHipAngle = getAngle(leftShoulder,leftHip,leftKnee)
        // Shoulder angle
        val rightShoulderAngle = getAngle(rightHip,rightShoulder,rightElbow)
        val leftShoulderAngle = getAngle(leftHip,leftShoulder,leftElbow)
        // Elbow Angle
        val rightElbowAngle = getAngle(rightShoulder,rightElbow,rightWrist)
        val leftElbowAngle = getAngle(leftShoulder,leftElbow,leftWrist)
        // Knee Angle
        val rightKneeAngle = getAngle(rightHip,rightKnee,rightAnkle)
        val leftKneeAngle = getAngle(leftHip,leftKnee,leftAnkle)
         */
        if (nowPose != null) {
            //Right Hip Angle
            if (nowPose.isAngle_rhS(rightHipAngle) && nowPose.getEnable(nowPose.rightHipAngleS)){ // Right Hip
                // UP 각도 내에 있는 경우, 그리고 해당 부위가 활성화 된 경우
                drawLine(canvas,rightShoulder,rightHip,correctPaint)
                drawLine(canvas,rightHip,rightKnee,correctPaint)
            }
            else if (nowPose.isAngle_rhD(rightHipAngle) && nowPose.getEnable(nowPose.rightHipAngleD)){
                // Down 각도 내에 있는 경우, 그리고 해당 부위가 활성화 된 경우
                drawLine(canvas,rightShoulder,rightHip,correctPaint)
                drawLine(canvas,rightHip,rightKnee,correctPaint)
            }
            else if (nowPose.isAngle_rhM(rightHipAngle) && nowPose.getEnable(nowPose.rightHipAngleM)){
                drawLine(canvas,rightShoulder,rightHip,progPaint)
                drawLine(canvas,rightHip,rightKnee,progPaint)
            }
            else if (nowPose.getEnable(nowPose.rightHipAngleS) || nowPose.getEnable(nowPose.rightHipAngleD)){
                // 각도 내에 없지만 활성화된 경우 (틀린것)
                drawLine(canvas,rightShoulder,rightHip,wrongPaint)
                drawLine(canvas,rightHip,rightKnee,wrongPaint)
                drawLine(canvas, leftHip, rightHip, wrongPaint)
            }
            //Left Hip Angle
            if (nowPose.isAngle_lhS(leftHipAngle)&& nowPose.getEnable(nowPose.leftHipAngleS)){
                drawLine(canvas,leftShoulder,leftHip,correctPaint)
                drawLine(canvas,leftHip,leftKnee,correctPaint)
            }
            else if (nowPose.isAngle_lhD(leftHipAngle)&& nowPose.getEnable(nowPose.leftHipAngleD)){
                drawLine(canvas,leftShoulder,leftHip,correctPaint)
                drawLine(canvas,leftHip,leftKnee,correctPaint)
            }
            else if (nowPose.isAngle_lhM(leftHipAngle) && nowPose.getEnable(nowPose.leftHipAngleM)){
                drawLine(canvas,leftShoulder,leftHip,progPaint)
                drawLine(canvas,leftHip,leftKnee,progPaint)
                drawLine(canvas, leftHip, rightHip, progPaint)
            }
            else if (nowPose.getEnable(nowPose.leftHipAngleS) || nowPose.getEnable(nowPose.leftHipAngleD)){
                drawLine(canvas,leftShoulder,leftHip,wrongPaint)
                drawLine(canvas,leftHip,leftKnee,wrongPaint)
                drawLine(canvas, leftHip, rightHip, wrongPaint)
            }
            // Right Shoulder Angle
            if (nowPose.isAngle_rsS(rightShoulderAngle) && nowPose.getEnable(nowPose.rightShoulderAngleS)){
                drawLine(canvas,rightHip,rightShoulder,correctPaint)
                drawLine(canvas,rightShoulder,rightElbow,correctPaint)
            }
            else if (nowPose.isAngle_rsD(rightShoulderAngle) && nowPose.getEnable(nowPose.rightShoulderAngleD)){
                drawLine(canvas,rightHip,rightShoulder,correctPaint)
                drawLine(canvas,rightShoulder,rightElbow,correctPaint)
            }
            else if (nowPose.isAngle_rsM(rightShoulderAngle) && nowPose.getEnable(nowPose.rightShoulderAngleM)){
                drawLine(canvas,rightHip,rightShoulder,progPaint)
                drawLine(canvas,rightShoulder,rightElbow,progPaint)
            }
            else if (nowPose.getEnable(nowPose.rightShoulderAngleS) || nowPose.getEnable(nowPose.rightShoulderAngleD)){
                drawLine(canvas,rightHip,rightShoulder,wrongPaint)
                drawLine(canvas,rightShoulder,rightElbow,wrongPaint)
            }
            // Left Shoulder Angle
            if (nowPose.isAngle_lsS(leftShoulderAngle) && nowPose.getEnable(nowPose.leftShoulderAngleS)){
                drawLine(canvas,leftHip,leftShoulder,correctPaint)
                drawLine(canvas,leftShoulder,leftElbow,correctPaint)
            }
            else if (nowPose.isAngle_lsD(leftShoulderAngle) && nowPose.getEnable(nowPose.leftShoulderAngleD)){
                drawLine(canvas,leftHip,leftShoulder,correctPaint)
                drawLine(canvas,leftShoulder,leftElbow,correctPaint)
            }
            else if (nowPose.isAngle_lsM(leftShoulderAngle) && nowPose.getEnable(nowPose.leftShoulderAngleM)){
                drawLine(canvas,leftHip,leftShoulder,progPaint)
                drawLine(canvas,leftShoulder,leftElbow,progPaint)
            }
            else if (nowPose.getEnable(nowPose.leftShoulderAngleS) || nowPose.getEnable(nowPose.leftShoulderAngleD)){
                drawLine(canvas,leftHip,leftShoulder,wrongPaint)
                drawLine(canvas,leftShoulder,leftElbow,wrongPaint)
            }
            // Right Elbow Angle
            if (nowPose.isAngle_reS(rightElbowAngle) && nowPose.getEnable(nowPose.rightElbowAngleS)){
                //rightShoulder,rightElbow,rightWrist
                drawLine(canvas,rightShoulder,rightElbow,correctPaint)
                drawLine(canvas,rightElbow,rightWrist,correctPaint)
            }
            else if (nowPose.isAngle_reD(rightElbowAngle) && nowPose.getEnable(nowPose.rightElbowAngleD)){
                drawLine(canvas,rightShoulder,rightElbow,correctPaint)
                drawLine(canvas,rightElbow,rightWrist,correctPaint)
            }
            else if (nowPose.isAngle_reM(rightElbowAngle) && nowPose.getEnable(nowPose.rightElbowAngleM)){
                drawLine(canvas,rightShoulder,rightElbow,progPaint)
                drawLine(canvas,rightElbow,rightWrist,progPaint)
            }
            else if (nowPose.getEnable(nowPose.rightElbowAngleD) || nowPose.getEnable(nowPose.rightElbowAngleS)){
                drawLine(canvas,rightShoulder,rightElbow,wrongPaint)
                drawLine(canvas,rightElbow,rightWrist,wrongPaint)
            }
            // Left Elbow Angle
            if (nowPose.isAngle_leS(leftElbowAngle) && nowPose.getEnable(nowPose.leftElbowAngleS)){
                //leftShoulder,leftElbow,leftWrist
                drawLine(canvas,leftShoulder,leftElbow,correctPaint)
                drawLine(canvas,leftElbow,leftWrist,correctPaint)
            }
            else if (nowPose.isAngle_leD(leftElbowAngle) && nowPose.getEnable(nowPose.leftElbowAngleD)){
                drawLine(canvas,leftShoulder,leftElbow,correctPaint)
                drawLine(canvas,leftElbow,leftWrist,correctPaint)
            }
            else if (nowPose.isAngle_leM(leftElbowAngle) && nowPose.getEnable(nowPose.leftElbowAngleM)){
                drawLine(canvas,leftShoulder,leftElbow,progPaint)
                drawLine(canvas,leftElbow,leftWrist,progPaint)
            }
            else if (nowPose.getEnable(nowPose.leftElbowAngleD) || nowPose.getEnable(nowPose.leftElbowAngleS)){
                drawLine(canvas,leftShoulder,leftElbow,wrongPaint)
                drawLine(canvas,leftElbow,leftWrist,wrongPaint)
            }
            // Right Knee Angle
            if (nowPose.isAngle_rkS(rightKneeAngle) && nowPose.getEnable(nowPose.rightKneeAngleS)){
                //rightHip,rightKnee,rightAnkle
                drawLine(canvas,rightHip,rightKnee,correctPaint)
                drawLine(canvas,rightKnee,rightAnkle,correctPaint)
            }
            else if (nowPose.isAngle_rkD(rightKneeAngle) && nowPose.getEnable(nowPose.rightKneeAngleD)){
                drawLine(canvas,rightHip,rightKnee,correctPaint)
                drawLine(canvas,rightKnee,rightAnkle,correctPaint)
            }
            else if (nowPose.isAngle_rkM(rightKneeAngle) && nowPose.getEnable(nowPose.rightKneeAngleM)){
                drawLine(canvas,rightHip,rightKnee,progPaint)
                drawLine(canvas,rightKnee,rightAnkle,progPaint)
            }
            else if (nowPose.getEnable(nowPose.rightKneeAngleD) || nowPose.getEnable(nowPose.rightKneeAngleS)){
                drawLine(canvas,rightHip,rightKnee,wrongPaint)
                drawLine(canvas,rightKnee,rightAnkle,wrongPaint)
            }
            // Left Knee Angle
            if (nowPose.isAngle_lkS(leftKneeAngle) && nowPose.getEnable(nowPose.leftKneeAngleS)){
                //leftHip,leftKnee,leftAnkle
                drawLine(canvas,leftHip,leftKnee,correctPaint)
                drawLine(canvas,leftKnee,leftAnkle,correctPaint)
            }
            else if (nowPose.isAngle_lkD(leftKneeAngle) && nowPose.getEnable(nowPose.leftKneeAngleD)){
                drawLine(canvas,leftHip,leftKnee,correctPaint)
                drawLine(canvas,leftKnee,leftAnkle,correctPaint)
            }
            else if (nowPose.isAngle_lkM(leftKneeAngle) && nowPose.getEnable(nowPose.leftKneeAngleM)){
                drawLine(canvas,leftHip,leftKnee,progPaint)
                drawLine(canvas,leftKnee,leftAnkle,progPaint)
            }
            else if (nowPose.getEnable(nowPose.leftKneeAngleD) || nowPose.getEnable(nowPose.leftKneeAngleS)){
                drawLine(canvas,leftHip,leftKnee,wrongPaint)
                drawLine(canvas,leftKnee,leftAnkle,wrongPaint)
            }
        }

        // Draw inFrameLikelihood for all points
        if (showInFrameLikelihood) {
            for (landmark in landmarks) {
                canvas.drawText(
                    String.format(Locale.US, "%.2f", landmark.inFrameLikelihood),
                    translateX(landmark.position.x),
                    translateY(landmark.position.y),
                    whitePaint
                )
            }
        }
    }

    internal fun drawPoint(canvas: Canvas, landmark: PoseLandmark, paint: Paint) {
        val point = landmark.position
        canvas.drawCircle(translateX(point.x), translateY(point.y), DOT_RADIUS, paint)
    }

    internal fun drawLine(
        canvas: Canvas,
        startLandmark: PoseLandmark?,
        endLandmark: PoseLandmark?,
        paint: Paint
    ) {
        // When visualizeZ is true, sets up the paint to draw body line in different colors based on
        // their z values.

        if (visualizeZ) {
            val start = startLandmark!!.position3D
            val end = endLandmark!!.position3D

            // Gets the range of z value.
            val zLowerBoundInScreenPixel: Float
            val zUpperBoundInScreenPixel: Float

            if (rescaleZForVisualization) {
                zLowerBoundInScreenPixel = min(-0.001f, scale(zMin))
                zUpperBoundInScreenPixel = max(0.001f, scale(zMax))
            } else {
                // By default, assume the range of z value in screen pixel is [-canvasWidth, canvasWidth].
                val defaultRangeFactor = 1f
                zLowerBoundInScreenPixel = -defaultRangeFactor * canvas.width
                zUpperBoundInScreenPixel = defaultRangeFactor * canvas.width
            }

            // Gets average z for the current body line
            val avgZInImagePixel = (start.z + end.z) / 2
            val zInScreenPixel = scale(avgZInImagePixel)

            if (zInScreenPixel < 0) {
                // Sets up the paint to draw the body line in red if it is in front of the z origin.
                // Maps values within [zLowerBoundInScreenPixel, 0) to [255, 0) and use it to control the
                // color. The larger the value is, the more red it will be.
                var v = (zInScreenPixel / zLowerBoundInScreenPixel * 255).toInt()
                v = Ints.constrainToRange(v, 0, 255)
                paint.setARGB(255, 255, 255 - v, 255 - v)
            } else {
                // Sets up the paint to draw the body line in blue if it is behind the z origin.
                // Maps values within [0, zUpperBoundInScreenPixel] to [0, 255] and use it to control the
                // color. The larger the value is, the more blue it will be.
                var v = (zInScreenPixel / zUpperBoundInScreenPixel * 255).toInt()
                v = Ints.constrainToRange(v, 0, 255)
                paint.setARGB(255, 255 - v, 255 - v, 255)
            }

            canvas.drawLine(
                translateX(start.x),
                translateY(start.y),
                translateX(end.x),
                translateY(end.y),
                paint
            )
        } else {
            val start = startLandmark!!.position
            val end = endLandmark!!.position
            canvas.drawLine(
                translateX(start.x), translateY(start.y), translateX(end.x), translateY(end.y), paint
            )
            //Log.d("COORDINATE",
            //    "startX : "+translateX(start.x).toString()+"\n"+"startY : "+translateY(start.y).toString()+"\n"
            //    +"endX : "+translateXsub(end.x).toString()+"\n"+"enY : "+translateY(end.y).toString()+"\n==============================")
        }
    }
    fun Beep(){
//        CameraXLivePreviewActivity.getInstance()?.initSound()
//        cameraXLivePreviewActivity?.playBeep()
    }

    private fun checkCorrect(){
        //Right Hip Angle
        val landmarks = pose.allPoseLandmarks
        if (landmarks.isEmpty()) {
            return
        }

//        Log.d("ExerciseName","PoseGraphic IN ENAME $exName")
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
        /* Get Angle */

        // Hip angle
        val rightHipAngle = getAngle(rightShoulder!!,rightHip!!,rightKnee!!)
        val leftHipAngle = getAngle(leftShoulder!!,leftHip!!,leftKnee!!)
        // Shoulder angle
        val rightShoulderAngle = getAngle(rightHip,rightShoulder,rightElbow!!)
        val leftShoulderAngle = getAngle(leftHip,leftShoulder,leftElbow!!)
        // Elbow Angle
        val rightElbowAngle = getAngle(rightShoulder,rightElbow,rightWrist!!)
        val leftElbowAngle = getAngle(leftShoulder,leftElbow,leftWrist!!)
        // Knee Angle
        val rightKneeAngle = getAngle(rightHip,rightKnee,rightAnkle!!)
        val leftKneeAngle = getAngle(leftHip,leftKnee,leftAnkle!!)

        if (nowPose != null) {
            // Right Hip
            if (nowPose.isAngle_rhS(rightHipAngle) && nowPose.getEnable(nowPose.rightHipAngleS)) {}
            else if (nowPose.isAngle_rhD(rightHipAngle) && nowPose.getEnable(nowPose.rightHipAngleD)) {}
            else if (!nowPose.isAngle_rhS(rightHipAngle) && nowPose.getEnable(nowPose.rightHipAngleS)) {correctArray[0] = false}
            //Left Hip Angle
            if (nowPose.isAngle_lhS(leftHipAngle) && nowPose.getEnable(nowPose.leftHipAngleS)) {}
            else if (nowPose.isAngle_lhD(leftHipAngle) && nowPose.getEnable(nowPose.leftHipAngleD)) {}
            else if (!nowPose.isAngle_lhS(leftHipAngle) &&nowPose.getEnable(nowPose.leftHipAngleS)) {correctArray[1] = false}
            // Right Shoulder Angle
            if (nowPose.isAngle_rsS(rightShoulderAngle) && nowPose.getEnable(nowPose.rightShoulderAngleS)) {}
            else if (nowPose.isAngle_rsD(rightShoulderAngle) && nowPose.getEnable(nowPose.rightShoulderAngleD)) {}
            else if (!nowPose.isAngle_rsS(rightShoulderAngle) &&nowPose.getEnable(nowPose.rightShoulderAngleS)) {correctArray[2] = false}
            // Left Shoulder Angle
            if (nowPose.isAngle_lsS(leftShoulderAngle) && nowPose.getEnable(nowPose.leftShoulderAngleS)) {}
            else if (nowPose.isAngle_lsD(leftShoulderAngle) && nowPose.getEnable(nowPose.leftShoulderAngleD)) {}
            else if (!nowPose.isAngle_lsS(leftShoulderAngle) && nowPose.getEnable(nowPose.leftShoulderAngleS)) {correctArray[3] = false}
            // Right Elbow Angle
            if (nowPose.isAngle_reS(rightElbowAngle) && nowPose.getEnable(nowPose.rightElbowAngleS)) {}
            else if (nowPose.isAngle_reD(rightElbowAngle) && nowPose.getEnable(nowPose.rightElbowAngleD)) {}
            else if (!nowPose.isAngle_reS(rightElbowAngle) && nowPose.getEnable(nowPose.rightElbowAngleS)) {correctArray[4] = false}
            // Left Elbow Angle
            if (nowPose.isAngle_leS(leftElbowAngle) && nowPose.getEnable(nowPose.leftElbowAngleS)) {}
            else if (nowPose.isAngle_leD(leftElbowAngle) && nowPose.getEnable(nowPose.leftElbowAngleD)) {}
            else if (!nowPose.isAngle_leS(leftElbowAngle) && nowPose.getEnable(nowPose.leftElbowAngleS)) {correctArray[5] = false}
            // Right Knee Angle
            if (nowPose.isAngle_rkS(rightKneeAngle) && nowPose.getEnable(nowPose.rightKneeAngleS)) {}
            else if (nowPose.isAngle_rkD(rightKneeAngle) && nowPose.getEnable(nowPose.rightKneeAngleD)) {}
            else if (!nowPose.isAngle_rkS(rightKneeAngle) && nowPose.getEnable(nowPose.rightKneeAngleS)) {correctArray[6] = false}
            // Left Knee Angle
            if (nowPose.isAngle_lkS(leftKneeAngle) && nowPose.getEnable(nowPose.leftKneeAngleS)) {}
            else if (nowPose.isAngle_lkD(leftKneeAngle) && nowPose.getEnable(nowPose.leftKneeAngleD)) {}
            else if (!nowPose.isAngle_lkS(leftKneeAngle) && nowPose.getEnable(nowPose.leftKneeAngleS)) {correctArray[7] = false}
        }
    }
    companion object {
        private val DOT_RADIUS = 8.0f
        private val IN_FRAME_LIKELIHOOD_TEXT_SIZE = 30.0f
        private val STROKE_WIDTH = 10.0f
        private val POSE_CLASSIFICATION_TEXT_SIZE = 60.0f

    }
}

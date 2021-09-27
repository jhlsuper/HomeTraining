package com.example.mlkit_pose.kotlin.posedetector

class PoseSearcher {
    private val poseList:ArrayList<ExercisePose> = ArrayList()
    /*
    rightHipAngleS = rhs        rightHipAngleD = rhd           rightHipAngleM = rhm
    leftHipAngleS = lhs         leftHipAngleD = lhd            leftHipAngleM = lhm
    rightShoulderAngleS = rss   rightShoulderAngleD = rsd      rightShoulderAngleM = rsm
    leftShoulderAngleS = lss    leftShoulderAngleD = lsd       leftShoulderAngleM = lsm
    rightElbowAngleS = res      rightElbowAngleD = red         rightElbowAngleM = rem
    leftElbowAngleS = les       leftElbowAngleD = led          leftElbowAngleM = lem
    rightKneeAngleS = rks       rightKneeAngleD = rkd          rightKneeAngleM = rkm
    leftKneeAngleS = lks        leftKneeAngleD = lkd           leftKneeAngleM = lkm    excerciseName
     */
    /*
    PushUp    FullPlank    BackLift    Superman    -ShoulderPressDB-    UpDownPlank    CrossoverExerciseDB
    LegRaise    SeatedKneeup    BirdDog    DeadBug    SitUp   ElbowPlank    SideBandDB    ForwardBand
    StepOnOneFoot    WideSquat    SideLegRaise    Lunge    Bridge    BandSideStep
     */

    init { // 운동 목록 초기화 # S (Raisd) D (Down)
        val ShoulderPressDB: ExercisePose = ExercisePose( // #1
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(145.0, 180.0, 1.0), listOf(77.0, 99.0, 1.0), listOf(107.0, 140.0, 1.0),
            listOf(145.0, 180.0, 1.0), listOf(77.0, 99.0, 1.0), listOf(107.0, 140.0, 1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), "ShoulderPressDB"
        )
        poseList.add(ShoulderPressDB)
        val WideSquat: ExercisePose = ExercisePose( // #2
            listOf(60.0, 90.0, 1.0), listOf(140.0, 165.0, 1.0), listOf(93.0, 140.0, 1.0),
            listOf(60.0, 90.0, 1.0), listOf(140.0, 165.0, 1.0), listOf(93.0, 140.0, 1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(50.0, 98.0, 1.0), listOf(150.0, 180.0, 1.0),listOf(110.0, 140.0, 1.0),
            listOf(50.0, 98.0, 1.0), listOf(150.0, 180.0, 1.0),listOf(110.0, 140.0, 1.0), "WideSquat"
        )
        poseList.add(WideSquat)
        val SideLegRaise: ExercisePose = ExercisePose(  // #3
            listOf(110.0, 144.5, 1.0), listOf(165.0, 180.0, 1.0),listOf(150.0, 160.0, 1.0),
            listOf(110.0, 144.5, 1.0), listOf(165.0, 180.0, 1.0),listOf(150.0, 160.0, 1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0), "SideLegRaise"
        )
        poseList.add(SideLegRaise)
        val DeadBug: ExercisePose = ExercisePose(   // #4
            listOf(166.0, 190.0, 1.0), listOf(70.0, 110.0, 1.0),listOf(115.0, 160.0, 1.0),
            listOf(166.0, 190.0, 1.0), listOf(70.0, 110.0, 1.0),listOf(115.0, 160.0, 1.0),
            listOf(160.0, 180.0, 1.0), listOf(70.0, 100.0, 1.0),listOf(105.0, 155.0, 1.0),
            listOf(160.0, 180.0, 1.0), listOf(70.0, 100.0, 1.0),listOf(105.0, 155.0, 1.0),
            listOf(152.0, 200.0, -1.0), listOf(152.0, 200.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(152.0, 200.0, -1.0), listOf(152.0, 200.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0, 190.0, 1.0), listOf(55.0, 95.0, 1.0),listOf(100.0, 155.0, 1.0),
            listOf(160.0, 190.0, 1.0), listOf(55.0, 95.0, 1.0),listOf(100.0, 155.0, 1.0), "DeadBug"
        )
        poseList.add(DeadBug)
        val SitUp: ExercisePose = ExercisePose( // #5
            listOf(50.0, 65.0, 1.0), listOf(170.0, 190.0, 1.0),listOf(70.0, 165.0, 1.0),
            listOf(50.0, 65.0, 1.0), listOf(170.0, 190.0, 1.0),listOf(70.0, 165.0, 1.0),
            listOf(70.0, 90.0, 1.0), listOf(160.0, 185.0, 1.0),listOf(95.0, 155.0, 1.0),
            listOf(70.0, 90.0, 1.0), listOf(160.0, 185.0, 1.0),listOf(95.0, 155.0, 1.0),
            listOf(155.0, 180.0, -1.0), listOf(155.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(155.0, 180.0, -1.0), listOf(155.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(140.0, 170.0, 1.0), listOf(140.0, 170.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(140.0, 170.0, 1.0), listOf(140.0, 170.0, 1.0),listOf(0.0, 180.0, -1.0), "SitUp"
        )
        poseList.add(SitUp)
        val ElbowPlank: ExercisePose = ExercisePose(    // #6
            listOf(160.0, 180.0, -1.0), listOf(160.0, 180.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0, 180.0, -1.0), listOf(160.0, 180.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(60.0, 90.0, 1.0), listOf(60.0, 90.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(60.0, 90.0, 1.0), listOf(60.0, 90.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(70.0, 90.0, 1.0), listOf(70.0, 90.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(70.0, 90.0, 1.0), listOf(70.0, 90.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0, 180.0, 1.0), listOf(160.0, 180.0, 1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0, 180.0, 1.0), listOf(160.0, 180.0, 1.0), listOf(0.0, 180.0, -1.0),"ElbowPlank"
        )
        poseList.add(ElbowPlank)
        val SideBandDB: ExercisePose = ExercisePose(    // #7
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(25.0, 40.0, 1.0), listOf(15.0, 30.0, 1.0),listOf(0.0, 180.0, 1.0),
            listOf(25.0, 40.0, 1.0), listOf(15.0, 30.0, 1.0),listOf(0.0, 180.0, 1.0),
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0), "SideBandDB"
        )
        poseList.add(SideBandDB)
        val ForwardBand: ExercisePose = ExercisePose(   // #8
            listOf(40.0, 60.0, 1.0), listOf(165.0, 180.0, 1.0),listOf(65.0, 160.0, 1.0),
            listOf(40.0, 60.0, 1.0), listOf(165.0, 180.0, 1.0),listOf(65.0, 160.0, 1.0),
            listOf(105.0, 120.0, 1.0), listOf(0.0, 25.0, 1.0),listOf(30.0, 100.0, 1.0),
            listOf(105.0, 120.0, 1.0), listOf(0.0, 25.0, 1.0),listOf(30.0, 100.0, 1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0, 180.0, -1.0), listOf(0.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0),
            listOf(165.0, 180.0, -1.0), listOf(165.0, 180.0, -1.0),listOf(0.0, 180.0, -1.0), "ForwardBand"
        )
        poseList.add(ForwardBand)
        // #9
        val UpDownPlank:ExercisePose = ExercisePose(
            listOf(159.0,168.0,1.0),listOf(154.0,168.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(159.0,168.0,1.0),listOf(154.0,168.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(149.0,166.0,1.0),listOf(85.0,103.0,1.0),listOf(108.0, 144.0, 1.0),
            listOf(149.0,166.0,1.0),listOf(44.0,77.0,1.0),listOf(82.0, 144.0, 1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),"UpDownPlank")
        poseList.add(UpDownPlank)
        // #10
        val LegRaise:ExercisePose = ExercisePose(
            listOf(90.0,115.0,1.0),listOf(135.0,150.0,1.0),listOf(120.0, 130.0, 1.0),
            listOf(86.0,100.0,1.0),listOf(120.0,140.0,1.0),listOf(105.0, 115.0, 1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),"LegRaise")
        poseList.add(LegRaise)
        // #11
        val SeatedKneeup:ExercisePose = ExercisePose(
            listOf(60.0,80.0,1.0),listOf(110.0,140.0,1.0),listOf(85.0, 105.0, 1.0),
            listOf(60.0,80.0,1.0),listOf(110.0,140.0,1.0),listOf(85.0, 105.0, 1.0),
            listOf(40.0,60.0,-1.0),listOf(60.0,75.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(20.0,40.0,-1.0),listOf(60.0,75.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(95.0,110.0,-1.0),listOf(125.0,140.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(90.0,110.0,-1.0),listOf(150.0,170.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(100.0,115.0,1.0),listOf(140.0,160.0,1.0),listOf(120.0, 135.0, 1.0),
            listOf(105.0,120.0,1.0),listOf(150.0,160.0,1.0),listOf(125.0, 145.0, 1.0),"SeatedKneeup")
        poseList.add(SeatedKneeup)
        // #12
        val BirdDog:ExercisePose = ExercisePose(
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0), // 힙 제외
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(150.0,160.0,1.0),listOf(70.0,85.0,1.0),listOf(90.0, 145.0, 1.0), // 어깨
            listOf(70.0,85.0,1.0),listOf(150.0,160.0,1.0),listOf(90.0, 145.0, 1.0),
            listOf(95.0,110.0,-1.0),listOf(125.0,140.0,-1.0),listOf(0.0, 180.0, -1.0), // 팔꿈치 제외
            listOf(90.0,110.0,-1.0),listOf(150.0,170.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(85.0,100.0,1.0),listOf(155.0,170.0,1.0),listOf(105.0, 150.0, 1.0), // 무릎
            listOf(155.0,170.0,1.0),listOf(85.0,100.0,1.0),listOf(105.0, 150.0, 1.0),"BirdDog")
        poseList.add(BirdDog)
        // #13
        val CrossoverExerciseDB:ExercisePose = ExercisePose(
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(150.0,160.0,-1.0),listOf(70.0,85.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(70.0,85.0,-1.0),listOf(150.0,160.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0,175.0,1.0),listOf(100.0,115.0,1.0),listOf(120.0, 155.0, 1.0), // 팔꿈치
            listOf(165.0,180.0,1.0),listOf(70.0,80.0,1.0),listOf(85.0, 160.0, 1.0),
            listOf(85.0,100.0,-1.0),listOf(155.0,170.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(155.0,170.0,-1.0),listOf(85.0,100.0,-1.0),listOf(0.0, 180.0, -1.0),"CrossoverExerciseDB")
        poseList.add(CrossoverExerciseDB)
        // #14
        val PushUp:ExercisePose = ExercisePose(
            listOf(150.0,165.0,1.0),listOf(165.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(150.0,165.0,1.0),listOf(165.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(165.0,180.0,1.0),listOf(40.0,80.0,1.0),listOf(85.0, 160.0, 1.0),
            listOf(165.0,180.0,1.0),listOf(40.0,80.0,1.0),listOf(85.0, 160.0, 1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),"PushUp")
        poseList.add(PushUp)
        // #15
        val FullPlank:ExercisePose = ExercisePose(
            listOf(145.0,180.0,1.0),listOf(145.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(145.0,180.0,1.0),listOf(145.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(130.0,180.0,1.0),listOf(130.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(130.0,180.0,1.0),listOf(130.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0,180.0,1.0),listOf(160.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0,180.0,1.0),listOf(160.0,180.0,1.0),listOf(0.0, 180.0, -1.0),"FullPlank")
        poseList.add(FullPlank)
        // #16
        val BackLift:ExercisePose = ExercisePose(
            listOf(155.0,180.0,1.0),listOf(155.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0,180.0,1.0),listOf(160.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(120.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(120.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,0.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,0.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),"BackLift")
        poseList.add(BackLift)
        // #17
        val Superman:ExercisePose = ExercisePose(
            listOf(160.0,175.0,1.0),listOf(170.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(160.0,170.0,1.0),listOf(170.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(150.0,170.0,1.0),listOf(140.0,150.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(140.0,155.0,1.0),listOf(145.0,160.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(155.0,170.0,1.0),listOf(145.0,160.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(140.0,165.0,1.0),listOf(150.0,175.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(150.0,180.0,1.0),listOf(160.0,180.0,1.0),listOf(0.0, 180.0, -1.0),
            listOf(150.0,180.0,1.0),listOf(160.0,170.0,1.0),listOf(0.0, 180.0, -1.0),"Superman")
        poseList.add(Superman)
        // #18
        val SideLateralRaiseDB:ExercisePose = ExercisePose(
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(80.0,110.0,1.0),listOf(0.0,45.0,1.0),listOf(50.0, 75.0, 1.0),
            listOf(80.0,110.0,1.0),listOf(0.0,45.0,1.0),listOf(50.0, 75.0, 1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(145.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),"SideLateralRaiseDB")
        poseList.add(SideLateralRaiseDB)
        // #19
        val Lunge:ExercisePose = ExercisePose(
            listOf(88.0,120.0,1.0),listOf(138.0,176.0,1.0),listOf(125.0, 133.0, 1.0),
            listOf(88.0,120.0,1.0),listOf(138.0,176.0,1.0),listOf(125.0, 133.0, 1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(80.0,135.0,1.0),listOf(153.0,180.0,1.0),listOf(140.0, 148.0, 1.0),
            listOf(80.0,135.0,1.0),listOf(153.0,180.0,1.0),listOf(140.0, 148.0, 1.0),"Lunge")
        poseList.add(Lunge)
        val Bridge:ExercisePose = ExercisePose(
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),"Bridge")
        poseList.add(Bridge)
        val BandSideStep:ExercisePose = ExercisePose(
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),
            listOf(0.0,180.0,-1.0),listOf(0.0,180.0,-1.0),listOf(0.0, 180.0, -1.0),"BandSideStep")
        poseList.add(BandSideStep)
    }

    public fun searchExByName(exname :String?) : ExercisePose?{
        if (exname == null){
            return null
        }
        for (ep in poseList){
            if (ep.getExName() == exname){
                return ep
            }
        }
        return null
    }

}
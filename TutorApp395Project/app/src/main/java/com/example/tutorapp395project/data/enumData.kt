package com.example.tutorapp395project.data


enum class Grade (
    val GRADE_1: Int,
    val GRADE_2: Int,
    val GRADE_3: Int,
    val GRADE_4: Int,
    val GRADE_5: Int,
    val GRADE_6: Int,
    val GRADE_7: Int,
    val GRADE_8: Int,
    val GRADE_9: Int,
    val GRADE_10: Int,
    val GRADE_12: Int
)
enum class TutorLevel (
    val FRESHER: String,
    val JUNIOR: String,
    val SENIOR: String
)

enum class TutoringSessionStatus (
    val SCHEDULED: String,
    val CANCELLED: String,
    val COMPLETED: String
)

enum class TutoringSubject (
    val MATHEMATICS: String,
    val ENGLISH: String,
    val PHYSICS: String,
    val CHEMISTRY: String,
    val BIOLOGY: String,
    val HISTORY: String,
    val GEOGRAPHY: String,
    val BUSINESS_STUDIES: String,
    val COMPUTER_SCIENCE: String,
    val LITERATURE: String
)

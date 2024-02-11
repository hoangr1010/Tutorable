package com.example.tutorapp395project.data


enum class Grade (
    val GRADE_1: String,
    val GRADE_2: String,
    val GRADE_3: String,
    val GRADE_4: String,
    val GRADE_5: String,
    val GRADE_6: String,
    val GRADE_7: String,
    val GRADE_8: String,
    val GRADE_9: String,
    val GRADE_10: String,
    val GRADE_11: String,
    val GRADE_12: String
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

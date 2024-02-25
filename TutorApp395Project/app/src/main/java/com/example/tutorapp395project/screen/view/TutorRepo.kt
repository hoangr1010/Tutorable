package com.example.tutorapp395project.screen.view

import com.example.tutorapp395project.R

class TutorRepo {
    fun getData():List<TutorData>{
        return listOf(
            TutorData(
                "Monkey D. Luffy",
                "Computer Science",
                R.drawable.image1
            ),
            TutorData(
                "Roronoa Zoro",
                "Geography",
                R.drawable.image2
            ),
            TutorData(
                "Nami",
                "Math",
                R.drawable.image3
            ),
            TutorData(
                "Vinsmoke Sanji",
                "Chemistry",
                R.drawable.image4
            ),
            TutorData(
                "Tony Tony Chopper",
                "Biology",
                R.drawable.image5
            ),
            TutorData(
                "Nico Robin",
                "History",
                R.drawable.image6
            ),
            TutorData(
                "Franky",
                "Physics",
                R.drawable.image7
            ),


        )
    }
}
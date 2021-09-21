package com.example.birthdayreminder

import java.time.LocalDate

data class Person (val imageResource: Int,
                   val name: String,
                   val gender: Boolean,
                   val date: LocalDate,
                   val remind: Boolean)
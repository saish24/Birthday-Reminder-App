package com.example.birthdayreminder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_new_user.*
import java.util.*

class NewUserFragment : Fragment(R.layout.fragment_new_user) {
    private val args: NewUserFragmentArgs by navArgs()
    private lateinit var textviewDate : TextView
    private lateinit var datePicker: DatePickerHelper

    private var day   = -1
    private var month = -1
    private var year  = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datePicker = context?.let { DatePickerHelper(it, true) }!!
        textviewDate = tv_date_view

        btn_schedule.setOnClickListener {
            showDatePickerDialog(day, month, year)
        }

        btn_save_user.setOnClickListener {
            val action = NewUserFragmentDirections.actionNewUserFragmentToHomeFragment(
                et_person_name.text.toString(),
                this.day,
                this.month,
                this.year,
                sw_gender.isChecked,
                sw_reminder.isChecked
            )
            findNavController().navigate(action)
        }

        et_person_name.setText(args.name)
        tv_date_view.text = args.date
        sw_reminder.isChecked = args.remindMe
        sw_gender.isChecked = args.gender
        putDate()
    }

    private fun showDatePickerDialog(d:Int, m:Int, y:Int) {
        datePicker.showDialog(d, m-1, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(day: Int, month: Int, year: Int) = putDate(day, month+1, year)
        })
    }

    private fun putDate(day:Int = -1, month:Int = -1, year: Int = -1) {
        if(day == -1 && month == -1 && year == -1) {
            if(textviewDate.text.isNotEmpty()) {
                this.day = args.date.substringAfterLast('-').toInt()
                this.month = args.date.substringAfter('-').substringBefore('-').toInt()
                this.year = args.date.substringBefore('-').toInt()
            } else {
                val cal = Calendar.getInstance()
                this.day = cal.get(Calendar.DAY_OF_MONTH)
                this.month = cal.get(Calendar.MONTH)
                this.year = cal.get(Calendar.YEAR)
            }
        } else {
            this.day = day
            this.month = month
            this.year = year
        }

        val dayStr = if (this.day < 10) "0${this.day}" else "${this.day}"
        val monthStr = if (this.month < 10) "0${this.month}" else "${this.month}"
        textviewDate.text = "$dayStr/$monthStr/${this.year}"
    }
}
package com.example.birthdayreminder

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import kotlin.random.Random

class HomeFragment : Fragment(R.layout.fragment_home), PersonAdapter.OnItemClickListener {
    private val args: HomeFragmentArgs by navArgs()

    private lateinit var list : ArrayList<Person>
    private lateinit var personAdapter : PersonAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = generateDummyList()
        linearLayoutManager = LinearLayoutManager(context)

        rv_listItems.apply {
            adapter = PersonAdapter(list, this@HomeFragment)
            layoutManager = linearLayoutManager
        }

        rv_listItems.setHasFixedSize(true)

        fab_new.setOnClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToNewUserFragment(gender = true, remindMe = true, name = "", date = "")
//            findNavController().navigate(action)
        }
    }

    private fun generateDummyList() : ArrayList<Person> {
        val list = ArrayList<Person>()
        val names : ArrayList<String> = arrayListOf(
            "Nichola Marshall",
            "Sonya Hoffman",
            "Finlay Kemp",
            "Dolores Mckinney",
            "Vanessa Nieves",
            "Chris Doherty",
            "Faye Valencia",
            "Tehya Patrick",
            "Suzanna Camacho",
            "Monica Coles"
        )
        for (i in 0 until names.size) {
            val bDate: LocalDate = LocalDate.of(
                Random.nextInt(1998, 2001),
                Random.nextInt(1,12),
                Random.nextInt(4,28))

            val imgResource = if(Random.nextInt(0,2) == 1) R.drawable.ic_male else R.drawable.ic_male2

            list += Person(imgResource, names[i], true, bDate, true)
        }
        return list
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, list[position].name, Toast.LENGTH_SHORT).show()
        val action = HomeFragmentDirections.actionHomeFragmentToNewUserFragment(
            list[position].gender,
            list[position].remind,
            list[position].name,
            list[position].date.toString())
        findNavController().navigate(action)
    }

    private fun addUser() {
        val date = LocalDate.of(args.year, args.month, args.day)
        val imgResource = if(args.gender) R.drawable.ic_male2 else R.drawable.ic_female2
        val newUser = Person(imgResource, args.name, args.gender, date, args.remindMe)
        list.add(newUser)
        personAdapter.notifyDataSetChanged()
    }
}
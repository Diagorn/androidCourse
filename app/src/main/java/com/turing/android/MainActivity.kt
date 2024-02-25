package com.turing.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.turing.android.databinding.ActivityMainBinding
import com.turing.android.ds.DataSourceAction
import com.turing.android.ds.DataSourceListener
import com.turing.android.ds.TuringPersonDs
import com.turing.android.dto.TuringPerson
import com.turing.android.ui.TuringPersonActionListener
import com.turing.android.ui.TuringPersonAdapter

const val NEXT_PERSON_ID = "NEXT_PERSON_ID"

/**
 * Основное активити
 *
 * @author Diagorn
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TuringPersonAdapter
    private lateinit var turingPersonDs: TuringPersonDs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        turingPersonDs = TuringPersonDs.getInstance()

        adapter = TuringPersonAdapter(this, object : TuringPersonActionListener {
            override fun onDelete(person: TuringPerson) {
                turingPersonDs.delete(person)
            }

            override fun onDetails(person: TuringPerson) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.delalis_person_msg, person.name),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        with(binding) {
            turingPersonListView.layoutManager = layoutManager
            turingPersonListView.adapter = adapter
            addButton.setOnClickListener {
                activityLaunched.launch(turingPersonDs.getNextId())
            }
        }

        with(turingPersonDs) {
            addListeners(listOf(addPersonListener, deletePersonListener))
            loadObjects()
            getAll().forEach { item -> addPersonListener.perform(item) }
        }
    }

    private val addPersonListener =
        object : DataSourceListener<TuringPerson>(DataSourceAction.INSERT) {
            override fun doPerform(obj: TuringPerson) {
                adapter.addItem(obj)
            }
        }

    private val deletePersonListener =
        object : DataSourceListener<TuringPerson>(DataSourceAction.REMOVE) {
            override fun doPerform(obj: TuringPerson) {
                adapter.removeItem(obj)
            }
        }

    private val activityLaunched =
        registerForActivityResult(AddTuringPersonContract()) { newPerson ->
            newPerson ?: return@registerForActivityResult
            turingPersonDs.add(newPerson)
        }
}
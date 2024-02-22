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

        turingPersonDs = (application as PersonsApp).turingPersonDs

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

        binding.turingPersonListView.layoutManager = layoutManager
        binding.turingPersonListView.adapter = adapter

        turingPersonDs.addListeners(listOf(addPersonListener, deletePersonListener))

        turingPersonDs.loadObjects()
        turingPersonDs.getAll().forEach { addPersonListener.perform(it) }

        binding.addButton.setOnClickListener {
            activityLaunched.launch(turingPersonDs.getNextId())
        }
    }

    private val addPersonListener =
        object : DataSourceListener<TuringPerson>(DataSourceAction.INSERT) {
            override fun doPerform(obj: TuringPerson) {
                adapter.turingPersons.add(obj)
                adapter.notifyItemInserted(obj.id.toInt() - 1)
            }
        }

    private val deletePersonListener =
        object : DataSourceListener<TuringPerson>(DataSourceAction.REMOVE) {
            override fun doPerform(obj: TuringPerson) {
                val index = adapter.turingPersons.indexOfFirst { it.id == obj.id }
                if (index != -1) { // Объект по айдишнику найден
                    adapter.turingPersons.removeAt(index)
                    adapter.notifyItemRemoved(obj.id.toInt() - 1)
                }
            }
        }

    private val activityLaunched =
        registerForActivityResult(AddTuringPersonContract()) { newPerson ->
            newPerson ?: return@registerForActivityResult
            turingPersonDs.add(newPerson)
        }
}
package dev.abgeo.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskFormFragment : Fragment() {

    private lateinit var etTaskTitle : EditText
    private lateinit var etTaskBody : EditText

    private var task : Task? = null

    private val taskViewModel: TaskViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_form, container, false)

        etTaskTitle = view.findViewById(R.id.etTaskTitle)
        etTaskBody = view.findViewById(R.id.etTaskBody)

        taskViewModel.taskLiveData.observe(viewLifecycleOwner, {
            (activity as AppCompatActivity).title = getString(R.string.edit_task)

            with(it) {
                task = this
                etTaskTitle.setText(title)
                etTaskBody.setText(body)
            }
        })

        view.findViewById<FloatingActionButton>(R.id.fabSaveTask).setOnClickListener {
            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
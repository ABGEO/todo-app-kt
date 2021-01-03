package dev.abgeo.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskDetailsFragment : Fragment() {

    private lateinit var cbTaskTitle : CheckBox
    private lateinit var tvTaskBody : TextView

    private var task : Task? = null

    private val taskViewModel: TaskViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)

        cbTaskTitle = view.findViewById(R.id.cbTaskTitle)
        tvTaskBody = view.findViewById(R.id.tvTaskBody)

        taskViewModel.taskLiveData.observe(viewLifecycleOwner, {
            with(it) {
                task = this
                cbTaskTitle.text = title
                cbTaskTitle.isChecked = isCompleted
                tvTaskBody.text = body
            }
        })

        view.findViewById<FloatingActionButton>(R.id.fabEditTask).setOnClickListener {
            task?.let { t -> taskViewModel.postTask(t) }
            it.findNavController().navigate(R.id.action_navTaskDetailsFragment_to_navTaskFormFragment)
        }

        return view
    }

}

package dev.abgeo.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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

                cbTaskTitle.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                    task?.let { t ->
                        run {
                            t.isCompleted = isChecked
                            context?.let { c -> TaskRepository.updateTask(c, t) }
                            Snackbar.make(
                                view,
                                getText(if (isChecked) R.string.mark_as_completed else R.string.mark_as_active),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        })

        view.findViewById<FloatingActionButton>(R.id.fabEditTask).setOnClickListener {
            task?.let { t -> taskViewModel.postTask(t) }
            it.findNavController().navigate(R.id.action_navTaskDetailsFragment_to_navTaskFormFragment)
        }

        return view
    }

}

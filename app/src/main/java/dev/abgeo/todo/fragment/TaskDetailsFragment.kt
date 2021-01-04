package dev.abgeo.todo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dev.abgeo.todo.R
import dev.abgeo.todo.repository.TaskRepository
import dev.abgeo.todo.viewmodel.TaskViewModel
import dev.abgeo.todo.entity.Task

class TaskDetailsFragment : Fragment() {

    private lateinit var cbTaskTitle : CheckBox
    private lateinit var tvTaskBody : TextView

    private var task : Task? = null

    private val taskViewModel: TaskViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_task, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_delete -> {
                    context?.let {
                        task?.let { t -> TaskRepository.delete(it, t) }
                        taskViewModel.getTasks(it)
                        activity?.run {
                            Snackbar.make(
                                    this.findViewById(R.id.content),
                                    getText(R.string.task_deleted),
                                    Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        findNavController().navigate(R.id.navTasksFragment)
                    }
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

}

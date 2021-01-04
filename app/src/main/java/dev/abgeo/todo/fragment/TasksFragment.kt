package dev.abgeo.todo.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dev.abgeo.todo.R
import dev.abgeo.todo.adapter.TaskRecyclerViewAdapter
import dev.abgeo.todo.repository.TaskRepository
import dev.abgeo.todo.viewmodel.TaskViewModel
import dev.abgeo.todo.entity.Task

class TasksFragment : Fragment(),
        TaskRecyclerViewAdapter.TaskCheckedListener,
        TaskRecyclerViewAdapter.TaskClickListener
{

    private lateinit var fragmentView : View
    private lateinit var rvTasks : RecyclerView
    private lateinit var tvAllTasks : TextView
    private lateinit var ivEmptyBG : ImageView
    private lateinit var tvEmptyBG : TextView

    private var tasksAlreadyLoaded : Boolean = false
    private var tasks : List<Task> = emptyList()

    private val taskViewModel: TaskViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_task_list, container, false)

        rvTasks = fragmentView.findViewById(R.id.rvTasks)
        tvAllTasks = fragmentView.findViewById(R.id.tvAllTasks)
        ivEmptyBG = fragmentView.findViewById(R.id.ivEmptyBG)
        tvEmptyBG = fragmentView.findViewById(R.id.tvEmptyBG)

        taskViewModel.tasksLiveData.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                setListIsEmpty()
            } else {
                setListIsEmpty(false)

                if (!tasksAlreadyLoaded) {
                    tasksAlreadyLoaded = true
                    tasks = it
                }

                rvTasks.adapter = TaskRecyclerViewAdapter(
                    it,
                    this@TasksFragment,
                    this@TasksFragment
                )
            }
        })
        context?.let { taskViewModel.getTasks(it) }

        fragmentView.findViewById<FloatingActionButton>(R.id.fabAddTask).setOnClickListener {
            taskViewModel.postTask(null)
            it.findNavController().navigate(R.id.action_navTasksFragment_to_navTaskFormFragment)
        }

        return fragmentView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_filter -> {
                context?.let { showFilteringPopUpMenu(it) }
                true
            }
            R.id.action_clear_completed -> {
                context?.let {
                    TaskRepository.deleteCompleted(it)
                    taskViewModel.getTasks(it)
                    Snackbar.make(
                        fragmentView,
                        getText(R.string.completed_task_cleared),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                true
            }
            R.id.action_refresh -> {
                context?.let { taskViewModel.getTasks(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun showFilteringPopUpMenu(context: Context) {
        val view = activity?.findViewById<View>(R.id.action_filter) ?: return

        PopupMenu(context, view).run {
            menuInflater.inflate(R.menu.menu_filter, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_all -> {
                        filterTasks(FILTER_ALL)
                        true
                    }
                    R.id.action_active -> {
                        filterTasks(FILTER_ACTIVE)
                        true
                    }
                    R.id.action_completed -> {
                        filterTasks(FILTER_COMPLETED)
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }

    private fun filterTasks(criteria: Int) {
        val filteredTasks = when(criteria) {
            FILTER_ACTIVE -> tasks.filter { !it.isCompleted }
            FILTER_COMPLETED -> tasks.filter { it.isCompleted }
            else -> tasks
        }

        taskViewModel.postTasks(filteredTasks)
    }

    override fun onTaskCheckedListener(task: Task, isChecked: Boolean) {
        task.isCompleted = isChecked
        context?.let { TaskRepository.updateTask(it, task) }

        Snackbar.make(
            fragmentView,
            getText(if (isChecked) R.string.mark_as_completed else R.string.mark_as_active),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onTaskClickListener(task: Task) {
        taskViewModel.postTask(task)
        fragmentView.findNavController().navigate(R.id.action_navTasksFragment_to_navTaskDetailsFragment)
    }

    private fun setListIsEmpty(isEmpty: Boolean = true) {
        rvTasks.visibility = if (isEmpty) View.GONE else View.VISIBLE
        tvAllTasks.visibility = if (isEmpty) View.GONE else View.VISIBLE
        ivEmptyBG.visibility = if (isEmpty) View.VISIBLE else View.GONE
        tvEmptyBG.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    companion object {
        private const val FILTER_ALL = 0
        private const val FILTER_ACTIVE = 1
        private const val FILTER_COMPLETED = 2
    }

}
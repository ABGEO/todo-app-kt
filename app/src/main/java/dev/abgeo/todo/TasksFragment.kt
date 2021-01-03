package dev.abgeo.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class TasksFragment : Fragment(), TaskRecyclerViewAdapter.TaskCheckedListener {

    private lateinit var fragmentView : View
    private lateinit var rvTasks : RecyclerView
    private lateinit var tvAllTasks : TextView
    private lateinit var ivEmptyBG : ImageView
    private lateinit var tvEmptyBG : TextView

    private val taskViewModel: TaskViewModel by navGraphViewModels(R.id.nav_graph)

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
                rvTasks.adapter = TaskRecyclerViewAdapter(it, this@TasksFragment)
            }
        })
        taskViewModel.getTasks()

        fragmentView.findViewById<FloatingActionButton>(R.id.fabAddTask).setOnClickListener {
            it.findNavController().navigate(R.id.action_navTasksFragment_to_navTaskFormFragment)
        }

        return fragmentView
    }

    override fun onTaskCheckedListener(task: Task, isChecked: Boolean) {
        Snackbar.make(
            fragmentView,
            getText(if (isChecked) R.string.mark_as_completed else R.string.mark_as_active),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setListIsEmpty(isEmpty: Boolean = true) {
        rvTasks.visibility = if (isEmpty) View.GONE else View.VISIBLE
        tvAllTasks.visibility = if (isEmpty) View.GONE else View.VISIBLE
        ivEmptyBG.visibility = if (isEmpty) View.VISIBLE else View.GONE
        tvEmptyBG.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

}
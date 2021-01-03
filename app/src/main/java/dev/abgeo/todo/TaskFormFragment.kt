package dev.abgeo.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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
            with(it) {
                task = this
                if (task == null) {
                    etTaskTitle.text = null
                    etTaskBody.text = null
                } else {
                    (activity as AppCompatActivity).title = getString(R.string.edit_task)
                    etTaskTitle.setText(title)
                    etTaskBody.setText(body)
                }
            }
        })

        view.findViewById<FloatingActionButton>(R.id.fabSaveTask).setOnClickListener {
            if (etTaskTitle.text.isEmpty() || etTaskTitle.text.isBlank()) {
                etTaskTitle.error = getText(R.string.title_is_required)

                return@setOnClickListener
            }

            val messageId: Int
            if (task == null) {
                task = Task(etTaskTitle.text.toString(), etTaskBody.text.toString())
                context?.let { c -> TaskRepository.insertTask(c, task!!) }
                messageId = R.string.task_added
            } else {
                task!!.title = etTaskTitle.text.toString()
                task!!.body = etTaskBody.text.toString()
                context?.let { c -> TaskRepository.updateTask(c, task!!) }
                messageId = R.string.task_edited
            }

            findNavController().navigate(R.id.action_navTaskFormFragment_to_navTasksFragment)
            Snackbar.make(it, getText(messageId), Snackbar.LENGTH_SHORT).show()
        }

        return view
    }

}
package dev.abgeo.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(R.id.navTasksFragment != destination.id)
            supportActionBar?.setDisplayHomeAsUpEnabled(R.id.navTasksFragment != destination.id)

            title = when (destination.id) {
                R.id.navTaskFormFragment -> getText(R.string.new_task)
                R.id.navTaskDetailsFragment -> getText(R.string.task_details)
                else -> getText(R.string.app_name)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
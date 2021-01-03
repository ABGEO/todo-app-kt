package dev.abgeo.todo

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_filter -> {
                showFilteringPopUpMenu()
                true
            }
            R.id.action_clear_completed -> {
                // TODO
                true
            }
            R.id.action_refresh -> {
                // TODO
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun showFilteringPopUpMenu() {
        val view = findViewById<View>(R.id.action_filter) ?: return

        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.menu_filter, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_all -> {
                        // TODO
                        true
                    }
                    R.id.action_active -> {
                        // TODO
                        true
                    }
                    R.id.action_completed -> {
                        // TODO
                        true
                    }
                    else -> super.onMenuItemSelected(it.itemId, it)
                }
            }
            show()
        }
    }

}
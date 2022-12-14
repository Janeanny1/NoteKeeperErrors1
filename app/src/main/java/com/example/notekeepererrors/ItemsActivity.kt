package com.example.notekeepererrors

import DataManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeepererrors.databinding.ActivityItemsBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class ItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val drawerlayout : DrawerLayout by lazy{
        binding.drawerLayout

    }
    val listItems :RecyclerView by lazy {
        binding.appBarItems.contentItems.listItems

    }

    val toolbar by lazy {
        binding.appBarItems.toolbar
    }

    val fab by lazy {
        binding.appBarItems.fab
    }

    val navview by lazy {
        binding.navView
    }

    lateinit var adapter: RecyclerView.Adapter<*>

    private lateinit var binding: ActivityItemsBinding

    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val noteRecyclerAdapter by lazy {
        NoteRecyclerAdapter(this, DataManager.notes)
    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity(intent (this, NoteActivity::class.java))
        }

        displayNotes()

        val toggle = ActionBarDrawerToggle(
            this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        navview.setNavigationItemSelectedListener(this)

        }

    private fun displayNotes() {
        listItems.layoutManager = noteLayoutManager
        listItems.adapter = noteRecyclerAdapter

        navview.menu.findItem(R.id.nav_notes).isChecked = true

    }

    private fun displayCourses(){
        listItems.layoutManager = courseLayoutManager
        listItems.adapter = courseRecyclerAdapter

        navview.menu.findItem(R.id.nav_courses).isChecked = true
    }

    private fun intent(activity: ItemsActivity, java: Class<NoteActivity>): Intent? = intent


    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()

            }


        }

        drawerlayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handleSelection(message: String) {
        Snackbar.make(listItems, message,Snackbar.LENGTH_LONG).show()

    }

}


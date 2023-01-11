package com.developersementsov.cocktailbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.developersementsov.cocktailbar.ui.fragment.*

class MainActivity : AppCompatActivity() {
    private val fragmentFactory = CocktailsFragmentFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = fragmentFactory.instantiate(
                classLoader,
                MainFragment::class.java.name
            )
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.fragmentContainer,
                fragment,
                fragment::class.java.name
            )
            transaction.commit()
        }
    }

    fun startFragmentByType(className: String) {
        val fragment = fragmentFactory.instantiate(classLoader, className)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment, className)
            addToBackStack(null)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            this.onBackPressedDispatcher.onBackPressed()
            true
        }
        R.id.action_about_app -> {
            startFragmentByType(AboutAppFragment::class.java.name)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
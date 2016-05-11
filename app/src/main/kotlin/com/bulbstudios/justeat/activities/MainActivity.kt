package com.bulbstudios.justeat.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import com.bulbstudios.justeat.R
import com.jakewharton.rxbinding.support.v7.widget.queryTextChanges

/**
 * Created by Terence Baker on 11/05/2016.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_activity_actions, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as? SearchView

        if (searchView != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

            searchView.queryTextChanges().subscribe { text -> Log.d("blah", "stuff: " + text) }
        }

        return super.onCreateOptionsMenu(menu)
    }
}
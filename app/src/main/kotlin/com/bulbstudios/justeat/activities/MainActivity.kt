package com.bulbstudios.justeat.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import com.bulbstudios.justeat.R
import com.bulbstudios.justeat.dataclasses.Resturant
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerViewAdapter
import com.jakewharton.rxbinding.support.v7.widget.queryTextChanges
import kotlinx.android.synthetic.main.activity_main.*
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

/**
 * Created by Terence Baker on 11/05/2016.
 */
class MainActivity : AppCompatActivity() {

    private val subscriptionList = CompositeSubscription()
    private val resturantDataSource = RxDataSource<Resturant>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_activity_actions, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchItem) as? SearchView

        if (searchView != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            subscribeSearchChange(searchView)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {

        super.onDestroy()
        subscriptionList.clear()
    }

    private fun clearSearch() {

    }

    private fun searchWithPostcode(text: String) {

    }


    private fun bindArrayToRecyclerView() {

        RxRecyclerViewAdapter.dataChanges(resturantDataSource)
        recyclerView.
    }

    private fun subscribeSearchChange(searchView: SearchView) {

        subscriptionList.add(searchView.queryTextChanges()
                                     .throttleWithTimeout(400, TimeUnit.MILLISECONDS)
                                     .distinctUntilChanged()
                                     .map { postcode ->

                                         postcode.toString()
                                     }
                                     .subscribe { text ->

                                         if (text.length == 0) clearSearch() else searchWithPostcode(text)
                                     })
    }
}

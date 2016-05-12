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
import com.bulbstudios.justeat.services.JustEatRetrofit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding.support.v7.widget.queryTextChanges
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Terence Baker on 11/05/2016.
 */
class MainActivity : AppCompatActivity() {

    private val subscriptionList = CompositeSubscription()

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

    private fun setResturantArray(restaurants: List<Resturant>) {

    }

    private fun clearSearch() {

        setResturantArray(ArrayList<Resturant>())
    }

    private fun searchWithPostcode(text: String) {

        subscriptionList.add(JustEatRetrofit.resturantService
                                     .getResturantsWithPostcode(text)
                                     .map {
                                         json ->

                                         val resturantJson = json.getAsJsonArray(JustEatRetrofit.JSON_RESTAURANTS).toString()
                                         val listType = object : TypeToken<List<Resturant>>() {}.type

                                         Gson().fromJson<List<Resturant>>(resturantJson, listType)
                                     }
                                     .subscribeOn(Schedulers.newThread())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe({
                                                    restaurants ->

                                                    setResturantArray(restaurants)
                                                },
                                                {
                                                    error ->

                                                    Log.e(MainActivity::class.java.simpleName, "Error retrieving restaurants: $error")
                                                }))
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


package com.natodriod.kotlin.rssreader

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.natodriod.kotlin.rssreader.Adapter.FeedAdapter
import com.natodriod.kotlin.rssreader.Common.HTTPDataHandler
import com.natodriod.kotlin.rssreader.model.RSSObject
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    private  val RSS_link = "http://www.qadinklubu.com/feed/"
    private  val RSS_TO_JSON = "https://api.rss2json.com/v1/api.json?rss_url="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Qadın Klubu RSS"
        setSupportActionBar(toolbar)

        val llm = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = llm

        loadRSS()

    }

    private fun loadRSS() {
        val loadRSSAsync = object:AsyncTask<String, String, String>(){
            internal  var mDialog = ProgressDialog(this@MainActivity)

            override fun doInBackground(vararg params: String?): String {
                val result: String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(params[0])
                return result
            }

            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                val rssObject: RSSObject
                rssObject = Gson().fromJson<RSSObject>(result, RSSObject::class.java!!)

                val adapter = FeedAdapter(rssObject, baseContext)
                recycler.adapter = adapter;
                adapter.notifyDataSetChanged()
            }

            override fun onPreExecute() {
                mDialog.setMessage("Loading....")
                mDialog.show()

            }
        }

        val urlGetData = StringBuilder(RSS_TO_JSON).append(RSS_link)

        loadRSSAsync.execute(urlGetData.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menu_load)
            loadRSS()
        return true
    }
}

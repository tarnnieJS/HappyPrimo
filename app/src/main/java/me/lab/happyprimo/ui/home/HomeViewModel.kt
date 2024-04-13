package me.lab.happyprimo.ui.home

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.lab.happyprimo.data.network.ServiceClient
import retrofit2.HttpException
import okhttp3.ResponseBody
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import me.lab.happyprimo.data.local.dao.DatabaseHelper
import me.lab.happyprimo.data.models.FeedData
import me.lab.happyprimo.data.models.FeedResponseModel
import me.lab.happyprimo.data.repositories.DatabaseRepository
import me.lab.happyprimo.utils.xmlToJson


import retrofit2.Response


class HomeViewModel(
    private val applicationContext: Context,
    private val lifecycleScope: LifecycleCoroutineScope
) : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val dbHelper = DatabaseHelper(applicationContext)
    private val repository = DatabaseRepository(dbHelper)

    val meduimProfileUrl = MutableLiveData<String?>()
    val showProgressbar = MutableLiveData<Boolean>()
    val feedData = MutableLiveData<List<FeedResponseModel>>()

    fun getDataFeed() {
        val url = "https://medium.com/"

        showProgressbar.value = true
        MainScope().launch {
            try {
                val result = async(Dispatchers.IO) {
                    ServiceClient.createForXML(url).getFeed()
                }
                val response = result.await()
                if (response.isSuccessful) {
                    showProgressbar.value = false
                    val responseBody: Response<ResponseBody> = response
                    val xmlString = responseBody.body()?.source()?.readUtf8()
                    val json = xmlToJson(xmlString.orEmpty())

                    val responseJsonBody =
                        Gson().fromJson(json.toString(), FeedResponseModel::class.java)
                    handleDataFeed(responseJsonBody)
                } else {
                    showProgressbar.value = false
                    Log.e(TAG, "Response not successful: ${response.code()}")
                }
            } catch (ex: Exception) {
                handleException(ex)
            }
        }
    }

    private fun handleDataFeed(responseJsonBody: FeedResponseModel) {
        val feedDataLocal = repository.getFeedData()
        if (feedDataLocal == null) {
            Toast.makeText(applicationContext, "Create", Toast.LENGTH_SHORT).show()
            if (saveFeedDataToDatabase(FeedData(1, Gson().toJson(responseJsonBody)))) {
                getFeedDataFromDatabase()
            }
        } else {
            val localData = Gson().fromJson(feedDataLocal.feedData, FeedResponseModel::class.java)
            if (localData.item?.size != responseJsonBody.item?.size) {
                Toast.makeText(applicationContext, "Update", Toast.LENGTH_SHORT).show()
                if (saveFeedDataToDatabase(FeedData(1, Gson().toJson(responseJsonBody)))) {
                    getFeedDataFromDatabase()
                }
            } else {
//                Toast.makeText(applicationContext, "No update", Toast.LENGTH_SHORT).show()
                getFeedDataFromDatabase()
            }
        }
    }

    private fun handleException(ex: Exception) {
        showProgressbar.value = false
        Log.e("Exception", "Error: ${ex.message}", ex)
        if (ex is HttpException) {
            Log.e("HttpException", "(${ex.code()}): $ex")
        }
    }

    private fun getFeedDataFromDatabase() {
        val feedDataLocal = repository.getFeedData()
        feedDataLocal?.let {
            val responseJsonBody = Gson().fromJson(it.feedData, FeedResponseModel::class.java)
            feedData.value = listOf(responseJsonBody)
            meduimProfileUrl.value =
                responseJsonBody.rss?.channel!!.title!!.description!!.link!!.image!!.url!!.urlData
            _text.value = responseJsonBody.rss.channel.title!!.titleData.toString()
        }
    }

    private fun saveFeedDataToDatabase(feedData: FeedData): Boolean {
        return repository.saveFeedData(feedData)
    }
}


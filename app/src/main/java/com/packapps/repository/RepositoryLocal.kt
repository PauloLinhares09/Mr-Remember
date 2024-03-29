package com.packapps.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import com.packapps.repository.database.AppDatabase
import com.packapps.repository.entity.ItemAudio
import io.reactivex.Observable

class RepositoryLocal(val appDatabase: AppDatabase) {

    val listAllItemsAudioLiveData : LiveData<List<ItemAudio>>

    init {
        val itemAudioDao = appDatabase.itemAudioDao()
        listAllItemsAudioLiveData = itemAudioDao.selectAll()
    }

    fun getAudioUnit(packageName : String) : Observable<String>{

        return Observable.create { sub ->
            sub.onNext("android.resource://" + packageName + "/raw/file_example")
            sub.onComplete()
        }
    }

    fun getFilesFromUserDevice() : Intent{

        return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/*"
        }
    }


    fun getAllItemsAudioFromRoom() : LiveData<List<ItemAudio>>{
        return listAllItemsAudioLiveData
    }

    fun saveItemsAudioFromRoom(itemAudio : ItemAudio) : Long{
        val itemAudioDao = appDatabase.itemAudioDao()
        return itemAudioDao.insertItemAudio(itemAudio)
    }

    fun deleteAll() {
        appDatabase.itemAudioDao().deleteAll()
    }

}
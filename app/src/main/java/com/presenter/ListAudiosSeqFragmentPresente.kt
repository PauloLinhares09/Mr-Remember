package com.presenter

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.adapters.FragmentListAudiosSeqAdapter
import com.di.AdaptersContract

class ListAudiosSeqFragmentPresente(private val adapter : FragmentListAudiosSeqAdapter, private val adaptersContract: AdaptersContract, private val snapHelper : SnapHelper) {

    private lateinit var context: Context
    private lateinit var activity: Activity

    fun adapter() = adapter

    fun adapterMain() = adaptersContract.adapterListAudios(adapter)
    fun layoutManager() = adaptersContract.layoutManager(activity)
    fun snapHelper() = adaptersContract.snapHelper(snapHelper)

    fun setContexActivity(activity: Activity){
        this.activity = activity
    }



}
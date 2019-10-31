package com.packapps.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.packapps.R
import kotlinx.android.synthetic.main.fragment_configuration_playing.*
import kotlinx.android.synthetic.main.fragment_configuration_playing.view.*

class ConfigurationPlayingFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_configuration_playing, container, false)

        mView.btCancel.setOnClickListener {
            dismiss()
        }
        mView.btApply.setOnClickListener {
            dismiss()
        }

        //how much times
        mView.bt1x.isActivated = true
        mView.bt1x.setOnClickListener {
            mView.bt1x.isActivated = !bt1x.isActivated
            mView.bt2x.isActivated = false
        }
        mView.bt2x.setOnClickListener {
            mView.bt1x.isActivated = false
            mView.bt2x.isActivated = !bt2x.isActivated
        }

        return mView
    }

}
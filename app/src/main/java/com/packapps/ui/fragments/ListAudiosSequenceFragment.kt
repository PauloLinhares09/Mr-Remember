package com.packapps.ui.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.packapps.dto.ItemAudio
import com.packapps.R
import com.packapps.audio_core.MediaPlayerApp
import com.packapps.presenter.ListAudiosSeqFragmentPresente
import com.packapps.utils.LogApp
import com.packapps.viewmodel.ListAudioSeqFragmentViewModel
import kotlinx.android.synthetic.main.fragment_list_audio_seq.view.*
import org.koin.android.ext.android.inject


class ListAudiosSequenceFragment : Fragment() {


    private var itemAudioPlayingCurrent: ItemAudio? = null
    lateinit var viewModel : ListAudioSeqFragmentViewModel
    lateinit var mediaPlayerApp : MediaPlayerApp
    lateinit var mediaPlayerState : MediaPlayerApp.MediaPlayerAppState


    val presenter : ListAudiosSeqFragmentPresente by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            presenter.setContexActivity(it)
        }

        viewModel = ViewModelProvider(this).get(ListAudioSeqFragmentViewModel::class.java)
        viewModel.repository = presenter.repository //Refactory it
        viewModel.composite = presenter.composite //Refactory it

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_list_audio_seq, container, false)

        //Bind view
        bindAdapterMain(mView)

        //Data from Repository
        observerDataFromRepository()

        //Listen events from card adapter
        listenClickFromAdapter()


        return mView
    }

    private fun observerDataFromRepository() {
        viewModel.pathAudioUnit.observe(viewLifecycleOwner, Observer { path ->
            Toast.makeText(context, "Path: $path", Toast.LENGTH_LONG).show()
            mediaPlayerApp = MediaPlayerApp()
            mediaPlayerApp.context = context!!
            //getState
            mediaPlayerApp.getSubjectState().subscribe { state ->
                mediaPlayerState = state
                //Change state view feedback
                LogApp.i("FRAGMENT", "Media Player state: $state")
                itemAudioPlayingCurrent?.let {
                    it.stateMediaPlayer = state
                    presenter.adapter().updateJustItemOnPosition(it)
                }

            }

            //Load media and play
            mediaPlayerApp.loadMedia(path)

        })
    }

    private fun listenClickFromAdapter() {
        val disposable = presenter.adapter().getSubjectClick().subscribe { itemAudio ->
            context?.let {
                //Check if has other
                itemAudioPlayingCurrent?.let {
                    if (itemAudioPlayingCurrent?.id != itemAudio.id) {
                        //clear item adapter
                        mediaPlayerApp.stop()
                        mediaPlayerApp.releasePlayer()

                        //Stop item current old
                        itemAudioPlayingCurrent?.let { itemAudioOld ->
                            itemAudioPlayingCurrent?.stateMediaPlayer = null
                            presenter.adapter().updateJustItemOnPosition(itemAudioOld)
                        }
                    }
                }
                itemAudioPlayingCurrent = itemAudio
                //Continue to next itemAudio for to play
                Handler().postDelayed({
                    //Para dar tempo de atualizar o card anterior
                    //Listen click button play/pause
                    if (itemAudio.stateMediaPlayer == null) {//action play first time
                        viewModel.getAudioUni(it.packageName)
                    } else if (itemAudio.stateMediaPlayer == MediaPlayerApp.MediaPlayerAppState.PLAYING) {//Action pause
                        mediaPlayerApp.pause()
                    } else if (itemAudio.stateMediaPlayer == MediaPlayerApp.MediaPlayerAppState.PAUSED) {
                        mediaPlayerApp.play()
                    }
                }, 500)

            }
        }
    }


    override fun onStop() {
        super.onStop()
        mediaPlayerApp?.stop()
        mediaPlayerApp?.releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerApp?.stop()
        mediaPlayerApp?.releasePlayer()
    }


    private fun bindAdapterMain(mView : View) {
        //create item empty
        val itemAudio = ItemAudio(1, "My First List", "Audio aula 1", "")
        val itemAudio2 = ItemAudio(2, "My First List", "Audio aula 1", "")
        val itemAudio3 = ItemAudio(3, "My First List", "Audio aula 1", "")

        val list = mutableListOf<ItemAudio>()
        list.add(itemAudio)
        list.add(itemAudio2)
        list.add(itemAudio3)

        mView.rvItemsAudioSeq.layoutManager = presenter.layoutManager()
        mView.rvItemsAudioSeq.adapter = presenter.adapterMain()
        presenter.adapterMain().updateList(list)

    }

}

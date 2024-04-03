package com.example.dogs.view

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.dogs.R
import com.example.dogs.databinding.FragmentDetailBinding
import com.example.dogs.databinding.ItemDogBinding
import com.example.dogs.databinding.SendSmsDialogBinding
import com.example.dogs.model.DogBreed
import com.example.dogs.model.DogPalette
import com.example.dogs.model.SmsInfo
import com.example.dogs.util.getProgessDrawable
import com.example.dogs.util.loadImage
import com.example.dogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.NonDisposableHandle.parent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding
    private var dogUuid = 0
    private var sendSmsStarted = false
    private var currentDog: DogBreed? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //to get args passed to this fragment via bundle or otherwise?
//        var id = arguments?.getInt("dogUuid")

        //or you can also do it like this
        //        buttonList.setOnClickListener {
//            val action = DetailFragmentDirections.actionListFragment()
//            Navigation.findNavController(it).navigate(action)
//        }

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
//            textView2.text = dogUuid.toString()
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(viewLifecycleOwner, Observer { dog ->
            currentDog = dog
            dog?.let {
//                dogName.text = dog.dogBreed
//                dogPurpose.text = dog.bredFor
//                dogTemperament.text = dog.temperament
//                dogLifespan.text = dog.lifespan
//                context?. let{dogImage.loadImage(dog.imageUrl, getProgessDrawable(it))}
                dataBinding.dog = dog

                dog.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            val myPalette = DogPalette(intColor)
                            dataBinding.palette = myPalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(
            R.menu.detail_menu,
            menu
        ) //our inflated menu layout attached to this menu variable
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send_sms -> {
                sendSmsStarted = true
                (activity as MainActivity).checkSmsPermission() //only activity can request permission, not fragment

            }

            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)//ACTION_SEND is generic flag for when an app wants to send some info to other apps
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "check out this dog breed")
                intent.putExtra(Intent.EXTRA_TEXT, "${currentDog?.dogBreed} bred for ${currentDog?.bredFor}")
                intent.putExtra(Intent.EXTRA_STREAM, currentDog?.imageUrl)
                startActivity(Intent.createChooser(intent, "Share with"))//chooser lets the user choose which app should handle the sharing
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun  onPermissionResult(permissionGranted: Boolean){
        if(sendSmsStarted && permissionGranted){
            context?.let{
                val smsInfo = SmsInfo("", "${currentDog?.dogBreed} bred for ${currentDog?.bredFor}", currentDog?.imageUrl)
                val dialogBinding = DataBindingUtil.inflate<SendSmsDialogBinding>(LayoutInflater.from(it), R.layout.send_sms_dialog, null, false)

                AlertDialog.Builder(it)
                    .setView(dialogBinding.root)
                    .setPositiveButton("Send SMS"){ dialog, which ->
                        if(!dialogBinding.smsDestination.text.isNullOrEmpty()){
                            smsInfo.to = dialogBinding.smsDestination.text.toString()
                            sendSms(smsInfo)
                        }
                    }
                    .setNegativeButton("Cancel"){dialog, which ->

                    }
                    .show()

                //to attach the smsInfo var from the sms dialog layout to this sms info
                dialogBinding.smsInfo = smsInfo
            }
        }
    }

    /**
     * will actually send an sms on an actual device
     */
    private fun sendSms(smsInfo: SmsInfo){
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(smsInfo.to, null, smsInfo.text, pendingIntent, null)
    }

    companion object {

    }
}
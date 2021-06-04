package com.yura.newaws.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.yura.newaws.AppPreferences
import com.yura.newaws.R


const val RC_SIGN_IN = 1

class ProfileFragmentList : Fragment() {
    lateinit var mGoogleSignInClient:GoogleSignInClient
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (AppPreferences.Sub==""){
            if (account==null){
                signIn()
            }
            else{
                AppPreferences.Sub = account.id
            }
        }


    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.result!!

            // Signed in successfully, show authenticated UI.
            AppPreferences.Sub=account.id
            Log.i("MyAmplifyApp", "handleSignInResult: ${AppPreferences.Sub} ")
           // updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.w("MyAmplifyApp", "signInResult:failed code=" + e.getStatusCode())
            findNavController().navigateUp()
           // updateUI(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_list_list, container, false)
        profileViewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(ProfileViewModel::class.java)
        var layManager = LinearLayoutManager(context)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.getContext(),
            layManager.orientation
        )
        val addButton = view.findViewById<Button>(R.id.btn_addprofile)
        val myadapter = MyProfileRecyclerViewAdapter{ item ->
            val bundle = bundleOf("profile_id" to item.id)
            findNavController().navigate(
                R.id.action_profileFragmentList_to_detailsFragment,
                bundle
            )
        }

        with(recyclerView) {
            layoutManager = layManager
            adapter = myadapter
            addItemDecoration(dividerItemDecoration)
        }



        profileViewModel.lst.observe(viewLifecycleOwner, {
            myadapter.submitList(profileViewModel.newlist)
        })



        addButton.setOnClickListener {
        findNavController().navigate(R.id.action_profileFragmentList_to_addProfile)
        }



        return view
    }


}




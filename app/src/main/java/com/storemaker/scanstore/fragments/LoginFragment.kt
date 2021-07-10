package com.storemaker.scanstore.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.storemaker.scanstore.ClientActivity
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.HomeActivity
import com.storemaker.scanstore.R
import com.storemaker.scanstore.viewmodels.LoginViewModel
import java.lang.Exception


class LoginFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var loginViewModel: LoginViewModel

    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.userFromSingleQuery.observe(this,{

            if(it!=null){

               if(it.role=="admin"){
                   val myIntent = Intent(requireActivity(),HomeActivity::class.java)
                   myIntent.putExtra("email",it.email)
                   Constants.currentUser = it
                   startActivity(myIntent)
               }else{
                   val myIntent = Intent(requireActivity(),ClientActivity::class.java)
                   myIntent.putExtra("email",it.email)
                   Constants.currentUser = it
                   startActivity(myIntent)
               }
            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        Constants.signInClient = mGoogleSignInClient

        val signInButton: SignInButton = requireView().findViewById(R.id.sign_in_button)
        signInButton.apply {
            // Set the dimensions of the sign-in button.
            setSize(SignInButton.SIZE_STANDARD)

            setOnClickListener { signIn() }

        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
            loginViewModel.retrieveUser(account!!.email!!,account!!.givenName!!)



        }catch (e:Exception){
            e.printStackTrace()
            Log.e("Coso",e.message!!.toString())
            e.printStackTrace()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // The Task returned from this call is always completed, no need to attach
                // a listener.

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } catch (e: ApiException) {
                //handle error
                Log.e("errorsito",e.message!!)
            }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            loginViewModel.retrieveUser(account!!.email!!,account!!.givenName!!)


        } catch (e: Exception) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("errorsito", "signInResult:failed code=" + e.message)
//            updateUI(null)
        }
    }



}
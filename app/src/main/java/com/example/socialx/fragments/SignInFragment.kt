package com.example.socialx.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialx.R
import com.example.socialx.activity.HomeActivity
import com.example.socialx.activity.MainActivity
import com.example.socialx.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInFragment : Fragment() {
    var binding:FragmentSignInBinding?=null
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

//        private companion object{
//        private const val RC_SIGN_IN=100
//        private const val TAG="GOOGLE_SIGN_IN_TAG"
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSignInBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        val googleSignInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestId()
            .build()
        googleSignInClient= GoogleSignIn.getClient(requireActivity(),googleSignInOptions)
        binding?.googleLoginBtn?.setOnClickListener {
            signInGoogle()
        }
        binding?.signInBtn?.setOnClickListener {
                authentication()
        }
        return binding?.root
    }
    private fun signInGoogle() {
        val signInIntent=googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private  val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
                if(result.resultCode== Activity.RESULT_OK){
                    val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResults(task)
                }
    }
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account:GoogleSignInAccount?= task.result
            if(account!=null){
                updateUI(account)
            }
        }else{
            Toast.makeText(context,task.exception.toString(),Toast.LENGTH_LONG).show()
        }
    }
    private fun updateUI(account: GoogleSignInAccount) {
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                var intent=Intent(context, HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(context,it.exception.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun authentication() {
        var email: String? =binding?.editEmailSignIN?.text?.toString()
        var password: String? =binding?.editPassSignIn?.text?.toString()
        binding?.progressBar?.visibility=View.VISIBLE
        if(email==""){
            binding?.progressBar?.visibility=View.INVISIBLE
            Toast.makeText(context,"Please enter a email", Toast.LENGTH_LONG).show()
        }else if(password==""){
            binding?.progressBar?.visibility=View.INVISIBLE
            Toast.makeText(context,"Please enter a password", Toast.LENGTH_LONG).show()
        }else{
            if (password != null&&email!=null) {
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    binding?.progressBar?.visibility=View.INVISIBLE
                    if(task.isSuccessful){
                        var intent=Intent(context, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(context,"Invalid Email or Password", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if(auth.currentUser!=null){
//            var intent=Intent(context, HomeActivity::class.java)
//            startActivity(intent)
//        }
//        super.onStart()
//    }

}
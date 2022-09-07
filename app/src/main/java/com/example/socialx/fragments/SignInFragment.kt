package com.example.socialx.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.socialx.HomeActivity
import com.example.socialx.R
import com.example.socialx.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    var binding:FragmentSignInBinding?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSignInBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        binding?.signInBtn?.setOnClickListener {
                authentication()
        }
        return binding?.root
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
                        var intent=Intent(context,HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(context,"Invalid Email or Password", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}
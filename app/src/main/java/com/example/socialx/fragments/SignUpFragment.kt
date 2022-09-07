package com.example.socialx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.socialx.databinding.FragmentSignUpBinding
import com.example.socialx.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {
    var binding:FragmentSignUpBinding?=null
    private lateinit var auth: FirebaseAuth
    private var firebaseUserId:String?=null
    private var database = Firebase.database
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignUpBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        binding?.signUpBtn?.setOnClickListener{
            signup()
        }
        return binding?.root
    }

    private fun signup() {
        binding?.progressBar?.visibility=View.VISIBLE
        val name:String?=binding?.editNameSignUp?.text?.toString()
        val email:String?=binding?.editEmailSignUp?.text?.toString()
        val password:String?=binding?.editPassSignUp?.text?.toString()
        val phoneNumber:String?=binding?.editNumberSignUp?.text.toString()
        if(name==""){
            binding?.progressBar?.visibility=View.INVISIBLE
            Toast.makeText(context,"Please enter a username", Toast.LENGTH_LONG).show()
        }else if(email==""){
            binding?.progressBar?.visibility=View.INVISIBLE
            Toast.makeText(context,"Please enter a email", Toast.LENGTH_LONG).show()
        }else if(password==""){
            binding?.progressBar?.visibility=View.INVISIBLE
            Toast.makeText(context,"Please enter a password", Toast.LENGTH_LONG).show()
        }else if(phoneNumber==""){
            binding?.progressBar?.visibility=View.INVISIBLE
            Toast.makeText(context,"Please enter a phone number", Toast.LENGTH_LONG).show()
        }else{
            if (password != null&&email!=null) {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        binding?.progressBar?.visibility=View.INVISIBLE
                        if(task.isSuccessful){
                            var user= User(name!!,email,password,phoneNumber!!)
                            var id:String=task.result.user?.uid.toString()
                            database.getReference().child("Users").child(id).setValue(user)
                            firebaseUserId=auth.currentUser?.uid
                            Toast.makeText(context,"Registered Successfully", Toast.LENGTH_LONG).show()
                        } else{
                            Toast.makeText(context,"Error Message: "+task.exception?.message.toString(),
                                Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

}
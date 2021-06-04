package com.yura.newaws.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.Profile
import com.yura.newaws.AppPreferences
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex



class ProfileViewModel : ViewModel() {
    val lock = Mutex()
    var lst = MutableLiveData<ArrayList<Profile>>()
    var newlist = arrayListOf<Profile>()
    var profile: Profile?=null


    init {

        query()
       viewModelScope.launch {

       Amplify.DataStore.observe(Profile::class.java,
           { Log.i("MyAmplifyApp", "Observation began") },
           {
              query()
           },
           { Log.e("MyAmplifyApp", "Observation failed", it) },
           { Log.i("MyAmplifyApp", "Observation complete") }
       )
       }
   }


    fun delete(id:String){

        Amplify.DataStore.query(
            Profile::class.java, Where.id(id),
            { matches ->
                if (matches.hasNext()) {
                    val profile = matches.next()
                    Amplify.DataStore.delete(profile,
                        { Log.i("MyAmplifyApp", "Deleted a post.") },
                        { Log.e("MyAmplifyApp", "Delete failed.", it) }
                    )
                }
            },
            { Log.e("MyAmplifyApp", "Query failed.", it) }
        )
    }
    fun update(profile: Profile){
        Amplify.DataStore.query(
            Profile::class.java, Where.id(profile.id),
            { matches ->
                if (matches.hasNext()) {
                    val original = matches.next()
                    val edited = original.copyOfBuilder()
                        .make(profile.make)
                        .model(profile.model)
                        .year(profile.year)
                        .build()
                    Amplify.DataStore.save(edited,
                        { Log.i("MyAmplifyApp", "Updated a post") },
                        { Log.e("MyAmplifyApp", "Update failed", it) }
                    )
                }
            },
            { Log.e("MyAmplifyApp", "Query failed", it) }
        )

    }
    fun create(profile: Profile){

        Amplify.DataStore.save(profile,
            { Log.i("MyAmplifyApp", "Created a new post successfully") },
            { Log.e("MyAmplifyApp", "Error creating post", it) }
        )
    }
    fun query(){
        Amplify.DataStore.query(
            Profile::class.java,Where.matches(Profile.SUB.contains(AppPreferences.Sub)),
            { matches ->
                newlist.clear()
                while (matches.hasNext()) {
                    newlist.add(matches.next())
                    Log.i("MyAmplifyApp", "query ")
                }
                lst.postValue(newlist)
            },
            { Log.e("MyAmplifyApp",  "Error retrieving posts", it) }
        )

    }
    fun query(id: String){
        Amplify.DataStore.query(
            Profile::class.java,Where.matches(Profile.SUB.contains(AppPreferences.Sub).and(Profile.ID.contains(id))),
            { matches ->
                if (matches.hasNext()) {
                   profile = matches.next()
                }
                lst.postValue(newlist)
            },
            { Log.e("MyAmplifyApp",  "Error retrieving posts", it) }
        )

    }

}
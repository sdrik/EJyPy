package fr.gabriello.ejypy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        updateConnection()

        calendarView.addDecorators(
            SelectionDecorator(this)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_signin).setVisible(!mSignedIn)
        menu.findItem(R.id.action_signout).setVisible(mSignedIn)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_signin -> {
                val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG, true)
                        .setAvailableProviders(providers)
                        .build(),
                    RC_SIGN_IN
                )
                true
            }
            R.id.action_signout -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener { updateConnection() }
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                updateConnection()
            } else {
                if (response != null) {
                    if (response.error != null) {
                        Toast.makeText(applicationContext, response.error.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun updateConnection() {
        val user = FirebaseAuth.getInstance().currentUser
        mSignedIn = when (user) {
            null -> {
                Toast.makeText(applicationContext, "Signed out", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                Toast.makeText(applicationContext, user.email, Toast.LENGTH_LONG).show()
                true
            }
        }
        invalidateOptionsMenu()
    }

    companion object {
        private const val RC_SIGN_IN = 42
    }

    private var mSignedIn = false
}

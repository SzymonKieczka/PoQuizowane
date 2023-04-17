import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.poquizowane.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun signIn(context: Context, email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            showMessage(context, "Email and password must not be empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showMessage(context, "Sign in successful")
                    navigateToHomeActivity(context)
                } else {
                    showMessage(context, "Sign in failed: ${task.exception?.localizedMessage}")
                }
            }
    }

    fun signUp(context: Context, email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            showMessage(context, "Email and password must not be empty")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showMessage(context, "Sign up successful")
                    navigateToHomeActivity(context)
                } else {
                    showMessage(context, "Sign up failed: ${task.exception?.localizedMessage}")
                }
            }
    }

    fun navigateToHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }

    private fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

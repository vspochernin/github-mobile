package company.vk.polis.github_mobile

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import company.vk.polis.github_mobile.database.AppDatabase
import company.vk.polis.github_mobile.databinding.ActivityMainBinding
import company.vk.polis.github_mobile.model.Repository
import company.vk.polis.github_mobile.remote.ApiClient
import company.vk.polis.github_mobile.remote.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val db = AppDatabase.getDatabase(applicationContext)
        val repositoryDao = db.repositoryDao()
        val gitHubRepository = GitHubRepository(ApiClient.gitHubApi, repositoryDao)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                //gitHubRepository.getRepositories()
                val repos: List<Repository> = gitHubRepository.getAllRepositories()
                for (rep in repos) {
                    Log.e("info", rep.toString())
                }
            }
        }
    }
}
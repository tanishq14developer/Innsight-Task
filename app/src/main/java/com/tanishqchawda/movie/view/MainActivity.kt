package com.tanishqchawda.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.tanishqchawda.movie.R
import com.tanishqchawda.movie.adapter.BeerAdapter
import com.tanishqchawda.movie.adapter.LoaderAdapter
import com.tanishqchawda.movie.databinding.ActivityMainBinding
import com.tanishqchawda.movie.utils.Utils
import com.tanishqchawda.movie.viewModel.BeerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val beerViewModel: BeerViewModel by viewModels()
    lateinit var beerAdapter: BeerAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            beerAdapter = BeerAdapter(this@MainActivity) {
                Utils.shareOnWhatsapp(this@MainActivity, "${it.title}", "${it.type}", it.poster)

            }
            lifecycleScope.launch {
                beerViewModel.beerLiveData.observe(this@MainActivity) {

                    val loaderAdapter = LoaderAdapter {
                        beerAdapter.retry()
                    }
                    beerAdapter.addLoadStateListener { loadState ->
                        val isRefreshing = loadState.refresh is LoadState.Loading
                        val isNotLoading = loadState.refresh !is LoadState.Loading
                        loader.isVisible = isRefreshing && beerAdapter.itemCount == 0
                        // Show the load state adapter when there's an error or when the load is in progress (but not for the initial load).
                        loaderAdapter.loadState = loadState.prepend
                        rvBeerList.setPadding(
                            0,
                            0,
                            0,
                            if (isNotLoading) 0 else resources.getDimensionPixelSize(R.dimen.dp_5)
                        )
                    }
                    rvBeerList.adapter = beerAdapter.withLoadStateFooter(loaderAdapter)
                    beerAdapter.submitData(lifecycle, it)
                }

            }

        }
    }


    override fun onResume() {
        super.onResume()
    }
}
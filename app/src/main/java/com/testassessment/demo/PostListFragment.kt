package com.testassessment.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testassessment.demo.databinding.PostListScreenBinding

class PostListFragment : Fragment() {
    private lateinit var binding: PostListScreenBinding
    private lateinit var adapter: PostAdapter
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PostListScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PostAdapter {
            (activity as MainActivity).navController.navigate(
                PostListFragmentDirections.actionPostListFragmentToPostDetailsFragment(it)
            )
        }
        binding.recyclerView.adapter = adapter

        /*viewModel = ViewModelProvider(this).get(MainViewModel::class.java)*/

        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        // Pagination logic: Load more items when user scrolls to the end of the list
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchNextPage()
                }
            }
        })
    }
}